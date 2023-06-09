
package com.tads.dac.saga.sagas.alteragerente;


import com.tads.dac.saga.sagas.rejeitarcliente.*;
import com.tads.dac.saga.sagas.inseregerente.*;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigProducersAlteraGerente {
 
    //4° Passo
    public static String queueAlteraGerenteGerente = "alt-ger-gerente-saga";
    
    public static String queueAlteraGerenteGerenteReceive = "alt-ger-gerente-saga-receive";
    
    public static String queueAlteraGerenteGerenteRollback = "alt-ger-gerente-saga-rollback";
    
    
    //2° Passo
    public static String queueAlteraGerenteAuth = "alt-ger-auth-saga";
    
    public static String queueAlteraGerenteAuthReceive = "alt-ger-auth-saga-receive";
    
    public static String queueAlteraGerenteAuthRollback = "alt-ger-auth-saga-rollback";

    
    //1° Passo
    @Bean
    public Queue queueAlteraGerenteGerente() {
        return new Queue(queueAlteraGerenteGerente);
    }

    @Bean
    public Queue queueAlteraGerenteGerenteReceive() {
        return new Queue(queueAlteraGerenteGerenteReceive);
    }
    
    @Bean
    public Queue queueAlteraGerenteGerenteRollback() {
        return new Queue(queueAlteraGerenteGerenteRollback);
    }
 
    //2° Passo
    @Bean
    public Queue queueAlteraGerenteAuth() {
        return new Queue(queueAlteraGerenteAuth);
    }
    
    public Queue queueAlteraGerenteAuthReceive() {
        return new Queue(queueAlteraGerenteAuthReceive);
    }

    @Bean
    public Queue queueAlteraGerenteAuthRollback() {
        return new Queue(queueAlteraGerenteAuthRollback);
    }
    
}
