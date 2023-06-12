
package com.tads.dac.auth.mensageria;

import com.tads.dac.auth.DTOs.AlteraGerenteDTO;
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
public class ConsumerSagaAlteraGerenteAuth {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private AuthService serv;

    @RabbitListener(queues = "alt-ger-auth-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        AlteraGerenteDTO dto = mapper.map(msg.getReturnObj(), AlteraGerenteDTO.class);
        try {
            if(!dto.getNewEmail().equals(dto.getOldEmail())){
                serv.updateAuth(dto.getOldEmail(), dto.getNewEmail());
            }
        } catch (ContaAlredyExists | ContaNotExistException ex) {
            msg.setMensagem(ex.getMessage());
        }
        template.convertAndSend("alt-ger-auth-saga-receive", msg);
    }

    @RabbitListener(queues = "alt-ger-auth-saga-rollback")
    public void rollbackOrdem(@Payload MensagemDTO msg) {
        try {
            AlteraGerenteDTO dto = mapper.map(msg.getReturnObj(), AlteraGerenteDTO.class);
            serv.updateAuth(dto.getNewEmail(), dto.getOldEmail());
        } catch (ContaAlredyExists | ContaNotExistException ex) {
            System.out.println("Deu erro no Módulo Auth altera Gerente: " + ex.getMessage());
        }
    }
    
}
