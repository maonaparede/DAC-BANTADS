
package com.tads.dac.auth.exception;



public class EncryptionException extends AppException{

    public EncryptionException() {
    }

    public EncryptionException(String message) {
        super(message);
    }

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
