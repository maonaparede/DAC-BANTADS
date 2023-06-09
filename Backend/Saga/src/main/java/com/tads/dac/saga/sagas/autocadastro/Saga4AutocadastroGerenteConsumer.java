
package com.tads.dac.saga.sagas.autocadastro;

import com.tads.dac.saga.DTO.GerenciadoGerenteDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.AutocadastroGerente;
import com.tads.dac.saga.repository.AutocadastroGerenteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga4AutocadastroGerenteConsumer {
    
    @Autowired
    private Saga3AutocadastroContaProducer prev;
    
    @Autowired
    private AutocadastroGerenteRepository rep;
    
    @Autowired
    private Saga5AutocadastroContaUpdateProducer next;
    
    @Autowired
    private ModelMapper mapper; 
    
    @RabbitListener(queues = "auto-gerente-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        //Recebe o GerenciadoGerenteDTO e converte
        if(msg.getMensagem() == null){
            GerenciadoGerenteDTO dto = mapper.map(msg.getSendObj(), GerenciadoGerenteDTO.class);
            AutocadastroGerente model = mapper.map(dto, AutocadastroGerente.class);
            model.setSagaId(msg.getSagaId());
            
            rep.save(model);
            
            next.commitOrdem(msg);
            return;
        }
        
        prev.rollbackOrdem(msg);
    }
}
