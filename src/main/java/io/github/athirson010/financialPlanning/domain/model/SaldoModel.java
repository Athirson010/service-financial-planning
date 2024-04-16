package io.github.athirson010.financialPlanning.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.athirson010.financialPlanning.domain.AbstractModel;
import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(value = "saldo")
@Data
@AllArgsConstructor
public class SaldoModel extends AbstractModel {
    @NotEmpty
    private String origem;

    @NotNull
    private Double valor;

    private UsuarioModel usuario;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate data;
}
