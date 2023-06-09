package com.tads.dac.gerente;

import com.tads.dac.gerente.model.Gerente;
import com.tads.dac.gerente.repository.GerenteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GerenteApplicationTests {

        @Autowired
        GerenteRepository rep;
    
	@Test
	void contextLoads() {
            Gerente ger = new Gerente();
            ger.setId(Long.MIN_VALUE);
            ger.setCpf("12345678901");
            ger.setEmail("jo@gmail.com");
            ger.setNome("joao");
            ger.setTelefone("123456778901");
            
	}

}
