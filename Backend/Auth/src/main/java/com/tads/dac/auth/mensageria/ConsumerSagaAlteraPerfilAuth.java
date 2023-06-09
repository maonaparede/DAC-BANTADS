
package com.tads.dac.auth.mensageria;

import com.tads.dac.auth.DTOs.AuthDTO;
import com.tads.dac.auth.DTOs.PerfilUpdateDTO;
import com.tads.dac.auth.DTOs.AuthTotalDTO;
import com.tads.dac.auth.DTOs.MensagemDTO;
import com.tads.dac.auth.exception.ContaAlredyExists;
import com.tads.dac.auth.exception.ContaNotExistException;
import com.tads.dac.auth.exception.EncryptionException;
import com.tads.dac.auth.exception.InvalidUserTypeException;
import com.tads.dac.auth.service.AuthService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaAlteraPerfilAuth {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private AuthService serv;

    @RabbitListener(queues = "perfil-auth-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        PerfilUpdateDTO dto = mapper.map(msg.getReturnObj(), PerfilUpdateDTO.class);
        try {
            if(!dto.getNewEmail().equals(dto.getOldEmail())){
                serv.updateAuth(dto.getOldEmail(), dto.getNewEmail());
            }
        } catch (ContaAlredyExists | ContaNotExistException ex) {
            msg.setMensagem(ex.getMessage());
        }
        template.convertAndSend("perfil-auth-saga-receive", msg);
    }

    @RabbitListener(queues = "perfil-auth-saga-rollback")
    public void rollbackOrdem(@Payload MensagemDTO msg) {
        AuthDTO dto = mapper.map(msg.getSendObj(), AuthDTO.class);
        serv.deleteLogin(dto.getEmail());
    }
    
}
