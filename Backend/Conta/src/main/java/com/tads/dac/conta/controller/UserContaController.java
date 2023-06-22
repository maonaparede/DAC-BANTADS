
package com.tads.dac.conta.controller;

import com.tads.dac.conta.DTOs.ClienteContaInfoDTO;
import com.tads.dac.conta.DTOs.OperacaoBdDTO;
import com.tads.dac.conta.DTOs.OperacaoDTO;
import com.tads.dac.conta.exception.ClienteNotFoundException;
import com.tads.dac.conta.exception.InvalidMovimentacaoException;
import com.tads.dac.conta.exception.InvalidValorException;
import com.tads.dac.conta.service.ContaService;
import com.tads.dac.conta.service.OperacaoService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UserContaController {
    
    @Autowired
    private OperacaoService opService;
  
    @Autowired
    private ContaService contaService;
    
    //R13 ou R3
    @GetMapping("/{id}")
    public ResponseEntity<?> getContaInfo(@PathVariable("id") Long id){
        try{
            ClienteContaInfoDTO dto = contaService.getById(id);          
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch(ClienteNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);   
        }
    }
    
    //R2 apiCompose
    @GetMapping("/idCliente/{id}")
    public ResponseEntity<?> getContaInfoByIdCliente(@PathVariable("id") Long id){
        try{
            ClienteContaInfoDTO dto = contaService.getContaByIdCliente(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch(ClienteNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);   
        }
    }
    
    //R8
    @GetMapping("/{id}/{dataInicio}/{dataFim}")
    public ResponseEntity<?> getExtrato(
            @PathVariable Long id,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicio,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataFim
    ){
        try {
            List<OperacaoDTO> list = opService.getExtrato(id, dataInicio, dataFim);
            
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    //R5, R6, R7
    @PostMapping("/op")
    public ResponseEntity<?> fazOperacao(@RequestBody OperacaoDTO op){
        try{
            OperacaoBdDTO opRet = new OperacaoBdDTO();
            switch (op.getOperacao()) {
                case "S":
                    opRet = opService.fazSaque(op.getDeUser(), op.getValor());
                    break;
                case "D":
                    opRet = opService.fazDeposito(op.getParaUser(), op.getValor());
                    break;
                case "T":
                    opRet = opService.fazTransferencia(op.getDeUser(), op.getParaUser(), op.getValor());
                    break; 
                default:
                    return new ResponseEntity<>("Essa Operação Não Existe", HttpStatus.BAD_REQUEST);
            }
            
            return new ResponseEntity<>(opRet, HttpStatus.OK);
            
        }catch(ClienteNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (InvalidMovimentacaoException | InvalidValorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
    }

    
}
