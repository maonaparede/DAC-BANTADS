
package com.tads.dac.saga.sagas.inseregerente;

import com.tads.dac.saga.DTO.MensagemDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Saga3InsertGerenteAuthProducer{

    @Autowired
    private AmqpTemplate template;
    
    
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersInsertGerente.queueGerenteInsertAuthCommit, dto);
    }
    
}
