package com.dni9.blogapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI apiInfo() {
    Contact contact = new Contact();
    contact.setName("DNI9");
    contact.setEmail("hi@dni9.com");
    contact.setUrl("www.dni9.tech");

    return new OpenAPI()
        .components(new Components())
        .info(new Info()
            .title("Blog API")
            .version("v1")
            .description("Spring boot blog api documentation")
            .contact(contact)
            .license(new License().name("Apache 2.0").url("https://dni9.tech"))
        );
  }
}
