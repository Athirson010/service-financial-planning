package io.github.athirson010.financialPlanning.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("API planejamento financeiro - public").pathsToMatch("/**").build();
    }
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(
                        new Info().title("Planejamento Financeiro").description("API Backend de planejamento financeiro").version("v0.0.1"))
                .components(new Components()
                        //API Key, see: https://swagger.io/docs/specification/authentication/api-keys/
                        .addSecuritySchemes("apiKeyScheme", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("X-API-KEY")
                        )
                );

    }
}
