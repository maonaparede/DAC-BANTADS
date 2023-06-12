
package com.tads.dac.gerente.Mensageria;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigProducersAll {
    
    
    public static String queueGerenteSyncConta= "altera-ger-sync-conta";
    
    @Bean
    public Queue queueGerenteSyncConta(){
        return new Queue(queueGerenteSyncConta);
    }
}
