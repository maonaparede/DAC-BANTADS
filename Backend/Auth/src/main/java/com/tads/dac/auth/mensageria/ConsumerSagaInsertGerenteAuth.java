
package com.tads.dac.auth.mensageria;

import com.tads.dac.auth.DTOs.AuthDTO;
import com.tads.dac.auth.DTOs.MensagemDTO;
import com.tads.dac.auth.exception.ContaAlredyExists;
import com.tads.dac.auth.exception.EncryptionException;
import com.tads.dac.auth.exception.InvalidUserTypeException;
import com.tads.dac.auth.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaInsertGerenteAuth {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private AuthService serv;

    @RabbitListener(queues = "ger-save-auth-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        AuthDTO dto = mapper.map(msg.getSendObj(), AuthDTO.class);
        try {
            dto = serv.insertAuthGerente(dto);
            msg.setSendObj(dto);
        } catch (ContaAlredyExists | InvalidUserTypeException | EncryptionException ex) {
            msg.setMensagem(ex.getMessage());
        }
        template.convertAndSend("ger-save-auth-saga-receive", msg);
    }

    @RabbitListener(queues = "ger-save-auth-saga-rollback")
    public void rollbackOrdem(@Payload MensagemDTO msg) {
        AuthDTO dto = mapper.map(msg.getSendObj(), AuthDTO.class);
        serv.deleteLogin(dto.getEmail());
    }
    
}
