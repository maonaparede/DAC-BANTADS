
package com.tads.dac.auth.exception;


public class ContaAlredyExists extends BusinessLogicException{

    public ContaAlredyExists() {
    }

    public ContaAlredyExists(String message) {
        super(message);
    }

    public ContaAlredyExists(String message, Throwable cause) {
        super(message, cause);
    }
    
}
