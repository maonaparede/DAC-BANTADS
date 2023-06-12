
package com.tads.dac.saga.DTO;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GerenteSenhaDTO implements Serializable{
    
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String senha;
  
}
