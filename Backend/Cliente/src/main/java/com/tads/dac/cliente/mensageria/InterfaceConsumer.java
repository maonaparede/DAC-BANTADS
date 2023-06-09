
package com.tads.dac.cliente.mensageria;

import com.tads.dac.cliente.DTO.MensagemDTO;


public interface InterfaceConsumer{
    
    public void commitOrdem(MensagemDTO dto);
    
    public void rollbackOrdem(MensagemDTO msg);

}
