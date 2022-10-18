package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.UsuarioModel;
import io.github.athirson010.financialPlanning.exception.SenhaInvalidaException;
import io.github.athirson010.financialPlanning.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository repository;
    private PasswordEncoder encoder;

    @Transactional
    public void criarUsuario(UsuarioModel usuario) {
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

}
