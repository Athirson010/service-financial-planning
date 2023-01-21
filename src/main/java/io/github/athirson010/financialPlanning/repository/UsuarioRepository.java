package io.github.athirson010.financialPlanning.repository;

import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<UsuarioModel, String> {
    Optional<UsuarioModel> findByEmail(String username);
}
