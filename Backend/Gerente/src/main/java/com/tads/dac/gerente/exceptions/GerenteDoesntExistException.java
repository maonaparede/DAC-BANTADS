/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tads.dac.gerente.exceptions;

/**
 *
 * @author jeffe
 */
public class GerenteDoesntExistException extends BusinessLogicException{

    public GerenteDoesntExistException() {
    }

    public GerenteDoesntExistException(String message) {
        super(message);
    }

    public GerenteDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
