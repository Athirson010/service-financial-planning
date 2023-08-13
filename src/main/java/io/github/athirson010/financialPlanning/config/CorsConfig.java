package io.github.athirson010.financialPlanning.config;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class CorsConfig implements WebMvcConfigurer {
    @Autowired
    ApplicationContext context;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

/*    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
            }
        };
    }*/

    @Bean
    public CommandLineRunner runner() throws Exception {
        return args -> {
            log.info("RODANDO A CONFIGURAÇÃO DE DESENVOLVIMENTO");
            System.out.println("RODANDO A CONFIGURAÇÃO DE DESENVOLVIMENTO");
        };
    }

    @Bean
    public SmartInitializingSingleton execAntesInjecaoDependencia() {
        return this::execAntesInjecaoDependenciaTexto;
    }

    public void execAntesInjecaoDependenciaTexto() {
        int banana = 1;
        log.info("ANTES DA INJEÇÃO!");
        if (banana != 1) {
            closeApplication();
        }
    }

    private void closeApplication() {
        SpringApplication.exit(context);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
