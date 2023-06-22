
package com.tads.dac.saga.sagas.inseregerente;

import com.tads.dac.saga.DTO.AuthDTO;
import com.tads.dac.saga.DTO.GerenteDTO;
import com.tads.dac.saga.DTO.GerenteSenhaDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsertGerenteSagaInitService {

    @Autowired
    private Saga1InsertGerenteProducer prod;
 
    @Autowired
    private ModelMapper mapper;
    
    public void initSagaInsertGerente(GerenteSenhaDTO dto){
        MensagemDTO msg = new MensagemDTO();
        
        GerenteDTO dtoSend = mapper.map(dto, GerenteDTO.class);
        msg.setSendObj(dtoSend);
        
        //Só vai ser usado no 3° Passo do Saga            
        AuthDTO auth = new AuthDTO();
        auth.setEmail(dto.getEmail());
        auth.setSenha(dto.getSenha());
        auth.setTipoUser("G");
        msg.setReturnObj(auth);
        
        prod.commitOrdem(msg);
    }
    
}
