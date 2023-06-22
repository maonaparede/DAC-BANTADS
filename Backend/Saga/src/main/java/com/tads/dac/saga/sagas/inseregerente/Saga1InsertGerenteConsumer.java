
package com.tads.dac.saga.sagas.inseregerente;

import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.InsertGerenteGerente;
import com.tads.dac.saga.repository.InsertGerenteGerenteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga1InsertGerenteConsumer {
 
    @Autowired
    private Saga2InsertGerenteContaProducer next;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private InsertGerenteGerenteRepository rep;   
    
    @RabbitListener(queues = "ger-save-gerente-saga-receive")
    public void receiveMsg(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            //Recebe GerenteDTO pelo returnObj -> Converte pro formato de inserir no bd direto
            InsertGerenteGerente model = mapper.map(msg.getReturnObj(), InsertGerenteGerente.class);
            model = rep.save(model);

            msg.setSagaId(model.getSagaId()); //Salva o id do saga
            
            next.commitOrdem(msg);
            return;
        }
        
        //Deu algum erro coloque código pra executar aqui
    }
}