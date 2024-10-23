package is.fistlab.exceptions.dataBaseExceptions.person;

public class PersonNotUnique extends RuntimeException {
    public PersonNotUnique(String message) {
        super(message);
    }
}
