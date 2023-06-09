
package com.tads.dac.saga.util;

import com.tads.dac.saga.DTO.MensagemDTO;


public interface InterfaceSagaOrquestration{
    
    public void commitOrdem(MensagemDTO msg);
    
    public void rollbackOrdem(MensagemDTO msg);

}
