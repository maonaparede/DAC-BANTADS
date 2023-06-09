
package com.tads.dac.conta.DTOs;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioClienteDTO {
    
    private Long idConta;
    private String nomeCliente;
    private String cpfCliente;
    private String NomeGerente;
    private BigDecimal limite;
    private BigDecimal saldo;
    
}
