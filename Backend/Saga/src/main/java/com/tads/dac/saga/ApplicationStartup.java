
package com.tads.dac.saga;

import com.tads.dac.saga.DTO.GerenteSenhaDTO;
import com.tads.dac.saga.sagas.inseregerente.InsertGerenteSagaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent>{
    
    @Autowired
    private InsertGerenteSagaInitService serv;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        GerenteSenhaDTO dto = new GerenteSenhaDTO();
        dto.setEmail("gerente1@gerente.com");
        dto.setSenha("gerente1");
        
        dto.setCpf("12345678988");
        dto.setTelefone("98765432100");
        
        serv.initSagaInsertGerente(dto);
        
    }
    
}
