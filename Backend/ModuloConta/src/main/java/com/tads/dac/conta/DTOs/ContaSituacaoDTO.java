
package com.tads.dac.conta.DTOs;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaSituacaoDTO implements Serializable{
    
    private Long contaId;
    private String situacao;
    private BigDecimal salario;
}
