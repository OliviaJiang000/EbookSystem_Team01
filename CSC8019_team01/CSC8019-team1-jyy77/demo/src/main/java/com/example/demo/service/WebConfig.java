package com.example.demo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**

 * Class Name: WebConfig

 *

 * Purpose:

 * Configuration class to define global Cross-Origin Resource Sharing (CORS) policies for the web layer.

 * Ensures that frontend applications (e.g., running on local ports) can communicate with backend services.

 *

 * Interface Description:

 * - Declares a WebMvcConfigurer bean to register allowed origins, methods, and headers for all API endpoints.

 * - Enables credentials and wildcard headers for development access.

 *

 * Important Data:

 * - Allowed origins: http://localhost:63342, http://127.0.0.1:5500 (e.g., Vite, VSCode Live Server)

 * - Allowed methods: GET, POST, PUT, DELETE, OPTIONS

 * - Credentials: enabled to support session cookies or Authorization headers

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Yunyi Jiang

 * - Created on: 2025-05-01

 * - Last modified: 2025-05-03 (Initial version)

 */
@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:63342",
                                "http://127.0.0.1:5500"
                        )

                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
