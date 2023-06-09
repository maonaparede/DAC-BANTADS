
package com.tads.dac.conta.exception;


public class OperacaoDoesntExist extends BusinessLogicException{

    public OperacaoDoesntExist() {
    }

    public OperacaoDoesntExist(String message) {
        super(message);
    }

    public OperacaoDoesntExist(String message, Throwable cause) {
        super(message, cause);
    }
    
}
