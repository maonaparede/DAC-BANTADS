

package com.tads.dac.conta.exception;


public class ClienteNotFoundException extends BusinessLogicException{

    public ClienteNotFoundException() {
    }

    public ClienteNotFoundException(String message) {
        super(message);
    }

    public ClienteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
