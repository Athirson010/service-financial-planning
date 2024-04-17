package io.github.athirson010.financialPlanning.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.athirson010.financialPlanning.domain.AbstractModel;
import io.github.athirson010.financialPlanning.domain.dto.enums.Tipos;
import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(value = "gasto")
@Builder
@Data
@AllArgsConstructor
public class GastoModel extends AbstractModel {
    private String nome;
    private Tipos tipo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate data;

    private UsuarioModel usuario;
    private BigDecimal valor;
    private MetaModel meta;
}
