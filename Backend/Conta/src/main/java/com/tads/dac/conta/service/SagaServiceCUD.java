
package com.tads.dac.conta.service;

import com.tads.dac.conta.exception.changeGerenteException;
import com.tads.dac.conta.DTOs.ContaDTO;
import com.tads.dac.conta.DTOs.GerenciadoGerenteDTO;
import com.tads.dac.conta.DTOs.GerenciadoGerenteSagaInsertDTO;
import com.tads.dac.conta.DTOs.GerenteNewOldDTO;
import com.tads.dac.conta.DTOs.GerentePrimeiraContaDTO;
import com.tads.dac.conta.DTOs.MensagemDTO;
import com.tads.dac.conta.DTOs.PerfilUpdateDTO;
import com.tads.dac.conta.DTOs.RejeitaClienteDTO;
import com.tads.dac.conta.DTOs.RemoveGerenteDTO;
import com.tads.dac.conta.exception.ClienteNotFoundException;
import com.tads.dac.conta.exception.ContaConstraintViolation;
import com.tads.dac.conta.exception.NegativeSalarioException;
import com.tads.dac.conta.exception.SituacaoInvalidaException;
import com.tads.dac.conta.mensageria.ConsumerContaSync;
import com.tads.dac.conta.mensageria.ProducerContaSync;
import com.tads.dac.conta.modelCUD.ContaCUD;
import com.tads.dac.conta.repositoryCUD.ContaRepositoryCUD;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SagaServiceCUD {
    
    @Autowired
    private ContaRepositoryCUD rep;
    
    @Autowired
    private ProducerContaSync contaSync;
    
    @Autowired
    private ModelMapper mapper;
    
    public RemoveGerenteDTO substituteGerenteConta(GerenteNewOldDTO dto) throws Exception{
        try {
           List<ContaCUD> contas = rep.findByIdGerente(dto.getIdOld());
           List<Long> idContas = new ArrayList<>();
           for (ContaCUD conta : contas) {
               idContas.add(conta.getIdConta());
           }
          

           RemoveGerenteDTO ret = new RemoveGerenteDTO();
           ret.setGerenteIdNew(dto.getIdNew());
           ret.setGerenteIdOld(dto.getIdOld());
           
           if(contas != null){
                if(!contas.isEmpty()){
                    ret.setGerenteNameOld(contas.get(0).getNomeGerente());
                }
           }
           
           ret.setContas(idContas);
           
           rep.updateGerenteIdNome(dto.getIdOld(), dto.getNomeNew(), dto.getIdNew());
           
           contaSync.syncRemoveGerenteCommit(dto);
           return ret;
        }catch(Exception e){
            throw new Exception("Houve Algum Erro Na Mudança(Exclusão) de Gerentes no Módulo Conta");
        }
    }

    public void rollbackUpdate(RemoveGerenteDTO dto) {
        for(Long id : dto.getContas()){
            Optional<ContaCUD> con = rep.findById(id);
            con.get().setIdGerente(dto.getGerenteIdOld());
            con.get().setNomeGerente(dto.getGerenteNameOld());
            rep.save(con.get());
        }
        
        contaSync.syncRemoveGerenteRollback(dto);
        
    }
    
    
    public ContaDTO saveAutocadastro(Long idCliente, BigDecimal salario) throws ContaConstraintViolation{
        try{
            ContaCUD savConta = new ContaCUD();
            savConta.setSaldo(BigDecimal.ZERO);
            savConta.setSituacao("E");
            savConta.setLimite(salario.divide(BigDecimal.valueOf(2)));
            savConta.setIdCliente(idCliente);
            savConta.setIdConta(null);
            savConta.setDataAproRep(null);
            savConta.setDataCriacao(null);
            
            savConta = rep.save(savConta);
            
            ContaDTO dto = mapper.map(savConta, ContaDTO.class);
            contaSync.syncConta(dto);
            
            return dto;
        }catch(DataIntegrityViolationException e){
            SQLException ex = ((ConstraintViolationException) e.getCause()).getSQLException();
            String campo = ex.getMessage();
            campo = campo.substring(campo.indexOf("(") + 1, campo.indexOf(")"));
            throw new ContaConstraintViolation("Esse " + campo + " já existe!");
        }
    }
    
    
    public void rollbackAutocadastro(Long id){
        rep.deleteById(id);
        contaSync.rollbackAutocadastro(id);
    }

    public GerenciadoGerenteSagaInsertDTO changeGerente(GerentePrimeiraContaDTO dto) throws changeGerenteException {
        GerenciadoGerenteSagaInsertDTO ret = new GerenciadoGerenteSagaInsertDTO();
        try{
            //O único caso da getPrimeiraConta ser null é quando o 1° gerente é add
            if(dto.getPrimeiraConta() != null){
                Optional<ContaCUD> contaOp = rep.findById(dto.getPrimeiraConta());
                if(contaOp.isPresent()){
                    ContaCUD conta = contaOp.get();

                    ret.setGerenteIdOld(conta.getIdGerente());
                    ret.setGerenteNomeOld(conta.getNomeGerente());
                    ret.setGerenteIdNew(dto.getId());
                    ret.setGerenteNomeNew(dto.getNome());

                    conta.setIdGerente(dto.getId());
                    conta.setNomeGerente(dto.getNome());

                    conta = rep.save(conta);
                    ContaDTO dto2 = mapper.map(conta, ContaDTO.class);
                    contaSync.syncConta(dto2);
                }
            }
            return ret;
        }catch(Exception e){
            System.out.println("Erro na mudança do Gerente:" + e.getMessage());
            throw new changeGerenteException("Não Foi Possível Realizar a Operação");
        }
    }

    public ContaDTO finalizaAutocadastroSetaGerente(GerenciadoGerenteDTO dto) throws ClienteNotFoundException {
        Optional<ContaCUD> ct = rep.findById(dto.getIdConta());
        if (ct.isPresent()) {
            ContaCUD conta = ct.get();
            conta.setIdGerente(dto.getGerenteId());
            conta.setNomeGerente(dto.getGerenteNome());
            conta = rep.save(conta);
            ContaDTO ctDto = mapper.map(conta, ContaDTO.class);
            contaSync.syncConta(ctDto);
            return ctDto;
        }else{
            throw new ClienteNotFoundException("O Cliente Não Existe!");
        }
    }
    
    public ContaDTO AprovarCliente(Long id, String situacao) throws ClienteNotFoundException{
        Optional<ContaCUD> conta = rep.findByIdCliente(id);
        if(conta.isPresent()){
            ContaCUD ct = conta.get();
            Date dt = Date.from(Instant.now());
            ct.setSituacao(situacao);
            ct.setDataAproRep(dt);
            ct = rep.save(ct);
            
            ///Sync bd R
            ContaDTO dto2 = mapper.map(ct, ContaDTO.class);
            contaSync.syncConta(dto2);
            
            return dto2;
        }else{
            throw new ClienteNotFoundException("O Cliente Com Essa Conta Não Existe");
        }
    }

    public ContaDTO rejeitaCliente(RejeitaClienteDTO dto) throws ClienteNotFoundException, SituacaoInvalidaException {
        Optional<ContaCUD> ct = rep.findByIdCliente(dto.getIdCliente());
        if(ct.isPresent()){
            ContaCUD conta = ct.get();
            if(!"A".equals(conta.getSituacao())){
                rep.deleteById(conta.getIdConta());
                contaSync.rollbackAutocadastro(conta.getIdConta());

                ContaDTO ctDto = mapper.map(conta, ContaDTO.class);

                return ctDto;
            }
            throw new SituacaoInvalidaException("A Conta Já Foi Aprovada!");
        }else{
            throw new ClienteNotFoundException("Essa Conta não Existe!");
        }
    }

    public void rollbackRejeitaCliente(ContaDTO dto) {
        ContaCUD conta = mapper.map(dto, ContaCUD.class);
        rep.save(conta);
        contaSync.syncConta(dto);
    }
    
    public void rollbackAlteraPerfil(MensagemDTO msg){ 
        ContaCUD conta = mapper.map(msg.getSendObj(), ContaCUD.class);
        conta = rep.save(conta);
        ContaDTO dto = mapper.map(conta, ContaDTO.class);
        contaSync.syncConta(dto);
    }
    
    
    public MensagemDTO updateLimite(MensagemDTO msg) throws ClienteNotFoundException, NegativeSalarioException{
        PerfilUpdateDTO dto = mapper.map(msg.getReturnObj(), PerfilUpdateDTO.class);
        if(dto.getSalario().compareTo(BigDecimal.ONE) < 1){
            throw new NegativeSalarioException("O Salário do Cliente deve ser Maior que R$1");
        }
        Optional<ContaCUD> conta = rep.findByIdCliente(dto.getIdCliente());
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
            
            ct = rep.save(ct);
            dto2 = mapper.map(ct, ContaDTO.class);
            
            contaSync.syncConta(dto2); //Synca com bd de Read
                
            return msg;
        }else{
            throw new ClienteNotFoundException("O Cliente com essa conta não existe");
        }
    }
}
