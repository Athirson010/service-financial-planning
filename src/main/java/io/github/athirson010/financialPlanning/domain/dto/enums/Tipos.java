package io.github.athirson010.financialPlanning.domain.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Tipos {
    META("meta"),
    CONTAS_MENSAIS("contas_mensais"),
    DIVERSAO("diversao"),
    SAUDE("saude");

    private String value;


}
