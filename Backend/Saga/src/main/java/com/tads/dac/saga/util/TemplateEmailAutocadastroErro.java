
package com.tads.dac.saga.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateEmailAutocadastroErro {
    
    private String to;
    private String subject = "Erro no Autocadastro";
    private String body;

    public TemplateEmailAutocadastroErro(String to, String erro) {
        this.to = to;
        this.body = "Não Foi Possível Efetuar o Autocadastro: " + erro +
                ". Caso o Erro seja muito estranho contate o desenvolvedor";
    }
    
    
    
}
