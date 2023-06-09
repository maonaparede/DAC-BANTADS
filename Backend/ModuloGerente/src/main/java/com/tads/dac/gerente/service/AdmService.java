/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tads.dac.gerente.service;

import com.tads.dac.gerente.DTOs.GerenteDashboardDTO;
import com.tads.dac.gerente.exceptions.DeleteLastGerenteException;
import com.tads.dac.gerente.exceptions.GerenteConstraintViolation;
import com.tads.dac.gerente.exceptions.GerenteDoesntExistException;
import com.tads.dac.gerente.model.Gerente;
import java.util.List;


public interface AdmService {
    
    Gerente save(Gerente gerente) throws GerenteConstraintViolation;
    
    List<Gerente> findAll();
    
    Gerente findById(Long id) throws GerenteDoesntExistException;
    
    List<GerenteDashboardDTO> findDashboard();
    
    Gerente update(Gerente gerente, Long id) throws GerenteDoesntExistException, GerenteConstraintViolation;
    
    void deleteById(Long id) throws DeleteLastGerenteException;
        
}
