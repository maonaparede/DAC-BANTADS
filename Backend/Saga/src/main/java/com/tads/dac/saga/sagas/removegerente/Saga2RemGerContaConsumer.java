
package com.tads.dac.saga.sagas.removegerente;

import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.DTO.RemoveGerenteDTO;
import com.tads.dac.saga.model.RemoveGerenteContas;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.tads.dac.saga.repository.RemoveGerenteContasRepository;

@Component
public class Saga2RemGerContaConsumer {
    
    @Autowired
    private Saga3RemGerGerenteProducer next;
    
    @Autowired
    private RemoveGerenteContasRepository rep;
    
    @Autowired
    private ModelMapper mapper; 
    
    @RabbitListener(queues = "ger-rem-conta-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        //Recebe o RemoveGerenteDTO e passa só o Id do Gerente a ser excluido pro prox saga
        if(msg.getMensagem() == null){
            RemoveGerenteDTO dto = mapper.map(msg.getSendObj(), RemoveGerenteDTO.class);
            RemoveGerenteContas model = mapper.map(dto, RemoveGerenteContas.class);            
            
            model = rep.save(model);
            msg.setSagaId(model.getSagaId());
            
            dto = mapper.map(model, RemoveGerenteDTO.class);
            dto.setContas(null); //Descartando info que n precisa pra próxima fase
            
            msg.setSendObj(dto);
            
            next.commitOrdem(msg);
            return;
        }
    }
}
