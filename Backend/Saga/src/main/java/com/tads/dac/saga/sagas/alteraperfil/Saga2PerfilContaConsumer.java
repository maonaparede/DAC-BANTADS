
package com.tads.dac.saga.sagas.alteraperfil;

import com.tads.dac.saga.DTO.ContaDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.PerfilContaUpdateSaga;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.tads.dac.saga.repository.PerfilContaUpdateRepository;

@Component
public class Saga2PerfilContaConsumer {
    
    @Autowired
    private Saga1PerfilClienteProducer prev;
 
    @Autowired
    private Saga3PerfilAuthProducer next;
    
    @Autowired
    private PerfilContaUpdateRepository rep;
    
    @Autowired
    private ModelMapper mapper;
    
    @RabbitListener(queues = "perfil-conta-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        
        if(msg.getMensagem() == null){
            PerfilContaUpdateSaga model = mapper.map(msg.getSendObj(), PerfilContaUpdateSaga.class); 
            model.setSagaId(msg.getSagaId());
            rep.save(model);
            next.commitOrdem(msg);
            return;
        }
        
        prev.rollbackOrdem(msg);
        //Faz algo
    }
    
}
