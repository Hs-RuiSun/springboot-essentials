package com.ruby.sun.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean("objectMapper")
    public ObjectMapper xmlMapper(){
        return new ObjectMapper();
    }
}
