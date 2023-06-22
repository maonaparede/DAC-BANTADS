
package com.tads.dac.saga.sagas.alteraperfil;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigProducersPerfil {
    
    //1° Passo
    public static String queuePerfilCliente = "perfil-cliente-saga";
    
    public static String queuePerfilClienteRollback = "perfil-cliente-saga-rollback";
    
    public static String queuePerfilClienteReceive = "perfil-cliente-saga-receive";
    
    //2° Passo
    public static String queuePerfilConta = "perfil-conta-saga";
    
    public static String queuePerfilContaRollback = "perfil-conta-saga-rollback";
    
    public static String queuePerfilContaReceive = "perfil-conta-saga-receive";
 
    //3° Passo
    public static String queuePerfilAuth = "perfil-auth-saga";
    
    public static String queuePerfilAuthRollback = "perfil-auth-saga-rollback";
    
    public static String queuePerfilAuthReceive = "perfil-auth-saga-receive";
    
    //1° Passo
    @Bean
    public Queue queuePerfilCliente(){
        return new Queue(queuePerfilCliente);
    }
    
    @Bean
    public Queue queuePerfilClienteRollback(){
        return new Queue(queuePerfilClienteRollback);
    }

    @Bean
    public Queue queuePerfilClienteReceive(){
        return new Queue(queuePerfilClienteReceive);
    }
    
    //2° Passo
    @Bean
    public Queue queuePerfilConta(){
        return new Queue(queuePerfilConta);
    }
    
    @Bean
    public Queue queuePerfilContaRollback(){
        return new Queue(queuePerfilContaRollback);
    } 
    
    @Bean
    public Queue queuePerfilContaReceive(){
        return new Queue(queuePerfilContaReceive);
    }
    
    //3° Passo
    @Bean
    public Queue queuePerfilAuth(){
        return new Queue(queuePerfilAuth);
    }
    
    @Bean
    public Queue queuePerfilAuthRollback(){
        return new Queue(queuePerfilAuthRollback);
    } 
    
    @Bean
    public Queue queuePerfilAuthReceive(){
        return new Queue(queuePerfilAuthReceive);
    } 
    
}
