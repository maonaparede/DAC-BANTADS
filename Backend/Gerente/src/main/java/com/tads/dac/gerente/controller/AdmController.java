
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
@CrossOrigin
@RequestMapping("/api")
public class AdmController {
       
    @Autowired
    private AdmServiceImp service;
    
    @Autowired
    private ModelMapper mapper;
    
    //R15
    @GetMapping("/adm") // Ex - http://localhost:8080/api/gerente -- sem
    public ResponseEntity<List<GerenteDashboardDTO>> telaInicial(){
        List<GerenteDashboardDTO> gerdto = service.findDashboard();
        return ResponseEntity.status(HttpStatus.OK).body(gerdto);
    }
    
    //R20 para consulta
    @GetMapping("/adm/{id}")
    public ResponseEntity<GerenteDTO> getById(@PathVariable(value = "id") Long id){       
        try{
            GerenteDTO gerdto = service.findById(id);
            return new ResponseEntity<>(gerdto, HttpStatus.OK);
        }catch(GerenteDoesntExistException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);   
        }
    }
    
    //R19
    @GetMapping("/adm/all")
    public ResponseEntity<?> getListagemGerente(){
            List<GerenteDTO> gerentes = service.listarGerente();
            return new ResponseEntity<>(gerentes, HttpStatus.OK);
    }   
    
    /*
    @PostMapping("/adm") //Ex - http://localhost:8080/api/gerente -- cm body;
    public ResponseEntity<?> save(@RequestBody Gerente gerente){
       
        try {
            Gerente ger = service.save(gerente);
            GerenteDTO gerdto = mapper.map(ger, GerenteDTO.class);
            return new ResponseEntity<>(gerdto, HttpStatus.CREATED);        
        }catch(GerenteConstraintViolation e){
            String msg = e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }
    
    
    @PutMapping("/adm/{id}") // Ex - http://localhost:8080/api/gerente/9 -- cm body
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody Gerente gerente){
        
        try{
            Gerente gerupdate = service.update(gerente, id);
            GerenteDTO gerdto = mapper.map(gerupdate, GerenteDTO.class);
            return new ResponseEntity<>(gerdto, HttpStatus.OK);
        }catch(GerenteDoesntExistException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }catch(GerenteConstraintViolation e){
            String msg = e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        
    }
    
    
    //Tem qe ver as especificações com o professor R18
    @DeleteMapping("/adm/{id}") //Ex - http://localhost:8080/api/gerente/9 -- sem body
    public ResponseEntity<HttpStatus> deleteById(@PathVariable(value = "id") Long id){
            try {
                service.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (DeleteLastGerenteException e) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);   
            } 
    }
    */
 
}
