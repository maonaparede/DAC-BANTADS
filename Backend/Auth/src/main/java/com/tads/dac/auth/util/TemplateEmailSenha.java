
package com.tads.dac.auth.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateEmailSenha {
    
    private String to;
    private String subject = "SENHA DO BANTADS";
    private String body;
    private String tipoUser;

    public TemplateEmailSenha(String to, String tipoUser, String senha) {
        this.to = to;
        this.tipoUser = tipoUser;
        if("G".equals(tipoUser)){
            body = "Gerente Sua Senha é: " + senha;
        }else if("A".equals(tipoUser)){
            body = "Admin Sua Senha é: " + senha;
        }else{
            body = "Usuário sua conta foi Aprovada! E sua senha é: " 
                    + senha + " E seu login é: " + to;
        }
    }
    
    
    
}
