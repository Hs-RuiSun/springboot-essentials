package com.ruby.sun.config;

import org.springframework.context.annotation.*;

@Configuration
@Import({DataSourceConfig.class, WebConfig.class})
@PropertySource("classpath:application.properties")
public class AppConfig {
}