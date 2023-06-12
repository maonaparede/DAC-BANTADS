
package com.tads.dac.saga.sagas.removegerente;

import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.RemoveGerenteAuth;
import com.tads.dac.saga.repository.RemoveGerenteAuthRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga4RemGerAuthConsumer {
    
    @Autowired
    private Saga3RemGerGerenteProducer prev;
    
    @Autowired
    private RemoveGerenteAuthRepository rep;
    
    @Autowired
    private ModelMapper mapper; 
    
    @RabbitListener(queues = "ger-rem-auth-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        //Recebe o AuthTotalDTO
        if(msg.getMensagem() == null){    
            RemoveGerenteAuth model = mapper.map(msg.getSendObj(), RemoveGerenteAuth.class); 
            model.setSagaId(msg.getSagaId());
            rep.save(model);
            
            return;
        }
        
        prev.rollback(msg);
    }
}
