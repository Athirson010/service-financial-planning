package io.github.athirson010.financialPlanning.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.athirson010.financialPlanning.domain.AbstractModel;
import io.github.athirson010.financialPlanning.domain.dto.enums.Tipos;
import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
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
    private Tipos tipo;
    private int parcelas;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate inicioPagamento;
    private UsuarioModel usuario;
}
