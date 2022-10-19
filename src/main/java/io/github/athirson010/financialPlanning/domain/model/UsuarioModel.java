package io.github.athirson010.financialPlanning.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.athirson010.financialPlanning.domain.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Document(value = "usuario")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel extends AbstractModel {

    @Email()
    private String email;

    @NotEmpty()
    private String nome;

    @Size(min = 8)
    @NotEmpty
    private String senha;

    private Boolean admin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
}
