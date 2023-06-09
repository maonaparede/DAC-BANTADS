
package com.tads.dac.gerente.Mensageria;

import com.tads.dac.gerente.DTOs.GerenciadoDTO;
import com.tads.dac.gerente.DTOs.MensagemDTO;
import com.tads.dac.gerente.DTOs.RejeitaClienteDTO;
import com.tads.dac.gerente.exceptions.GerenciadoDoesntExistException;
import com.tads.dac.gerente.service.SagaService;
import org.modelmapper.ModelMapper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaRejeitaClienteGerente {
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private SagaService serv;
    

    @RabbitListener(queues = "rejeita-gerente-saga")
    public void rejeitaCliente(@Payload MensagemDTO msg) {
        RejeitaClienteDTO dto = mapper.map(msg.getReturnObj(), RejeitaClienteDTO.class);
        
        try{
            GerenciadoDTO dtoRet = serv.rejeitaCliente(dto);
            msg.setSendObj(dtoRet);
        }catch(GerenciadoDoesntExistException e){
            msg.setMensagem(e.getMessage());
        }
        
        template.convertAndSend("rejeita-gerente-saga-receive", msg);
    }
    
    @RabbitListener(queues = "rejeita-gerente-saga-rollback")
    public void rejeitaClienteRollback(@Payload MensagemDTO msg) {
        GerenciadoDTO dtoRet = mapper.map(msg.getSendObj(), GerenciadoDTO.class);
        serv.rejeitaClienteRollback(dtoRet);
    }
    
}
