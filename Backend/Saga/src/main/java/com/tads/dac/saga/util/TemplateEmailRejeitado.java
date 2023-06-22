
package com.tads.dac.saga.util;

public class TemplateEmailRejeitado implements TemplateEmailInterface{
    
    private String to;
    private String subject = "BANTADS - Rejeitado ";
    private String body;

    public TemplateEmailRejeitado(String to, String content) {
        this.to = to;
        this.body = "Seu cadastro no Bantads foi rejeitado porque: " + content +
                ". Obrigado pelo seu tempo";
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
