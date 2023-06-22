
package com.tads.dac.auth.DTOs;

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
