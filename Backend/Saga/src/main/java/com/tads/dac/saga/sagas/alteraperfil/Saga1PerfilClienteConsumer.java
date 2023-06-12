
package com.tads.dac.saga.sagas.alteraperfil;

import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.PerfilClienteUpdateSaga;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.tads.dac.saga.repository.PerfilClienteUpdateRepository;

@Component
public class Saga1PerfilClienteConsumer {
 
    @Autowired
    private Saga2PerfilContaProducer next;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private PerfilClienteUpdateRepository rep;   
    
    @RabbitListener(queues = "perfil-cliente-saga-receive")
    public void receiveMsg(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            
            PerfilClienteUpdateSaga model = mapper.map(msg.getSendObj(), PerfilClienteUpdateSaga.class);
            model = rep.save(model);

            msg.setSagaId(model.getSagaId());
        
            next.commitOrdem(msg);
            return;
        }
        
        //Deu algum erro coloque código pra executar aqui
    }
}