package com.pifrans.ecommerce.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Value("${openapi.server.url.dev}")
    private String devUrl;

    @Value("${openapi.server.url.prod}")
    private String prodUrl;


    @Bean
    public OpenAPI openAPI() {
        var devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL da API no ambiente de desenvolvimento");

        var prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("URL da API no ambiente de produção");

        var contact = new Contact();
        contact.setEmail("pifrans.technology@gmail.com");
        contact.setName("Pifrans");
        contact.setUrl("https://www.pifrans.com");

        var license = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
        var info = new Info().title("API de ecommerce Pifrans").version("1.0.3").contact(contact).description("Esta API expõe endpoints para gerenciar ecommerce Pifrans.").termsOfService("https://www.ecommerce.pifrans.com/terms").license(license);

        var securityRequirement = new SecurityRequirement().addList("Bearer Authentication");
        var securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
        var components = new Components().addSecuritySchemes("Bearer Authentication", securityScheme);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer)).addSecurityItem(securityRequirement).components(components);
    }
}