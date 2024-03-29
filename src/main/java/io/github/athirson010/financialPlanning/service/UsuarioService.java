package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.dto.token.TokenDTO;
import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.github.athirson010.financialPlanning.domain.model.usuario.dto.CredenciaisDTO;
import io.github.athirson010.financialPlanning.domain.model.usuario.dto.UsuarioModelDTO;
import io.github.athirson010.financialPlanning.exception.NaoEncontradoException;
import io.github.athirson010.financialPlanning.exception.SenhaInvalidaException;
import io.github.athirson010.financialPlanning.jwt.JwtService;
import io.github.athirson010.financialPlanning.mapper.UsuarioMapper;
import io.github.athirson010.financialPlanning.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class UsuarioService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UsuarioRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    UsuarioMapper mapper;

    @Transactional
    public void criarUsuario(UsuarioModel usuario) {
        buscarUsuarioPorEmail(usuario.getEmail()).ifPresentOrElse(
                (usuarioModel) -> {
                    throw new ResponseStatusException(UNAUTHORIZED, "Email já cadastrado");
                }, () -> {
                    usuario.setSenha(encoder.encode(usuario.getSenha()));
                    repository.save(usuario);
                });
    }

    public Optional<UsuarioModel> buscarUsuarioPorEmail(String email) {
        return repository.findByEmail(email);
    }

    public UserDetails autenticar(UsuarioModel usuario) {
        UserDetails user = loadUserByUsername(usuario.getEmail());

        if (encoder.matches(usuario.getSenha(), user.getPassword())) {
            return user;
        }
        throw new SenhaInvalidaException();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioModel usuario = repository.findByEmail(username)
                .orElseThrow(() -> new NaoEncontradoException("Usuário"));

        String[] roles = new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }

    public TokenDTO certificar(CredenciaisDTO credenciais) {
        try {
            UsuarioModel usuario = UsuarioModel.builder()
                    .email(credenciais.getEmail())
                    .senha(credenciais.getSenha()).build();
            autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getEmail(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(UNAUTHORIZED, e.getMessage());
        }
    }

    public Page<UsuarioModelDTO> buscarTodosUsuarios(UsuarioModelDTO filter, Pageable pageable) {

        UsuarioModel filterMapeado = mapper.toUsuarioModel(filter);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<UsuarioModel> example = Example.of(filterMapeado, matcher);

        Page<UsuarioModelDTO> usuariosPage = repository.findAll(example, pageable)
                .map(mapper::toUsuarioModelDTO);

        if (usuariosPage.getContent().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return usuariosPage;
    }

    public void deletarUsuario(String id) {
        buscarUsuarioPorId(id);
        repository.deleteById(id);
    }

    public UsuarioModelDTO atualizarDadosUsuario(String id, UsuarioModel user) {
        buscarUsuarioPorId(id);
        user.setSenha(encoder.encode(user.getSenha()));
        user.setId(id);
        return mapper.toUsuarioModelDTO(repository.save(user));
    }

    public UsuarioModel buscarUsuarioPorId(String id) {
        return repository.findById(id).orElseThrow(() -> new NaoEncontradoException("Usuario"));
    }

    public UsuarioModelDTO buscarUsuarioDTOPorEmail(String email) {
        UsuarioModel usuarioModel = buscarUsuarioPorEmail(email).orElseThrow(() -> new NaoEncontradoException("E-mail"));
        return mapper.toUsuarioModelDTO(usuarioModel);
    }

    public UsuarioModelDTO buscarUsuarioDTOPorId(String id) {
        UsuarioModel usuarioModel = buscarUsuarioPorId(id);
        return mapper.toUsuarioModelDTO(usuarioModel);
    }

    public UsuarioModel buscarUsuarioLogado() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return repository.findByEmail(username).orElseGet(() -> {
            throw new NaoEncontradoException("Usuario");
        });
    }
}
