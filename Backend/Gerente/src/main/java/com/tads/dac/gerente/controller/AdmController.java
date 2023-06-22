
package com.tads.dac.gerente.controller;

import com.tads.dac.gerente.DTOs.ClienteDTO;
import com.tads.dac.gerente.DTOs.GerenteDTO;
import com.tads.dac.gerente.DTOs.GerenteDashboardDTO;
import com.tads.dac.gerente.exceptions.DeleteLastGerenteException;
import com.tads.dac.gerente.exceptions.GerenteConstraintViolation;
import com.tads.dac.gerente.exceptions.GerenteDoesntExistException;
import com.tads.dac.gerente.model.Gerente;
import com.tads.dac.gerente.service.AdmServiceImp;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@CrossOrigin(origins = "http://localhost:5000")
@RequestMapping("/api/adm")
public class AdmController {
       
    @Autowired
    private AdmServiceImp service;
    
    //R15
    @GetMapping("/adm") 
    public ResponseEntity<List<GerenteDashboardDTO>> telaInicial(){
        List<GerenteDashboardDTO> gerdto = service.findDashboard();
        return ResponseEntity.status(HttpStatus.OK).body(gerdto);
    }
    
    //R20 para consulta
    @GetMapping("/ger/{id}")
    public ResponseEntity<GerenteDTO> getById(@PathVariable(value = "id") Long id){       
        try{
            GerenteDTO gerdto = service.findById(id);
            return new ResponseEntity<>(gerdto, HttpStatus.OK);
        }catch(GerenteDoesntExistException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);   
        }
    }
    
    //R2 para consulta api Composite de login
    @GetMapping("/ger/email/{email}")
    public ResponseEntity<GerenteDTO> getByEmail(@PathVariable(value = "email") String email){       
        try{
            GerenteDTO gerdto = service.findByEmail(email);
            return new ResponseEntity<>(gerdto, HttpStatus.OK);
        }catch(GerenteDoesntExistException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);   
        }
    }
    
    //R19
    @GetMapping("/ger/all")
    public ResponseEntity<?> getListagemGerente(){
            List<GerenteDTO> gerentes = service.listarGerente();
            return new ResponseEntity<>(gerentes, HttpStatus.OK);
    }
    
 
}
