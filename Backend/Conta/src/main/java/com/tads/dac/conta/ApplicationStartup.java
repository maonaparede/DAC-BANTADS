
package com.tads.dac.conta;


import com.tads.dac.conta.modelCUD.ContaCUD;
import com.tads.dac.conta.modelR.ClienteR;
import com.tads.dac.conta.modelR.ContaR;
import com.tads.dac.conta.repositoryCUD.ContaRepositoryCUD;
import com.tads.dac.conta.repositoryR.ClienteRepositoryR;
import com.tads.dac.conta.repositoryR.ContaRepositoryR;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent>{
    
    @Autowired
    private ClienteRepositoryR repCliente;
    
    @Autowired
    private ContaRepositoryCUD repCud;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private ContaRepositoryR repR;
    

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        
        ClienteR cliente1 = new ClienteR();
        cliente1.setId(Long.valueOf("1"));
        cliente1.setNome("John Doe");
        cliente1.setSalario(new BigDecimal("5000.00"));
        cliente1.setCpf("12345678901");
        cliente1.setCidade("New York");
        cliente1.setEstado("SP");

        ClienteR cliente2 = new ClienteR();
        cliente2.setId(Long.valueOf("2"));
        cliente2.setNome("Jane Smith");
        cliente2.setSalario(new BigDecimal("6000.00"));
        cliente2.setCpf("98765432109");
        cliente2.setCidade("Los Angeles");
        cliente2.setEstado("BA");

        ClienteR cliente3 = new ClienteR();
        cliente3.setId(Long.valueOf("3"));
        cliente3.setNome("Maria Silva");
        cliente3.setSalario(new BigDecimal("4000.00"));
        cliente3.setCpf("98765492108");
        cliente3.setCidade("Chicago");
        cliente3.setEstado("PR");

        repCliente.save(cliente1);
        repCliente.save(cliente2);
        repCliente.save(cliente3);
        
        Date dt = Date.from(Instant.now());
        
        ContaCUD conta1 = new ContaCUD();
        conta1.setIdConta(1L);
        conta1.setSaldo(new BigDecimal("1000.00"));
        conta1.setLimite(new BigDecimal("2500.00"));
        conta1.setSituacao("A");
        conta1.setIdCliente(Long.valueOf("1"));
        conta1.setDataCriacao(dt);
        conta1.setDataAproRep(dt);
        conta1.setIdGerente(Long.valueOf("1"));
        conta1.setNomeGerente("João Doria");

        ContaCUD conta2 = new ContaCUD();
        conta2.setIdConta(Long.valueOf("2"));
        conta2.setSaldo(new BigDecimal("500.00"));
        conta2.setLimite(new BigDecimal("3000.00"));
        conta2.setSituacao("A");
        conta2.setIdCliente(Long.valueOf("2"));
        conta2.setDataCriacao(dt);
        conta2.setDataAproRep(dt);
        conta2.setIdGerente(Long.valueOf("2"));
        conta2.setNomeGerente("Doctor Who");

        ContaCUD conta3 = new ContaCUD();
        conta3.setIdConta(Long.valueOf("3"));
        conta3.setSaldo(new BigDecimal("0.00"));
        conta3.setLimite(new BigDecimal("2000.00"));
        conta3.setSituacao("E");
        conta3.setIdCliente(Long.valueOf("3"));
        conta3.setDataCriacao(dt);
        conta3.setDataAproRep(dt);
        conta3.setIdGerente(Long.valueOf("2"));
        conta3.setNomeGerente("Doctor Who");
        
        repCud.save(conta1);
        repCud.save(conta2);
        repCud.save(conta3);
        
        ContaR ct1 = mapper.map(conta1, ContaR.class);
        ContaR ct2 = mapper.map(conta2, ContaR.class);
        ContaR ct3 = mapper.map(conta3, ContaR.class);
        
        repR.save(ct1);
        repR.save(ct2);
        repR.save(ct3);
        
        
        
    }
    
}
