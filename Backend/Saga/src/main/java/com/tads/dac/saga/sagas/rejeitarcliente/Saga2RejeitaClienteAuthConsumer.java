
package com.tads.dac.saga.sagas.rejeitarcliente;

import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.RejeitaClienteAuth;
import com.tads.dac.saga.repository.RejeitaClienteAuthRepository;
import com.tads.dac.saga.repository.RejeitaClienteContentEmailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga2RejeitaClienteAuthConsumer {
    
    @Autowired
    private Saga3RejeitaClienteContaProducer next;
    
    @Autowired
    private RejeitaClienteAuthRepository rep;
    
    @Autowired
    private ModelMapper mapper; 
    
    @Autowired
    private RejeitaClienteContentEmailRepository repEmail;
    
    @RabbitListener(queues = "rejeita-auth-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            //Recebe AuthTotalDTO
            RejeitaClienteAuth model = mapper.map(msg.getSendObj(), RejeitaClienteAuth.class);
            model.setSagaId(msg.getSagaId());
            rep.save(model);
            
            next.commitOrdem(msg);
            return;
        }
        System.out.println("Deu Ruim: " + msg.getMensagem());
        repEmail.deleteById(msg.getSagaId());
        
    }
}
