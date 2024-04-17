package io.github.athirson010.financialPlanning.controller;

import io.github.athirson010.financialPlanning.domain.dto.autenticacao.UsuarioLoginDTO;
import io.github.athirson010.financialPlanning.domain.dto.autenticacao.UsuarioTokenDTO;
import io.github.athirson010.financialPlanning.domain.dto.usuario.UsuarioCriacaoDTO;
import io.github.athirson010.financialPlanning.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Usuario")
@RestController
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> criar(@RequestBody @Valid UsuarioCriacaoDTO usuarioCriacaoDto) {
        this.usuarioService.criar(usuarioCriacaoDto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDTO> login(@RequestBody UsuarioLoginDTO usuarioLoginDto) {
        UsuarioTokenDTO usuarioTokenDto = this.usuarioService.autenticar(usuarioLoginDto);
        return ResponseEntity.status(200).body(usuarioTokenDto);
    }
}

