package com.example.siternbackend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://cp23sy4.sit.kmutt.ac.th")
                .allowedOrigins("http://10.4.85.44:8080")
                .allowedOrigins("https://10.4.85.44:8080")
                .allowedOrigins("https://capstone23.sit.kmutt.ac.th/sy4")
                .allowedOrigins("10.4.85.44:8080")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }
}
