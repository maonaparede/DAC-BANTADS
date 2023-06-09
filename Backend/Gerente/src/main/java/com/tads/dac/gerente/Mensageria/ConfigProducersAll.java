
package com.tads.dac.gerente.Mensageria;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigProducersAll {
    
    //1° Passo
    public static String queueGerenteRemConsulta = "ger-rem-consulta";
    
    //3° Passo
    public static String queueGerenteRemGerenteCommit = "ger-rem-gerente-saga";
    
    
    //1° Passo
    @Bean
    public Queue queueGerenteRemConsulta() {
        return new Queue(queueGerenteRemConsulta);
    }
    
    //3° Passo
    @Bean
    public Queue queueGerenteRemGerenteCommit(){
        return new Queue(queueGerenteRemGerenteCommit);
    }
}
