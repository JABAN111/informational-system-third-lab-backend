package is.fistlab.exceptionHandlers;

import is.fistlab.mappers.exceptions.InvalidFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DataBaseExceptionHandlers {

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String fieldInvalid(InvalidFieldException exc){
        return exc.getMessage();
    }

}
