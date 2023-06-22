
package com.tads.dac.conta.DTOs;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaDTO {
    
    private Long idConta;
    
    private BigDecimal saldo;
    
    private BigDecimal limite;
    
    private String situacao;
    
    private Long idCliente;
    
    private Date dataCriacao;
    
    private Date dataAproRep;
    
    private String idGerente;
    
    private String nomeGerente;   
}
