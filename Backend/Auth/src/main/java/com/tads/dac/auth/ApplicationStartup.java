
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
            //Salva o ADM
            String salt = Encrypt.gerarSalt(Encrypt.SALT_SIZE);
            String senha = Encrypt.encriptarSenhaLogin("root", salt);
            Auth reg = new Auth("root@root", senha, salt, "A");
            rep.save(reg);
            
            //Salva o gerente 1
            salt = Encrypt.gerarSalt(Encrypt.SALT_SIZE);
            senha = Encrypt.encriptarSenhaLogin("gerente", salt);
            reg = new Auth("gerente1@gerente.com", senha, salt, "G");
            rep.save(reg);
            
            //Salva o gerente 2
            salt = Encrypt.gerarSalt(Encrypt.SALT_SIZE);
            senha = Encrypt.encriptarSenhaLogin("gerente", salt);
            reg = new Auth("gerente2@gerente.com", senha, salt, "G");
            rep.save(reg);
            
            //Salva cliente 1
            salt = Encrypt.gerarSalt(Encrypt.SALT_SIZE);
            senha = Encrypt.encriptarSenhaLogin("1234", salt);
            reg = new Auth("user1@user.com", senha, salt, "G");      
            rep.save(reg);
            
            //Salva cliente 2
            salt = Encrypt.gerarSalt(Encrypt.SALT_SIZE);
            senha = Encrypt.encriptarSenhaLogin("1234", salt);
            reg = new Auth("user2@user.com", senha, salt, "G");
            rep.save(reg);
            
            //Salva cliente 2
            salt = Encrypt.gerarSalt(Encrypt.SALT_SIZE);
            senha = Encrypt.encriptarSenhaLogin("1234", salt);
            reg = new Auth("user3@user.com", senha, salt, "G");
            rep.save(reg);
            
        } catch (EncryptionException ex) {
            System.out.println("Excessão ao erro: " + ex.getMessage());
        }
        
    }
    
}
