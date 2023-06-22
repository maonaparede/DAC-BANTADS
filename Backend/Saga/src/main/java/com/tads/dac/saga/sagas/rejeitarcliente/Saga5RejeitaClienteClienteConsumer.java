
package com.tads.dac.saga.sagas.rejeitarcliente;

import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.model.RejeitaClienteCliente;
import com.tads.dac.saga.model.RejeitaClienteContentEmail;
import com.tads.dac.saga.repository.RejeitaClienteClienteRepository;
import com.tads.dac.saga.repository.RejeitaClienteContentEmailRepository;
import com.tads.dac.saga.util.EnviarEmail;
import com.tads.dac.saga.util.TemplateEmailRejeitado;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Saga5RejeitaClienteClienteConsumer {
    
    
    @Autowired 
    private Saga4RejeitaClienteGerenteProducer prev;
            
    @Autowired
    private RejeitaClienteClienteRepository rep;
    
    @Autowired
    private ModelMapper mapper; 
    
    @Autowired
    private RejeitaClienteContentEmailRepository repEmail;
    
    @Autowired
    private EnviarEmail email;
    
    @RabbitListener(queues = "rejeita-cliente-saga-receive")
    public void receiveCommit(@Payload MensagemDTO msg) {
        if(msg.getMensagem() == null){
            //Recebe ClienteEndDTO
            RejeitaClienteCliente model = mapper.map(msg.getSendObj(), RejeitaClienteCliente.class);
            model.setSagaId(msg.getSagaId());
            rep.save(model);
            
            enviaEmailRejeitado(msg);
            return;
        }
        System.out.println("Deu Ruim: " + msg.getMensagem());
        prev.rollbackOrdem(msg);
    }

    private void enviaEmailRejeitado(MensagemDTO msg) {
        Optional<RejeitaClienteContentEmail> model = repEmail.findById(msg.getSagaId());
        if(model.isPresent()){
            TemplateEmailRejeitado rej = 
                    new TemplateEmailRejeitado(model.get().getEmailTo(), model.get().getContent());
            
            email.sendEmail(rej);
        }
    }
}
