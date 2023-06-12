
package com.tads.dac.saga.sagas.alteragerente;

import com.tads.dac.saga.DTO.GerenteDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlteraGerenteServiceInit{
    
    @Autowired
    private Saga1AlteraGerenteGerenteProducer cli;
    
    public void initSaga(GerenteDTO dto){
        MensagemDTO msg = new MensagemDTO();
        msg.setSendObj(dto);
        
        cli.commitOrdem(msg);
    }
}
