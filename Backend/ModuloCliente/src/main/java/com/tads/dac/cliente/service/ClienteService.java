
package com.tads.dac.cliente.service;

import com.tads.dac.cliente.DTO.ClienteContaDTO;
import com.tads.dac.cliente.DTO.ClienteEndDTO;
import com.tads.dac.cliente.DTO.ClienteUpdateDTO;
import com.tads.dac.cliente.exception.ClienteConstraintViolation;
import com.tads.dac.cliente.exception.ClienteNotFoundException;
import com.tads.dac.cliente.exception.NegativeSalarioException;
import com.tads.dac.cliente.mensageria.ProducerClienteContaSync;
import com.tads.dac.cliente.model.Cliente;
import com.tads.dac.cliente.repository.ClienteRepository;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;
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
            
            //Atualiza o bd read do modulo conta + o limite
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
    
    public ClienteEndDTO update(Long id, ClienteUpdateDTO dto) throws ClienteNotFoundException, ClienteConstraintViolation, NegativeSalarioException{
        Optional<Cliente> cl = rep.findById(id);
        
        if(!cl.isPresent()){
            throw new ClienteNotFoundException("O Cliente com esse Id não Existe!");
        }
        
        if(dto.getSalario().compareTo(BigDecimal.ONE) < 1){
            throw new NegativeSalarioException("O Salário do Cliente deve ser Maior que R$1");
        }
        
        try{
            Cliente cliente = cl.get();
            cliente.setCep(dto.getCep());
            cliente.setCidade(dto.getCidade());
            cliente.setComplemento(dto.getComplemento());
            cliente.setEmail(dto.getEmail());
            cliente.setEstado(cliente.getEstado());
            cliente.setLogradouro(dto.getLogradouro());
            cliente.setNome(dto.getNome());
            cliente.setNumero(dto.getNumero());
            cliente.setTelefone(dto.getTelefone());
            cliente.setTipo(dto.getTipo());
            
            cliente.setSalario(dto.getSalario());
            
            //Atualiza o bd read do modulo conta + o limite
            ClienteContaDTO conta = mapper.map(cliente, ClienteContaDTO.class);
            atualizaBdContaRead(conta);
            
            ClienteEndDTO dto2 = mapper.map(cliente, ClienteEndDTO.class);
            return dto2;
            
        }catch(DataIntegrityViolationException e){
            SQLException ex = ((ConstraintViolationException) e.getCause()).getSQLException();
            String campo = ex.getMessage();
            campo = campo.substring(campo.indexOf("(") + 1, campo.indexOf(")"));
            throw new ClienteConstraintViolation("Esse " + campo + " já existe!");
        }
        
    }
    
    private void atualizaBdContaRead(ClienteContaDTO dto){
        System.out.println("Conta Atualizada " + dto.getNome());
        clienteContaMens.syncCliente(dto);
        
    }
    
    public int mudarAllGerentes(Long idGerente, String nomeGerente, Long idOld){
        return rep.updateGerenteCliente(idGerente, nomeGerente, idOld);
    }
    
}
