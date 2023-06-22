
package com.tads.dac.conta.service;

import com.tads.dac.conta.DTOs.AprovaR9DTO;
import com.tads.dac.conta.DTOs.ClienteContaDTO;
import com.tads.dac.conta.DTOs.ClienteContaInfoDTO;
import com.tads.dac.conta.DTOs.ContaDTO;
import com.tads.dac.conta.DTOs.GerenteIdNomeDTO;
import com.tads.dac.conta.DTOs.GerenteNewOldDTO;
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
    
    public ClienteContaInfoDTO getContaByIdCliente(Long idCliente) throws ClienteNotFoundException{
        Optional<ContaR> conta = repR.findByIdCliente(idCliente);
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

    public void atualizaNomeGerente(GerenteIdNomeDTO dto) {
        repCUD.updateGerenteIdNome(dto.getId(), dto.getNome(), dto.getId());
        GerenteNewOldDTO retDto = new GerenteNewOldDTO();
        retDto.setIdNew(dto.getId());
        retDto.setIdOld(dto.getId());
        retDto.setNomeNew(dto.getNome());
        
        mensagemProducer.syncRemoveGerenteCommit(retDto);

    }
    
}
