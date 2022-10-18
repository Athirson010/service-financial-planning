package io.github.athirson010.financialPlanning.controller;

import io.github.athirson010.financialPlanning.controller.security.RestSecurity;
import io.github.athirson010.financialPlanning.domain.dto.CredenciaisDTO;
import io.github.athirson010.financialPlanning.domain.dto.TokenDTO;
import io.github.athirson010.financialPlanning.domain.model.UsuarioModel;
import io.github.athirson010.financialPlanning.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Usuario")
@RestController
public class UsuarioController extends RestSecurity {
    @Autowired
    private UsuarioService service;

    @PostMapping(path = "/")
    @ResponseStatus(CREATED)
    public void postCriarUsuario(@Valid @RequestBody UsuarioModel usuario) {
        service.criarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void deleteUsuario(@PathVariable String id){
        service.deletarUsuario(id);
    }


    @PostMapping("/auth")
    public TokenDTO auth(@RequestBody CredenciaisDTO credenciais) {
        return service.certificar(credenciais);
    }

    @GetMapping()
    public List<UsuarioModel> buscarUsuarios(){
        return service.buscarTodosUsuarios();
    }


}
