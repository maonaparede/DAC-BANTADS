
package com.tads.dac.saga.sagas.alteragerente;

import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.AlteraGerenteAuth;
import com.tads.dac.saga.repository.AlteraGerenteAuthRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga2AlteraGerenteAuthConsumer {
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private AlteraGerenteAuthRepository rep;   
    
    @Autowired
    private Saga1AlteraGerenteGerenteProducer prev;
    
    @RabbitListener(queues = "alt-ger-auth-saga-receive")
    public void receiveMsg(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            //Recebe AlteraGerenteDTO
            AlteraGerenteAuth model = mapper.map(msg.getReturnObj(), AlteraGerenteAuth.class);
            model.setSagaId(msg.getSagaId());
            
            rep.save(model);
            
            //Faz algo
            return;
        }
        
        prev.rollbackOrdem(msg);
        //Deu algum erro coloque código pra executar aqui
    }
}