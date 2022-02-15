package guru.oze.hospitalmedicalrecords.utils;

import guru.oze.hospitalmedicalrecords.exception.FailedDecryptionException;
import guru.oze.hospitalmedicalrecords.exception.InvalidAccessKeyException;
import guru.oze.hospitalmedicalrecords.repository.UserRepository;
import guru.oze.hospitalmedicalrecords.security.jwt.JWTUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
@Slf4j
public class SecurityUtil {

    private final UserRepository repository;
    private final JWTUtils jwtUtils;

    public void ensureApiKeyIsValid(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            String jwtToken = requestTokenHeader.substring(7);
            String encryptedUuidFromJwtToken = jwtUtils.getUuidFromJwtToken(jwtToken);
            log.info("encrypted Uuid From Jwt Token {}", encryptedUuidFromJwtToken );
            String decrypt = EncryptionUtil.decrypt(encryptedUuidFromJwtToken);
            log.info("Decrypted uuid {}", decrypt);
            if (decrypt == null){
                throw new FailedDecryptionException("Failed to decrypt access key");
            }
            if(!repository.existsByUuid(decrypt)){
                throw new InvalidAccessKeyException("Invalid access key");
            }
        }
    }
}
