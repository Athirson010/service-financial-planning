package io.github.athirson010.financialPlanning.domain.model.cep;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CepResposta {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private Boolean erro;
}
