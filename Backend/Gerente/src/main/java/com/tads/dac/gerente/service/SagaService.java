/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tads.dac.gerente.service;

import com.tads.dac.gerente.DTOs.AlteraGerenteDTO;
import com.tads.dac.gerente.DTOs.AutocadastroDTO;
import com.tads.dac.gerente.DTOs.GerenciadoDTO;
import com.tads.dac.gerente.DTOs.GerenciadoGerenteDTO;
import com.tads.dac.gerente.DTOs.GerenciadoGerenteSagaInsertDTO;
import com.tads.dac.gerente.DTOs.GerenciadoSaldoDTO;
import com.tads.dac.gerente.DTOs.GerenteDTO;
import com.tads.dac.gerente.DTOs.GerentePrimeiraContaDTO;
import com.tads.dac.gerente.DTOs.MensagemDTO;
import com.tads.dac.gerente.DTOs.RejeitaClienteDTO;
import com.tads.dac.gerente.exceptions.GerenciadoDoesntExistException;
import com.tads.dac.gerente.exceptions.GerenteConstraintViolation;
import com.tads.dac.gerente.exceptions.GerenteDoesntExistException;
import com.tads.dac.gerente.model.Gerenciados;
import com.tads.dac.gerente.model.Gerente;
import com.tads.dac.gerente.repository.GerenciadosRepository;
import com.tads.dac.gerente.repository.GerenteRepository;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SagaService {
    
    @Autowired
    private GerenciadosRepository repGerenciados;
    
    @Autowired
    private GerenteRepository repGer;
    
    @Autowired
    private ModelMapper mapper;
    
    public Long getGerenteMenosContas(Long idExcluir) throws GerenteDoesntExistException{
        List<Long> gerIds = repGerenciados.selectIdGerenteMenorNumGerenciados();
        if(!gerIds.isEmpty()){
            if(gerIds.size() > 1){
                if(!Objects.equals(gerIds.get(0), idExcluir) || idExcluir == null){
                    return gerIds.get(0);
                }else{
                    return gerIds.get(1);
                }
            }
            return gerIds.get(0);
        }
        throw new GerenteDoesntExistException("O BD de Gerente Está Vazio!");
    }

    public MensagemDTO atribuiGerenteAutocadastro(MensagemDTO msg) {
        try {
            AutocadastroDTO dto = mapper.map(msg.getSendObj(), AutocadastroDTO.class);
            
            Long id = getGerenteMenosContas(null);
            Optional<Gerente> gerente = repGer.findById(id);
            
            Gerenciados model = new Gerenciados();
            model.setGerenteId(gerente.get());
            model.setIdConta(dto.getIdConta());
            model.setSaldoPositivo(true);
            
            model = repGerenciados.save(model);
            
            GerenciadoGerenteDTO gerDto = new GerenciadoGerenteDTO();
            gerDto.setIdConta(model.getIdConta());
            gerDto.setGerenteNome(gerente.get().getNome());
            gerDto.setGerenteId(gerente.get().getId());
            
            msg.setSendObj(gerDto);
        }catch(DataIntegrityViolationException e){
            SQLException ex = ((ConstraintViolationException) e.getCause()).getSQLException();
            String campo = ex.getMessage();
            campo = campo.substring(campo.indexOf("(") + 1, campo.indexOf(")"));
            
            msg.setMensagem("Esse " + campo + " já existe!");
        } catch (GerenteDoesntExistException ex) {
            msg.setMensagem(ex.getMessage());
        }
        return msg;
    }

    public void rollbackAutocadastro(Long idGerenciado) {
        repGerenciados.deleteById(idGerenciado);
    }
    

    public MensagemDTO novoGerente(MensagemDTO msg){
        GerenteDTO dto = mapper.map(msg.getSendObj(), GerenteDTO.class);
        dto.setId(null);
        
        //Mapeia e salva Gerente pra guardar no bd de Event Sourcing no saga
        Gerente gerente = mapper.map(dto, Gerente.class);
        gerente = repGer.save(gerente);
        GerentePrimeiraContaDTO gDto = mapper.map(gerente, GerentePrimeiraContaDTO.class);
        
        
        //seleciona o gerente com maior numero de Gerenciados
        Long idGerente = repGerenciados.selectIdGerenteMaiorNumGerenciados();
        gDto.setIdOld(idGerente);
        
        //Seleciona um gerenciado do gerente com maior número
        Optional<Gerenciados> gerenciado = repGerenciados.selectOneGerenciadoByGerenteId(idGerente);
            
        if (gerenciado.isPresent()) {
            //Muda o gerente pro novo gerente
            Gerenciados g1 = gerenciado.get();
            g1.setGerenteId(gerente);
            g1 = repGerenciados.save(g1);
            
            gDto.setPrimeiraConta(g1.getIdConta());
            
            
            //Salva o gerenciado num DTO pra mandar pro saga que vai mandar pro mod. Conta
            GerenciadoGerenteSagaInsertDTO gerDto = new GerenciadoGerenteSagaInsertDTO();
            gerDto.setGerenteIdNew(gerente.getId());
            gerDto.setGerenteNomeNew(gerente.getNome());
            gerDto.setIdConta(g1.getIdConta());

            msg.setSendObj(gerDto); // Dados pra mandar pro prox saga
            
            msg.setReturnObj(gDto); //Dados pra salvar no Event Sourcing
        }
            
        return msg;
    }

    public void deleteGerente(MensagemDTO msg) {
        GerentePrimeiraContaDTO dto = mapper.map(msg.getSendObj(), GerentePrimeiraContaDTO.class);
        try{
            Optional<Gerenciados> ger = repGerenciados.findById(dto.getPrimeiraConta());
            if(ger.isPresent()){
                System.out.println("dadad");
                Gerenciados gerenciados = ger.get();
                Optional<Gerente> gerente = repGer.findById(dto.getIdOld());
                gerenciados.setGerenteId(gerente.get());
                repGerenciados.save(gerenciados);
                repGer.deleteById(dto.getId());
            }
            
        }catch(Exception e){
            System.err.println("Erro ao dar rollback na criação de Gerente:" + e.getMessage());
        }
    }

    public GerenciadoDTO rejeitaCliente(RejeitaClienteDTO dto) throws GerenciadoDoesntExistException {
        Optional<Gerenciados> model = repGerenciados.findById(dto.getIdConta());
        if(model.isPresent()){
            GerenciadoDTO dtoRet = mapper.map(model.get(), GerenciadoDTO.class);
            repGerenciados.deleteById(dto.getIdConta());
            return dtoRet;
        }
        throw new GerenciadoDoesntExistException("Esse Gerenciado Não Existe!");
    }

    public void rejeitaClienteRollback(GerenciadoDTO dtoRet) {
        Gerenciados model = mapper.map(dtoRet, Gerenciados.class);
        repGerenciados.save(model);
    }
    
    public MensagemDTO alteraGerente(MensagemDTO msg){
        GerenteDTO dtoGer = mapper.map(msg.getSendObj(), GerenteDTO.class);
        Optional<Gerente> model = repGer.findById(dtoGer.getId());
        if(model.isPresent()){
            Gerente ger = model.get();
            
            AlteraGerenteDTO altDto = new AlteraGerenteDTO();
            altDto.setOldEmail(ger.getEmail());
            altDto.setNewEmail(dtoGer.getEmail());
            msg.setReturnObj(altDto);
            
            ger.setEmail(dtoGer.getEmail());
            ger.setNome(dtoGer.getNome());
            ger.setTelefone(dtoGer.getTelefone());
            ger = repGer.save(ger);
            
            dtoGer = mapper.map(ger, GerenteDTO.class);
            msg.setSendObj(dtoGer);
            return msg;
        }
        msg.setMensagem("Um Gerente Com Esse Id não Existe!");
        return msg;
    }

    public void alteraGerenteRollback(GerenteDTO dtoGer) {
        Optional<Gerente> model = repGer.findById(dtoGer.getId());
        if(model.isPresent()){
            Gerente gerente = mapper.map(dtoGer, Gerente.class);
            repGer.save(gerente);
            return;
        }
        System.out.println("Deu Algum erro no Rollback de Alterar o Gerente");
    }
    
}
