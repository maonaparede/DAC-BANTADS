

package com.tads.dac.saga.util;

import com.tads.dac.saga.DTO.MensagemDTO;
import com.tads.dac.saga.exception.NullDtoObjectException;
import com.tads.dac.saga.exception.RollbackException;
import com.tads.dac.saga.exception.SagaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
public class SagaTemplate{
    
    private List<ItemOrdemMensagem> orderCommit = new ArrayList<>();
    
    public SagaTemplate(List<ItemOrdemMensagem> lista) {
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).setIndex(i);
            orderCommit.add(lista.get(i));
        }
    }
    
    public void commit(MensagemDTO obj) throws RollbackException, NullDtoObjectException{
        try {
            if(obj.getObj() == null) throw new NullDtoObjectException("O DTO DE ENTRADA NÃO PODE SER NULO");
            
            ItemOrdemMensagem item = nextState(null);
            doCommit(item, obj);
            
        } catch (SagaException ex) {
            rollback(orderCommit.get(ex.getIndex()), ex.getSagaId());
            throw new RollbackException(ex.getMessage());
        }
    }
    
    public void rollback(ItemOrdemMensagem current, Long sagaId){
        if(current == null){
            return;
        }
        doRollback(current, sagaId);
    }
    
    private void doCommit(ItemOrdemMensagem item, MensagemDTO obj) throws SagaException{
        
        if(item != null){
            
            obj = item.getItem().commitOrdem(obj);
            
            if(obj.getMensagem() != null){ //Se retornar não nulo é pq aconteceu algum erro
                //Retorna a mensagem de erro e o index da mensageria
                throw new SagaException(obj.getMensagem(), item.getIndex(), obj.getSagaId());
            }
            doCommit(nextState(item), obj);
        }
    }
    
    
    private Boolean doRollback(ItemOrdemMensagem current, Long sagaId){
        if(current != null){
            current.getItem().rollbackOrdem(sagaId);
            current = prevState(current);
            return doRollback(current, sagaId);
        }
        return true;
    }
    
    private ItemOrdemMensagem nextState(ItemOrdemMensagem current){
        int index;
        if(current != null) {
            index = current.getIndex() + 1;
        } else {
            index = 0;
        }
        
        if(index < orderCommit.size()){
            ItemOrdemMensagem item = orderCommit.get(index);
            return item;
        }else{
            return null;
        }
    }

    private ItemOrdemMensagem prevState(ItemOrdemMensagem current){
        int index = current.getIndex() - 1;
        
        if(index >= 0){
            ItemOrdemMensagem item = orderCommit.get(index);
            return item;
        }else{
            return null;
        }
    }
    
}
*/