
package com.tads.dac.gerente.Mensageria;

import com.tads.dac.gerente.DTOs.MensagemDTO;
import com.tads.dac.gerente.service.SagaService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaInsertGerente {
    
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private SagaService serv;
    
    @Autowired
    private ModelMapper mapper;
   
    @RabbitListener(queues = "ger-save-gerente-saga")
    public void insertGerente(@Payload MensagemDTO msg){
        try{
            msg = serv.novoGerente(msg);
        }catch(Exception e){
            msg.setMensagem("Aconteceu Algum Erro no Saga Insert Gerente No Módulo Gerente");
        }
        template.convertAndSend("ger-save-gerente-saga-receive", msg);
    }


    @RabbitListener(queues = "ger-save-gerente-saga-rollback")
    public void rollbackInsertGerente(@Payload MensagemDTO msg){
        serv.deleteGerente(msg);
    }
}