
package com.tads.dac.auth;

import com.tads.dac.auth.exception.EncryptionException;
import com.tads.dac.auth.model.Auth;
import com.tads.dac.auth.repository.AuthRepository;
import com.tads.dac.auth.util.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent>{
    
    @Autowired
    private AuthRepository rep;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            String salt = Encrypt.gerarSalt(Encrypt.SALT_SIZE);
            String senha = Encrypt.encriptarSenhaLogin("root", salt);
            Auth reg = new Auth("root@root", senha, salt, "A");
            rep.save(reg);
        } catch (EncryptionException ex) {
            System.out.println("Excessão ao erro: " + ex.getMessage());
        }
        
    }
    
}
