package io.github.athirson010.financialPlanning.controller;

import io.github.athirson010.financialPlanning.domain.dto.token.TokenDTO;
import io.github.athirson010.financialPlanning.domain.model.jwt.AuthRequest;
import io.github.athirson010.financialPlanning.domain.model.jwt.AuthResponse;
import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.github.athirson010.financialPlanning.domain.model.usuario.dto.CredenciaisDTO;
import io.github.athirson010.financialPlanning.domain.model.usuario.dto.UsuarioModelDTO;
import io.github.athirson010.financialPlanning.jwt.JwtUtil;
import io.github.athirson010.financialPlanning.jwt.PBKDF2Encoder;
import io.github.athirson010.financialPlanning.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Usuario")
@RestController
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {
    @Autowired
    UsuarioService service;
    private JwtUtil jwtUtil;
    private PBKDF2Encoder passwordEncoder;
    private UsuarioService userService;

    public UsuarioController(UsuarioService service, JwtUtil jwtUtil, PBKDF2Encoder passwordEncoder, UsuarioService userService) {
        this.service = service;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/autenticar")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar) {
        return userService.findByUsername(ar.getUsername())
                .filter(userDetails -> passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword()))
                .map(userDetails -> ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
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
