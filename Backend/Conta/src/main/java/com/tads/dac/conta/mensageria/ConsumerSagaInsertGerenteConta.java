
package com.tads.dac.conta.mensageria;

import com.tads.dac.conta.DTOs.GerenciadoGerenteSagaInsertDTO;
import com.tads.dac.conta.DTOs.GerentePrimeiraContaDTO;
import com.tads.dac.conta.DTOs.MensagemDTO;
import com.tads.dac.conta.exception.changeGerenteException;
import com.tads.dac.conta.service.SagaServiceCUD;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaInsertGerenteConta {
    
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AmqpTemplate template; 
    
    @Autowired
    private SagaServiceCUD serv;
    

    @RabbitListener(queues = "ger-save-conta-saga")
    public void commitOrdem(@Payload MensagemDTO msg) {
        GerentePrimeiraContaDTO dto = 
                mapper.map(msg.getSendObj(), GerentePrimeiraContaDTO.class);
        
        try {
            GerenciadoGerenteSagaInsertDTO ret = serv.changeGerente(dto);
            msg.setSendObj(ret);
        } catch (changeGerenteException ex) {
            msg.setMensagem(ex.getMessage());
        }
        template.convertAndSend("ger-save-conta-saga-receive", msg);
    }

    @RabbitListener(queues = "ger-save-conta-saga-rollback")
    public void rollbackOrdem(@Payload MensagemDTO msg) {
        
        //Inverte o gerente novo pelo velho pra desfazer a operação
        GerenciadoGerenteSagaInsertDTO dto = mapper.map(msg.getSendObj(), GerenciadoGerenteSagaInsertDTO.class);
        
        GerentePrimeiraContaDTO dtoR = new GerentePrimeiraContaDTO();
        dtoR.setId(dto.getGerenteIdOld());
        dtoR.setNome(dto.getGerenteNomeOld());
        dtoR.setPrimeiraConta(dto.getIdConta());
        
        try {
            serv.changeGerente(dtoR);
        } catch (changeGerenteException ex) {
            System.err.println("Ocorreu um erro no rollback de inserção do gerente - Contate o Desenvolvedor");
        }
        
    }    
}
