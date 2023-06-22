package com.tads.dac.auth;

import com.tads.dac.auth.util.EnviarEmail;
import com.tads.dac.auth.util.TemplateEmailSenha;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthApplicationTests {

    @Autowired
    private EnviarEmail serv;
        
    @Test
    public void commit(){
        TemplateEmailSenha email = new TemplateEmailSenha("jefferson9312@gmail.com", "C", "12g234");
        serv.sendEmail(email);
    }  
}
