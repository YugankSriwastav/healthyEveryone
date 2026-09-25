package shiva_care.healthify.exception.ExceptionHandling;

public class AppointmentSlotAlreadyBooked extends RuntimeException {
    public AppointmentSlotAlreadyBooked(String message) {
        super(message);
    }
}
