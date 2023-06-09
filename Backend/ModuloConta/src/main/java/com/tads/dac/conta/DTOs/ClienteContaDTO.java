
package com.tads.dac.conta.DTOs;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteContaDTO {

    private Long id;
    
    private String nome;
    
    private BigDecimal salario;
    
    private String cpf;
  
    private String nomeGerente;
    
    private Long idGerente;
    
}
