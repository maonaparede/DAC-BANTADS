/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.tads.dac.gerente.controller;

import com.tads.dac.gerente.model.Gerenciados;
import com.tads.dac.gerente.repository.GerenciadosRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jeffe
 */
@RestController
@RequestMapping("/ger")
public class GerenteController {
    
    @Autowired
    GerenciadosRepository rep;
    
    @GetMapping("/adm") // Ex - http://localhost:8080/api/gerente -- sem
    public ResponseEntity<Long> findAll(){
        Long ger = rep.selectIdGerenteMaiorNumGerenciados();
        return ResponseEntity.status(HttpStatus.OK).body(ger);
    }

}
