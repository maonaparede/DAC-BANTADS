
package com.tads.dac.saga.sagas.autocadastro;

import com.tads.dac.saga.DTO.AutocadastroDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.AutocadastroAuth;
import com.tads.dac.saga.repository.AutocadastroAuthRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga2AutocadastroAuthConsumer {
    
    @Autowired
    private Saga3AutocadastroContaProducer next;
    
    @Autowired
    private Saga1AutocadastroClienteProducer prev;
    
    @Autowired
    private AutocadastroAuthRepository rep;
    
    @Autowired
    private ModelMapper mapper; 
    
    @RabbitListener(queues = "auto-auth-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            AutocadastroAuth model = mapper.map(msg.getSendObj(), AutocadastroAuth.class);
            model.setSagaId(msg.getSagaId());
            
            rep.save(model);
            
            AutocadastroDTO dto = mapper.map(msg.getReturnObj(), AutocadastroDTO.class);
            msg.setSendObj(dto);
            
            next.commitOrdem(msg);
            return;
        }
        prev.rollbackOrdem(msg);
    }
}
