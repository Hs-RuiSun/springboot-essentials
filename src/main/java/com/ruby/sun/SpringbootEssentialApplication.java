package com.ruby.sun;

import com.ruby.sun.config.PropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
@ComponentScan(basePackages = "com.ruby")
@EnableAutoConfiguration
//@SpringBootApplication equivalent
public class SpringbootEssentialApplication implements CommandLineRunner {
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootEssentialApplication.class, args);
	}

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void run(String... args) {
		PropertiesConfig propertiesConfig = applicationContext.getBean(PropertiesConfig.class);
		System.out.println(propertiesConfig.getHostName());
		//System.out.println(applicationContext.getBean(MongoClient.class).getClass()); // Spring Boot auto configuration
	}
}
