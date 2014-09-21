package example.todo.dtos;

/**
 * Created by tatsuya on 2014/09/21.
 */
public final class FieldErrorDTO {

    private String path;
    private String message;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
