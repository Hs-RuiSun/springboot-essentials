package com.ruby.sun.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ApplicationAuthException extends RuntimeException {
    public ApplicationAuthException(String message) {
        super(message);
    }
}
