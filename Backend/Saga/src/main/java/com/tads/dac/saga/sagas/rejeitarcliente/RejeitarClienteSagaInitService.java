
package com.tads.dac.saga.sagas.rejeitarcliente;

import com.tads.dac.saga.sagas.inseregerente.*;
import com.tads.dac.saga.DTO.GerenteDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.DTO.RejeitaClienteIdContentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RejeitarClienteSagaInitService {

    @Autowired
    private Saga1RejeitaClienteClienteProducerConsumer prod;
    
    public void initSagaInsertGerente(RejeitaClienteIdContentDTO dto){
        
        prod.requestConsulta(dto);
    }
    
}
