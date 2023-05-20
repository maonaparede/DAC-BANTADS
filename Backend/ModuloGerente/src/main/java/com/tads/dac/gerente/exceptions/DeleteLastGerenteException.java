/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tads.dac.gerente.exceptions;

/**
 *
 * @author jeffe
 */
public class DeleteLastGerenteException extends BusinessLogicException{

    public DeleteLastGerenteException() {
    }

    public DeleteLastGerenteException(String message) {
        super(message);
    }

    public DeleteLastGerenteException(String message, Throwable cause) {
        super(message, cause);
    }
}
