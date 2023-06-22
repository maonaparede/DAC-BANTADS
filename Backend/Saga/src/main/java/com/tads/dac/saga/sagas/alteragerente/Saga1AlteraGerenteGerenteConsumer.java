
package com.tads.dac.saga.sagas.alteragerente;

import com.tads.dac.saga.sagas.alteraperfil.*;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.AlteraGerenteGerente;
import com.tads.dac.saga.model.PerfilClienteUpdateSaga;
import com.tads.dac.saga.repository.AlteraGerenteGerenteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.tads.dac.saga.repository.PerfilClienteUpdateRepository;

@Component
public class Saga1AlteraGerenteGerenteConsumer {
 
    @Autowired
    private Saga2AlteraGerenteAuthProducer next;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AlteraGerenteGerenteRepository rep;   
    
    @RabbitListener(queues = "alt-ger-gerente-saga-receive")
    public void receiveMsg(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            
            AlteraGerenteGerente model = mapper.map(msg.getSendObj(), AlteraGerenteGerente.class);
            model = rep.save(model);

            msg.setSagaId(model.getSagaId());
            
            msg.setSendObj(model.getEmail());
        
            next.commitOrdem(msg);
            return;
        }
        
        //Deu algum erro coloque código pra executar aqui
    }
}