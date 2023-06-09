
package com.tads.dac.auth.DTOs;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginDTO implements Serializable{    
    private String email;
    private String senha;
}
