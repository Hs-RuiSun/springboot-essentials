package com.ruby.sun.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.forEach(converter -> {
            if(converter instanceof MappingJackson2HttpMessageConverter){
                ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(objectMapper());
            } else if(converter instanceof MappingJackson2XmlHttpMessageConverter){
                ((MappingJackson2XmlHttpMessageConverter) converter).setObjectMapper(xmlMapper());
            }
        });
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.registerModule(simpleModule());
        return mapper;
    }

    @Bean
    public XmlMapper xmlMapper(){
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(simpleModule());
        return xmlMapper;
    }

    @Bean
    public SimpleModule simpleModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new JsonDeserializer<String>() {
            @Override
            public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                String result = StringDeserializer.instance.deserialize(jsonParser, deserializationContext);
                if (StringUtils.isEmpty(result)) {
                    return null;
                }
                return result.trim();
            }
        });
        return module;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
