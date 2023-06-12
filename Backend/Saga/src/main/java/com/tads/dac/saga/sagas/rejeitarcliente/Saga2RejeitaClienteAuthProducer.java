
package com.tads.dac.saga.sagas.rejeitarcliente;

import com.tads.dac.saga.DTO.AuthTotalDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.RejeitaClienteAuth;
import com.tads.dac.saga.repository.RejeitaClienteAuthRepository;
import com.tads.dac.saga.repository.RejeitaClienteContentEmailRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Saga2RejeitaClienteAuthProducer{

    @Autowired
    private AmqpTemplate template;
   
    @Autowired
    private RejeitaClienteAuthRepository rep;

    @Autowired
    private ModelMapper mapper; 
    
    @Autowired
    private RejeitaClienteContentEmailRepository repEmail;
    
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersRejeitaCliente.queueRejeitaClienteAuth, dto);
    }

    public void rollbackOrdem(MensagemDTO msg) {
        if (msg.getSagaId() != null) {
            Optional<RejeitaClienteAuth> model = rep.findById(msg.getSagaId());
            if (model.isPresent()) {
                AuthTotalDTO dto = mapper.map(model.get(), AuthTotalDTO.class);
                msg.setSendObj(dto);
                template.convertAndSend(ConfigProducersRejeitaCliente.queueRejeitaClienteAuthRollback, msg);
                rep.deleteById(msg.getSagaId());
                
                rollbackEmail(msg);
            } else {
                System.err.println("Id Não Existe - Rollback de Saga2RejeitaClienteAuthProducer");
            }
        } else {
            System.err.println("Id não pode ser Null - Rollback de Saga2RejeitaClienteAuthProducer");
        }
    }
    
    private void rollbackEmail(MensagemDTO msg){
        repEmail.deleteById(msg.getSagaId());
    }
    
}
