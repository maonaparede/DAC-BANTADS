
package com.tads.dac.auth.mensageria;

import com.tads.dac.auth.DTOs.PerfilUpdateDTO;
import com.tads.dac.auth.DTOs.MensagemDTO;
import com.tads.dac.auth.exception.ContaAlredyExists;
import com.tads.dac.auth.exception.ContaNotExistException;
import com.tads.dac.auth.service.AuthService;
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

    //Refaz
    @RabbitListener(queues = "perfil-auth-saga-rollback")
    public void rollbackOrdem(@Payload MensagemDTO msg) {
        try {
            PerfilUpdateDTO dto = mapper.map(msg.getReturnObj(), PerfilUpdateDTO.class);
            serv.updateAuth(dto.getNewEmail(), dto.getOldEmail());
        } catch (ContaAlredyExists | ContaNotExistException ex) {
            System.out.println("Deu erro no Módulo Auth altera Perfil: " + ex.getMessage());
        }
    }
    
}
