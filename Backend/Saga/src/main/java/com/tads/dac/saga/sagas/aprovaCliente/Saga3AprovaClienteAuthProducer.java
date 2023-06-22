
package com.tads.dac.saga.sagas.aprovaCliente;

import com.tads.dac.saga.DTO.MensagemDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Saga3AprovaClienteAuthProducer{

    @Autowired
    private AmqpTemplate template;

    
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersAprovaCliente.queueAprovaClienteAuth, dto);
    }

    public void rollbackOrdem(MensagemDTO msg) {
        template.convertAndSend(ConfigProducersAprovaCliente.queueAprovaClienteAuthRollback, msg);
    }
    
}
