package com.ruby.sun.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import(DataSourceConfig.class)
@PropertySource("classpath:application.properties")
public class AppConfig {
}