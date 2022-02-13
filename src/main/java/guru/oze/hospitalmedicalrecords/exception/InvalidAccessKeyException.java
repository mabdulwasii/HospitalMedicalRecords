package guru.oze.hospitalmedicalrecords.exception;

public class InvalidAccessKeyException extends RuntimeException {
    public InvalidAccessKeyException(String message) {
        super(message);
    }
}
