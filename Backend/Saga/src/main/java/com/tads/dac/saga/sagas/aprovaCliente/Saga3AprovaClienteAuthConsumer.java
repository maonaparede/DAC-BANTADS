
package com.tads.dac.saga.sagas.aprovaCliente;

import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.AprovaCliente;
import com.tads.dac.saga.repository.AprovaClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga3AprovaClienteAuthConsumer {
    
    @Autowired
    private Saga2AprovaClienteContaProducer prev;
    
    @Autowired
    private AprovaClienteRepository rep;
    
    @Autowired
    private ModelMapper mapper;
    
    @RabbitListener(queues = "aprova-auth-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            String email = mapper.map(msg.getReturnObj(), String.class);
            Long id = mapper.map(msg.getSendObj(), Long.class);
            
            AprovaCliente apro = new AprovaCliente();
            apro.setEmail(email);
            apro.setIdCliente(id);
            rep.save(apro);
            return;
        }
        prev.rollbackOrdem(msg);
    }
}
