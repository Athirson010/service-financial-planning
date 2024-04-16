package io.github.athirson010.financialPlanning.controller;

import io.github.athirson010.financialPlanning.domain.dto.pix.PixTransferenciaDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@RequestMapping(value = "/pix", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Pix")
@RestController
//@SecurityRequirement(name = "bearerAuth")
public class PixController {
    protected WebClient webClient;

    public PixController() {
        this.webClient = WebClient.create();
    }

    static String openPixUrl = "https://api.openpix.com.br/api/";

    @PostMapping
    private Mono<String> buscarContaPixPorId(PixTransferenciaDTO transferenciaDTO) {
        String url = UriComponentsBuilder.fromHttpUrl(openPixUrl)
                .pathSegment("v1", "transfer").build()
                .toUriString();

        return this.webClient
                .post()
                .uri(url)
                .bodyValue(transferenciaDTO)
                .header("Authorization", "Q2xpZW50X0lkXzVhZTQzYzlmLWNlMzYtNDRmOS1hOWNlLTgyNWRmNTI5ZWY5NTpDbGllbnRfU2VjcmV0X0ZRMisvVFJ3T3lkaVdxR0hIdGQ4UjhxZmI5czdZcFZUcWxDRnRjT0E3L2c9")
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(s -> {
                    System.out.println(s);
                    return Mono.just(s);
                });
    }

}
