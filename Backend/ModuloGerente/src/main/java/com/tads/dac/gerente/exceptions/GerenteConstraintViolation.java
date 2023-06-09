
package com.tads.dac.gerente.exceptions;

public class GerenteConstraintViolation extends BusinessLogicException{

    public GerenteConstraintViolation() {
    }

    public GerenteConstraintViolation(String message) {
        super(message);
    }

    public GerenteConstraintViolation(String message, Throwable cause) {
        super(message, cause);
    }
    
}
