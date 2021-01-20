package com.ruby.sun.controller;

import com.ruby.sun.config.WebMVCConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WebMVCConfig.class)
@WebAppConfiguration
public class ControllerUnitTest {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testApplicationContext(){
        System.out.println(applicationContext);
        System.out.println(webApplicationContext);
        assertNotNull(applicationContext.getBean("xmlMapper"));
        assertNotNull(webApplicationContext.getBean("xmlMapper"));
    }
}