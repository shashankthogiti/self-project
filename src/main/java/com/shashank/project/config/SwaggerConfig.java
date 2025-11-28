package com.shashank.project.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    @Bean
    public OpenAPI openAPI(
            @Value("${server.port:8080}") String serverPort) {
        logger.info("Configuring OpenAPI for Shashank Project");

        return new OpenAPI()
                .info(new Info()
                        .title("Shashank Project API")
                        .description("REST API documentation for Shashank Project - User Management System")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Shashank")
                                .email("shashank@example.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Local Development Server"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Docker Server")
                ));
    }
}

