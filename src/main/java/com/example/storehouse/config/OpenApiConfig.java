package com.example.storehouse.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import java.util.Arrays;
import org.springdoc.core.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// https://springdoc.org/
// https://www.baeldung.com/spring-rest-openapi-documentation
@Configuration
@OpenAPIDefinition
public class OpenApiConfig {

    @Bean
    public OpenAPI springOpenAPI(@Value("${info.api.v1.version}") String apiVersion,
        @Value("${app.jwt.header}") String authHeader) {

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
            .components(new Components()
                .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                    .type(Type.HTTP)
                    .scheme("bearer").bearerFormat("JWT")
                    .in(In.HEADER)
                    .name(authHeader)
                )
            )
            .addSecurityItem(new SecurityRequirement()
                //TODO надо уточнить, что дока действительно добавляется, и в каком виде
                .addList("bearer-jwt", Arrays.asList("db:users:read", "db:users:write"))
            )
            ;
    }

}
