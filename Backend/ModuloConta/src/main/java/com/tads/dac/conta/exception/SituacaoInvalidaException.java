
package com.tads.dac.conta.exception;


public class SituacaoInvalidaException extends BusinessLogicException{

    public SituacaoInvalidaException() {
    }

    public SituacaoInvalidaException(String message) {
        super(message);
    }

    public SituacaoInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
