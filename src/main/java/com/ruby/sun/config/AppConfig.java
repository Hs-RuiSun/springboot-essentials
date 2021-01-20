package com.ruby.sun.config;

import org.springframework.context.annotation.*;

@Configuration
@Import({DataSourceConfig.class, WebMVCConfig.class})
@PropertySource("classpath:application.properties")
public class AppConfig {
}