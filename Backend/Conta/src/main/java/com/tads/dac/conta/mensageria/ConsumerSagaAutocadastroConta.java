
package com.tads.dac.conta.mensageria;

import com.tads.dac.conta.DTOs.AutocadastroDTO;
import com.tads.dac.conta.DTOs.ContaDTO;
import com.tads.dac.conta.DTOs.GerenciadoGerenteDTO;
import com.tads.dac.conta.DTOs.MensagemDTO;
import com.tads.dac.conta.exception.ClienteNotFoundException;
import com.tads.dac.conta.exception.ContaConstraintViolation;
import com.tads.dac.conta.service.SagaServiceCUD;
import org.modelmapper.ModelMapper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaAutocadastroConta {
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AmqpTemplate template; 
    
    @Autowired
    private SagaServiceCUD serv;
    

    @RabbitListener(queues = "auto-conta-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        AutocadastroDTO dto = mapper.map(msg.getSendObj(), AutocadastroDTO.class);
        
        try {
            ContaDTO contaDto = serv.saveAutocadastro(dto.getIdCliente(), dto.getSalario());
            msg.setSendObj(contaDto);
            
            dto.setIdConta(contaDto.getIdConta());
            msg.setReturnObj(dto);
        } catch (ContaConstraintViolation ex) {
            msg.setMensagem(ex.getMessage());
        }
        
        template.convertAndSend("auto-conta-saga-receive", msg);
    }

    @RabbitListener(queues = "auto-conta-saga-rollback")
    public void rollbackOrdem(@Payload MensagemDTO msg) {
        ContaDTO dto = mapper.map(msg.getSendObj(), ContaDTO.class);
        serv.rollbackAutocadastro(dto.getIdCliente());
    }    
    
    
    //5° Passo do Saga Autocadastro
    @RabbitListener(queues = "auto-conta-update-saga")
    public void commitOrdemSaga(@Payload MensagemDTO msg) {
        GerenciadoGerenteDTO dto = mapper.map(msg.getSendObj(), GerenciadoGerenteDTO.class);
        
        try {
            ContaDTO contaDto = serv.finalizaAutocadastroSetaGerente(dto);
            msg.setSendObj(contaDto);
            
            dto.setIdConta(dto.getIdConta());
            msg.setReturnObj(dto);
        } catch (ClienteNotFoundException ex) {
            msg.setMensagem(ex.getMessage());
        }
        
        template.convertAndSend("auto-conta-update-saga-receive", msg);
    }
    
    /*
    @RabbitListener(queues = "auto-conta-update-saga-rollback")
    public void rollbackOrdemSaga(@Payload MensagemDTO msg) {
        ContaDTO dto = mapper.map(msg.getSendObj(), ContaDTO.class);
        serv.rollbackAutocadastro(dto.getIdCliente());
    } 
    */
}
