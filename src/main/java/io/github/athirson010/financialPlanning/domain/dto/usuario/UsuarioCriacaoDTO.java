package io.github.athirson010.financialPlanning.domain.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioCriacaoDTO {
    @Size(min = 3, max = 10)
    @Schema(description = "Nome do usuário", example = "Athirson")
    private String nome;

    @Email
    @Schema(description = "Email do usuário", example = "athirson@teste.com")
    private String email;

    @Size(min = 6, max = 20)
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;

}
