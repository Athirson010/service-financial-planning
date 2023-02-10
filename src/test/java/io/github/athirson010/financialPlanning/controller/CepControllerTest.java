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

import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureWebTestClient
class CepControllerTest {

}