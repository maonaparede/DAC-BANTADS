
package com.tads.dac.saga.sagas.autocadastro;

import com.tads.dac.saga.DTO.GerenciadoGerenteDTO;
import com.tads.dac.saga.DTO.GerenteDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.AutocadastroGerente;
import com.tads.dac.saga.repository.AutocadastroGerenteRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Saga4AutocadastroGerenteProducer{

    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AutocadastroGerenteRepository rep;
    
    @Autowired
    private Saga3AutocadastroContaProducer prev;

    
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersAutocadastro.queueAutoGerenteCommit, dto);
    }

    public void rollbackOrdem(MensagemDTO msg) {
        if(msg.getSagaId() != null){
            Optional<AutocadastroGerente> model = rep.findById(msg.getSagaId());
            if(model.isPresent()){
                GerenciadoGerenteDTO dto = mapper.map(model, GerenciadoGerenteDTO.class);
                msg.setSendObj(dto);
                template.convertAndSend(ConfigProducersAutocadastro.queueAutoGerenteRollback, msg);
                rep.deleteById(msg.getSagaId());
                
                prev.rollbackOrdem(msg);
                //Faz alguma coisa
            }else{
                System.err.println("Id Não Existe - Rollback de Saga4AutocadastroGerenteProducer");
            }
        }else{
            System.err.println("Id não pode ser Null - Rollback de Saga4AutocadastroGerenteProducer");
        }
    }
    
}
