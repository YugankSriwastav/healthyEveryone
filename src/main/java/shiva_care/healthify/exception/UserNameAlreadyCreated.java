package shiva_care.healthify.exception;

public class UserNameAlreadyCreated extends RuntimeException {
    public UserNameAlreadyCreated(String message) {
        super(message);
    }
}
