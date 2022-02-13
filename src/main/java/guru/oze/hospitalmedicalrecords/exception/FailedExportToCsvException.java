package guru.oze.hospitalmedicalrecords.exception;

public class FailedExportToCsvException extends RuntimeException {
    public FailedExportToCsvException(String message) {
        super(message);
    }
}
