package is.fistlab.controllers;

import lombok.Getter;
import lombok.Setter;

/**
 * Обертка для того, чтобы можно было удобно укладывать уведомление и основную информацию
 */

@Getter
@Setter
public class Response<T> {
    private String message;
    private Object body;


    public Response(String message, T body) {
        this.message = message;
        this.body = body;
    }


    public Response(String message){
        this(message,null);
    }

    public Response(T body){
        this(null,body);
    }
}
