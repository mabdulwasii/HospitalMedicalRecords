package guru.oze.hospitalmedicalrecords.utils;

import guru.oze.hospitalmedicalrecords.entity.User;
import guru.oze.hospitalmedicalrecords.exception.FailedDecryptionException;
import guru.oze.hospitalmedicalrecords.exception.InvalidAccessKeyException;
import guru.oze.hospitalmedicalrecords.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SecurityUtil {

    private final UserRepository repository;

    public void ensureApiKeyIsValid() {
        User currentUser = repository.findByUserIsCurrentUser();
        String decrypt = EncryptionUtil.decrypt(currentUser.getUuid());
        if (decrypt == null){
            throw new FailedDecryptionException("Failed to decrypt access key");
        }
        if(!repository.existsByUuid(decrypt)){
            throw new InvalidAccessKeyException("Invalid access key");
        }
    }
}
