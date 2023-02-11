package io.github.athirson010.financialPlanning.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.athirson010.financialPlanning.domain.model.cep.CepResposta;
import io.github.athirson010.financialPlanning.recursos.CepRecursosTestes;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import reactor.test.StepVerifier;

import java.io.IOException;

@SpringBootTest
class CepServiceTest {

    private static final CepResposta cepModelo = CepRecursosTestes.gerarModeloSucesso();
    public static MockWebServer mockApi;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    CepService service;

    @BeforeAll
    static void setup() throws IOException {
        mockApi = new MockWebServer();
        mockApi.start();
    }

    @AfterAll
    static void cleanup() throws IOException {
        mockApi.shutdown();
    }

    @BeforeEach
    void setupEach() {
        mockApi = new MockWebServer();
    }

    @Test
    void buscarDetalhesEnderecoPorCep_sucesso() throws IOException {
        mockApi = new MockWebServer();
        mockApi.start();

        String cep = "12.123-123";
        HttpUrl url = mockApi.url("/cep/" + cep);

        service.viacepApiUrl = url.toString();
        mockApi.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(cepModelo))
                .addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE));

        StepVerifier
                .create(service.buscarDetalhesEnderecoPorCep(cep))
                .expectNextMatches(cepDTO -> {
                    return true;
                })
                .expectComplete();
        mockApi.shutdown();
    }
}