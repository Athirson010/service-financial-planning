package io.github.athirson010.financialPlanning.domain.model;

import io.github.athirson010.financialPlanning.domain.AbstractModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(value = "usuario")
@Builder
@Data
public class UsuarioModel extends AbstractModel {
    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
}
