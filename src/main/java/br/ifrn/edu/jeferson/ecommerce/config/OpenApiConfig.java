package br.ifrn.edu.jeferson.ecommerce.config;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de E-commerce")
                        .version("1.0.0")
                        .description("API para gerenciamento de categorias e produtos da Empresa Queiroga"));
    }
}
