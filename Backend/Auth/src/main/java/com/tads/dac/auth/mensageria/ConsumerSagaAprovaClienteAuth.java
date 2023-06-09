
package com.tads.dac.auth.mensageria;

import com.tads.dac.auth.DTOs.MensagemDTO;
import com.tads.dac.auth.exception.ContaNotExistException;
import com.tads.dac.auth.exception.EncryptionException;
import com.tads.dac.auth.service.AuthService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelmapper.ModelMapper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaAprovaClienteAuth {
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AmqpTemplate template; 
    
    @Autowired
    private AuthService serv;

    @RabbitListener(queues = "aprova-auth-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        try {
            String email = mapper.map(msg.getReturnObj(), String.class);
            serv.aprovarCliente(email);
        } catch (EncryptionException | ContaNotExistException ex) {
            msg.setMensagem("Deu erro: " + ex.getMessage());
        }
        
        template.convertAndSend("aprova-auth-saga-receive", msg);
    }

    @RabbitListener(queues = "aprova-auth-saga-rollback")
    public void rollbackOrdem(@Payload MensagemDTO msg) {
        try {
            String email = mapper.map(msg.getReturnObj(), String.class);
            serv.aprovarClienteRollback(email);
        } catch (ContaNotExistException ex) {
            msg.setMensagem(ex.getMessage());
        }        
    }
    
}
