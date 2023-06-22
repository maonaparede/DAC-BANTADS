
package com.tads.dac.saga.sagas.autocadastro;

import com.tads.dac.saga.DTO.AutocadastroDTO;
import com.tads.dac.saga.DTO.ClienteAutocadastroDTO;
import com.tads.dac.saga.DTO.ClienteEndDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SagaAutocadastroInitService {
    
    @Autowired
    private Saga1AutocadastroClienteProducer prod;
    
    @Autowired
    private ModelMapper mapper;
        
    public void initSagaAutocadastro(ClienteAutocadastroDTO dto){
        MensagemDTO msg = new MensagemDTO();
        AutocadastroDTO auth = new AutocadastroDTO();
        
        ClienteEndDTO cli = mapper.map(dto, ClienteEndDTO.class);
        auth.setSenha(dto.getSenha());
        auth.setSalario(cli.getSalario());
        
        msg.setSendObj(cli);
        msg.setReturnObj(auth);
        
        prod.commitOrdem(msg);
    }
}
