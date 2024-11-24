package is.fistlab.exceptions;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import is.fistlab.controllers.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalHandler {

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Response<String> exception(Exception e) {
//        return new Response<>("Ош");
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Response<String> runtime(RuntimeException e) {
//        return new Response<>("Ошибка сервера");
//    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response<String> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(e.getLocalizedMessage());
        return new Response<>("Невалидное значение фильтра",null);
    }
}
