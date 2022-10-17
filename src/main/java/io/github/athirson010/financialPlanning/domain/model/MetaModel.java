package io.github.athirson010.financialPlanning.domain.model;

import io.github.athirson010.financialPlanning.domain.AbstractModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;


@Document(value = "meta")
@Builder
@Data
public class MetaModel extends AbstractModel {
    private String nome;
    private String descricao;
    private Double valorBruto;
    private int parcelas;
    private LocalDate inicioPagamento;
    private LocalDate finalPagamento;
}