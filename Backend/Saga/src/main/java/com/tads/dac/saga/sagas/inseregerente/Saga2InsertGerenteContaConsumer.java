
package com.tads.dac.saga.sagas.inseregerente;

import com.tads.dac.saga.DTO.AuthDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.InsertGerenteConta;
import com.tads.dac.saga.repository.InsertGerenteContaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga2InsertGerenteContaConsumer {
    
    @Autowired
    private Saga1InsertGerenteProducer prev;
    
    @Autowired
    private Saga3InsertGerenteAuthProducer next;
    
    @Autowired
    private InsertGerenteContaRepository rep;
    
    @Autowired
    private ModelMapper mapper; 
    
    @RabbitListener(queues = "ger-save-conta-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        //Recebe um GerenciadoGerenteSagaInsertDTO no sendObj
        if(msg.getMensagem() == null){
            InsertGerenteConta model =
                    mapper.map(msg.getSendObj(), InsertGerenteConta.class);
            
            model.setSagaId(msg.getSagaId());
            rep.save(model);
            
            msg.setSendObj(msg.getReturnObj());//AuthDTO 
            msg.setReturnObj(null); //limpa o campo
            
            next.commitOrdem(msg);
            return;
        }
        
        prev.rollbackOrdem(msg);
        //Faz Alguma coisa
    }
}
