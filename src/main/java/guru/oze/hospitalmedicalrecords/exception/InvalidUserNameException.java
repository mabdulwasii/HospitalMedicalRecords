package guru.oze.hospitalmedicalrecords.exception;

public class InvalidUserNameException extends RuntimeException {
    public InvalidUserNameException(String message) {
        super(message);
    }
}
