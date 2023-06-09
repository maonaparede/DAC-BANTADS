
package com.tads.dac.auth.DTOs;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilUpdateDTO {
    private Long idCliente;
    private String oldEmail;
    private String newEmail;
    private BigDecimal salario;
}
