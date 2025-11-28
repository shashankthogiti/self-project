package com.shashank.project;

import com.shashank.project.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.shashank.project"})
@Import({SwaggerConfig.class})
public class ShashankProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShashankProjectApplication.class, args);
    }
}

