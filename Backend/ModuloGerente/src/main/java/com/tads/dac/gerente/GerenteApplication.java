package com.tads.dac.gerente;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GerenteApplication {

        @Bean
        public static ModelMapper modelMapper(){
            return new ModelMapper();
        }
    
	public static void main(String[] args) {
		SpringApplication.run(GerenteApplication.class, args);
	}
        

}
