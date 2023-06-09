
package com.tads.dac.saga.sagas.inseregerente;

import com.tads.dac.saga.DTO.GerenteDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsertGerenteSagaInitService {

    @Autowired
    private Saga1InsertGerenteProducer prod;
    
    public void initSagaInsertGerente(GerenteDTO dto){
        MensagemDTO msg = new MensagemDTO();
        msg.setSendObj(dto);
        prod.commitOrdem(msg);
    }
    
}
