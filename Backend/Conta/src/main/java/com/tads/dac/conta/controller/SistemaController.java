
package com.tads.dac.conta.controller;

import com.tads.dac.conta.DTOs.AprovaR9DTO;
import com.tads.dac.conta.DTOs.ClienteContaInfoDTO;
import com.tads.dac.conta.DTOs.ContaDTO;
import com.tads.dac.conta.DTOs.ContaSaveDTO;
import com.tads.dac.conta.DTOs.RelatorioClienteDTO;
import com.tads.dac.conta.exception.ClienteNotFoundException;
import com.tads.dac.conta.exception.ContaConstraintViolation;
import com.tads.dac.conta.service.ContaService;
import com.tads.dac.conta.service.SysAdmService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@RestController
@RequestMapping("/api/sys/cli")
public class SistemaController {
    
    @Autowired
    private ContaService contaService;
    
    @Autowired
    private SysAdmService servSys;
    
    //R13 - Com Api compose
    @GetMapping("/ger/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id){       
        try{
            ClienteContaInfoDTO dto = contaService.getById(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch(ClienteNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);   
        }
    }

    //R9 - Clientes que precisam ser apr ou rep pelo gerente
    @GetMapping("/ger/esp/{id}")
    public ResponseEntity<?> getAllEsperando(@PathVariable(value = "id") Long id){        
        List<AprovaR9DTO> contaList = servSys.getAllSituacaoEsperando(id);
        return new ResponseEntity<>(contaList, HttpStatus.OK);
    }
    
    //R12
    @GetMapping("/ger/myCli/{id}")
    public ResponseEntity<?> getAllClientesGerente(@PathVariable(value = "id") Long id){     
        List<ClienteContaInfoDTO> dto = servSys.clientesDoGerente(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    //R14
    @GetMapping("/ger/bestCli/{id}")
    public ResponseEntity<?> getBestClientes(@PathVariable(value = "id") Long id){     
        List<ClienteContaInfoDTO> dto = servSys.get3MaioresSaldoClientes(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    //R16
    @GetMapping("/adm/RelCli")
    public ResponseEntity<?> getRelatorioClientes(){     
        List<RelatorioClienteDTO> dto = servSys.relatorioClientes();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    
    //R12
    @GetMapping("/ger/allCli/{id}")
    public ResponseEntity<?> getAllClientes(@PathVariable(value = "id") Long id){     
        List<ClienteContaInfoDTO> dto = servSys.getAllClientes(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
}
