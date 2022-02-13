package guru.oze.hospitalmedicalrecords.exception;

public class InvalidPatientException extends RuntimeException {
    public InvalidPatientException(String message) {
        super(message);
    }
}
