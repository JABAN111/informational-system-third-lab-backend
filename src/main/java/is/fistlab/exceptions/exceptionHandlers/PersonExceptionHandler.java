package is.fistlab.exceptions.exceptionHandlers;

import is.fistlab.controllers.Response;
import is.fistlab.exceptions.auth.NotEnoughRights;
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
    public Response<String> personNotUnique(PersonNotUnique exc){
        return new Response<>(exc.getMessage());
    }

    @ExceptionHandler(PersonNotExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response<String> personNotExist(PersonNotExistException exc){
        return new Response<>(exc.getMessage());
    }

    @ExceptionHandler(InvalidActionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response<String> personNotExist(InvalidActionException exc){
        return new Response<>(exc.getMessage());
    }

    @ExceptionHandler(NotEnoughRights.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response<String> notEnoughRights(NotEnoughRights exc){
        return new Response<>(exc.getMessage());
    }
}
