
package com.tads.dac.conta.mensageria;

import com.tads.dac.conta.DTOs.ContaDTO;
import com.tads.dac.conta.DTOs.GerenciadoSaldoDTO;
import com.tads.dac.conta.DTOs.OperacaoBdDTO;
import java.math.BigDecimal;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProducerGerenteSaldoSync {
    
    @Autowired
    private AmqpTemplate template;
    
    public void syncClienteSaldo(Long idCliente, BigDecimal saldo){
        System.out.println("Top: " + saldo);
        GerenciadoSaldoDTO dto = new GerenciadoSaldoDTO(idCliente, saldo);
        template.convertAndSend("gerente", dto);
    }
}
