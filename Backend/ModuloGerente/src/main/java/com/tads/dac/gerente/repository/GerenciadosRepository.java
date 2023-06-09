
package com.tads.dac.gerente.repository;

import com.tads.dac.gerente.model.Gerenciados;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface GerenciadosRepository extends JpaRepository<Gerenciados, Long> {
    
    @Query(nativeQuery = true, value = "select id_conta, gerente_id, saldo_positivo from tb_gerenciados where gerente_id = :id")
    List<Gerenciados> findByGerenteId(@Param("id") Long id); //@Param("id") 
    
    
    @Query(nativeQuery = true, value = """
                                       select gerente_id from (select gerente_id , count(gerente_id) ct from tb_gerenciados group by gerente_id
                                       order by ct desc limit 1) tb""")
    Optional<Long> selectIdGerenteMaiorNumGerenciados();
    
    
    @Query(nativeQuery = true, value = """
                                       select gerente_id from (select gerente_id , count(gerente_id) ct from tb_gerenciados group by gerente_id
                                       order by ct asc limit 1) tb""")
    Optional<Long> selectIdGerenteMenorNumGerenciados();

    @Transactional //  A transação é uma unidade de trabalho isolada que leva o banco de dados de um estado consistente a outro estado consistente
    @Modifying // Retorna numero de linhas alteradas no bd
    @Query(nativeQuery = true, value = "update tb_gerenciados set gerente_id = :id_destino where gerente_id = :id_origem ")
    int transferirTodasAsContas(@Param("id_origem") Long id_origem, @Param("id_destino") Long id_destino);
    
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update tb_gerenciados set gerente_id = :gerente where id_conta = :conta")
    int mudaGerenteConta(@Param("conta") Long conta, @Param("gerente") Long gerente);

}

