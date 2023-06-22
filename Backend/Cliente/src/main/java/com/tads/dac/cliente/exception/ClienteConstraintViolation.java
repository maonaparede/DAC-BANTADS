
package com.tads.dac.cliente.exception;


public class ClienteConstraintViolation extends BusinessLogicException{

    public ClienteConstraintViolation() {
    }

    public ClienteConstraintViolation(String message) {
        super(message);
    }

    public ClienteConstraintViolation(String message, Throwable cause) {
        super(message, cause);
    }
    
}
