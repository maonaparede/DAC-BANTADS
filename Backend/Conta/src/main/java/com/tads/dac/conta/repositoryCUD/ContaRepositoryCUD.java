
package com.tads.dac.conta.repositoryCUD;

import com.tads.dac.conta.modelCUD.ContaCUD;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface ContaRepositoryCUD extends JpaRepository<ContaCUD, Long> {
    
    public List<ContaCUD> findAllBySituacao(String sit);
    
    public Optional<ContaCUD> findByIdCliente(Long id);
    
    public List<ContaCUD> findByIdGerente(Long id);
    
    @Transactional //  A transação é uma unidade de trabalho isolada que leva o banco de dados de um estado consistente a outro estado consistente
    @Modifying // Retorna numero de linhas alteradas no bd    
    @Query(nativeQuery = true, 
            value = "update conta.tb_conta set idgerente = ?1 , nomegerente = ?2 where idgerente = ?3 ")
    public int updateGerenteIdNome(Long oldId, String newNome, Long newId);
}

