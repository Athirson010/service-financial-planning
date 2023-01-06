package io.github.athirson010.financialPlanning.config;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    private Logger logger = LoggerFactory.getLogger(CorsConfig.class);
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
            }
        };
    }
    @Bean
    public CommandLineRunner runner() throws Exception {
            return args -> {
                logger.info("RODANDO A CONFIGURAÇÃO DE DESENVOLVIMENTO");
                System.out.println("RODANDO A CONFIGURAÇÃO DE DESENVOLVIMENTO");
            };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
