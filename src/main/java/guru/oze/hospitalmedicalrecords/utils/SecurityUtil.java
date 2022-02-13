package guru.oze.hospitalmedicalrecords.utils;

import guru.oze.hospitalmedicalrecords.exception.FailedDecryptionException;
import guru.oze.hospitalmedicalrecords.exception.InvalidAccessKeyException;
import guru.oze.hospitalmedicalrecords.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SecurityUtil {

    private final StaffRepository repository;

    public void ensureApiKeyIsValid(String apiKey) {
        String decrypt = EncryptionUtil.decrypt(apiKey);
        if (decrypt == null){
            throw new FailedDecryptionException("Failed to decrypt access key");
        }
        if(!repository.existsByUuid(decrypt)){
            throw new InvalidAccessKeyException("Invalid access key");
        }
    }
}
