
package com.tads.dac.saga.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GerenteNewOldDTO {
    
    private Long idOld;
    
    private Long idNew;
    private String nomeNew;    
}
