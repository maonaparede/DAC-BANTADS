
package com.tads.dac.auth.mensageria;

import com.tads.dac.auth.DTOs.AuthDTO;
import com.tads.dac.auth.DTOs.AuthTotalDTO;
import com.tads.dac.auth.DTOs.MensagemDTO;
import com.tads.dac.auth.exception.ContaNotExistException;
import com.tads.dac.auth.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaRemoveGerenteAuth {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private AuthService serv;

    @RabbitListener(queues = "ger-rem-auth-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        try {
            AuthDTO dto = mapper.map(msg.getSendObj(), AuthDTO.class);
            
            AuthTotalDTO dtoTotal = serv.removeGerente(dto);
            msg.setSendObj(dtoTotal);
            
            
        } catch (ContaNotExistException ex) {
            System.out.println("Dá uma olhada aqui ConsumerSagaRemoveGerenteAuth: " + ex.getMessage());
        }
        template.convertAndSend("ger-rem-auth-saga-receive", msg);
    }
    
}
