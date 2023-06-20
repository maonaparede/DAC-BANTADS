
package com.tads.dac.cliente.repository;

import com.tads.dac.cliente.model.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
    @Transactional //  A transação é uma unidade de trabalho isolada que leva o banco de dados de um estado consistente a outro estado consistente
    @Modifying // Retorna numero de linhas alteradas no bd
    @Query(nativeQuery = true, value = "update cliente set id_gerente = ?1 , nome_gerente = ?2 where id_gerente = ?3 ;")
    int updateTransferAllClientes(Long idNew, String nomeNew, Long idOld);
    
    @Transactional //  A transação é uma unidade de trabalho isolada que leva o banco de dados de um estado consistente a outro estado consistente
    @Modifying // Retorna numero de linhas alteradas no bd
    @Query(nativeQuery = true, value = "update cliente set id_gerente = ?1 , nome_gerente = ?2 where id = ?3")
    int updateGerenteCliente(Long idGerente, String nomeGerente, Long idCliente);
    
    public Optional<Cliente> findByEmail(String email);
}
