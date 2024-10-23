package is.fistlab.exceptions.dataBaseExceptions.person;

public class PersonNotExistException extends RuntimeException {
    public PersonNotExistException(String message) {
        super(message);
    }
}
