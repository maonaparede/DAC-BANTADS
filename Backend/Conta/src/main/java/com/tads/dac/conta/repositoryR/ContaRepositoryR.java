
package com.tads.dac.conta.repositoryR;

import com.tads.dac.conta.modelR.ContaR;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ContaRepositoryR extends JpaRepository<ContaR, Long> {
    
    
    public Optional<ContaR> findByIdCliente(Long id);
    
    @Transactional //  A transação é uma unidade de trabalho isolada que leva o banco de dados de um estado consistente a outro estado consistente
    @Modifying // Retorna numero de linhas alteradas no bd    
    @Query(nativeQuery = true, 
        value = "update contar.tb_conta set idgerente = ?1 , nomegerente = ?2 where idgerente = ?3 ")
    public int updateGerenteIdNome(Long oldId, String newNome, Long newId);
}

