
package com.tads.dac.conta.DTOs;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteContaInfoDTO {

    private Long idConta;
    
    private Long idCliente;
    
    private String nome;
    
    private BigDecimal salario;
    
    private String cpf;
  
    private String nomeGerente;
    
    private Long idGerente;
    
    private BigDecimal saldo;
    
    private BigDecimal limite;
    
    private String situacao;
    
    private Date dataCriacao;
    
    private Date dataAproRep;
    
}
