package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.cep.CepResposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class CepService {
    Logger logger = LoggerFactory.getLogger(CepService.class);
    @Autowired
    @Qualifier("viaCepClient")
    public WebClient webClient;
    private static final String CEP_PATH = "json";

    public Mono<CepResposta> buscarCepExterno(String cep) {
        cep.replace("-", "");
        return Mono.deferContextual(contextView -> webClient.get()
                .uri(uriBuilder -> uriBuilder.pathSegment(cep).path(CEP_PATH).build())
                .retrieve()
                .bodyToMono(CepResposta.class)
                .flatMap(cepResposta -> {
                    if (cepResposta != null && Boolean.TRUE.equals(cepResposta.getErro())) {
                        logger.error("CEP NAO ENCONTRADO");
                        return Mono.error(new Throwable("CEP NAO ENCONTRADO"));
                    }
                    assert cepResposta != null;
                    return Mono.just(cepResposta);
                }));
    }
}
