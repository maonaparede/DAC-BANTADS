
package com.tads.dac.conta.mensageria;


import com.tads.dac.conta.DTOs.GerenteNewOldDTO;
import com.tads.dac.conta.DTOs.MensagemDTO;
import com.tads.dac.conta.DTOs.RemoveGerenteDTO;
import com.tads.dac.conta.service.SagaServiceCUD;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaRemoveGerenteConta implements InterfaceConsumer{
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private SagaServiceCUD serv;
    
    @Autowired
    private AmqpTemplate template;

    @RabbitListener(queues = "ger-rem-conta-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        try {
            GerenteNewOldDTO dto = mapper.map(msg.getSendObj(), GerenteNewOldDTO.class);
            RemoveGerenteDTO ger = serv.substituteGerenteConta(dto);
            msg.setSendObj(ger);
        } catch (Exception e) {
            msg.setMensagem(e.getMessage());
        }
        template.convertAndSend("ger-rem-conta-saga-receive",msg);
    }

    @RabbitListener(queues = "ger-rem-conta-saga-rollback")
    public void rollbackOrdem(@Payload MensagemDTO msg){
        RemoveGerenteDTO dto = mapper.map(msg.getSendObj(), RemoveGerenteDTO.class);
        serv.rollbackUpdate(dto);
    }
    
}
