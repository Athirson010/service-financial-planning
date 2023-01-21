package io.github.athirson010.financialPlanning.domain.model.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CredenciaisDTO {
    private String email;
    private String senha;
}
