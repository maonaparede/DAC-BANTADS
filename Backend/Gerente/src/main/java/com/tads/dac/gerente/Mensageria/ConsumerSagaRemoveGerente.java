
package com.tads.dac.gerente.Mensageria;

import com.tads.dac.gerente.DTOs.GerenciadoSaldoDTO;
import com.tads.dac.gerente.DTOs.GerenteDTO;
import com.tads.dac.gerente.DTOs.GerenteIdNomeDTO;
import com.tads.dac.gerente.DTOs.GerenteNewOldDTO;
import com.tads.dac.gerente.DTOs.MensagemDTO;
import com.tads.dac.gerente.DTOs.RemoveGerenteDTO;
import com.tads.dac.gerente.model.Gerenciados;
import com.tads.dac.gerente.model.Gerente;
import com.tads.dac.gerente.repository.GerenciadosRepository;
import com.tads.dac.gerente.repository.GerenteRepository;
import com.tads.dac.gerente.service.SagaService;
import java.math.BigDecimal;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSagaRemoveGerente {
    
    @Autowired
    private GerenciadosRepository rep;
    
    @Autowired 
    private GerenteRepository repGer;
    
    @Autowired
    private AmqpTemplate template;
    
    @Autowired
    private SagaService serv;
    
    @Autowired
    private ModelMapper mapper; 
   
    @RabbitListener(queues = "ger-rem-consulta")
    public void consultaGerente(@Payload MensagemDTO msg){
        
        try{
            if ("-".equals(msg.getSendObj().toString())) {
                Long numGerente = repGer.count();
                if (numGerente > 1) {

                    GerenteNewOldDTO dto = mapper.map(msg.getReturnObj(), GerenteNewOldDTO.class);
                    Long id = serv.getGerenteMenosContas(dto.getIdOld());

                    Optional<Gerente> ger = repGer.findById(id);

                    dto.setIdNew(ger.get().getId());
                    dto.setNomeNew(ger.get().getNome());

                    msg.setReturnObj(null); // Reseta o Return obj
                    msg.setSendObj(dto); //coloca o DTO como novo sendObj
                }else{
                    msg.setMensagem("Não É Possível Remover Gerente, Num de Gerente deve ser > 1 Para Ser Removido");
                }
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
            msg.setMensagem(e.getMessage());
        }
        template.convertAndSend("ger-rem-consulta-receive", msg);
    }


    @RabbitListener(queues = "ger-rem-gerente-saga")
    public void removeGerente(@Payload MensagemDTO msg){
        try {
            RemoveGerenteDTO dto = mapper.map(msg.getSendObj(), RemoveGerenteDTO.class);
            Optional<Gerente> ger = repGer.findById(dto.getGerenteIdOld());
            GerenteDTO gerDto = mapper.map(ger.get(), GerenteDTO.class);
            
            msg.setSendObj(gerDto);
            
            rep.transferirTodasAsContas(dto.getGerenteIdOld(), dto.getGerenteIdNew());
            repGer.deleteById(dto.getGerenteIdOld());
        } catch (Exception e) {
            msg.setMensagem("Erro Ao Remover o Gerente No Modulo Gerente: " + e.getMessage());
            
        }
        template.convertAndSend("ger-rem-gerente-saga-receive", msg);
    }
    
    @RabbitListener(queues = "ger-rem-gerente-saga-rollback")
    public void removeGerenteRollback(@Payload MensagemDTO msg){
        try {
            GerenteDTO dtoGer = mapper.map(msg.getSendObj(), GerenteDTO.class);
                
            RemoveGerenteDTO dtoConta = mapper.map(msg.getReturnObj(), RemoveGerenteDTO.class);  

            Gerente ger = mapper.map(dtoGer, Gerente.class);
            ger = repGer.save(ger);
            
            for(Long id : dtoConta.getContas()){
                Gerenciados gerenciados = new Gerenciados(id, Boolean.FALSE, ger);
                rep.save(gerenciados);
            }
        } catch (Exception e) {
            msg.setMensagem("Erro Ao Remover o Gerente No Modulo Gerente: " + e.getMessage());
            
        }
        template.convertAndSend("ger-rem-gerente-saga-receive", msg);
    }
}