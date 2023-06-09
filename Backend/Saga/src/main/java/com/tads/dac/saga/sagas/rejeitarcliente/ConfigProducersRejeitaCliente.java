
package com.tads.dac.saga.sagas.rejeitarcliente;


import com.tads.dac.saga.sagas.inseregerente.*;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigProducersRejeitaCliente {
    
    //1° Passo
    public static String queueRejeitaClienteClienteCon = "rejeita-cliente-con-saga";
    
    public static String queueRejeitaClienteClienteConReceive = "rejeita-cliente-con-saga-receive";
 
    //2° Passo
    public static String queueRejeitaClienteAuth = "rejeita-auth-saga";
    
    public static String queueRejeitaClienteAuthReceive = "rejeita-auth-saga-receive";
    
    public static String queueRejeitaClienteAuthRollback = "rejeita-auth-saga-rollback";
  
    //3° Passo
    public static String queueRejeitaClienteConta = "rejeita-conta-saga";
    
    public static String queueRejeitaClienteContaReceive = "rejeita-conta-saga-receive";
    
    public static String queueRejeitaClienteContaRollback = "rejeita-conta-saga-rollback";

    //4° Passo
    public static String queueRejeitaClienteGerente = "rejeita-gerente-saga";
    
    public static String queueRejeitaClienteGerenteReceive = "rejeita-gerente-saga-receive";
    
    public static String queueRejeitaClienteGerenteRollback = "rejeita-gerente-saga-rollback";
    
    //5° Passo
    public static String queueRejeitaClienteCliente = "rejeita-cliente-saga";
    
    public static String queueRejeitaClienteClienteReceive = "rejeita-cliente-saga-receive";
    
    public static String queueRejeitaClienteClienteRollback = "rejeita-cliente-saga-rollback";
    
    //1° Passo
    @Bean
    public Queue queueRejeitaClienteClienteCon() {
        return new Queue(queueRejeitaClienteClienteCon);
    }

    @Bean
    public Queue queueRejeitaClienteClienteConReceive() {
        return new Queue(queueRejeitaClienteClienteConReceive);
    }
 
    //2° Passo
    @Bean
    public Queue queueRejeitaClienteAuth() {
        return new Queue(queueRejeitaClienteAuth);
    }
    
    public Queue queueRejeitaClienteAuthReceive() {
        return new Queue(queueRejeitaClienteAuthReceive);
    }

    @Bean
    public Queue queueRejeitaClienteAuthRollback() {
        return new Queue(queueRejeitaClienteAuthRollback);
    }
    
    //3° Passo
    @Bean
    public Queue queueRejeitaClienteConta() {
        return new Queue(queueRejeitaClienteConta);
    }
    
    public Queue queueRejeitaClienteContaReceive() {
        return new Queue(queueRejeitaClienteContaReceive);
    }

    @Bean
    public Queue queueRejeitaClienteContaRollback() {
        return new Queue(queueRejeitaClienteContaRollback);
    }
    
    //4° Passo
    @Bean
    public Queue queueRejeitaClienteGerente() {
        return new Queue(queueRejeitaClienteGerente);
    }
    
    public Queue queueRejeitaClienteGerenteReceive() {
        return new Queue(queueRejeitaClienteGerenteReceive);
    }

    @Bean
    public Queue queueRejeitaClienteGerenteRollback() {
        return new Queue(queueRejeitaClienteGerenteRollback);
    }
    
    //5° Passo
    @Bean
    public Queue queueRejeitaClienteCliente() {
        return new Queue(queueRejeitaClienteCliente);
    }
    
    public Queue queueRejeitaClienteClienteReceive() {
        return new Queue(queueRejeitaClienteClienteReceive);
    }

    @Bean
    public Queue queueRejeitaClienteClienteRollback() {
        return new Queue(queueRejeitaClienteClienteRollback);
    }    
    
}
