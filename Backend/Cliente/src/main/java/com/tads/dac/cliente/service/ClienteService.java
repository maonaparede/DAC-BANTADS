
package com.tads.dac.cliente.service;

import com.tads.dac.cliente.DTO.ClienteContaDTO;
import com.tads.dac.cliente.DTO.ClienteEndDTO;
import com.tads.dac.cliente.DTO.ClienteUpdateDTO;
import com.tads.dac.cliente.DTO.MensagemDTO;
import com.tads.dac.cliente.DTO.PerfilUpdateDTO;
import com.tads.dac.cliente.exception.ClienteConstraintViolation;
import com.tads.dac.cliente.exception.ClienteNotFoundException;
import com.tads.dac.cliente.exception.NegativeSalarioException;
import com.tads.dac.cliente.mensageria.ProducerClienteContaSync;
import com.tads.dac.cliente.model.Cliente;
import com.tads.dac.cliente.repository.ClienteRepository;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository rep;
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private ProducerClienteContaSync clienteContaMens;
    
    
    public ClienteEndDTO save(ClienteEndDTO dto) throws ClienteConstraintViolation{
        try{
            dto.setId(null);
            Cliente cl = mapper.map(dto, Cliente.class);
            cl = rep.save(cl);
            
            //Atualiza o bd read do modulo conta
            ClienteContaDTO conta = mapper.map(cl, ClienteContaDTO.class);
            atualizaBdContaRead(conta);
            
            dto = mapper.map(cl, ClienteEndDTO.class);
            return dto;
            
        }catch(DataIntegrityViolationException e){
            SQLException ex = ((ConstraintViolationException) e.getCause()).getSQLException();
            String campo = ex.getMessage();
            campo = campo.substring(campo.indexOf("(") + 1, campo.indexOf(")"));
            throw new ClienteConstraintViolation("Esse " + campo + " já existe!");
        }
    }
    
    public MensagemDTO update(MensagemDTO msg) throws ClienteNotFoundException, ClienteConstraintViolation, NegativeSalarioException{
        ClienteUpdateDTO dto = mapper.map(msg.getSendObj(), ClienteUpdateDTO.class);
        Optional<Cliente> cl = rep.findById(dto.getId());
        
        if(!cl.isPresent()){
            throw new ClienteNotFoundException("O Cliente com esse Id não Existe!");
        }
        
        if(dto.getSalario().compareTo(BigDecimal.ONE) < 1){
            throw new NegativeSalarioException("O Salário do Cliente deve ser Maior que R$1");
        }
        
        ClienteEndDTO dto2 = mapper.map(cl.get(), ClienteEndDTO.class);
        msg.setSendObj(dto2); //Objeto de Retorno pra ser gravado no Event Sourcing
        
        try{
            Cliente cliente = cl.get();
            
            PerfilUpdateDTO ret = new PerfilUpdateDTO();
            ret.setIdCliente(dto.getId());
            ret.setOldEmail(cliente.getEmail());
            ret.setNewEmail(dto.getEmail());
            ret.setSalario(dto.getSalario());
            msg.setReturnObj(ret); // O que retorna pra prox fase do saga
            
            cliente.setCep(dto.getCep());
            cliente.setCidade(dto.getCidade());
            cliente.setComplemento(dto.getComplemento());
            cliente.setEmail(dto.getEmail()); //
            cliente.setEstado(cliente.getEstado());
            cliente.setLogradouro(dto.getLogradouro());
            cliente.setNome(dto.getNome());
            cliente.setNumero(dto.getNumero());
            cliente.setTelefone(dto.getTelefone());
            cliente.setTipo(dto.getTipo());
            
            cliente.setSalario(dto.getSalario());
                   
            cliente = rep.save(cliente);
            
             //Atualiza o bd read do modulo conta
            ClienteContaDTO conta = mapper.map(cliente, ClienteContaDTO.class);
            atualizaBdContaRead(conta);           
            return msg;
            
        }catch(DataIntegrityViolationException e){
            SQLException ex = ((ConstraintViolationException) e.getCause()).getSQLException();
            String campo = ex.getMessage();
            campo = campo.substring(campo.indexOf("(") + 1, campo.indexOf(")"));
            throw new ClienteConstraintViolation("Esse " + campo + " já existe!");
        }
        
    }
    
    public ClienteEndDTO getClienteById(Long id) throws ClienteNotFoundException{
        Optional<Cliente> cl = rep.findById(id);
        if(cl.isPresent()){
            ClienteEndDTO dto = mapper.map(cl.get(), ClienteEndDTO.class);
            return dto;
        }
        throw new ClienteNotFoundException("Esse Cliente Não Existe");
    }
    
    public void rollbackClienteUpdate(MensagemDTO msg){
        try {
            update(msg);
        } catch (ClienteNotFoundException | ClienteConstraintViolation | 
                NegativeSalarioException ex) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void rollbackAutocadastro(Long contaId){
        rep.deleteById(contaId);
    }
    
    private void atualizaBdContaRead(ClienteContaDTO dto){
        System.out.println("Conta Atualizada " + dto.getNome());
        clienteContaMens.syncCliente(dto);
    }
    
    public int mudarAllGerentes(Long idGerente, String nomeGerente, Long idOld){
        return rep.updateGerenteCliente(idGerente, nomeGerente, idOld);
    }
    
}
