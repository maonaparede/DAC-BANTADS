

package com.tads.dac.saga.util;

import com.tads.dac.saga.util.InterfaceSagaOrquestration;


public class ItemOrdemMensagem{
    
    private Integer index;
    private final InterfaceSagaOrquestration item;

    public ItemOrdemMensagem(InterfaceSagaOrquestration item) {
        this.item = item;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public InterfaceSagaOrquestration getItem() {
        return item;
    }
    
    
    
}
