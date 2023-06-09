
package com.tads.dac.gerente.service;

import com.tads.dac.gerente.DTOs.GerenteDTO;
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
import java.util.stream.Collectors;
import javax.persistence.Tuple;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AdmServiceImp{
    
    @Autowired
    private GerenteRepository rep;
    
    @Autowired
    private GerenciadosRepository gerRep;

    @Autowired
    private ModelMapper mapper;
    
    public GerenteDTO save(Gerente gerente) throws GerenteConstraintViolation{
        try{
            Gerente ger = new Gerente();
            ger.setCpf(gerente.getCpf());
            ger.setEmail(gerente.getEmail());
            ger.setNome(gerente.getNome());
            ger.setTelefone(gerente.getTelefone());
            
            ger = rep.save(ger);

            Long gerenteMaiorNumCliente = gerRep.selectIdGerenteMaiorNumGerenciados();
            
            List<Gerenciados> list = gerRep.findByGerenteId(gerenteMaiorNumCliente);
            if(list.size() > 1){
                gerRep.mudaGerenteConta(list.get(0).getIdConta(), ger.getId());
            }
            
            GerenteDTO dto = mapper.map(ger, GerenteDTO.class);
            return dto;
        }catch(DataIntegrityViolationException e){
            SQLException ex = ((ConstraintViolationException) e.getCause()).getSQLException();
            String campo = ex.getMessage();
            campo = campo.substring(campo.indexOf("(") + 1, campo.indexOf(")"));
            throw new GerenteConstraintViolation("Esse " + campo + " já existe!");
        }
    }

    
    public List<GerenteDTO> listarGerente() {
        List<Gerente> model = rep.findAllOrderByNome();
        List<GerenteDTO> dto = model.stream()
                .map(user -> mapper.map(user, GerenteDTO.class))
                .collect(Collectors.toList());
        return dto;
    }

    
    public GerenteDTO findById(Long id) throws GerenteDoesntExistException{
        Optional<Gerente> ger = rep.findById(id);
        if(ger.isPresent()){
            GerenteDTO dto = mapper.map(ger.get(), GerenteDTO.class);
            return dto;
        }else{
            throw new GerenteDoesntExistException("Um Gerente Com Esse Id Não Existe!");
        }
    }
    
    
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

    
    public void deleteById(Long id) throws DeleteLastGerenteException{
        Long count = rep.count();
        if(count > 1){
            Long id_dest = gerRep.selectIdGerenteMenorNumGerenciados().get(0);
            
            //Se a conta que estiver sendo excluida for a mesma que contem o menor numero de gerenciados
            if(id.equals(id_dest)){
                List<Gerente> gers = rep.findAll(); //Busca todas os gerentes
                for (Gerente ger : gers) { //Itera sobre todos até achar um que não seja a conta a ser excluida
                    if(!id.equals(ger.getId())){
                        gerRep.transferirTodasAsContas(id, ger.getId());//Transfere tds as contas
                        break;
                    }
                }
            }else{
                gerRep.transferirTodasAsContas(id, id_dest);
            }
            
            rep.deleteById(id); 
        }else{
          throw new DeleteLastGerenteException("É Necessário Existir No Mínimo um (1) Gerente!");
        }
    }

    
}
