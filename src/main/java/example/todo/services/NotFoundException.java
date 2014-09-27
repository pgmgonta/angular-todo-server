package example.todo.services;

/**
 * Created by tatsuya on 2014/09/20.
 */
public class NotFoundException extends Exception {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

}
