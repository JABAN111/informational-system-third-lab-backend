package is.fistlab.exceptions.exceptionHandlers;

import is.fistlab.exceptions.dataBaseExceptions.person.InvalidActionException;
import is.fistlab.exceptions.dataBaseExceptions.person.PersonNotExistException;
import is.fistlab.exceptions.dataBaseExceptions.person.PersonNotUnique;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PersonExceptionHandler {

    @ExceptionHandler(PersonNotUnique.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String personNotUnique(PersonNotUnique exc){
        return exc.getMessage();
    }

    @ExceptionHandler(PersonNotExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String personNotExist(PersonNotExistException exc){
        return exc.getMessage();
    }

    @ExceptionHandler(InvalidActionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String personNotExist(InvalidActionException exc){
        return exc.getMessage();
    }
}
