package io.github.athirson010.financialPlanning.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String login;
    private String token;
}
