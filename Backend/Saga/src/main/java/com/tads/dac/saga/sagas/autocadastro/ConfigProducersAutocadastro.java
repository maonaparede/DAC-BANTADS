
package com.tads.dac.saga.sagas.autocadastro;


import com.tads.dac.saga.sagas.removegerente.*;
import com.tads.dac.saga.sagas.alteraperfil.*;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigProducersAutocadastro {
    
    //1° Passo
    public static String queueAutoClienteCommit = "auto-cliente-saga";
  
    public static String queueAutoClienteReceive = "auto-cliente-saga-receive";
    
    public static String queueAutoClienteRollback = "auto-cliente-saga-rollback";
    
    //2° Passo
    public static String queueAutoAuthCommit = "auto-auth-saga";
  
    public static String queueAutoAuthReceive = "auto-auth-saga-receive";
    
    public static String queueAutoAuthRollback = "auto-auth-saga-rollback";
 
    //3° Passo
    public static String queueAutoContaCommit = "auto-conta-saga";
  
    public static String queueAutoContaReceive = "auto-conta-saga-receive";
    
    public static String queueAutoContaRollback = "auto-conta-saga-rollback";
    
    //4° Passo
    public static String queueAutoGerenteCommit = "auto-gerente-saga";
  
    public static String queueAutoGerenteReceive = "auto-gerente-saga-receive";
    
    public static String queueAutoGerenteRollback = "auto-gerente-saga-rollback";
  
    //3° Passo
    public static String queueAutoContaUpCommit = "auto-conta-update-saga";
  
    public static String queueAutoContaUpReceive = "auto-conta-update-saga-receive";
    
    public static String queueAutoContaUpRollback = "auto-conta-update-saga-rollback";
    
    //1° Passo
    @Bean
    public Queue queueAutoClienteCommit(){
        return new Queue(queueAutoClienteCommit);
    }
    
    @Bean
    public Queue queueAutoClienteReceive(){
        return new Queue(queueAutoClienteReceive);
    }

    @Bean
    public Queue queueAutoClienteRollback(){
        return new Queue(queueAutoClienteRollback);
    }
    
    
    //2° Passo
    @Bean
    public Queue queueAutoAuthCommit(){
        return new Queue(queueAutoAuthCommit);
    }
    
    @Bean
    public Queue queueAutoAuthReceive(){
        return new Queue(queueAutoAuthReceive);
    }

    @Bean
    public Queue queueAutoAuthRollback(){
        return new Queue(queueAutoAuthRollback);
    }
    
    //3° Passo
    @Bean
    public Queue queueAutoContaCommit(){
        return new Queue(queueAutoContaCommit);
    }
    
    @Bean
    public Queue queueAutoContaReceive(){
        return new Queue(queueAutoContaReceive);
    }

    @Bean
    public Queue queueAutoContaRollback(){
        return new Queue(queueAutoContaRollback);
    }
    
    //4° Passo
    @Bean
    public Queue queueAutoGerenteCommit(){
        return new Queue(queueAutoGerenteCommit);
    }
    
    @Bean
    public Queue queueAutoGerenteReceive(){
        return new Queue(queueAutoGerenteReceive);
    }

    @Bean
    public Queue queueAutoGerenteRollback(){
        return new Queue(queueAutoGerenteRollback);
    }
    
    //5° Passo
    @Bean
    public Queue queueAutoContaUpCommit(){
        return new Queue(queueAutoContaUpCommit);
    }
    
    @Bean
    public Queue queueAutoContaUpReceive(){
        return new Queue(queueAutoContaUpReceive);
    }

    @Bean
    public Queue queueAutoContaUpRollback(){
        return new Queue(queueAutoContaUpRollback);
    }
}
