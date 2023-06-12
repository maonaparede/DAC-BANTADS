
package com.tads.dac.saga.sagas.inseregerente;

import com.tads.dac.saga.DTO.AuthDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.InsertGerenteAuth;
import com.tads.dac.saga.repository.InsertGerenteAuthRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga3InsertGerenteAuthConsumer {
    
    @Autowired
    private Saga2InsertGerenteContaProducer prev;
    
    @Autowired
    private InsertGerenteAuthRepository rep;
    
    @Autowired
    private ModelMapper mapper; 
    
    @RabbitListener(queues = "ger-save-auth-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            AuthDTO dto = mapper.map(msg.getSendObj(), AuthDTO.class);     
            InsertGerenteAuth model = mapper.map(dto, InsertGerenteAuth.class); 
            model.setSagaId(msg.getSagaId());
            rep.save(model);
            
            return;
        }
        prev.rollbackOrdem(msg);
    }
}
