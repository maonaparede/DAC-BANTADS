
package com.tads.dac.cliente.exception;


public class NegativeSalarioException extends BusinessLogicException{

    public NegativeSalarioException() {
    }

    public NegativeSalarioException(String message) {
        super(message);
    }

    public NegativeSalarioException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
