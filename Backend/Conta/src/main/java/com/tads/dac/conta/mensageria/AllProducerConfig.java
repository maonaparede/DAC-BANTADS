
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
    
    @Value("conta-gerente")
    private String queueContaGerente; 
    
    @Value("conta-gerente-rollback")
    private String queueContaGerenteRollback;  
    
    @Value("conta-autocadastro-rollback")
    private String queueContaAutocadastroRollback;
    
    @Bean
    public Queue queueConta(){
        return new Queue(queueConta);
    }
    
    @Bean
    public Queue queueOperacao(){
        return new Queue(queueOperacao);
    }

    @Bean
    public Queue queueContaGerente(){
        return new Queue(queueContaGerente);
    }
    
    @Bean
    public Queue queueContaGerenteRollback(){
        return new Queue(queueContaGerenteRollback);
    }  
    
    @Bean
    public Queue queueContaAutocadastroRollback(){
        return new Queue(queueContaAutocadastroRollback);
    }
    
}
