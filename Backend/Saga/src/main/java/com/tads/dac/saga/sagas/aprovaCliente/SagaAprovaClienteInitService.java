
package com.tads.dac.saga.sagas.aprovaCliente;

import com.tads.dac.saga.sagas.autocadastro.*;
import com.tads.dac.saga.DTO.AutocadastroDTO;
import com.tads.dac.saga.DTO.ClienteAutocadastroDTO;
import com.tads.dac.saga.DTO.ClienteEndDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SagaAprovaClienteInitService {
    
    @Autowired
    private Saga1AprovaClienteProducerConsumer saga;
   
        
    public void initSagaAutocadastro(Long idCliente){
        saga.requestConsulta(idCliente);
    }
}
