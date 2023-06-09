
package com.tads.dac.gerente.exceptions;

public class GerenciadoDoesntExistException extends BusinessLogicException{

    public GerenciadoDoesntExistException() {
    }

    public GerenciadoDoesntExistException(String message) {
        super(message);
    }

    public GerenciadoDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
