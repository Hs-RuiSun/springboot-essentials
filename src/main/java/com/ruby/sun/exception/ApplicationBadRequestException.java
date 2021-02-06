package com.ruby.sun.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "it's bad request")
public class ApplicationBadRequestException extends RuntimeException{
    public ApplicationBadRequestException(String message){
        super(message);
    }
}
