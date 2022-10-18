package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.dto.CredenciaisDTO;
import io.github.athirson010.financialPlanning.domain.dto.TokenDTO;
import io.github.athirson010.financialPlanning.domain.model.UsuarioModel;
import io.github.athirson010.financialPlanning.exception.SenhaInvalidaException;
import io.github.athirson010.financialPlanning.jwt.JwtService;
import io.github.athirson010.financialPlanning.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtService jwtService;

    @Transactional
    public void criarUsuario(UsuarioModel usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        repository.save(usuario);
    }

    public UserDetails autenticar(UsuarioModel usuario) {
        UserDetails user = loadUserByUsername(usuario.getEmail());
        boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());

        if (senhasBatem) {
            return user;
        }
        throw new SenhaInvalidaException();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioModel usuario = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

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
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    public List<UsuarioModel> buscarTodosUsuarios(){
        return repository.findAll();
    }

    public void deletarUsuario(String id) {
       // repository.findById(id).orElseThrow()
      repository.deleteById(id);
    }
}
