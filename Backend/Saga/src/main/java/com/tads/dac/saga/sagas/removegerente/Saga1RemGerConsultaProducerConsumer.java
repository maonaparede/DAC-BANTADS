
package com.tads.dac.saga.sagas.removegerente;

import com.tads.dac.saga.DTO.GerenteNewOldDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga1RemGerConsultaProducerConsumer {
    
    @Autowired
    private Saga2RemGerContaProducer next;
    
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private ModelMapper mapper;
    
    public void requestConsulta(Long id){
        MensagemDTO msg = new MensagemDTO();
        GerenteNewOldDTO dto = new GerenteNewOldDTO();
        dto.setIdOld(id);
        
        msg.setSendObj("-");
        msg.setReturnObj(dto);
        template.convertAndSend(ConfigProducersRemGerente.queueGerenteRemConsulta, msg);
    }
    
    @RabbitListener(queues = "ger-rem-consulta-receive")
    public void receiveConsulta(@Payload MensagemDTO msg) {
        GerenteNewOldDTO dto = mapper.map(msg.getSendObj(), GerenteNewOldDTO.class);
        if(dto != null && dto.getIdNew().equals(dto.getIdOld())){
            System.out.println("Só Existe 1 Gerente, Não É Possível Prosseguir");
            //Faz Algo
            return;
        }
        if(msg.getMensagem() == null){
            //Ele recebe um obj GerenteNewOldDTO e passa pra prox fase do saga
            next.commitOrdem(msg);
            return;
        }
        //faz algo
    }
}
