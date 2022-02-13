package guru.oze.hospitalmedicalrecords.exception;

public class ApiKeyNotSetException extends RuntimeException {
    public ApiKeyNotSetException(String message) {
        super(message);
    }
}
