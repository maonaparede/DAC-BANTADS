
package com.tads.dac.saga.sagas.autocadastro;

import com.tads.dac.saga.DTO.AuthDTO;
import com.tads.dac.saga.DTO.AutocadastroDTO;
import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.AutocadastroCliente;
import com.tads.dac.saga.repository.AutocadastroClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga1AutocadastroClienteConsumer {
 
    @Autowired
    private Saga2AutocadastroAuthProducer next;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AutocadastroClienteRepository rep;   
    
    @RabbitListener(queues = "auto-cliente-saga-receive")
    public void receiveMsg(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            //Recebe ClienteEndDTO e converte
            AutocadastroCliente model = mapper.map(msg.getSendObj(), AutocadastroCliente.class);
            model = rep.save(model);
            msg.setSagaId(model.getSagaId());
            
            AutocadastroDTO auto = mapper.map(msg.getReturnObj(), AutocadastroDTO.class);

            AuthDTO dto = new AuthDTO();
            dto.setEmail(model.getEmail());
            dto.setSenha(auto.getSenha());
            dto.setTipoUser("C");
            
            msg.setSendObj(dto);
        
            next.commitOrdem(msg);
            return;
        }
        
        //Deu algum erro coloque código pra executar aqui
    }
}