
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
    
    private String cpf;
    
    private BigDecimal saldo;
    
    private String cidade;
    
    private String estado;
    
}
