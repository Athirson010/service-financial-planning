package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.cep.CepResposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.*;


@Service
public class CepService {
    Logger logger = LoggerFactory.getLogger(CepService.class);
    protected WebClient webClient;
    @Value("${viaCep.url}")
    public String viacepApiUrl;

    public Mono<CepResposta> buscarDetalhesEnderecoPorCep(String cep) {
        cep = validarCep(cep);
        return buscarDetalhesEnderecoPorCepExterno(cep);
    }

    private Mono<CepResposta> buscarDetalhesEnderecoPorCepExterno(String cep) {
        String url = UriComponentsBuilder.fromHttpUrl(this.viacepApiUrl)
                .pathSegment(cep)
                .path("json")
                .toUriString();

        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(
                                new ResponseStatusException(NOT_FOUND, "CEP nao encontrado")))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(
                                new ResponseStatusException(INTERNAL_SERVER_ERROR, "Erro ao invocar o servico do via CEP")))
                .bodyToMono(CepResposta.class)
                .flatMap(resposta -> {
                    if (resposta != null && Boolean.TRUE.equals(resposta.getErro())) {
                        logger.error("cep não encontrado");
                        throw new ResponseStatusException(NOT_FOUND, "CEP nao encontrado");
                    }
                    return Mono.just(resposta);
                });
    }

    private String validarCep(String cep) {
        cep = cep.replaceAll("\\D", "");

        if (cep.length() != 8) {
            logger.error("cep invalido");
            throw new ResponseStatusException(BAD_REQUEST, "CEP invalido!");
        }

        logger.info("CEP informado valido: " + cep);
        return cep;
    }
}
