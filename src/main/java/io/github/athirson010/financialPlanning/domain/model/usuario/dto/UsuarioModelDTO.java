package io.github.athirson010.financialPlanning.domain.model.usuario.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(value = "usuario")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModelDTO {

    private String id;

    private String email;

    @NotEmpty()
    private String nome;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
}
