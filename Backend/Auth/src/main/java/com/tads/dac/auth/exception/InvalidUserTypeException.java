
package com.tads.dac.auth.exception;


public class InvalidUserTypeException extends BusinessLogicException{

    public InvalidUserTypeException() {
    }

    public InvalidUserTypeException(String message) {
        super(message);
    }

    public InvalidUserTypeException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
