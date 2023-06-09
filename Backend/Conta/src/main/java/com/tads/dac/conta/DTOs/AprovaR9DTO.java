
package com.tads.dac.conta.DTOs;

import java.math.BigDecimal;
import lombok.Data;

@Data

public class AprovaR9DTO {
    
    private Long idCliente;
    private String cpf;
    private String nome;
    private BigDecimal salario;
}
