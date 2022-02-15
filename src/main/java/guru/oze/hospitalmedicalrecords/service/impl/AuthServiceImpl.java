package guru.oze.hospitalmedicalrecords.service.impl;

import guru.oze.hospitalmedicalrecords.config.RSAKeyConfigProperties;
import guru.oze.hospitalmedicalrecords.entity.RefreshToken;
import guru.oze.hospitalmedicalrecords.security.exception.TokenRefreshExpiredException;
import guru.oze.hospitalmedicalrecords.security.jwt.JWTUtils;
import guru.oze.hospitalmedicalrecords.security.jwt.UserDetailsImpl;
import guru.oze.hospitalmedicalrecords.service.AuthService;
import guru.oze.hospitalmedicalrecords.service.RefreshTokenService;
import guru.oze.hospitalmedicalrecords.service.UserService;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.Jwt;
import guru.oze.hospitalmedicalrecords.service.dto.LoginDetails;
import guru.oze.hospitalmedicalrecords.service.dto.RefreshTokenRequest;
import guru.oze.hospitalmedicalrecords.service.dto.RefreshTokenResponse;
import guru.oze.hospitalmedicalrecords.service.dto.StaffInfo;
import guru.oze.hospitalmedicalrecords.utils.DtoTransformer;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.List;
import java.util.stream.Collectors;

import static guru.oze.hospitalmedicalrecords.security.jwt.UserDetailsImpl.build;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final JWTUtils jwtUtils;
    private final RSAKeyConfigProperties rsaKeyProp;

    public ApiResponse authenticate(LoginDetails loginDetails) throws Exception {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDetails.getUsername(), loginDetails.getPassword()));
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new BadCredentialsException("Bad Credentials");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("User authentication failed");
        }

        // Generating Token
        PrivateKey privateKey = rsaKeyProp.getPrivateKey();

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtils.generateToken(loginUser, privateKey);

            List<String> authorities = loginUser.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            var optionalRefreshToken = refreshTokenService.createRefreshToken(loginUser.getId());

            String refreshToken = optionalRefreshToken.map(RefreshToken::getToken).orElse("");

            Jwt newJwt = new Jwt(jwt, refreshToken, loginUser.getId(), loginUser.getUsername(), authorities);

            return DtoTransformer.buildApiResponse("Login successful", newJwt);

        } else {
            throw new BadCredentialsException("Invalid Login Details");
        }
    }

    public ApiResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String requestRefreshToken = refreshTokenRequest.getRefreshToken();

        RefreshTokenResponse refreshTokenResponse = refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration).map(RefreshToken::getUser)
                .map(user -> {
                    String token = null;
                    try {
                        token = jwtUtils.generateToken(build(user), rsaKeyProp.getPrivateKey());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return new RefreshTokenResponse(token, requestRefreshToken);
                })
                .orElseThrow(() -> new TokenRefreshExpiredException(requestRefreshToken,
                        "Error: Invalid refresh token!"));
        return DtoTransformer.buildApiResponse(refreshTokenResponse);
    }

    @Override
    public ApiResponse register(StaffInfo staffInfo) {
       return userService.registerStaff(staffInfo);
    }
}
