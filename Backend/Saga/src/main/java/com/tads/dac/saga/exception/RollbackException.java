
package com.tads.dac.saga.exception;


public class RollbackException extends BusinessLogicException{

    public RollbackException(String message) {
        super(message);
    }

    public RollbackException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
