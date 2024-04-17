package io.github.athirson010.financialPlanning.domain.model.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.athirson010.financialPlanning.domain.AbstractModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
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
