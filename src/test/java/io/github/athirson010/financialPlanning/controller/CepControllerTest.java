package io.github.athirson010.financialPlanning.controller;

import io.github.athirson010.financialPlanning.domain.model.cep.CepResposta;
import io.github.athirson010.financialPlanning.service.CepService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureWebTestClient
class CepControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CepService cepService;

    @Test
    public void buscarCep_Success() {
        CepResposta resposta = CepResposta.builder()
                .cep("12345678")
                .logradouro("Test Logradouro")
                .build();

        Mono<CepResposta> cepMono = Mono.just(resposta);
        when(cepService.buscarCepExterno("12345678")).thenReturn(cepMono);

        webTestClient.get().uri("/cep/12345678")
                .exchange()
                .expectStatus().isOk()
                .expectBody(CepResposta.class)
                .isEqualTo(resposta);
    }
}