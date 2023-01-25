package io.github.athirson010.financialPlanning.recursos;

import io.github.athirson010.financialPlanning.domain.model.cep.CepResposta;

public class CepRecursosTestes {
    public static CepResposta gerarModeloSucesso() {
        return CepResposta.builder()
                .uf("SP")
                .bairro("TESTE-BAIRRO")
                .complemento("TESTE-COMPLEMENTO")
                .logradouro("LOGRADOURO-COMPLEMENTO")
                .localidade("LOCALIDADE-COMPLEMENTO")
                .cep("08581-015").build();
    }
}
