package com.ruby.sun.controller;

import com.ruby.sun.domain.Guest;
import com.ruby.sun.exception.ApplicationBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Controller
@ResponseBody
public class ResponseEntityController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @GetMapping("exceptionHandler/{index}")
    public String exceptionHandler(@PathVariable int index){
        if(index %2 == 0){
            throw new ApplicationBadRequestException("It's a bad request");
        } else if(index == 1) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "it's unauthorized.");
        }
        return "success";
    }
    @GetMapping("/test")
    public ResponseEntity<Guest> testEntity(){
        //return ResponseEntity.ok(new Guest());
        //return ResponseEntity.status(HttpStatus.OK).body(new Guest());
        return ResponseEntity.ok()
                .header("ContentType", MediaType.APPLICATION_JSON_VALUE)
                .body(new Guest());
        //return new ResponseEntity<>(new Guest(), HttpStatus.OK);
    }
}
