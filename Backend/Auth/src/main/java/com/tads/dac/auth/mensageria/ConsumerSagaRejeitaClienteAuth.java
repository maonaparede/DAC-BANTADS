
package com.tads.dac.auth.mensageria;

import com.tads.dac.auth.DTOs.AuthTotalDTO;
import com.tads.dac.auth.DTOs.MensagemDTO;
import com.tads.dac.auth.DTOs.RejeitaClienteDTO;
import com.tads.dac.auth.service.AuthService;
import org.modelmapper.ModelMapper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaRejeitaClienteAuth {
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private AuthService serv;
    

    @RabbitListener(queues = "rejeita-auth-saga")
    public void rejeitaCliente(@Payload MensagemDTO msg) {
        try{
            RejeitaClienteDTO dto = mapper.map(msg.getReturnObj(), RejeitaClienteDTO.class);
            AuthTotalDTO dtoRet = serv.rejeitaCliente(dto);
            msg.setSendObj(dtoRet);
            
        }catch(Exception e){
            msg.setMensagem(e.getMessage());
        }
        template.convertAndSend("rejeita-auth-saga-receive", msg);
    }
    
    @RabbitListener(queues = "rejeita-auth-saga-rollback")
    public void rejeitaClienteRollback(@Payload MensagemDTO msg) {
        AuthTotalDTO dto = mapper.map(msg.getSendObj(), AuthTotalDTO.class);
        serv.rejeitaClienteRollback(dto);
    }
    
}
