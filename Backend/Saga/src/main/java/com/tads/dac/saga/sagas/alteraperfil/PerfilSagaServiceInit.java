
package com.tads.dac.saga.sagas.alteraperfil;

import com.tads.dac.saga.DTO.ClienteEndDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilSagaServiceInit{
    
    @Autowired
    private Saga2PerfilContaProducer cont;
    
    @Autowired
    private Saga1PerfilClienteProducer cli;
    
    public void initSaga(ClienteEndDTO dto){
        MensagemDTO msg = new MensagemDTO();
        msg.setSendObj(dto);
        
        cli.commitOrdem(msg);
    }
}
