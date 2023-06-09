
package com.tads.dac.conta.mensageria;


import com.tads.dac.conta.DTOs.MensagemDTO;
import com.tads.dac.conta.exception.ClienteNotFoundException;
import com.tads.dac.conta.exception.NegativeSalarioException;
import com.tads.dac.conta.service.ContaService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaUpdatePerfilConta implements InterfaceConsumer{
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private ContaService serv;
    
    @Autowired
    private AmqpTemplate template;

    @RabbitListener(queues = "perfil-conta-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        try {
            msg = serv.updateLimite(msg);
        } catch (ClienteNotFoundException | NegativeSalarioException ex) {
            msg.setMensagem(ex.getMessage());
        }
        
        template.convertAndSend("perfil-conta-saga-receive",msg);
    }

    @RabbitListener(queues = "perfil-conta-saga-rollback")
    public void rollbackOrdem(@Payload MensagemDTO msg){
        serv.rollbackOp(msg);
    }
    
}
