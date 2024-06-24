package com.tasktide.projectServices.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .components(apiComponents())
                .addSecurityItem(new SecurityRequirement().addList("token"));
    }

    private Components apiComponents() {
        return new Components()
                .addSecuritySchemes("token", apiSecurityScheme());
    }

    private SecurityScheme apiSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .description("Use any IT bearer token.")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .scheme("Bearer");
    }

    private Info apiInfo() {
        Contact contact = new Contact();
        contact.setName("Turcut Stefan");
        contact.setEmail("stefanturcut@yahoo.com");
        return new Info()
                .title("Project Services")
                .description("Project Rest Api services")
                .version("1.0")
                .contact(contact);
    }
}