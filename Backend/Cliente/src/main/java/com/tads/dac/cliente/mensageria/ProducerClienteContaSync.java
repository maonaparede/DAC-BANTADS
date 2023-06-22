
package com.tads.dac.cliente.mensageria;

import com.tads.dac.cliente.DTO.ClienteContaDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProducerClienteContaSync {
    
    @Autowired
    private AmqpTemplate template;
    
    public void syncCliente(ClienteContaDTO dto){
        template.convertAndSend("cliente", dto);
    }
}
