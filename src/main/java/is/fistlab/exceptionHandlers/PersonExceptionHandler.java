package is.fistlab.exceptionHandlers;

import is.fistlab.exceptions.person.PersonNotUnique;
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

}
