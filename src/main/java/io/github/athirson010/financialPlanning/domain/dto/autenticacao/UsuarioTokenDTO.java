package io.github.athirson010.financialPlanning.domain.dto.autenticacao;

import lombok.Data;

@Data
public class UsuarioTokenDTO {
    private Long userId;
    private String nome;
    private String email;
    private String token;
}
