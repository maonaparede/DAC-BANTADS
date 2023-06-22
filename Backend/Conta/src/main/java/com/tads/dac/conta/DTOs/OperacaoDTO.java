
package com.tads.dac.conta.DTOs;

import java.io.Serializable;
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
public class OperacaoDTO implements Serializable{
    
    private Long id;
    private Date dataTempo;
    
    private BigDecimal valor;
    private String operacao;
    
    private Long paraUser;
    private Long deUser;

    
}
