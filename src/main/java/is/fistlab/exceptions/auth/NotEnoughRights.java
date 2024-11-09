package is.fistlab.exceptions.auth;

public class NotEnoughRights extends RuntimeException {
    public NotEnoughRights(String message) {
        super(message);
    }
}
