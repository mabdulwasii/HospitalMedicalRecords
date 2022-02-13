package guru.oze.hospitalmedicalrecords.exception;

public class FailedDecryptionException extends RuntimeException {
    public FailedDecryptionException(String message) {
        super(message);
    }
}
