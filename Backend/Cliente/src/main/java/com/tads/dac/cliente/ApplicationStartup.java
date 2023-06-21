
package com.tads.dac.cliente;


import com.tads.dac.cliente.model.Cliente;
import com.tads.dac.cliente.repository.ClienteRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent>{
    
    @Autowired
    private ClienteRepository rep;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        
        Cliente cliente1 = new Cliente();
        cliente1.setId(Long.valueOf("1"));
        cliente1.setNome("John Doe");
        cliente1.setEmail("user1@user.com");
        cliente1.setTelefone("1234567890");
        cliente1.setSalario(new BigDecimal("5000.00"));
        cliente1.setCpf("12345678901");
        cliente1.setLogradouro("123 Main Street");
        cliente1.setComplemento("Apt 4B");
        cliente1.setCidade("New York");
        cliente1.setEstado("SP");
        cliente1.setTipo("Residential");
        cliente1.setCep("12345");
        cliente1.setNumero(10);

        Cliente cliente2 = new Cliente();
        cliente2.setId(Long.valueOf("2"));
        cliente2.setNome("Jane Smith");
        cliente2.setEmail("user2@user.com");
        cliente2.setTelefone("9876543210");
        cliente2.setSalario(new BigDecimal("6000.00"));
        cliente2.setCpf("98765432109");
        cliente2.setLogradouro("456 Elm Street");
        cliente2.setComplemento("Suite 200");
        cliente2.setCidade("Los Angeles");
        cliente2.setEstado("BA");
        cliente2.setTipo("Commercial");
        cliente2.setCep("54321");
        cliente2.setNumero(20);

        Cliente cliente3 = new Cliente();
        cliente3.setId(Long.valueOf("3"));
        cliente3.setNome("Maria Silva");
        cliente3.setEmail("user3@user.com");
        cliente3.setTelefone("5555555555");
        cliente3.setSalario(new BigDecimal("4000.00"));
        cliente3.setCpf("98765492108");
        cliente3.setLogradouro("789 Oak Avenue");
        cliente3.setComplemento("Unit 5C");
        cliente3.setCidade("Chicago");
        cliente3.setEstado("PR");
        cliente3.setTipo("Residential");
        cliente3.setCep("98765");
        cliente3.setNumero(30);

        rep.save(cliente1);
        rep.save(cliente2);
        rep.save(cliente3);
        
    }
    
}
