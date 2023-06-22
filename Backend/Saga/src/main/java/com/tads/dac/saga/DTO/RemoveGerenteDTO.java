
package com.tads.dac.saga.DTO;

import java.util.List;
import lombok.Data;

@Data
public class RemoveGerenteDTO {
    private Long gerenteIdNew;
    private Long gerenteIdOld;  
    private String gerenteNameOld;
    private List<Long> contas;
}
