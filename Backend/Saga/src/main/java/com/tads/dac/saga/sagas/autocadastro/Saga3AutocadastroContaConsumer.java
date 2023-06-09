
package com.tads.dac.saga.sagas.autocadastro;

import com.tads.dac.saga.DTO.AutocadastroDTO;
import com.tads.dac.saga.sagas.removegerente.*;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.AutocadastroConta;
import com.tads.dac.saga.repository.AutocadastroContaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga3AutocadastroContaConsumer {
    
    @Autowired
    private Saga4AutocadastroGerenteProducer next;
    
    @Autowired
    private Saga2AutocadastroAuthProducer prev;
    
    @Autowired
    private AutocadastroContaRepository rep;
    
    @Autowired
    private ModelMapper mapper; 
    
    @RabbitListener(queues = "auto-conta-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            //Recebe ContaDTO
            AutocadastroConta model = mapper.map(msg.getSendObj(), AutocadastroConta.class);
            model.setSagaId(msg.getSagaId());
            
            rep.save(model);
            
            AutocadastroDTO dto = mapper.map(msg.getReturnObj(), AutocadastroDTO.class);
            msg.setSendObj(dto);
            
            msg.setReturnObj(null); //limpa o campo
            
            next.commitOrdem(msg);
            return;
        }
        
        prev.rollbackOrdem(msg);
    }
}
