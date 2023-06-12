
package com.tads.dac.saga.sagas.aprovaCliente;

import com.tads.dac.saga.sagas.removegerente.*;
import com.tads.dac.saga.DTO.MensagemDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga2AprovaClienteContaConsumer {
    
    @Autowired
    private Saga3AprovaClienteAuthProducer next;
    
    
    @RabbitListener(queues = "aprova-conta-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            next.commitOrdem(msg);
            return;
        }
        System.out.println("Deu Ruim: " + msg.getMensagem());
        
    }
}
