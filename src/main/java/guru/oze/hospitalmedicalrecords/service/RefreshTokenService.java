package guru.oze.hospitalmedicalrecords.service;

import guru.oze.hospitalmedicalrecords.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    Optional<RefreshToken> createRefreshToken(Integer id);

    Optional<RefreshToken> findByToken(String refreshToken);

    RefreshToken verifyExpiration(RefreshToken refreshToken);
}
