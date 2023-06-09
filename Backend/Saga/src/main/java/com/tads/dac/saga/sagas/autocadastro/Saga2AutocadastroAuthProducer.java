
package com.tads.dac.saga.sagas.autocadastro;

import com.tads.dac.saga.DTO.AuthDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.AutocadastroAuth;
import com.tads.dac.saga.repository.AutocadastroAuthRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Saga2AutocadastroAuthProducer{

    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AutocadastroAuthRepository rep;
    
    @Autowired
    private Saga1AutocadastroClienteProducer prev;

    
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersAutocadastro.queueAutoAuthCommit, dto);
    }

    public void rollbackOrdem(MensagemDTO msg) {
        if(msg.getSagaId() != null){
            Optional<AutocadastroAuth> model = rep.findById(msg.getSagaId());
            if(model.isPresent()){
                
                AuthDTO dto = mapper.map(model, AuthDTO.class);
                msg.setSendObj(dto);
                
                template.convertAndSend(ConfigProducersAutocadastro.queueAutoAuthRollback, msg);
                rep.deleteById(msg.getSagaId());
                
                prev.rollbackOrdem(msg);
                //Faz alguma coisa
            }else{
                System.err.println("Id Não Existe - Rollback de Saga2AutocadastroAuthProducer");
            }
        }else{
            System.err.println("Id não pode ser Null - Rollback de Saga2AutocadastroAuthProducer");
        }
    }
    
}
