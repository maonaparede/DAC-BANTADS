
package com.tads.dac.saga.sagas.rejeitarcliente;

import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.RejeitaClienteGerenciado;
import com.tads.dac.saga.repository.RejeitaClienteGerenciadosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga4RejeitaClienteGerenteConsumer {
    
    @Autowired
    private Saga5RejeitaClienteClienteProducer next;
    
    @Autowired 
    private Saga3RejeitaClienteContaProducer prev;
            
    @Autowired
    private RejeitaClienteGerenciadosRepository rep;
    
    @Autowired
    private ModelMapper mapper; 
    
    @RabbitListener(queues = "rejeita-gerente-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            //Recebe GerenciadoDTO
            RejeitaClienteGerenciado model = mapper.map(msg.getSendObj(), RejeitaClienteGerenciado.class);
            model.setSagaId(msg.getSagaId());
            rep.save(model);
            
            next.commitOrdem(msg);
            return;
        }
        System.out.println("Deu Ruim: " + msg.getMensagem());
        prev.rollbackOrdem(msg);
    }
}
