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


@Document(value = "meta")
@Data
@Builder
@AllArgsConstructor
public class MetaModel extends AbstractModel {
    private String nome;
    private String descricao;
    private BigDecimal valorBruto;
    private Tipos tipo;
    private Integer parcelas;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate inicioPagamento;
    private UsuarioModel usuario;
}
