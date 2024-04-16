package io.github.athirson010.financialPlanning.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;

@Data
public abstract class AbstractModel {
    @Id
    private String id;

    @LastModifiedDate
    private LocalDate ultimaAtualizacao = LocalDate.now();

}
