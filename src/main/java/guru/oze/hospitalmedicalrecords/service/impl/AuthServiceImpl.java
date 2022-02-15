package guru.oze.hospitalmedicalrecords.service.impl;

import guru.oze.hospitalmedicalrecords.exception.GenericException;
import guru.oze.hospitalmedicalrecords.security.jwt.JWTUtils;
import guru.oze.hospitalmedicalrecords.security.jwt.UserDetailsImpl;
import guru.oze.hospitalmedicalrecords.service.AuthService;
import guru.oze.hospitalmedicalrecords.service.UserService;
import guru.oze.hospitalmedicalrecords.service.dto.ApiResponse;
import guru.oze.hospitalmedicalrecords.service.dto.Jwt;
import guru.oze.hospitalmedicalrecords.service.dto.LoginDetails;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTUtils jwtUtils;

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

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(authentication);
            List<String> authorities = loginUser.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            Jwt newJwt = new Jwt(jwt, loginUser.getId(), loginUser.getUsername(), authorities);

            return DtoTransformer.buildApiResponse("Login successful", newJwt);

        } else {
            throw new BadCredentialsException("Invalid Login Details");
        }
    }

    @Override
    public ApiResponse register(StaffInfo staffInfo) {
        if (userService.existsByUsername(staffInfo.getUsername())){
            throw new GenericException("Error: Username taken. Please input another username");
        }
       return userService.registerStaff(staffInfo);
    }
}
