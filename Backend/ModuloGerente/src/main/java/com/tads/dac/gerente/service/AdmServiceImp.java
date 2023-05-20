/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tads.dac.gerente.service;

import com.tads.dac.gerente.DTOs.GerenteDashboardDTO;
import com.tads.dac.gerente.exceptions.DeleteLastGerenteException;
import com.tads.dac.gerente.exceptions.GerenteConstraintViolation;
import com.tads.dac.gerente.exceptions.GerenteDoesntExistException;
import com.tads.dac.gerente.model.Gerenciados;
import com.tads.dac.gerente.model.Gerente;
import com.tads.dac.gerente.repository.GerenciadosRepository;
import com.tads.dac.gerente.repository.GerenteRepository;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Tuple;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AdmServiceImp implements AdmService{
    
    @Autowired
    private GerenteRepository rep;
    
    @Autowired
    private GerenciadosRepository gerRep;

    @Override
    public Gerente save(Gerente gerente) throws GerenteConstraintViolation{
        try{
            Gerente ger = rep.save(new Gerente(gerente.getCpf(), gerente.getNome(), 
                    gerente.getEmail(), gerente.getTelefone())
            );

            Optional<Long> gerenteMaiorNumCliente = gerRep.selectIdGerenteMaiorNumGerenciados();
            
            List<Gerenciados> list = gerRep.findByGerenteId(gerenteMaiorNumCliente.get());
            if(list.size() > 1){
                gerRep.mudaGerenteConta(list.get(0).getIdConta(), ger.getId());
            }
                
            return ger;
        }catch(DataIntegrityViolationException e){
            SQLException ex = ((ConstraintViolationException) e.getCause()).getSQLException();
            String campo = ex.getMessage();
            campo = campo.substring(campo.indexOf("(") + 1, campo.indexOf(")"));
            throw new GerenteConstraintViolation("Esse " + campo + " já existe!");
        }
    }

    @Override
    public List<Gerente> findAll() {
        return rep.findAllOrderByNome();
    }

    @Override
    public Gerente findById(Long id) throws GerenteDoesntExistException{
        Optional<Gerente> ger = rep.findById(id);
        if(ger.isPresent()){
            return ger.get();
        }else{
            throw new GerenteDoesntExistException("Um Gerente Com Esse Id Não Existe!");
        }
    }
    
    @Override
    public List<GerenteDashboardDTO> findDashboard() {
        List<Tuple> tuples = rep.findDashboard();
        List<GerenteDashboardDTO> gers = new ArrayList<>();
        for (Tuple tuple : tuples) {
            GerenteDashboardDTO ger = new GerenteDashboardDTO();
            ger.setId(((BigInteger)tuple.get("id")).longValue());
            ger.setNome((String)tuple.get("nome"));
            ger.setEmail((String)tuple.get("email"));
            ger.setClientes(((BigInteger) tuple.get("clientes")).longValue());
            ger.setPos(((BigInteger)tuple.get("pos")).longValue());
            ger.setNeg(((BigInteger)tuple.get("neg")).longValue());
            
            gers.add(ger);
        }
        
        return gers;
    }

    @Override
    public Gerente update(Gerente gerente, Long id) throws GerenteDoesntExistException, GerenteConstraintViolation{
        Optional<Gerente> ger = rep.findById(id);
        if(ger.isPresent()){
            
            Gerente gerupdate = ger.get();
            
            gerupdate.setEmail(gerente.getEmail());
            gerupdate.setTelefone(gerente.getTelefone());
            gerupdate.setNome(gerente.getNome());
            
            try {
               gerupdate = rep.save(gerupdate); 
            }catch(DataIntegrityViolationException e){
                SQLException ex = ((ConstraintViolationException) e.getCause()).getSQLException();
                String campo = ex.getMessage();
                campo = campo.substring(campo.indexOf("(") + 1, campo.indexOf(")"));
                throw new GerenteConstraintViolation("Esse " + campo + " já existe!");
            }
            
            
            return gerupdate;
        }else{
            throw new GerenteDoesntExistException("Um Gerende Com Esse Id Não Existe!");
        }
    }

    @Override
    public void deleteById(Long id) throws DeleteLastGerenteException{
        Long count = rep.count();
        if(count > 1){
            Optional<Long> id_dest = gerRep.selectIdGerenteMenorNumGerenciados();
            
            //Se a conta que estiver sendo excluida for a mesma que contem o menor numero de gerenciados
            if(id.equals(id_dest.get())){
                List<Gerente> gers = rep.findAll(); //Busca todas os gerentes
                for (Gerente ger : gers) { //Itera sobre todos até achar um que não seja a conta a ser excluida
                    if(!id.equals(ger.getId())){
                        gerRep.transferirTodasAsContas(id, ger.getId());//Transfere tds as contas
                        break;
                    }
                }
            }else{
                gerRep.transferirTodasAsContas(id, id_dest.get());
            }
            
            rep.deleteById(id); 
        }else{
          throw new DeleteLastGerenteException("É Necessário Existir No Mínimo um (1) Gerente!");
        }
    }

    
}
