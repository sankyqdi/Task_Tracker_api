package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> fieldErrors = new HashMap<>();

        for (var error : e.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError fieldError) {
                String fieldName = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();
                fieldErrors.put(fieldName, errorMessage);
            } else {

                fieldErrors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }

           ValidationErrorResponse body = new ValidationErrorResponse(
                   LocalDateTime.now(),
                   HttpStatus.BAD_REQUEST.value(),
                   "Validation error",
                   fieldErrors
           );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);

    }
}
