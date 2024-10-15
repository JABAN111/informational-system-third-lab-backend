package is.fistlab.exceptions.auth;

public class UserConflictException extends RuntimeException {

    public UserConflictException(String message) {
        super(message);
    }

}
