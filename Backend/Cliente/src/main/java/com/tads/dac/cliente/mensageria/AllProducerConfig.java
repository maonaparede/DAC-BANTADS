
package com.tads.dac.cliente.mensageria;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AllProducerConfig {
    
    @Value("cliente")
    private String queueCliente;
    
    @Bean
    public Queue queueCliente(){
        return new Queue(queueCliente);
    }
}
