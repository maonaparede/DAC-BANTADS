
package com.tads.dac.conta.service;

import com.tads.dac.conta.DTOs.AprovaR9DTO;
import com.tads.dac.conta.DTOs.ClienteContaDTO;
import com.tads.dac.conta.DTOs.ClienteContaInfoDTO;
import com.tads.dac.conta.DTOs.ContaDTO;
import com.tads.dac.conta.DTOs.MensagemDTO;
import com.tads.dac.conta.DTOs.PerfilUpdateDTO;
import com.tads.dac.conta.exception.ClienteNotFoundException;
import com.tads.dac.conta.exception.ContaConstraintViolation;
import com.tads.dac.conta.exception.NegativeSalarioException;
import com.tads.dac.conta.exception.SituacaoInvalidaException;
import com.tads.dac.conta.mensageria.ProducerContaSync;
import com.tads.dac.conta.modelCUD.ContaCUD;
import com.tads.dac.conta.modelR.ClienteR;
import com.tads.dac.conta.modelR.ContaR;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.tads.dac.conta.repositoryCUD.ContaRepositoryCUD;
import com.tads.dac.conta.repositoryR.ClienteRepositoryR;
import com.tads.dac.conta.repositoryR.ContaRepositoryR;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

@Service
public class ContaService{

    @Autowired
    private ContaRepositoryCUD repCUD;
    
    @Autowired
    private ContaRepositoryR repR;
    
    @Autowired
    private ClienteRepositoryR repClienteR;
    
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProducerContaSync mensagemProducer;
    
    public ContaDTO save(Long idCliente) throws ContaConstraintViolation{
        try{
            ContaCUD savConta = new ContaCUD();
            savConta.setSaldo(BigDecimal.ZERO);
            savConta.setSituacao("E");
            savConta.setLimite(BigDecimal.ZERO);
            savConta.setIdCliente(idCliente);
            savConta.setIdConta(null);
            savConta.setDataAproRep(null);
            savConta.setDataCriacao(null);
            
            savConta = repCUD.save(savConta);
            
            ContaDTO dto = mapper.map(savConta, ContaDTO.class);
            mensagemProducer.syncConta(dto);
            
            return dto;
        }catch(DataIntegrityViolationException e){
            SQLException ex = ((ConstraintViolationException) e.getCause()).getSQLException();
            String campo = ex.getMessage();
            campo = campo.substring(campo.indexOf("(") + 1, campo.indexOf(")"));
            throw new ContaConstraintViolation("Esse " + campo + " já existe!");
        }
    }
    
    
    public ContaDTO AprovarCliente(Long id) throws ClienteNotFoundException, SituacaoInvalidaException{
        Optional<ContaCUD> conta = repCUD.findById(id);
        if(conta.isPresent()){
            ContaCUD ct = conta.get();
            Date dt = Date.from(Instant.now());
            ct.setSituacao("A");
            ct.setDataAproRep(dt);
            ct = repCUD.save(ct);
            
            ///Sync bd R
            ContaDTO dto2 = mapper.map(ct, ContaDTO.class);
            mensagemProducer.syncConta(dto2);
            
            return dto2;
        }else{
            throw new ClienteNotFoundException("O Cliente Com Essa Conta Não Existe");
        }
    }

    
    public MensagemDTO updateLimite(MensagemDTO msg) throws ClienteNotFoundException, NegativeSalarioException{
        PerfilUpdateDTO dto = mapper.map(msg.getReturnObj(), PerfilUpdateDTO.class);
        if(dto.getSalario().compareTo(BigDecimal.ONE) < 1){
            throw new NegativeSalarioException("O Salário do Cliente deve ser Maior que R$1");
        }
        Optional<ContaCUD> conta = repCUD.findByIdCliente(dto.getIdCliente());
        if(conta.isPresent()){
            
            ContaCUD ct = conta.get();
            
            ContaDTO dto2 = mapper.map(ct, ContaDTO.class);
            msg.setSendObj(dto2); //Salva estado anterior pra Event Sourcing
            
            BigDecimal saldo = ct.getSaldo();
            BigDecimal limite = dto.getSalario().divide(BigDecimal.valueOf(2)); //Calcula limite
            
            if(saldo.compareTo(BigDecimal.ZERO) < 0){ // Se o saldo for negativo, ou seja menor q 0
                //multiplica por -1, pra ficar positivo, pra poder comparar com o limite
                saldo = saldo.multiply(new BigDecimal("-1")); 
                if(saldo.compareTo(limite) > 0){ // Verifica se o saldo (negativo) é maior que o limite
                    limite = saldo; // Coloca o saldo (negativo) como novo limite
                }
            }
            
            ct.setLimite(limite);
            
            ct = repCUD.save(ct);
            dto2 = mapper.map(ct, ContaDTO.class);
            
            mensagemProducer.syncConta(dto2); //Synca com bd de Read
                
            return msg;
        }else{
            throw new ClienteNotFoundException("O Cliente com essa conta não existe");
        }
    }
    
    
    public ClienteContaInfoDTO getById(Long id) throws ClienteNotFoundException {
        Optional<ContaR> conta = repR.findById(id);
        if(conta.isPresent()){
            ContaDTO dto = mapper.map(conta.get(), ContaDTO.class);
            
            ClienteContaInfoDTO dtoInfo = new ClienteContaInfoDTO();
            
            dtoInfo.setIdCliente(dto.getIdCliente());
            dtoInfo.setIdConta(dto.getIdConta());
            dtoInfo.setSaldo(dto.getSaldo());
            
            
            Optional<ClienteR> cliente = repClienteR.findById(dto.getIdCliente());
            if(cliente.isPresent()){
                ClienteContaDTO dtoCliente = mapper.map(cliente, ClienteContaDTO.class);
                dtoInfo.setCpf(dtoCliente.getCpf());
                dtoInfo.setNome(dtoCliente.getNome());
                dtoInfo.setCidade(dtoCliente.getCidade());
                dtoInfo.setEstado(dtoCliente.getEstado());
            }
            return dtoInfo;
        }
        
        throw new ClienteNotFoundException("O Cliente com essa conta não existe");
    }
    
    public void rollbackOp(MensagemDTO msg){ 
        ContaCUD conta = mapper.map(msg.getSendObj(), ContaCUD.class);
        conta = repCUD.save(conta);
        ContaDTO dto = mapper.map(conta, ContaDTO.class);
        mensagemProducer.syncConta(dto);
    }
    
}
