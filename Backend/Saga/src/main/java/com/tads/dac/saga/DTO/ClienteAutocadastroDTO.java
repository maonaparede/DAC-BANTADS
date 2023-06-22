
package com.tads.dac.saga.DTO;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteAutocadastroDTO {
    private String nome;
    
    private String email;
    
    private String senha;
    
    private String telefone;
    
    private BigDecimal salario;
    
    private String cpf;
    
    private String logradouro;
    
    private String complemento;
    
    private String cidade;
    
    private String estado;
    
    private String tipo;
    
    private String cep;
    
    private Integer numero;
}
