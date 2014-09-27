package example.todo.controllers;

import example.todo.dtos.ValidateErrorDTO;
import example.todo.services.NotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;

/**
 * Created by tatsuya on 2014/09/27.
 */
@ControllerAdvice
public class ApplicationExceptionHandler {

    @Resource
    private MessageSource messageSource;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final @ResponseBody
    String notFoundExceptionHandle(NotFoundException ex) {
        Locale current = LocaleContextHolder.getLocale();
        return messageSource.getMessage(ex.getMessage(), null, current);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final @ResponseBody
    ValidateErrorDTO validateExceptionHandle(MethodArgumentNotValidException ex) {
        Locale current = LocaleContextHolder.getLocale();
        ValidateErrorDTO validateErrorDTO = new ValidateErrorDTO();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError f : fieldErrors) {
            validateErrorDTO.addFieldError(f.getField(), messageSource.getMessage(f.getDefaultMessage(), null, current));
        }
        return validateErrorDTO;
    }
}
