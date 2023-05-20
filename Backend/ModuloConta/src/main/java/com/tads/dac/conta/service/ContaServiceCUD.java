
package com.tads.dac.conta.service;

import com.tads.dac.conta.DTOs.ClienteContaDTO;
import com.tads.dac.conta.DTOs.ClienteContaInfoDTO;
import com.tads.dac.conta.DTOs.ContaDTO;
import com.tads.dac.conta.DTOs.ContaSituacaoDTO;
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
public class ContaServiceCUD{

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
    
    
    public ContaDTO updateSituacao(ContaSituacaoDTO dto) throws ClienteNotFoundException, SituacaoInvalidaException{
        Optional<ContaCUD> conta = repCUD.findById(dto.getContaId());
        if(conta.isPresent()){
            ContaCUD ct = conta.get();
            Date dt = Date.from(Instant.now());
            if("A".equals(dto.getSituacao())){
                ct.setSituacao("A");
                ct.setLimite(dto.getSalario().divide(BigDecimal.valueOf(2)));
                ct.setDataAproRep(dt);
                ct = repCUD.save(ct);
                
                ContaDTO dto2 = mapper.map(ct, ContaDTO.class);
                mensagemProducer.syncConta(dto2);
                
                return dto2;
            }else if("R".equals(dto.getSituacao())){
                ct.setSituacao("R");
                ct.setDataAproRep(dt);
                ct = repCUD.save(ct);
                
                ContaDTO dto2 = mapper.map(ct, ContaDTO.class);
                mensagemProducer.syncConta(dto2);
                
                return dto2;
            }
            throw new SituacaoInvalidaException("Esse Estado de Situação Não Existe");
        }else{
            throw new ClienteNotFoundException("O Cliente Com Essa Conta Não Existe");
        }
    }

    
    public ContaDTO updateLimite(Long contaId, BigDecimal salario) throws ClienteNotFoundException, NegativeSalarioException{
        if(salario.compareTo(BigDecimal.ONE) < 1){
            throw new NegativeSalarioException("O Salário do Cliente deve ser Maior que R$1");
        }
        Optional<ContaCUD> conta = repCUD.findById(contaId);
        if(conta.isPresent()){
            ContaCUD ct = conta.get();
            BigDecimal saldo = ct.getSaldo();
            BigDecimal limite = salario.divide(BigDecimal.valueOf(2)); //Calcula limite
            
            if(saldo.compareTo(BigDecimal.ZERO) < 0){ // Se o saldo for negativo, ou seja menor q 0
                //multiplica por -1, pra ficar positivo, pra poder comparar com o limite
                saldo = saldo.multiply(new BigDecimal("-1")); 
                if(saldo.compareTo(limite) > 0){ // Verifica se o saldo (negativo) é maior que o limite
                    limite = saldo; // Coloca o saldo (negativo) como novo limite
                }
            }
            ct.setLimite(limite);
            ct = repCUD.save(ct);
            
            ContaDTO dto2 = mapper.map(ct, ContaDTO.class);
            mensagemProducer.syncConta(dto2);
                
            return dto2;
        }else{
            throw new ClienteNotFoundException("O Cliente com essa conta não existe");
        }
    }
    
    
    public ClienteContaInfoDTO getById(Long id) throws ClienteNotFoundException {
        Optional<ContaR> conta = repR.findById(id);
        if(conta.isPresent()){
            ContaDTO dto = mapper.map(conta.get(), ContaDTO.class);
            
            ClienteContaInfoDTO dtoInfo = new ClienteContaInfoDTO();
            
            dtoInfo.setDataAproRep(dto.getDataAproRep());
            dtoInfo.setDataCriacao(dto.getDataCriacao());
            dtoInfo.setIdCliente(dto.getIdCliente());
            dtoInfo.setIdConta(dto.getIdConta());
            dtoInfo.setLimite(dto.getLimite());
            dtoInfo.setSaldo(dto.getSaldo());
            dtoInfo.setSituacao(dto.getSituacao());
            
            
            Optional<ClienteR> cliente = repClienteR.findById(dto.getIdCliente());
            if(cliente.isPresent()){
                ClienteContaDTO dtoCliente = mapper.map(cliente, ClienteContaDTO.class);
                dtoInfo.setCpf(dtoCliente.getCpf());
                dtoInfo.setNome(dtoCliente.getNome());
                dtoInfo.setIdGerente(dtoCliente.getIdGerente());
                dtoInfo.setNomeGerente(dtoCliente.getNomeGerente());
                dtoInfo.setSalario(dtoCliente.getSalario());
                
            }
            return dtoInfo;
        }
        
        throw new ClienteNotFoundException("O Cliente com essa conta não existe");
    }
    
    public List<ContaDTO> getAllSituacaoEsperando(){
        List<ContaR> contas = repR.findAllBySituacao("E");
        List<ContaDTO> contaList = contas.stream()
                .map(item -> mapper.map(item, ContaDTO.class))
                .collect(Collectors.toList());
        return contaList;
    }
    
}
