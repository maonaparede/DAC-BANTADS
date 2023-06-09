
package com.tads.dac.auth.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejeitaClienteDTO {
    private Long idCLiente;
    private Long idConta;
    private String email;
}
