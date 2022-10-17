package io.github.athirson010.financialPlanning.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.athirson010.financialPlanning.domain.AbstractModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Document(value = "usuario")
@Builder
@Data
public class UsuarioModel extends AbstractModel {
    @NotEmpty()
    private String nome;
    @Email()
    private String email;
    @Size(min = 8)
    private String senha;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
}
