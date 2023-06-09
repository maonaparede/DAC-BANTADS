
package com.tads.dac.auth.exception;


public class ContaNotExistException extends BusinessLogicException{

    public ContaNotExistException(String message) {
        super(message);
    }

    public ContaNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
