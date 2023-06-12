
package com.tads.dac.saga.sagas.rejeitarcliente;

import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.DTO.RejeitaClienteDTO;
import com.tads.dac.saga.DTO.RejeitaClienteIdContentDTO;
import com.tads.dac.saga.model.RejeitaClienteContentEmail;
import com.tads.dac.saga.repository.RejeitaClienteContentEmailRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga1RejeitaClienteClienteProducerConsumer {
    
    @Autowired
    private Saga2RejeitaClienteAuthProducer next;
    
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private RejeitaClienteContentEmailRepository rep;
    
    @Autowired
    private ModelMapper mapper;
    
    public void requestConsulta(RejeitaClienteIdContentDTO dto){
        MensagemDTO msg = new MensagemDTO();
        
        RejeitaClienteContentEmail model = new RejeitaClienteContentEmail();
        model.setContent(dto.getContent());
        model = rep.save(model);
        msg.setSagaId(model.getSagaId());
        
        
        RejeitaClienteDTO dtoR = new RejeitaClienteDTO();
        dtoR.setIdCliente(dto.getIdCliente());
        msg.setSendObj(dtoR);
        
        template.convertAndSend(ConfigProducersRejeitaCliente.queueRejeitaClienteClienteCon, msg);
    }
    
    @RabbitListener(queues = "rejeita-cliente-con-saga-receive")
    public void receiveConsulta(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            RejeitaClienteDTO dto = mapper.map(msg.getReturnObj(), RejeitaClienteDTO.class);
            Optional<RejeitaClienteContentEmail> md = rep.findById(msg.getSagaId());
            if(md.isPresent()){
                RejeitaClienteContentEmail model = md.get();
                model.setEmailTo(dto.getEmail());
                rep.save(model);
                
                next.commitOrdem(msg);
            }
            
            return;
        }
        System.out.println(msg.getMensagem());
        //Faz Algo
    }
}
