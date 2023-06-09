
package com.tads.dac.auth.exception;


public class ContaNotAprovedException extends BusinessLogicException{

    public ContaNotAprovedException() {
    }

    public ContaNotAprovedException(String message) {
        super(message);
    }

    public ContaNotAprovedException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
