package com.example.Todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("http://localhost:5500", "http://127.0.0.1:5500") // 정확한 주소만 명시
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowCredentials(true); // 인증정보(쿠키 등) 허용
            }
        };
    }
}