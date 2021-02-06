package com.ruby.sun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    @GetMapping("/home")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/login")
    public String login(){
        return "hello";
    }

    @GetMapping("/logout")
    public String logout(){
        return "login";
    }
}
