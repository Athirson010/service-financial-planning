package io.github.athirson010.financialPlanning.domain.dto.pix;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PixTransferenciaDTO {
    private BigDecimal value;
    private String fromPixKey;
    private String toPixKey;
}
