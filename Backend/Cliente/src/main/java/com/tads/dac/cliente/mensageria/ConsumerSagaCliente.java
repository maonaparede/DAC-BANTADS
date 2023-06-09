
package com.tads.dac.cliente.mensageria;


import com.tads.dac.cliente.DTO.MensagemDTO;
import com.tads.dac.cliente.exception.ClienteConstraintViolation;
import com.tads.dac.cliente.exception.ClienteNotFoundException;
import com.tads.dac.cliente.exception.NegativeSalarioException;
import com.tads.dac.cliente.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaCliente{
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private ClienteService serv;
    
    @Autowired
    private AmqpTemplate template;

    @RabbitListener(queues = "perfil-cliente-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        
        try{
            System.out.println("Chegou");
            msg = serv.update(msg);
        }catch (ClienteConstraintViolation | ClienteNotFoundException |
            NegativeSalarioException ex ){
            
            msg.setMensagem(ex.getMessage());
        }
        
        template.convertAndSend("perfil-cliente-saga-receive",msg);
    }

    @RabbitListener(queues = "perfil-cliente-saga-rollback")
    public void rollbackOrdem(@Payload MensagemDTO msg) {
        serv.rollbackClienteUpdate(msg);
    }
    
}
