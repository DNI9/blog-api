package com.dni9.blogapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  public final String SECURITY_SCHEME_NAME = "bearerAuth";

  @Bean
  public OpenAPI customizeOpenAPI() {
    Contact contact = new Contact();
    contact.setName("DNI9");
    contact.setEmail("hi@dni9.com");
    contact.setUrl("www.dni9.tech");

    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
        .components(new Components()
            .addSecuritySchemes(SECURITY_SCHEME_NAME,
                new SecurityScheme()
                    .name(SECURITY_SCHEME_NAME)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"))
        )
        .info(new Info()
            .title("Blog API")
            .version("v1")
            .description("Spring boot blog api documentation")
            .contact(contact)
            .license(new License().name("Apache 2.0").url("https://dni9.tech"))
        );
  }
}
