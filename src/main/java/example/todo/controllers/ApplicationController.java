package example.todo.controllers;

import example.todo.dtos.ValidateErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Created by tatsuya on 2014/09/21.
 */
public abstract class ApplicationController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final @ResponseBody
    ValidateErrorDTO validateExceptionHandle(MethodArgumentNotValidException ex) {

        ValidateErrorDTO validateErrorDTO = new ValidateErrorDTO();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError f : fieldErrors) {
            validateErrorDTO.addFieldError(f.getField(), f.getDefaultMessage());
        }
        return validateErrorDTO;
    }
}
