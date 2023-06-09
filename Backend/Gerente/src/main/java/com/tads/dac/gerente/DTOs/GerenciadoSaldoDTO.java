
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
    
    private Long idConta;
    private BigDecimal saldo;
    private Long idGerente;
    
}
