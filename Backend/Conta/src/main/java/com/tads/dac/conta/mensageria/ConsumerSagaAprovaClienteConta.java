
package com.tads.dac.conta.mensageria;

import com.tads.dac.conta.DTOs.AutocadastroDTO;
import com.tads.dac.conta.DTOs.ContaDTO;
import com.tads.dac.conta.DTOs.GerenciadoGerenteDTO;
import com.tads.dac.conta.DTOs.MensagemDTO;
import com.tads.dac.conta.exception.ClienteNotFoundException;
import com.tads.dac.conta.exception.ContaConstraintViolation;
import com.tads.dac.conta.exception.SituacaoInvalidaException;
import com.tads.dac.conta.service.SagaServiceCUD;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelmapper.ModelMapper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaAprovaClienteConta {
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AmqpTemplate template; 
    
    @Autowired
    private SagaServiceCUD serv;
    

    @RabbitListener(queues = "aprova-conta-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        try {
            Long idCliente = mapper.map(msg.getSendObj(), Long.class);
            serv.AprovarCliente(idCliente, "A");
        } catch (ClienteNotFoundException ex) {
            msg.setMensagem(ex.getMessage());
        }
        template.convertAndSend("aprova-conta-saga-receive", msg);
    }

    @RabbitListener(queues = "aprova-conta-saga-rollback")
    public void rollbackOrdem(@Payload MensagemDTO msg) {
        try {
            Long idCliente = mapper.map(msg.getSendObj(), Long.class);
            serv.AprovarCliente(idCliente, "E");
        } catch (ClienteNotFoundException ex) {
            msg.setMensagem(ex.getMessage());
        }
    }
    
}
