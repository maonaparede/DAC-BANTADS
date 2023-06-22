
package com.tads.dac.saga.sagas.aprovaCliente;


import com.tads.dac.saga.sagas.inseregerente.*;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigProducersAprovaCliente {
    
    //1° Passo
    public static String queueAprovaClienteCliente = "aprova-cliente-saga";
    
    public static String queueAprovaClienteClienteReceive = "aprova-cliente-saga-receive";
    
    //2° Passo 
    public static String queueAprovaClienteConta = "aprova-conta-saga";
    
    public static String queueAprovaClienteContaReceive = "aprova-conta-saga-receive";
    
    public static String queueAprovaClienteContaRollback = "aprova-conta-saga-rollback";
    
    //3° Passo
    public static String queueAprovaClienteAuth = "aprova-auth-saga";
    
    public static String queueAprovaClienteAuthReceive = "aprova-auth-saga-receive";
    
    public static String queueAprovaClienteAuthRollback = "aprova-auth-saga-rollback";    
    
    
    //1° Passo
    @Bean
    public Queue queueAprovaClienteCliente() {
        return new Queue(queueAprovaClienteCliente);
    }

    @Bean
    public Queue queueAprovaClienteClienteReceive() {
        return new Queue(queueAprovaClienteClienteReceive);
    }
 
    
    //2° Passo
    @Bean
    public Queue queueAprovaClienteConta() {
        return new Queue(queueAprovaClienteConta);
    }
    
    @Bean
    public Queue queueAprovaClienteContaReceive() {
        return new Queue(queueAprovaClienteContaReceive);
    }
    
    @Bean
    public Queue queueAprovaClienteContaRollback() {
        return new Queue(queueAprovaClienteContaRollback);
    }
  
    
    //3° Passo
    @Bean
    public Queue queueAprovaClienteAuth(){
        return new Queue(queueAprovaClienteAuth);
    }
    
    @Bean
    public Queue queueAprovaClienteAuthReceive() {
        return new Queue(queueAprovaClienteAuthReceive);
    }
    
    @Bean
    public Queue queueAprovaClienteAuthRollback() {
        return new Queue(queueAprovaClienteAuthRollback);
    }
    
}
