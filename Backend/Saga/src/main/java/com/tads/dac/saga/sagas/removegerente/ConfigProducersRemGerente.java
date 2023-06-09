
package com.tads.dac.saga.sagas.removegerente;


import com.tads.dac.saga.sagas.alteraperfil.*;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigProducersRemGerente {
    
    //1° Passo
    public static String queueGerenteRemConsulta = "ger-rem-consulta";
    
    public static String queueGerenteRemReceiveConsulta = "ger-rem-consulta-receive";
    
    //2° Passo
    public static String queueGerenteRemContaCommit = "ger-rem-conta-saga";
  
    public static String queueGerenteRemContaReceive = "ger-rem-conta-saga-receive";
    
    public static String queueGerenteRemContaRollback = "ger-rem-conta-saga-rollback";
    
    //3° Passo
    public static String queueGerenteRemGerenteCommit = "ger-rem-gerente-saga";
    
    public static String queueGerenteRemGerenteReceive = "ger-rem-gerente-saga-receive";
    
    public static String queueGerenteRemGerenteRollback = "ger-rem-gerente-saga-rollback";
    
    //3° Passo
    public static String queueGerenteRemAuthCommit = "ger-rem-auth-saga";
    
    public static String queueGerenteRemAuthReceive = "ger-rem-auth-saga-receive";
    
    //1° Passo
    @Bean
    public Queue queueGerenteRemConsulta() {
        return new Queue(queueGerenteRemConsulta);
    }

    @Bean
    public Queue queueGerenteRemReceiveConsulta() {
        return new Queue(queueGerenteRemReceiveConsulta);
    }
    
    @Bean
    public Queue queueGerenteRemContaRollback() {
        return new Queue(queueGerenteRemContaRollback);
    }    
    
    //2° Passo
    @Bean
    public Queue queueGerenteRemContaCommit(){
        return new Queue(queueGerenteRemContaCommit);
    }
    
    @Bean
    public Queue queueGerenteRemContaReceive(){
        return new Queue(queueGerenteRemContaReceive);
    }
    
    //3° Passo
    @Bean
    public Queue queueGerenteRemGerenteCommit(){
        return new Queue(queueGerenteRemGerenteCommit);
    }
    
    @Bean
    public Queue queueGerenteRemGerenteRollback(){
        return new Queue(queueGerenteRemGerenteRollback);
    }

    @Bean
    public Queue queueGerenteRemGerenteReceive(){
        return new Queue(queueGerenteRemGerenteReceive);
    }
    
    //4° Passo
    @Bean
    public Queue queueGerenteRemAuthCommit(){
        return new Queue(queueGerenteRemAuthCommit);
    }

    @Bean
    public Queue queueGerenteRemAuthReceive(){
        return new Queue(queueGerenteRemAuthReceive);
    }
}
