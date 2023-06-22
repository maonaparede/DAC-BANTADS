
package com.tads.dac.saga.sagas.rejeitarcliente;

import com.tads.dac.saga.DTO.ContaDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.RejeitaClienteConta;
import com.tads.dac.saga.repository.RejeitaClienteContaRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Saga3RejeitaClienteContaProducer{

    @Autowired
    private AmqpTemplate template;
   
    @Autowired
    private RejeitaClienteContaRepository rep;

    @Autowired
    private ModelMapper mapper; 
    
    @Autowired
    private Saga2RejeitaClienteAuthProducer prev;
    
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersRejeitaCliente.queueRejeitaClienteConta, dto);
    }

    public void rollbackOrdem(MensagemDTO msg) {
        if (msg.getSagaId() != null) {
            Optional<RejeitaClienteConta> model = rep.findById(msg.getSagaId());
            if (model.isPresent()) {
                ContaDTO dto = mapper.map(model.get(), ContaDTO.class);
                msg.setSendObj(dto);
                template.convertAndSend(ConfigProducersRejeitaCliente.queueRejeitaClienteContaRollback, msg);
                rep.deleteById(msg.getSagaId());
                //Faz alguma coisa
                prev.rollbackOrdem(msg);
            } else {
                System.err.println("Id Não Existe - Rollback de Saga2RejeitaClienteAuthProducer");
            }
        } else {
            System.err.println("Id não pode ser Null - Rollback de Saga2RejeitaClienteAuthProducer");
        }
    }
    
}
