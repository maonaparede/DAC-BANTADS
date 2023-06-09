

package com.tads.dac.saga.DTO;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaUpdateLimiteDTO {
    private Long contaId;
    private BigDecimal salario;
}
