
package com.tads.dac.saga.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlteraGerenteDTO {
    private String oldEmail;
    private String newEmail;    
}
