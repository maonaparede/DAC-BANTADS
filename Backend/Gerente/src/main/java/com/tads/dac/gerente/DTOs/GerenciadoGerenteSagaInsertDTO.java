
package com.tads.dac.gerente.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GerenciadoGerenteSagaInsertDTO {
    private Long idConta;
    private Long gerenteIdNew;
    private String gerenteNomeNew;
    private Long gerenteIdOld;
    private String gerenteNomeOld;
}
