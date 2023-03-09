package com.project.lms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI floOpenAPI() {
        Info info = new Info().version("0.0.1").title("LMS서비스").description("LMS서비스 API 명세서");
        return new OpenAPI().info(info);
    }
    
}
