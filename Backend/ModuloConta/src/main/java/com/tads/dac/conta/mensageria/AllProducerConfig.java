
package com.tads.dac.conta.mensageria;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AllProducerConfig {
    
    @Value("conta")
    private String queueConta;
    
    @Value("operacao")
    private String queueOperacao;
    
    @Value("gerente")
    private String queueGerente;
    
    @Bean
    public Queue queueConta(){
        return new Queue(queueConta);
    }
    
    @Bean
    public Queue queueOperacao(){
        return new Queue(queueOperacao);
    }
    
    @Bean
    public Queue queueCliente(){
        return new Queue(queueGerente);
    }
}
