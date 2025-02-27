package com.pentabyte.projects.sorteador.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
//              .components(new Components()
//                  .addSecuritySchemes("bearer-key",
//                      new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))

                .info(new Info()
                        .title("Sorteador")
                        .description("""
                                API Rest para el proyecto sorteador i2t.
                                                                       [LinkedIn](https://linkedin.com/in/alejo-beliz) [GitHub](https://github.com/albelizGH/)
                                """)
                        .contact(new Contact()
                                .name("Alejo Beliz")
                                .url("https://linkedin.com/in/alejo-beliz")
                        )
                );
    }

}
