
package com.tads.dac.saga.sagas.alteragerente;

import com.tads.dac.saga.DTO.GerenteDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.AlteraGerenteGerente;
import com.tads.dac.saga.repository.AlteraGerenteGerenteRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Saga1AlteraGerenteGerenteProducer{

    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AlteraGerenteGerenteRepository rep;

    
    //Primeiro da sequencia
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersAlteraGerente.queueAlteraGerenteGerente, dto);
    }

    
    public void rollbackOrdem(MensagemDTO msg) {
        if(msg.getSagaId() != null){
            Optional<AlteraGerenteGerente> model = rep.findById(msg.getSagaId());
            if(model.isPresent()){
                GerenteDTO dto = mapper.map(model.get(), GerenteDTO.class);
                msg.setSendObj(dto);
                template.convertAndSend(ConfigProducersAlteraGerente.queueAlteraGerenteGerenteRollback, msg);
                //Faz alguma coisa
                rep.deleteById(msg.getSagaId());
                
            }else{
                System.err.println("Id Não Existe - Rollback de Saga1PerfilClienteProducer");
            }
        }else{
            System.err.println("Id não pode ser Null - Rollback de Saga1PerfilClienteProducer");
        }
    }
    
}