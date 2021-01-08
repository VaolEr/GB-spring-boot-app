package com.example.storehouse.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// https://springdoc.org/
// https://www.baeldung.com/spring-rest-openapi-documentation
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springOpenAPI(@Value("${info.api.v1.version}") String apiVersion) {
        SpringDocUtils.getConfig().replaceWithClass(org.springframework.data.domain.Pageable.class,
            org.springdoc.core.converters.models.Pageable.class);

        return new OpenAPI()
            .info(new Info()
                .title("Storehouse App REST API documentation")
                //.description("Description...")
                .version(apiVersion)
                //.license(new License().name("MIT").url("https://github.com/VaolEr/GB-spring-boot-app/blob/master/LICENSE"))
            )
            .externalDocs(new ExternalDocumentation()
                .description("Storehouse App home page")
                .url("https://gb-storehouse.herokuapp.com/")
            )
            ;
    }

}
