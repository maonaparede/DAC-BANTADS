
package com.tads.dac.saga.sagas.inseregerente;

import com.tads.dac.saga.DTO.GerenciadoGerenteSagaInsertDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.InsertGerenteConta;
import com.tads.dac.saga.repository.InsertGerenteContaRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Saga2InsertGerenteContaProducer{

    @Autowired
    private AmqpTemplate template;

    @Autowired
    private Saga1InsertGerenteProducer prev;
    
    @Autowired
    private InsertGerenteContaRepository rep;
    
    @Autowired
    private ModelMapper mapper;
    
    public void commitOrdem(MensagemDTO dto) {
        template.convertAndSend(ConfigProducersInsertGerente.queueGerenteInsertContaCommit, dto);
    }

    public void rollbackOrdem(MensagemDTO msg) {
        if (msg.getSagaId() != null) {
            Optional<InsertGerenteConta> model = rep.findById(msg.getSagaId());
            if (model.isPresent()) {
                GerenciadoGerenteSagaInsertDTO dto = mapper.map(model.get(), GerenciadoGerenteSagaInsertDTO.class);
                msg.setSendObj(dto);
                template.convertAndSend(ConfigProducersInsertGerente.queueGerenteInsertContaRollback, msg);
                //Faz alguma coisa
                rep.deleteById(msg.getSagaId());
                prev.rollbackOrdem(msg);
            } else {
                System.err.println("Id Não Existe - Rollback de Saga2InsertGerenteContaProducer");
            }
        } else {
            System.err.println("Id não pode ser Null - Rollback de Saga2InsertGerenteContaProducer");
        }
    }

    
}
