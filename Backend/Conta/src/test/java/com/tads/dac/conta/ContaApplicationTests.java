package com.tads.dac.conta;

import com.tads.dac.conta.DTOs.AprovaR9DTO;
import com.tads.dac.conta.DTOs.ClienteContaInfoDTO;
import com.tads.dac.conta.DTOs.ContaDTO;
import com.tads.dac.conta.DTOs.RelatorioClienteDTO;
import com.tads.dac.conta.repositoryR.ClienteRepositoryR;
import com.tads.dac.conta.service.ContaService;
import com.tads.dac.conta.service.SysAdmService;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ContaApplicationTests {

@Autowired
private SysAdmService serv;

@Autowired
private ContaService ctServ;

@Test
void testar(){
    List<RelatorioClienteDTO> lista= serv.relatorioClientes();
    for (RelatorioClienteDTO cl : lista) {
        System.out.println("cl: " + cl.getNomeCliente());
    }
}

@Test
void listad(){
    
    Long id = new Long("6");
    List<AprovaR9DTO> lista = serv.getAllSituacaoEsperando(id);
    for (AprovaR9DTO contaDTO : lista) {
        System.out.println("DAD: " + contaDTO.toString());
    }

}
void a(){
    listad();
}

}
