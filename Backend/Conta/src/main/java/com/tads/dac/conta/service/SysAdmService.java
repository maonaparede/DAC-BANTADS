
package com.tads.dac.conta.service;

import com.tads.dac.conta.DTOs.AprovaR9DTO;
import com.tads.dac.conta.DTOs.ClienteContaInfoDTO;
import com.tads.dac.conta.DTOs.RelatorioClienteDTO;
import com.tads.dac.conta.modelR.ContaR;
import com.tads.dac.conta.repositoryR.ClienteRepositoryR;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysAdmService {
    
    @Autowired
    private ClienteRepositoryR repCliente;
    
    
    //R14
    public List<ClienteContaInfoDTO> get3MaioresSaldoClientes(Long id){
        List<Tuple> lst = repCliente.get3BestClientes(id);
        List<ClienteContaInfoDTO> lista = mapTupleClienteContaInfo(lst);
        return lista;
    }
    
    //R16
    public List<RelatorioClienteDTO> relatorioClientes(){
        List<Tuple> lst = repCliente.getAllClientesRelatorioAdm();
        List<RelatorioClienteDTO> dto = mapTupleRelatorioClienteDTO(lst);
        
        return dto;
    }
    
    //R12
    public List<ClienteContaInfoDTO> clientesDoGerente(Long id){
        List<Tuple> lst = repCliente.getAllClientesGerente(id);
        List<ClienteContaInfoDTO> dto = mapTupleClienteContaInfo(lst);
        return dto;    
    }
 
    public List<AprovaR9DTO> getAllSituacaoEsperando(Long idGerente){
        List<Tuple> contas = repCliente.findAllBySituacaoAndIdGerente("E", idGerente);
        List<AprovaR9DTO> lista = new ArrayList<>();
        for (Tuple tuple : contas) {
            AprovaR9DTO dto = new AprovaR9DTO();
            dto.setIdCliente(((BigInteger)tuple.get("idcliente")).longValue());    
            dto.setNome(tuple.get("nome", String.class));
            dto.setCpf(tuple.get("cpf", String.class));
            dto.setSalario(tuple.get("salario", BigDecimal.class));
            lista.add(dto);
        }
        return lista;
    }
    
    private List<ClienteContaInfoDTO> mapTupleClienteContaInfo(List<Tuple> lst){
        List<ClienteContaInfoDTO> lista = new ArrayList<>();
        for (Tuple tuple : lst) {
            ClienteContaInfoDTO dto = new ClienteContaInfoDTO();
            dto.setIdConta(((BigInteger)tuple.get("idconta")).longValue());
            dto.setIdCliente(((BigInteger)tuple.get("idcliente")).longValue());
            dto.setSaldo(tuple.get("saldo", BigDecimal.class));
            dto.setNome(tuple.get("nome", String.class));
            dto.setCpf(tuple.get("cpf", String.class));
            dto.setCidade(tuple.get("cidade", String.class));
            dto.setEstado(tuple.get("estado", String.class));
            lista.add(dto);
        }
        return lista;
    }

    private List<RelatorioClienteDTO> mapTupleRelatorioClienteDTO(List<Tuple> lst){
        List<RelatorioClienteDTO> lista = new ArrayList<>();
        for (Tuple tuple : lst) {
            RelatorioClienteDTO dto = new RelatorioClienteDTO();
            dto.setIdConta(Long.getLong(tuple.get("idconta").toString()));
            dto.setNomeCliente(tuple.get("nome", String.class));
            dto.setCpfCliente(tuple.get("cpf", String.class));
            dto.setNomeGerente(tuple.get("nomegerente", String.class));
            dto.setSaldo(tuple.get("saldo", BigDecimal.class));
            dto.setLimite(tuple.get("limite", BigDecimal.class));
            
            lista.add(dto);
        }
        return lista;
    }
    
 
    
}
