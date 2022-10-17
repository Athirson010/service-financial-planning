package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.UsuarioModel;
import io.github.athirson010.financialPlanning.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;
    public void criarUsuario(UsuarioModel usuario) {
        repository.save(usuario);
    }
}
