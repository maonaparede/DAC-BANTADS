
package com.tads.dac.gerente.DTOs;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GerenciadoSaldoDTO {
    
    private Long idGerenciado;
    private BigDecimal saldo;
    
}
