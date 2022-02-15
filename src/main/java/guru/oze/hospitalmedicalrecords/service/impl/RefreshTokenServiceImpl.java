package guru.oze.hospitalmedicalrecords.service.impl;

import guru.oze.hospitalmedicalrecords.entity.RefreshToken;
import guru.oze.hospitalmedicalrecords.repository.RefreshTokenRepository;
import guru.oze.hospitalmedicalrecords.repository.UserRepository;
import guru.oze.hospitalmedicalrecords.security.exception.TokenRefreshExpiredException;
import guru.oze.hospitalmedicalrecords.security.jwt.JWTUtils;
import guru.oze.hospitalmedicalrecords.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<RefreshToken> createRefreshToken(Integer userId) {
	    RefreshToken refreshToken = null;

	    var optionalUser = userRepository.findById(userId);

	    if (optionalUser.isPresent()) {
		    refreshToken = new RefreshToken();
		    refreshToken.setUser(optionalUser.get());
		    refreshToken.setExpiryDate(Instant.now().plusMillis(jwtUtils.getRefreshExpiration()));
		    refreshToken.setToken(UUID.randomUUID().toString());

		    refreshToken = refreshTokenRepository.save(refreshToken);
	    }

	    return Optional.ofNullable(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshExpiredException(refreshToken.getToken(), "Refresh token was expired. Please make a new sign in request");
        }

        return refreshToken;
    }
}
