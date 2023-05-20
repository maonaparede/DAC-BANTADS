
package com.tads.dac.auth.exception;

public class ContaWrongPassword extends BusinessLogicException{

    public ContaWrongPassword() {
    }

    public ContaWrongPassword(String message) {
        super(message);
    }

    public ContaWrongPassword(String message, Throwable cause) {
        super(message, cause);
    }
    
}
