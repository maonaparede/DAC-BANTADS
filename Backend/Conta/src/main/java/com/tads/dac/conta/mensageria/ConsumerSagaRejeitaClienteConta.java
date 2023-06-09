
package com.tads.dac.conta.mensageria;

import com.tads.dac.conta.DTOs.ContaDTO;
import com.tads.dac.conta.DTOs.MensagemDTO;
import com.tads.dac.conta.DTOs.RejeitaClienteDTO;
import com.tads.dac.conta.exception.ClienteNotFoundException;
import com.tads.dac.conta.service.SagaServiceCUD;
import org.modelmapper.ModelMapper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaRejeitaClienteConta {
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private SagaServiceCUD serv;

    @RabbitListener(queues = "rejeita-conta-saga")
    public void rejeitaCliente(@Payload MensagemDTO msg) {
        
        try {
            RejeitaClienteDTO dto = mapper.map(msg.getReturnObj(), RejeitaClienteDTO.class);
            ContaDTO dtoRet = serv.rejeitaCliente(dto);
            msg.setSendObj(dtoRet);
        } catch (ClienteNotFoundException ex) {
            msg.setMensagem(ex.getMessage());
        }
        template.convertAndSend("rejeita-conta-saga-receive", msg);
    }
    
    @RabbitListener(queues = "rejeita-conta-saga-rollback")
    public void rejeitaClienteRollback(@Payload MensagemDTO msg) {
        ContaDTO dto = mapper.map(msg.getSendObj(), ContaDTO.class);
        serv.rollbackRejeitaCliente(dto);
    }
    
}
