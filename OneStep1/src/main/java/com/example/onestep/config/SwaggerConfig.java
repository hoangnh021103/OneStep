package com.example.onestep.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("OneStep Sneaker - REST API")
                        .description("Tài liệu API cho hệ thống bán giày sneaker")
                        .version("1.0"));
    }
}

