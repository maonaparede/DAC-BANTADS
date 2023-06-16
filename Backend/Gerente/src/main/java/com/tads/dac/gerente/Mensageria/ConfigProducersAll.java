
package com.tads.dac.gerente.Mensageria;


import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigProducersAll {
    
    
    public static String queueGerenteSyncConta= "altera-ger-sync-conta";
    
    
    @Value("gerente")
    private String queueGerente;
    
    @Bean
    public Queue queueGerenteSyncConta(){
        return new Queue(queueGerenteSyncConta);
    }
    
    @Bean
    public Queue queueGerente(){
        return new Queue(queueGerente);
    }
    
}
