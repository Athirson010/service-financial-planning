package io.github.athirson010.financialPlanning.domain.model;

import io.github.athirson010.financialPlanning.domain.AbstractModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(value = "gasto")
@Builder
@Data
public class GastoModel extends AbstractModel {
    private String nome;
    private String tipo;
    private LocalDate data;
    private UsuarioModel usuario;
    private Double valor;
    private MetaModel meta;
}
