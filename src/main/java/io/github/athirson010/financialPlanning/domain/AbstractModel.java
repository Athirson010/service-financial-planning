package io.github.athirson010.financialPlanning.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Data
public class AbstractModel {
    @Id
    private String id;

    @LastModifiedDate
    private LocalDate ultimaAtualizacao = LocalDate.now();
}
