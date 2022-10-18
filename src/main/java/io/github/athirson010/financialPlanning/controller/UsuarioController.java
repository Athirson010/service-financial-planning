package io.github.athirson010.financialPlanning.controller;

import io.github.athirson010.financialPlanning.domain.dto.CredenciaisDTO;
import io.github.athirson010.financialPlanning.domain.dto.TokenDTO;
import io.github.athirson010.financialPlanning.domain.model.UsuarioModel;
import io.github.athirson010.financialPlanning.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @PostMapping(path = "/")
    @ResponseStatus(CREATED)
    public void postCriarUsuario(@Valid @RequestBody UsuarioModel usuario) {
        service.criarUsuario(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO auth(@RequestBody CredenciaisDTO credenciais) {
        return service.certificar(credenciais);
    }

}
