
package com.tads.dac.saga.sagas.removegerente;

import com.tads.dac.saga.DTO.GerenteDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.DTO.RemoveGerenteDTO;
import com.tads.dac.saga.model.RemoveGerenteContas;
import com.tads.dac.saga.model.RemoveGerenteGerente;
import com.tads.dac.saga.repository.RemoveGerenteContasRepository;
import com.tads.dac.saga.repository.RemoveGerenteGerenteRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.messaging.handler.annotation.Payload;

@Component
public class Saga3RemGerGerenteProducer{

    @Autowired
    private AmqpTemplate template;  
    
    @Autowired
    private Saga2RemGerContaProducer prev;
    
    @Autowired
    private RemoveGerenteGerenteRepository rep;
 
    @Autowired
    private RemoveGerenteContasRepository repContas;
    
    @Autowired
    private ModelMapper mapper;    
    
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersRemGerente.queueGerenteRemGerenteCommit, dto);
    }

    public void rollback(@Payload MensagemDTO msg) {
        //Se deu Erro Rollback na fase anterior
        if(msg.getSagaId() != null){
            Optional<RemoveGerenteGerente> model = rep.findById(msg.getSagaId());
            Optional<RemoveGerenteContas> model2 = repContas.findById(msg.getSagaId());
            if(model.isPresent()){
                
                GerenteDTO dtoGer = mapper.map(model.get(), GerenteDTO.class);
                msg.setSendObj(dtoGer);
                
                RemoveGerenteDTO dtoConta = mapper.map(model2.get(), RemoveGerenteDTO.class);  
                msg.setReturnObj(dtoConta);
                
                template.convertAndSend(ConfigProducersRemGerente.queueGerenteRemGerenteRollback, msg);
                rep.deleteById(msg.getSagaId());
                //Faz alguma coisa
                prev.rollbackOrdem(msg);
            }else{
                System.err.println("Id Não Existe - Rollback de Saga3RemGerGerenteProducer");
            }
        }else{
            System.err.println("Id não pode ser Null - Rollback de Saga3RemGerGerenteProducer");
        }
    }    
    
}
