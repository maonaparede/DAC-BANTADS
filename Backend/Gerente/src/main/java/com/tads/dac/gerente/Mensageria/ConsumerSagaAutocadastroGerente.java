
package com.tads.dac.gerente.Mensageria;

import com.tads.dac.gerente.DTOs.GerenciadoSaldoDTO;
import com.tads.dac.gerente.DTOs.MensagemDTO;
import com.tads.dac.gerente.service.SagaService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaAutocadastroGerente {
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AmqpTemplate template; 
    
    @Autowired
    private SagaService serv;
    

    @RabbitListener(queues = "auto-gerente-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        try {
            msg = serv.atribuiGerenteAutocadastro(msg);
        } catch (Exception ex) {
            msg.setMensagem(ex.getMessage());
        }
        
        template.convertAndSend("auto-gerente-saga-receive", msg);
    }

    @RabbitListener(queues = "auto-gerente-saga-rollback")
    public void rollbackOrdem(@Payload MensagemDTO msg) {
        GerenciadoSaldoDTO dto = mapper.map(msg.getSendObj(), GerenciadoSaldoDTO.class);
        serv.rollbackAutocadastro(dto.getIdConta());
    }    
}
