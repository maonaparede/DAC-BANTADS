
package com.tads.dac.saga.sagas.rejeitarcliente;

import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.RejeitaClienteConta;
import com.tads.dac.saga.repository.RejeitaClienteContaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga3RejeitaClienteContaConsumer {
    
    @Autowired
    private Saga4RejeitaClienteGerenteProducer next;
    
    @Autowired
    private RejeitaClienteContaRepository rep;
    
    @Autowired
    private Saga2RejeitaClienteAuthProducer prev;
    
    @Autowired
    private ModelMapper mapper; 
    
    @RabbitListener(queues = "rejeita-conta-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            //Recebe ContaDTO
            RejeitaClienteConta model = mapper.map(msg.getSendObj(), RejeitaClienteConta.class);
            model.setSagaId(msg.getSagaId());
            rep.save(model);
            
            next.commitOrdem(msg);
            return;
        }
        System.out.println("Deu Ruim: " + msg.getMensagem());
        prev.rollbackOrdem(msg);
        
    }
}
