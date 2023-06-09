

package com.tads.dac.saga.exception;

public class SagaException extends BusinessLogicException{
    private int index;
    private Long sagaId;
    
    public SagaException(String message, int index, Long sagaId) {
        super(message);
        this.index = index;
    }

    public Long getSagaId() {
        return sagaId;
    }

    public int getIndex() {
        return index;
    }
}
