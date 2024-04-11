package io.github.athirson010.financialPlanning.controller;

import io.github.athirson010.financialPlanning.domain.model.cep.CepResposta;
import io.github.athirson010.financialPlanning.service.CepService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Buscar CEP")
@RequestMapping(value = "/cep", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class CepController {
    @Autowired
    CepService service;

    @GetMapping(path = "/{cep}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(content = @Content(schema = @Schema(implementation = CepResposta.class)))
    public Mono<CepResposta> buscarListaCep(@PathVariable String cep) {
        return service.buscarDetalhesEnderecoPorCep(cep);
    }
}
