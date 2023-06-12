
package com.tads.dac.cliente.mensageria;

import com.tads.dac.cliente.DTO.MensagemDTO;
import com.tads.dac.cliente.model.Cliente;
import com.tads.dac.cliente.repository.ClienteRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaAprovaClienteCliente {
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private ClienteRepository rep;
    

    @RabbitListener(queues = "aprova-cliente-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        Long idCliente = mapper.map(msg.getSendObj(), Long.class);
        Optional<Cliente> cl  = rep.findById(idCliente);
        if(cl.isPresent()){
            msg.setReturnObj(cl.get().getEmail());
        }else{
            msg.setMensagem("O Cliente Não Existe!");
        }
        template.convertAndSend("aprova-cliente-saga-receive", msg);
    }
    
    
}
