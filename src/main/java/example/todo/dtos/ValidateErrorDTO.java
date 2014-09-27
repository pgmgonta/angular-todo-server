package example.todo.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tatsuya on 2014/09/21.
 */
public final class ValidateErrorDTO {

    private List<FieldErrorDTO> errorMessages = new ArrayList<FieldErrorDTO>();

    public List<FieldErrorDTO> getErrorMessages() {
        return errorMessages;
    }

    public void addFieldError(final String path, final String message) {
        FieldErrorDTO error = new FieldErrorDTO();
        error.setPath(path);
        error.setMessage(message);
        errorMessages.add(error);
    }
}
