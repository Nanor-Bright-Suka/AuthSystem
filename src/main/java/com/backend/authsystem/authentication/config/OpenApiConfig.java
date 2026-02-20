package com.backend.authsystem.authentication.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
    public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .info(new Info()
                            .title("RBAC SYSTEM API")
                            .version("1.0")
                            .description("This is the API documentation for the RBAC System, which provides endpoints for user authentication, role management, and permission handling. The API allows clients to register, log in, manage profiles, and perform role-based access control operations.")
                    );
        }
    }


