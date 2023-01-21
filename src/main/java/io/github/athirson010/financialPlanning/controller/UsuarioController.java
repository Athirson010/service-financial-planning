package io.github.athirson010.financialPlanning.controller;

import io.github.athirson010.financialPlanning.controller.security.RestSecurity;
import io.github.athirson010.financialPlanning.domain.dto.token.TokenDTO;
import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.github.athirson010.financialPlanning.domain.model.usuario.dto.CredenciaisDTO;
import io.github.athirson010.financialPlanning.domain.model.usuario.dto.UsuarioModelDTO;
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

@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Usuario")
@RestController
public class UsuarioController extends RestSecurity {
    @Autowired
    private UsuarioService service;

    @PostMapping("/autenticar")
    public TokenDTO auth(@RequestBody CredenciaisDTO credenciais) {
        return service.certificar(credenciais);
    }

    @PostMapping
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
    public UsuarioModelDTO putDadosUsuarioPorId(@PathVariable String id, @Valid @RequestBody UsuarioModel user) {
        return service.atualizarDadosUsuario(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuarioPorId(@PathVariable String id) {
        service.deletarUsuario(id);
    }


}
