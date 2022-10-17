package io.github.athirson010.financialPlanning.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorGenericoModel {
    ValorNaoAtingeRequisitos("O valor informado para o campo não atinge os requisitos minimos."),
    EmailInvalido("O valor informado para o campo de Email é invalido."),
    SenhaNaoAtingeRequisitos("O campo de senha não atinge os requisitos minimos."),
    CampoNaoInformado("campo não informado!");

        @Getter
        private String value;

    @Override
    public String toString() {
        return "ErrorGenericoModel{" +
                "value='" + value + '\'' +
                '}';
    }
}
