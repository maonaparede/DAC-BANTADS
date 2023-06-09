
package com.tads.dac.conta.repositoryR;

import com.tads.dac.conta.DTOs.ClienteContaInfoDTO;
import com.tads.dac.conta.modelR.ClienteR;
import com.tads.dac.conta.modelR.ContaR;
import java.util.List;
import javax.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ClienteRepositoryR extends JpaRepository<ClienteR, Long>{
    
    @Query(nativeQuery = true, value = """
        select c.id_conta idconta, c.id_cliente idcliente, c.saldo, cl.nome, cl.cpf, cl.cidade,
        cl.estado from clienter cl, tb_conta c where cl.id = c.id_cliente and c.situacao = 'A'
        order by cl.nome                               
                                       """)
    List<Tuple> getAllClientes();
    
    
    @Query(nativeQuery = true, value = """
        select c.id_conta idconta, c.id_cliente idcliente, c.saldo, cl.nome, cl.cpf, cl.cidade,
        cl.estado from clienter cl, tb_conta c where cl.id = c.id_cliente and c.situacao = 'A'
        order by c.saldo desc limit 3;
                                       """)
    List<Tuple> get3BestClientes();
    
    @Query(nativeQuery = true, value = """
    select c.id_conta idconta, cl.nome, cl.cpf, c.nomegerente,
    c.limite, c.saldo from clienter cl, tb_conta c where
    cl.id = c.id_cliente and c.situacao = 'A' order by cl.nome;
                                       """)
    List<Tuple> getAllClientesRelatorioAdm();
    
    @Query(nativeQuery = true, value = """
            select c.id_conta idconta, c.id_cliente idcliente, c.saldo, cl.nome, cl.cpf, cl.cidade,
            cl.estado from clienter cl, tb_conta c where cl.id = c.id_cliente and c.situacao = 'A'
             and c.idgerente = ?1 order by cl.nome 
                                       """)
    List<Tuple> getAllClientesGerente(Long id);
    
    
    @Query(nativeQuery = true , value = """
                select c.id_conta idconta, c.id_cliente idcliente, cl.cpf, cl.nome, cl.salario
                from clienter cl, tb_conta c where cl.id = c.id_cliente 
                and c.situacao = ?1 and idgerente = ?2 order by cl.nome;
                                        """)
    public List<Tuple> findAllBySituacaoAndIdGerente(String sit, Long idGerente);
    
}
