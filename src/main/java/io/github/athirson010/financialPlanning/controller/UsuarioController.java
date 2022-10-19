package io.github.athirson010.financialPlanning.controller;

import io.github.athirson010.financialPlanning.controller.security.RestSecurity;
import io.github.athirson010.financialPlanning.domain.dto.CredenciaisDTO;
import io.github.athirson010.financialPlanning.domain.dto.TokenDTO;
import io.github.athirson010.financialPlanning.domain.dto.UsuarioModelDTO;
import io.github.athirson010.financialPlanning.domain.model.UsuarioModel;
import io.github.athirson010.financialPlanning.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Usuario")
@RestController
public class UsuarioController extends RestSecurity {
    @Autowired
    private UsuarioService service;

    @PostMapping("/auth")
    public TokenDTO auth(@RequestBody CredenciaisDTO credenciais) {
        return service.certificar(credenciais);
    }

    @PostMapping(path = "/")
    @ResponseStatus(CREATED)
    public void postCriarUsuario(@Valid @RequestBody UsuarioModel usuario) {
        service.criarUsuario(usuario);
    }

    @GetMapping()
    public Page<UsuarioModelDTO> buscarUsuarios(UsuarioModelDTO filter, @PageableDefault() Pageable pageable) {
        return service.buscarTodosUsuarios(filter, pageable);
    }

    @GetMapping("/{email}")
    public UsuarioModelDTO getUsuarioPorEmail(@PathVariable String email) {
        return service.buscarUsuarioDTOPorEmail(email);
    }

    @GetMapping("/{id}")
    public UsuarioModelDTO getUsuarioPorId(@PathVariable String id) {
        return service.buscarUsuarioDTOPorId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public UsuarioModelDTO putDadosUsuario(@PathVariable String id, @Valid @RequestBody UsuarioModel user) {
        return service.atualizarDadosUsuario(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void deleteUsuario(@PathVariable String id) {
        service.deletarUsuario(id);
    }


}
