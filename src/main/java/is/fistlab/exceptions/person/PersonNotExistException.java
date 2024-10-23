package is.fistlab.exceptions.person;

public class PersonNotExistException extends RuntimeException {
    public PersonNotExistException(String message) {
        super(message);
    }
}
