package example.todo.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by tatsuya on 2014/09/20.
 */
public class NotFoundException extends Exception {

    public NotFoundException() {}

    public NotFoundException(String message) {
        super(message);
    }

}
