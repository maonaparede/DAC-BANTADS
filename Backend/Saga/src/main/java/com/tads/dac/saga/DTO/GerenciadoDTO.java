
package com.tads.dac.saga.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GerenciadoDTO {
    
    private Long idConta;
    private Boolean saldoPositivo;
    private Long gerenteId;
}
