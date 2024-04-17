package io.github.athirson010.financialPlanning.controller;

import io.github.athirson010.financialPlanning.domain.model.cep.CepResposta;
import io.github.athirson010.financialPlanning.service.CepService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Buscar CEP")
@RequestMapping(value = "/cep", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@SecurityRequirement(name = "bearerAuth")
public class CepController {
    private final CepService service;

    public CepController(CepService service) {
        this.service = service;
    }

    @GetMapping(path = "/{cep}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(content = @Content(schema = @Schema(implementation = CepResposta.class)))
    public CepResposta buscarListaCep(@PathVariable String cep) {
        return service.buscarDetalhesEnderecoPorCep(cep);
    }
}
