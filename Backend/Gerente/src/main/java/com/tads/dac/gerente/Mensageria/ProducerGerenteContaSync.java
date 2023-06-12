
package com.tads.dac.gerente.mensageria;

import com.tads.dac.gerente.DTOs.GerenteIdNomeDTO;
import com.tads.dac.gerente.Mensageria.ConfigProducersAll;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class ProducerGerenteContaSync {
    
    @Autowired
    private AmqpTemplate template;
    
    public void AprovarCliente(GerenteIdNomeDTO dto){
        template.convertAndSend(ConfigProducersAll.queueGerenteSyncConta, this);
    }
}
