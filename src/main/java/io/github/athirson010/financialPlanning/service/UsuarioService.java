package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.dto.CredenciaisDTO;
import io.github.athirson010.financialPlanning.domain.dto.TokenDTO;
import io.github.athirson010.financialPlanning.domain.dto.UsuarioModelDTO;
import io.github.athirson010.financialPlanning.domain.model.UsuarioModel;
import io.github.athirson010.financialPlanning.exception.NaoEncontradoException;
import io.github.athirson010.financialPlanning.exception.SenhaInvalidaException;
import io.github.athirson010.financialPlanning.jwt.JwtService;
import io.github.athirson010.financialPlanning.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    @Autowired
    UsuarioRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtService jwtService;

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

    private Optional<UsuarioModel> buscarUsuarioPorEmail(String email) {
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

        UsuarioModel filterMapeado = modelMapper.map(filter, UsuarioModel.class);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<UsuarioModel> example = Example.of(filterMapeado, matcher);

        Page<UsuarioModelDTO> usuariosPage = repository.findAll(example, pageable).map(this::toUsuarioModelDTO);

        if (usuariosPage.getContent().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return usuariosPage;
    }

    public void deletarUsuario(String id) {
        buscarUsuarioPorID(id);
        repository.deleteById(id);
    }

    public UsuarioModelDTO toUsuarioModelDTO(UsuarioModel usuarioModel) {
        return modelMapper.map(usuarioModel, UsuarioModelDTO.class);
    }

    public UsuarioModel toUsuarioModel(UsuarioModelDTO usuarioModelDTO) {
        return modelMapper.map(usuarioModelDTO, UsuarioModel.class);
    }

    public UsuarioModelDTO atualizarDadosUsuario(String id, UsuarioModel user) {
        buscarUsuarioPorID(id);
        user.setSenha(encoder.encode(user.getSenha()));
        user.setId(id);
        return this.toUsuarioModelDTO(repository.save(user));
    }

    public UsuarioModel buscarUsuarioPorID(String id) {
        return repository.findById(id).orElseThrow(() -> new NaoEncontradoException("Usuario"));
    }

}
