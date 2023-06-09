
package com.tads.dac.saga.sagas.aprovaCliente;

import com.tads.dac.saga.sagas.removegerente.*;
import com.tads.dac.saga.DTO.GerenteDTO;
import com.tads.dac.saga.DTO.GerenteIdNomeDTO;
import com.tads.dac.saga.DTO.GerenteNewOldDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga1AprovaClienteProducerConsumer {
    
    @Autowired
    private Saga2AprovaClienteContaProducer next;
    
    @Autowired
    private AmqpTemplate template;
    
    public void requestConsulta(Long idCliente){
        MensagemDTO msg = new MensagemDTO();
        msg.setSendObj(idCliente);
        template.convertAndSend(ConfigProducersAprovaCliente.queueAprovaClienteCliente, msg);
    }
    
    @RabbitListener(queues = "aprova-cliente-saga-receive")
    public void receiveConsulta(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            next.commitOrdem(msg);
            return;
        }
        System.out.println(msg.getMensagem());
        //Faz Algo
    }
}
