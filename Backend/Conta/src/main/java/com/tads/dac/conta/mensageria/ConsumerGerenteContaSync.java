
package com.tads.dac.conta.mensageria;

import com.tads.dac.conta.DTOs.GerenteIdNomeDTO;
import com.tads.dac.conta.service.ContaService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerGerenteContaSync {
    
    @Autowired
    private ContaService serv;

    @RabbitListener(queues = "altera-ger-sync-conta")
    public void escutaSyncConta(@Payload GerenteIdNomeDTO dto){
        serv.atualizaNomeGerente(dto);
    }  
  
}
