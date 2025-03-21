package org.jboss.as.quickstarts.kitchensink.config;

import io.swagger.v3.oas.annotations.Hidden;
import org.jboss.as.quickstarts.kitchensink.constants.ExceptionTypeEnum;
import org.jboss.as.quickstarts.kitchensink.dto.error.ExceptionResponse;
import org.jboss.as.quickstarts.kitchensink.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .exceptionType(ExceptionTypeEnum.VALIDATION_ERROR)
                .fieldErrors(errors)
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MemberNotFoundException ex) {

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .exceptionType(ExceptionTypeEnum.NOT_FOUND)
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
