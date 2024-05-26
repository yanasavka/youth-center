package com.example.youthcenter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class YouthCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(YouthCenterApplication.class, args);
    }
}
