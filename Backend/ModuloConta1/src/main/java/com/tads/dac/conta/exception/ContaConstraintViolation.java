
package com.tads.dac.conta.exception;


public class ContaConstraintViolation extends BusinessLogicException{

    public ContaConstraintViolation() {
    }

    public ContaConstraintViolation(String message) {
        super(message);
    }

    public ContaConstraintViolation(String message, Throwable cause) {
        super(message, cause);
    }
    
}
