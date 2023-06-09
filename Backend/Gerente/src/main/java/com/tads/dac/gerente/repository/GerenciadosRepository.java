
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
                                       select id_ger from (select g.id as id_ger, COUNT(m.id_conta) as total from tb_gerente g 
                                       left join tb_gerenciados m on g.id = m.gerente_id 
                                       group by g.id order by total desc limit 1) tb""")
    Long selectIdGerenteMaiorNumGerenciados();
    
    
    @Query(nativeQuery = true, value = """
                                       select id_ger from (select g.id as id_ger, COUNT(m.id_conta) as total from tb_gerente g 
                                       left join tb_gerenciados m on g.id = m.gerente_id 
                                       group by g.id order by total asc limit 2) tb""")
    List<Long> selectIdGerenteMenorNumGerenciados();

    @Transactional //  A transação é uma unidade de trabalho isolada que leva o banco de dados de um estado consistente a outro estado consistente
    @Modifying // Retorna numero de linhas alteradas no bd
    @Query(nativeQuery = true, value = "update tb_gerenciados set gerente_id = :id_destino where gerente_id = :id_origem ")
    int transferirTodasAsContas(@Param("id_origem") Long id_origem, @Param("id_destino") Long id_destino);
    
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update tb_gerenciados set gerente_id = :gerente where id_conta = :conta")
    int mudaGerenteConta(@Param("conta") Long conta, @Param("gerente") Long gerente);
    
    @Query(nativeQuery = true, value = "select id_conta, gerente_id, saldo_positivo from tb_gerenciados where gerente_id = :id limit 1")
    Optional<Gerenciados> selectOneGerenciadoByGerenteId(@Param("id") Long id);

}

