package is.fistlab.exceptions.person;

public class PersonNotUnique extends RuntimeException {
    public PersonNotUnique(String message) {
        super(message);
    }
}
