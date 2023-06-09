
package com.tads.dac.conta.exception;


public class InvalidValorException extends BusinessLogicException{

    public InvalidValorException() {
    }

    public InvalidValorException(String message) {
        super(message);
    }

    public InvalidValorException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
