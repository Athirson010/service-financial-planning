package io.github.athirson010.financialPlanning.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.athirson010.financialPlanning.domain.AbstractModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Document(value = "saldo")
@Builder
@Data
public class SaldoModel extends AbstractModel {
    @NotEmpty
    private String origem;

    @NotEmpty
    private Double valor;

    @NotEmpty
    private UsuarioModel usuario;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate data;
}
