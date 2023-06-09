
package com.tads.dac.saga.sagas.alteraperfil;

import com.tads.dac.saga.DTO.ClienteEndDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.PerfilClienteUpdateSaga;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.tads.dac.saga.repository.PerfilClienteUpdateRepository;

@Component
public class Saga1PerfilClienteProducer{

    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private PerfilClienteUpdateRepository rep;

    
    //Primeiro da sequencia
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersPerfil.queuePerfilCliente, dto);
    }

    
    public void rollbackOrdem(MensagemDTO msg) {
        if(msg.getSagaId() != null){
            Optional<PerfilClienteUpdateSaga> model = rep.findById(msg.getSagaId());
            if(model.isPresent()){
                ClienteEndDTO dto = mapper.map(model.get(), ClienteEndDTO.class);
                msg.setSendObj(dto);
                template.convertAndSend(ConfigProducersPerfil.queuePerfilClienteRollback, msg);
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