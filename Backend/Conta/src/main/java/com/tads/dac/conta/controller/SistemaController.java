
package com.tads.dac.conta.controller;

import com.tads.dac.conta.DTOs.AprovaR9DTO;
import com.tads.dac.conta.DTOs.ClienteContaInfoDTO;
import com.tads.dac.conta.DTOs.ContaDTO;
import com.tads.dac.conta.DTOs.RelatorioClienteDTO;
import com.tads.dac.conta.exception.ClienteNotFoundException;
import com.tads.dac.conta.service.ContaService;
import com.tads.dac.conta.service.SysAdmService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class SistemaController {
    
    @Autowired
    private ContaService contaService;
    
    @Autowired
    private SysAdmService servSys;
    
    @Autowired
    private ModelMapper mapper;
    
    //R13 - Com Api compose
    @GetMapping("/sys/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id){       
        try{
            ClienteContaInfoDTO dto = contaService.getById(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch(ClienteNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);   
        }
    }

    //R9 - Clientes que precisam ser apr ou rep pelo gerente
    @GetMapping("/sys/esp/{id}")
    public ResponseEntity<?> getAllEsperando(@PathVariable(value = "id") Long id){        
        List<AprovaR9DTO> contaList = servSys.getAllSituacaoEsperando(id);
        return new ResponseEntity<>(contaList, HttpStatus.OK);
    }
    
    //R12
    @GetMapping("/sys/ger/myCli/{id}")
    public ResponseEntity<?> getAllClientesGerente(@PathVariable(value = "id") Long id){     
        List<ClienteContaInfoDTO> dto = servSys.clientesDoGerente(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    //R14
    @GetMapping("/sys/bestCli")
    public ResponseEntity<?> getBestClientes(){     
        List<ClienteContaInfoDTO> dto = servSys.get3MaioresSaldoClientes();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    //R16
    @GetMapping("/sys/RelCli")
    public ResponseEntity<?> getRelatorioClientes(){     
        List<RelatorioClienteDTO> dto = servSys.relatorioClientes();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
    
       /*
    @GetMapping("/sys/adm/allCli")
    public ResponseEntity<?> getAllClientes(){     
        List<ClienteContaInfoDTO> dto = servSys.getAllClientes();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    */
       /*
    @PostMapping("/sys") 
    public ResponseEntity<?> save(@RequestBody ContaSaveDTO dtoR){
       
        try {
            ContaDTO dto = contaService.save(dtoR.getIdCliente());
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        }catch(ContaConstraintViolation e){
            String msg = e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/sys")
    public ResponseEntity<?> updateSituacao(@RequestBody ContaSituacaoDTO dtoR){
       
        try {
            ContaDTO dto = contaService.updateSituacao(dtoR);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch(SituacaoInvalidaException e){
            String msg = e.getMessage();
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        } catch (ClienteNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/sys/{id}/{salario}")
    public ResponseEntity<?> updateLimite(@PathVariable(value = "id") Long id, @PathVariable(value = "salario") BigDecimal salario){
       
        try {
            MensagemDTO msg = new MensagemDTO();
            ClienteEndDTO end = new ClienteEndDTO();
            end.setId(id);
            end.setSalario(salario);
            msg.setSendObj(end);
            msg = contaService.updateLimite(msg);
            return new ResponseEntity<>(msg.getSendObj(), HttpStatus.OK);
        } catch (ClienteNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NegativeSalarioException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/
}
