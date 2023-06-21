
package com.tads.dac.gerente;

import com.tads.dac.gerente.model.Gerenciados;
import com.tads.dac.gerente.model.Gerente;
import com.tads.dac.gerente.repository.GerenciadosRepository;
import com.tads.dac.gerente.repository.GerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent>{
    
    @Autowired
    private GerenteRepository gerenteRep;
    
    @Autowired
    private GerenciadosRepository gerenciadosRep;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Gerente gerente1 = new Gerente();
        gerente1.setId(Long.valueOf("1"));
        gerente1.setCpf("92846678901");
        gerente1.setNome("João Doria");
        gerente1.setEmail("gerente1@gerente.com");
        gerente1.setTelefone("1234567890");

        Gerente gerente2 = new Gerente();
        gerente2.setId(Long.valueOf("2"));
        gerente2.setCpf("98765732109");
        gerente2.setNome("Doctor Who");
        gerente2.setEmail("gerente2@gerente.com");
        gerente2.setTelefone("9876543210");
        
        gerenteRep.save(gerente1);
        gerenteRep.save(gerente2);
        
        Gerenciados gerenciados1 = new Gerenciados(Long.valueOf("1"), Boolean.TRUE, gerente1);
        Gerenciados gerenciados2 = new Gerenciados(Long.valueOf("2"), Boolean.TRUE, gerente2);
        Gerenciados gerenciados3 = new Gerenciados(Long.valueOf("3"), Boolean.TRUE, gerente2);

        gerenciadosRep.save(gerenciados1);
        gerenciadosRep.save(gerenciados2);
        gerenciadosRep.save(gerenciados3);
    }
    
}
