package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.cep.CepResposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Service
public class CepService {
    Logger logger = LoggerFactory.getLogger(CepService.class);
    RestTemplate restTemplate = new RestTemplate();
    @Value("${viaCep.url}")
    public String viacepApiUrl;

    public CepResposta buscarDetalhesEnderecoPorCep(String cep) {
        cep = validarCep(cep);
        return buscarDetalhesEnderecoPorCepExterno(cep);
    }

    private CepResposta buscarDetalhesEnderecoPorCepExterno(String cep) {
        String url = UriComponentsBuilder.fromHttpUrl(this.viacepApiUrl)
                .pathSegment(cep)
                .path("json")
                .toUriString();

        ResponseEntity<CepResposta> response
                = restTemplate.getForEntity(url, CepResposta.class);

        return response.getBody();
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
