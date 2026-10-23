package com.yuri.taskmanager.infrastructure.http;

import com.yuri.taskmanager.domain.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTaskNotFoundException(TaskNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        /*Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            var code = ((FieldError) error).getField();
            var message = error.getDefaultMessage();
            errors.put(code, message);
        });
        return errors;
*/
        return ex.getBindingResult().getAllErrors().stream().collect(Collectors.toMap(
                error -> ((FieldError) error).getField(),
                error -> Objects.requireNonNullElse(error.getDefaultMessage(), "Invalid error message"),
                (existing, ignored) -> existing
        ));
    }

}
