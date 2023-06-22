
package com.tads.dac.saga.sagas.rejeitarcliente;

import com.tads.dac.saga.DTO.ClienteEndDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.RejeitaClienteCliente;
import com.tads.dac.saga.repository.RejeitaClienteClienteRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Saga5RejeitaClienteClienteProducer{

    @Autowired
    private AmqpTemplate template;
   
    @Autowired
    private RejeitaClienteClienteRepository rep;

    @Autowired
    private ModelMapper mapper; 
    
    @Autowired
    private Saga4RejeitaClienteGerenteProducer prev;
    
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersRejeitaCliente.queueRejeitaClienteCliente, dto);
    }

    public void rollbackOrdem(MensagemDTO msg) {
        if (msg.getSagaId() != null) {
            Optional<RejeitaClienteCliente> model = rep.findById(msg.getSagaId());
            if (model.isPresent()) {
                ClienteEndDTO dto = mapper.map(model.get(), ClienteEndDTO.class);
                msg.setSendObj(dto);
                template.convertAndSend(ConfigProducersRejeitaCliente.queueRejeitaClienteClienteRollback, msg);
                rep.deleteById(msg.getSagaId());
                //Faz alguma coisa
                prev.rollbackOrdem(msg);
            } else {
                System.err.println("Id Não Existe - Rollback de Saga5RejeitaClienteClienteProducer");
            }
        } else {
            System.err.println("Id não pode ser Null - Rollback de Saga5RejeitaClienteClienteProducer");
        }
    }
    
}
