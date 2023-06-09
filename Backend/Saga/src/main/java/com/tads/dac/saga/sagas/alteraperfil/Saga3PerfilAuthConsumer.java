
package com.tads.dac.saga.sagas.alteraperfil;

import com.tads.dac.saga.DTO.ContaDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.PerfilAuthUpdateSaga;
import com.tads.dac.saga.model.PerfilContaUpdateSaga;
import com.tads.dac.saga.repository.PerfilAuthUpdateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.tads.dac.saga.repository.PerfilContaUpdateRepository;

@Component
public class Saga3PerfilAuthConsumer {
    
    @Autowired
    private Saga2PerfilContaProducer prev;
    
    @Autowired
    private PerfilAuthUpdateRepository rep;
    
    @Autowired
    private ModelMapper mapper;
    
    @RabbitListener(queues = "perfil-auth-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        
        if(msg.getMensagem() == null){
            PerfilAuthUpdateSaga model = mapper.map(msg.getReturnObj(), PerfilAuthUpdateSaga.class); 
            model.setSagaId(msg.getSagaId());
            rep.save(model);
            
            return;
        }
        
        prev.rollbackOrdem(msg);
        //Faz algo
    }
    
}
