package com.ruby.sun.advice;

import com.ruby.sun.exception.ApplicationBadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ControllerHandlerAdvice {
    @ExceptionHandler({ApplicationBadRequestException.class, Exception.class})
    public ResponseEntity<String> handleBadRequestException() {
        return ResponseEntity.badRequest().body("It's a bad request");
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
}
