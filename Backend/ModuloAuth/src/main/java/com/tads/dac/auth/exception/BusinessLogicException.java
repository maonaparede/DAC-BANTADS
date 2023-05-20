
package com.tads.dac.auth.exception;


public class BusinessLogicException extends AppException{

    public BusinessLogicException() {
    }

    public BusinessLogicException(String message) {
        super(message);
    }

    public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
