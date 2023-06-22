
package com.tads.dac.saga.util;

public class TemplateEmailAutocadastroErro implements TemplateEmailInterface{
    
    private String to;
    private String subject = "Erro no Autocadastro";
    private String body;

    public TemplateEmailAutocadastroErro(String to, String erro) {
        this.to = to;
        this.body = "Não Foi Possível Efetuar o Autocadastro: " + erro +
                ". Caso o Erro seja muito estranho contate o desenvolvedor";
    }
    

    @Override
    public String getSubject() {
        return this.subject;
    }

    @Override
    public String getTo() {
        return this.to;
    }

    @Override
    public String getContent() {
        return this.body;
    }    
    
}
