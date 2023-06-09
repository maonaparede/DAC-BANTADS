
package com.tads.dac.saga.exception;


public class NullDtoObjectException extends BusinessLogicException{

    public NullDtoObjectException() {
    }

    public NullDtoObjectException(String message) {
        super(message);
    }

    public NullDtoObjectException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
