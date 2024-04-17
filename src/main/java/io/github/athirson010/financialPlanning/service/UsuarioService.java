package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.config.jwt.GerenciamentoTokenJwt;
import io.github.athirson010.financialPlanning.domain.dto.autenticacao.UsuarioLoginDTO;
import io.github.athirson010.financialPlanning.domain.dto.autenticacao.UsuarioTokenDTO;
import io.github.athirson010.financialPlanning.domain.dto.usuario.UsuarioCriacaoDTO;
import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.github.athirson010.financialPlanning.mapper.UsuarioMapper;
import io.github.athirson010.financialPlanning.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GerenciamentoTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void criar(UsuarioCriacaoDTO usuarioCriacaoDto) {
        final UsuarioModel novoUsuario = UsuarioMapper.of(usuarioCriacaoDto);

        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        this.usuarioRepository.save(novoUsuario);
    }

    public UsuarioTokenDTO autenticar(UsuarioLoginDTO usuarioLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        UsuarioModel usuarioAutenticado =
                usuarioRepository.findByEmail(usuarioLoginDto.getEmail())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }
}