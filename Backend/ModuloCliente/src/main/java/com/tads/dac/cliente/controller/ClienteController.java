
package com.tads.dac.cliente.controller;

import com.tads.dac.cliente.DTO.ClienteEndDTO;
import com.tads.dac.cliente.DTO.ClienteUpdateDTO;
import com.tads.dac.cliente.exception.ClienteConstraintViolation;
import com.tads.dac.cliente.exception.ClienteNotFoundException;
import com.tads.dac.cliente.exception.NegativeSalarioException;
import com.tads.dac.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClienteController {
    
    @Autowired
    private ClienteService serv;
    
    @PostMapping("/cli")
    public ResponseEntity<?> save(@RequestBody ClienteEndDTO dto){
        try{
            dto = serv.save(dto);
            return new ResponseEntity<>(dto, HttpStatus.OK); 
        } catch (ClienteConstraintViolation e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/cli/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable(value = "id") Long id, @RequestBody ClienteUpdateDTO dto){
        try{
            
            ClienteEndDTO dto2 = serv.update(id, dto);
            return new ResponseEntity<>(dto2, HttpStatus.OK);
            
        } catch (ClienteConstraintViolation | NegativeSalarioException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ClienteNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/cli/{idG}/{nomeG}/{idU}")
    public ResponseEntity<?> changeAllGerente(
            @PathVariable(value = "idG") Long idG,
            @PathVariable(value = "nomeG") String nomeG,
            @PathVariable(value = "idU") Long idU
    ){
        Integer num = serv.mudarAllGerentes(idG, nomeG, idU);
        return new ResponseEntity<>(num, HttpStatus.OK);
    }
    
}
