package io.github.athirson010.financialPlanning.domain.model;

import io.github.athirson010.financialPlanning.domain.AbstractModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;

@Document(value = "saldo")
@Builder
@Data
public class SaldoModel extends AbstractModel {
    private Double valor;
    private UsuarioModel usuario;
    private LocalDate data;
}
