
package com.tads.dac.auth.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthTotalDTO {
    
    private String email;
    private String senha;
    private String salt;
    private String tipoUser;    
}
