
package com.tads.dac.gerente.Mensageria;

import com.tads.dac.gerente.DTOs.AlteraGerenteDTO;
import com.tads.dac.gerente.DTOs.GerenciadoSaldoDTO;
import com.tads.dac.gerente.DTOs.GerenteDTO;
import com.tads.dac.gerente.DTOs.GerenteIdNomeDTO;
import com.tads.dac.gerente.DTOs.GerenteNewOldDTO;
import com.tads.dac.gerente.DTOs.MensagemDTO;
import com.tads.dac.gerente.DTOs.RemoveGerenteDTO;
import com.tads.dac.gerente.exceptions.GerenteDoesntExistException;
import com.tads.dac.gerente.model.Gerenciados;
import com.tads.dac.gerente.model.Gerente;
import com.tads.dac.gerente.repository.GerenciadosRepository;
import com.tads.dac.gerente.repository.GerenteRepository;
import com.tads.dac.gerente.service.SagaService;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaAlteraGerente {
    
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private SagaService serv;
    
    @Autowired
    private ModelMapper mapper; 
   
    @RabbitListener(queues = "alt-ger-gerente-saga")
    public void alteraGerente(@Payload MensagemDTO msg){
        msg = serv.alteraGerente(msg);
        template.convertAndSend("alt-ger-gerente-saga-receive", msg);
    }

    
    @RabbitListener(queues = "alt-ger-gerente-saga-rollback")
    public void alteraGerenteRollback(@Payload MensagemDTO msg){
        GerenteDTO dtoGer = mapper.map(msg.getSendObj(), GerenteDTO.class);
        serv.alteraGerenteRollback(dtoGer);
    }
}