
package com.tads.dac.cliente.mensageria;

import com.tads.dac.cliente.DTO.ClienteEndDTO;
import com.tads.dac.cliente.DTO.MensagemDTO;
import com.tads.dac.cliente.DTO.RejeitaClienteDTO;
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
public class ConsumerSagaRejeitaClienteCliente {
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private ClienteRepository rep;
    

    @RabbitListener(queues = "rejeita-cliente-con-saga")
    public void consultaCliente(@Payload MensagemDTO msg) {
        Long idCliente = mapper.map(msg.getSendObj(), Long.class);
        Optional<Cliente> cl  = rep.findById(idCliente);
        if(cl.isPresent()){
            RejeitaClienteDTO dto = new RejeitaClienteDTO();
            dto.setEmail(cl.get().getEmail());
            dto.setIdCLiente(cl.get().getId());
            
            msg.setReturnObj(dto);
        }else{
            msg.setMensagem("O Cliente Não Existe!");
        }
        template.convertAndSend("rejeita-cliente-con-saga-receive", msg);
    }
    
    @RabbitListener(queues = "rejeita-cliente-saga")
    public void rejeitaCliente(@Payload MensagemDTO msg) {
        RejeitaClienteDTO dto = mapper.map(msg.getReturnObj(), RejeitaClienteDTO.class);
        
        Optional<Cliente> cli = rep.findById(dto.getIdCLiente());
        if(cli.isPresent()){
            Cliente cliente = cli.get();
            ClienteEndDTO retDto = mapper.map(cliente, ClienteEndDTO.class);
            msg.setSendObj(retDto);
            
            rep.deleteById(dto.getIdCLiente());
            
        }else{
            msg.setMensagem("Essa Cliente Não Existe!");
        }
        
        template.convertAndSend("rejeita-cliente-saga-receive", msg);
    }
    
    @RabbitListener(queues = "rejeita-cliente-saga-rollback")
    public void rejeitaClienteRollback(@Payload MensagemDTO msg) {
        ClienteEndDTO retDto = mapper.map(msg.getSendObj(), ClienteEndDTO.class);
        Cliente cliente = mapper.map(retDto, Cliente.class);
        rep.save(cliente);
    }
    
}
