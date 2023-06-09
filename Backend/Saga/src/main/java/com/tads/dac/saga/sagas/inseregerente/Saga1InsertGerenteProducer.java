
package com.tads.dac.saga.sagas.inseregerente;

import com.tads.dac.saga.DTO.GerentePrimeiraContaDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.InsertGerenteGerente;
import com.tads.dac.saga.repository.InsertGerenteGerenteRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Saga1InsertGerenteProducer{

    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private InsertGerenteGerenteRepository rep;

    
    //Primeiro da sequencia
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersInsertGerente.queueGerenteInsertGerente, dto);
    }

    
    public void rollbackOrdem(MensagemDTO msg) {
        if(msg.getSagaId() != null){
            Optional<InsertGerenteGerente> model = rep.findById(msg.getSagaId());
            if(model.isPresent()){
                GerentePrimeiraContaDTO dto = mapper.map(model.get(), GerentePrimeiraContaDTO.class);
                msg.setSendObj(dto);
                template.convertAndSend(ConfigProducersInsertGerente.queueGerenteInsertGerenteRollback, msg);
                //Faz alguma coisa
                rep.deleteById(msg.getSagaId());
                
            }else{
                System.err.println("Id Não Existe - Rollback de Saga1InsertGerenteProducer");
            }
        }else{
            System.err.println("Id não pode ser Null - Rollback de Saga1InsertGerenteProducer");
        }
    }
    
}