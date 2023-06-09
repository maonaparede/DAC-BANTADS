
package com.tads.dac.saga.sagas.autocadastro;

import com.tads.dac.saga.DTO.ContaDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.AutocadastroConta;
import com.tads.dac.saga.repository.AutocadastroContaRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Saga5AutocadastroContaUpdateProducer{

    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AutocadastroContaRepository rep;
    
    @Autowired
    private Saga4AutocadastroGerenteProducer prev;

    
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersAutocadastro.queueAutoContaUpCommit, dto);
    }

    public void rollbackOrdem(MensagemDTO msg) {
        if(msg.getSagaId() != null){
            Optional<AutocadastroConta> model = rep.findById(msg.getSagaId());
            if(model.isPresent()){
                ContaDTO dto = mapper.map(model, ContaDTO.class);
                msg.setSendObj(dto);
                template.convertAndSend(ConfigProducersAutocadastro.queueAutoContaUpRollback, msg);
                rep.deleteById(msg.getSagaId());
                //Faz alguma coisa
                prev.rollbackOrdem(msg);
            }else{
                System.err.println("Id Não Existe - Rollback de Saga3AutocadastroContaProducer");
            }
        }else{
            System.err.println("Id não pode ser Null - Rollback de Saga3AutocadastroContaProducer");
        }
    }
    
}
