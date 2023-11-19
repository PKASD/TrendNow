package com.example.trend_project.apis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApisConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
