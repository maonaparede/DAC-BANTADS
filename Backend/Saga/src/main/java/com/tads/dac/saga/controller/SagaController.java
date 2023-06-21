
package com.tads.dac.saga.controller;

import com.tads.dac.saga.DTO.ClienteAutocadastroDTO;
import com.tads.dac.saga.DTO.ClienteEndDTO;
import com.tads.dac.saga.DTO.GerenteDTO;
import com.tads.dac.saga.DTO.GerenteSenhaDTO;
import com.tads.dac.saga.DTO.RejeitaClienteIdContentDTO;
import com.tads.dac.saga.model.AutocadastroCliente;
import com.tads.dac.saga.model.RejeitaClienteContentEmail;
import com.tads.dac.saga.sagas.alteragerente.AlteraGerenteServiceInit;
import com.tads.dac.saga.sagas.alteraperfil.PerfilSagaServiceInit;
import com.tads.dac.saga.sagas.aprovaCliente.SagaAprovaClienteInitService;
import com.tads.dac.saga.sagas.autocadastro.SagaAutocadastroInitService;
import com.tads.dac.saga.sagas.inseregerente.InsertGerenteSagaInitService;
import com.tads.dac.saga.sagas.rejeitarcliente.RejeitarClienteSagaInitService;
import com.tads.dac.saga.sagas.removegerente.RemoveGerenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@CrossOrigin(origins = "http://localhost:5000")
@RequestMapping("/api/saga")
public class SagaController {
    
    @Autowired
    private PerfilSagaServiceInit updatePerfilserv;
    
    @Autowired
    private RemoveGerenteService gerRemoveServ;
    
    @Autowired
    private InsertGerenteSagaInitService gerInsertServ;
    
    @Autowired
    private SagaAutocadastroInitService autocadastroServ;

    @Autowired
    private SagaAprovaClienteInitService aprovaClienteServ;
    
    @Autowired
    private AlteraGerenteServiceInit alteraGerenteServ;
    
    @Autowired
    private RejeitarClienteSagaInitService rejeitaClienteServ;
    
    //R4 - CUD
    @PostMapping("/cli/update")
    public ResponseEntity<?> sagaUpdatePerfilCliente(@RequestBody ClienteEndDTO dto){
        
        updatePerfilserv.initSaga(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //R11
    @PutMapping("/cli/rej")
    public ResponseEntity<?> sagaRejeitaCliente(@RequestBody RejeitaClienteIdContentDTO dto){      
        rejeitaClienteServ.initSagaInsertGerente(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //R1
    @PostMapping("/cli/auto")
    public ResponseEntity<?> sagaAutocadastro(@RequestBody ClienteAutocadastroDTO dto){      
        autocadastroServ.initSagaAutocadastro(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //R10
    @PutMapping("/cli/apro/{id}")
    public ResponseEntity<?> sagaAprovaCliente(@PathVariable("id") Long id){      
        aprovaClienteServ.initSagaAutocadastro(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //R20 - Insert
    @PutMapping("/ger/alt")
    public ResponseEntity<?> sagaAlteraGerente(@RequestBody GerenteDTO dto){      
        alteraGerenteServ.initSaga(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //R18 - CUD
    @DeleteMapping("/ger/rem/{id}")
    public ResponseEntity<?> sagaRemoveGerente(@PathVariable("id") Long id){      
        gerRemoveServ.initSaga(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //R17
    @PostMapping("/ger/insert")
    public ResponseEntity<?> sagaInsertGerente(@RequestBody GerenteSenhaDTO dto){      
        gerInsertServ.initSagaInsertGerente(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
