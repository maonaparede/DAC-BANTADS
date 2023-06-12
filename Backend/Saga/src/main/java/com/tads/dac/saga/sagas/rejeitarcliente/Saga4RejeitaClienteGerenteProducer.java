
package com.tads.dac.saga.sagas.rejeitarcliente;

import com.tads.dac.saga.DTO.GerenciadoDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.RejeitaClienteGerenciado;
import com.tads.dac.saga.repository.RejeitaClienteGerenciadosRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Saga4RejeitaClienteGerenteProducer{

    @Autowired
    private AmqpTemplate template;
   
    @Autowired
    private RejeitaClienteGerenciadosRepository rep;

    @Autowired
    private ModelMapper mapper; 
    
    @Autowired
    private Saga3RejeitaClienteContaProducer prev;
    
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersRejeitaCliente.queueRejeitaClienteGerente, dto);
    }

    public void rollbackOrdem(MensagemDTO msg) {
        if (msg.getSagaId() != null) {
            Optional<RejeitaClienteGerenciado> model = rep.findById(msg.getSagaId());
            if (model.isPresent()) {
                GerenciadoDTO dto = mapper.map(model.get(), GerenciadoDTO.class);
                msg.setSendObj(dto);
                template.convertAndSend(ConfigProducersRejeitaCliente.queueRejeitaClienteGerenteRollback, msg);
                rep.deleteById(msg.getSagaId());
                //Faz alguma coisa
                prev.rollbackOrdem(msg);
            } else {
                System.err.println("Id Não Existe - Rollback de Saga4RejeitaClienteGerenteProducer");
            }
        } else {
            System.err.println("Id não pode ser Null - Rollback de Saga4RejeitaClienteGerenteProducer");
        }
    }
    
}
