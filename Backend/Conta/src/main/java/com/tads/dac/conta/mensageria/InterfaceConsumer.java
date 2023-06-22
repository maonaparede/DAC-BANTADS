
package com.tads.dac.conta.mensageria;

import com.tads.dac.conta.DTOs.MensagemDTO;


public interface InterfaceConsumer{
    
    public void commitOrdem(MensagemDTO dto);
    
    public void rollbackOrdem(MensagemDTO dto);

}
