package io.github.athirson010.financialPlanning.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    @Bean
    public OpenAPI config() {
        return new OpenAPI().info(
                        new Info().title("API planejamento financeiro").description("API Backendde planejamento financeiro").version("v0.0.1"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("API planejamento financeiro - public").pathsToMatch("/**").build();
    }

}