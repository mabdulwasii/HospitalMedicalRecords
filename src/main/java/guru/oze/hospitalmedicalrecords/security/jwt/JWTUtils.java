package guru.oze.hospitalmedicalrecords.security.jwt;

import guru.oze.hospitalmedicalrecords.utils.EncryptionUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@ToString
@Slf4j
public class JWTUtils {

    @Value("${token.header}")
    private String header;

    @Value("${token.expiration}")
    private long expiration;

    @Value("${token.refreshExpiration}")
    private long refreshExpiration;

    @Value("${token.secret}")
    private String secret;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        log.info("userPrincipal generateJwtToken {}", userPrincipal);
        log.info("username generateJwtToken {}", userPrincipal.getUsername());

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userPrincipal.getUsername());
        claims.put("id", userPrincipal.getId());
        claims.put("uuid", EncryptionUtil.encrypt(userPrincipal.getUuid()));
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        log.info("Signing secret {}", secret);
        String username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("username",
                String.class);
        log.info("Username from token {}", username);
        return username;
    }

     public String getUuidFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("uuid", String.class);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        log.info("Invalid jwt");

        return false;
    }

    public long getRefreshExpiration() {
        return refreshExpiration;
    }
}
