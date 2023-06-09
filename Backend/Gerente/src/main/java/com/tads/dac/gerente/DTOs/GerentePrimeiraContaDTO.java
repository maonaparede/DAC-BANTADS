
package com.tads.dac.gerente.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GerentePrimeiraContaDTO {
    
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private Long primeiraConta;
    private Long idOld;
}
