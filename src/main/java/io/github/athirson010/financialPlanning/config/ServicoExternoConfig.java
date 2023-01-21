package io.github.athirson010.financialPlanning.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ServicoExternoConfig {
    @Bean
    @Qualifier("viaCepClient")
    public WebClient viaCepClient(@Value("${viaCep.url}") String url){
        return WebClient.builder()
                .baseUrl(url)
                .build();
    }

}
