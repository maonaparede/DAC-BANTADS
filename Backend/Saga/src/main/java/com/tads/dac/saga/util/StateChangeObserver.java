
package com.tads.dac.saga.util;

import com.tads.dac.saga.DTO.MensagemDTO;


public interface StateChangeObserver {
    
    public void nextStateCommit(MensagemDTO msg, int index);
    
    public void prevStateCommit(MensagemDTO msg, int index);
    
}
