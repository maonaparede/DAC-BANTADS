
package com.tads.dac.saga.sagas.removegerente;

import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.DTO.RemoveGerenteDTO;
import com.tads.dac.saga.model.RemoveGerenteContas;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.tads.dac.saga.repository.RemoveGerenteContasRepository;

@Component
public class Saga2RemGerContaProducer{

    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private RemoveGerenteContasRepository rep;

    
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersRemGerente.queueGerenteRemContaCommit, dto);
    }

    public void rollbackOrdem(MensagemDTO msg) {
        if(msg.getSagaId() != null){
            Optional<RemoveGerenteContas> model = rep.findById(msg.getSagaId());
            if(model.isPresent()){
                
                RemoveGerenteDTO dto = new RemoveGerenteDTO();
                dto.setContas(model.get().getContas());
                dto.setGerenteIdNew(model.get().getGerenteIdNew());
                dto.setGerenteIdOld(model.get().getGerenteIdOld());
                dto.setGerenteNameOld(model.get().getGerenteNameOld());
                
                msg.setSendObj(dto);
                template.convertAndSend(ConfigProducersRemGerente.queueGerenteRemContaRollback, msg);
                rep.deleteById(msg.getSagaId());
                //Faz alguma coisa
            }else{
                System.err.println("Id Não Existe - Rollback de RemGerProducer");
            }
        }else{
            System.err.println("Id não pode ser Null - Rollback de RemGerProducer");
        }
    }
    
}
