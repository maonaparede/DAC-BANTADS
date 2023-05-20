

package com.tads.dac.conta.exception;


public class InvalidMovimentacaoException extends BusinessLogicException{

    public InvalidMovimentacaoException() {
    }

    public InvalidMovimentacaoException(String message) {
        super(message);
    }

    public InvalidMovimentacaoException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
