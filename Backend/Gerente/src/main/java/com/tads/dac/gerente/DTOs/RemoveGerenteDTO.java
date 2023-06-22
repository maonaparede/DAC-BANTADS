
package com.tads.dac.gerente.DTOs;

import java.util.List;
import lombok.Data;

@Data
public class RemoveGerenteDTO {
    private Long gerenteIdNew;
    private Long gerenteIdOld;
    private String gerenteNameOld;
    private List<Long> contas;
}
