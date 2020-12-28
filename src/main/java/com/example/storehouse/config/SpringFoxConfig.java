package com.example.storehouse.config;

import static springfox.documentation.builders.PathSelectors.ant;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    @Value("${info.api.v1.version}")
    private String apiVersion;

    @Value("${info.api.v1.path}")
    private String apiPath;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
            // https://github.com/springfox/springfox/issues/1139
            // enable this if spring-security is used
            //.ignoredParameterTypes(AuthenticationPrincipal.class)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(ant(apiPath + "/**"))
            .build()
            .apiInfo(new ApiInfoBuilder()
                .title("Storehouse App REST API documentation")
                .description("<a href='https://gb-storehouse.herokuapp.com'>Storehouse backend API</a>")
                .version(apiVersion)
                .build())
            // https://github.com/springfox/springfox/issues/3518
            //.securitySchemes(List.of(new BasicAuth("basicAuth")))
            .securitySchemes(List.of(HttpAuthenticationScheme.BASIC_AUTH_BUILDER.name("basicAuth").build()))
            .securityContexts(List.of(SecurityContext.builder()
                .securityReferences(List.of(new SecurityReference("basicAuth", new AuthorizationScope[0])))
                .build()
            ));
    }
}
