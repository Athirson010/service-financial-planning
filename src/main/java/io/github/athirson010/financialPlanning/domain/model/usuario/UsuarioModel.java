package io.github.athirson010.financialPlanning.domain.model.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.athirson010.financialPlanning.domain.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document(value = "usuario")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel extends AbstractModel {

    private String email;
    private String nome;
    private String senha;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
}
