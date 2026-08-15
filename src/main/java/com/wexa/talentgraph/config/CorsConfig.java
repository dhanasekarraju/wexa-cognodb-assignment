package com.wexa.talentgraph.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS configuration for handling cross-origin requests.
 * Reads the allowed frontend origin from FRONTEND_ORIGIN environment variable.
 * For local development without FRONTEND_ORIGIN set, allows localhost:5173.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String frontendOrigin = System.getenv("FRONTEND_ORIGIN");

        // For local development when FRONTEND_ORIGIN is not set
        if (frontendOrigin == null || frontendOrigin.isEmpty()) {
            frontendOrigin = "http://localhost:5173";
        }

        registry.addMapping("/**")
                .allowedOrigins(frontendOrigin)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}