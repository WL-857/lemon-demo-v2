package com.nhsoft.lemon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanglei
 */
@Configuration
public class JacksonConfig {
    @Bean(name = "objectMapper")
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
