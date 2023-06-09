

package com.tads.dac.saga.exception;



public class AppException extends Exception{

    public AppException() {
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
