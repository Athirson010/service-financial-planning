package io.github.athirson010.financialPlanning.domain.dto.autenticacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsuarioLoginDTO {
    @Schema(description = "E-mail do usuário", example = "athirson@teste.com")
    private String email;
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;
}
