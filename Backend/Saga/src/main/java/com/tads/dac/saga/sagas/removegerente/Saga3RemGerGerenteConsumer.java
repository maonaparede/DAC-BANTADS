
package com.tads.dac.saga.sagas.removegerente;

import com.tads.dac.saga.DTO.AuthDTO;
import com.tads.dac.saga.DTO.GerenteDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.RemoveGerenteGerente;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.tads.dac.saga.repository.RemoveGerenteGerenteRepository;

@Component
public class Saga3RemGerGerenteConsumer {
    
    @Autowired
    private Saga2RemGerContaProducer prev;
    
    @Autowired
    private Saga4RemGerAuthProducer next;
    
    @Autowired
    private RemoveGerenteGerenteRepository rep;
    
    @Autowired
    private ModelMapper mapper; 
    
    @RabbitListener(queues = "ger-rem-gerente-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        //Recebe o RemoveGerenteDTO e passa só o Id do Gerente a ser excluido pro prox saga
        if(msg.getMensagem() == null){
            GerenteDTO dto = mapper.map(msg.getSendObj(), GerenteDTO.class);     
            RemoveGerenteGerente model = mapper.map(dto, RemoveGerenteGerente.class); 
            model.setSagaId(msg.getSagaId());
            rep.save(model);
            
            AuthDTO authDto = new AuthDTO();
            authDto.setEmail(dto.getEmail());
            msg.setSendObj(authDto);
            
            next.commitOrdem(msg);
            return;
        }
        prev.rollbackOrdem(msg);
    }
}
