package io.github.athirson010.financialPlanning.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Document(value = "usuario")
@Builder
@Data
public class UsuarioModel {
    @Id
    @Email()
    private String email;

    @NotEmpty()
    private String nome;

    @Size(min = 8)
    private String senha;

    private Boolean admin;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
}
