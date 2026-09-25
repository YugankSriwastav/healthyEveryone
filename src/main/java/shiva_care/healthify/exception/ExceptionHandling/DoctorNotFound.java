package shiva_care.healthify.exception.ExceptionHandling;

public class DoctorNotFound extends RuntimeException {
    public DoctorNotFound(String message) {
        super(message);
    }
}
