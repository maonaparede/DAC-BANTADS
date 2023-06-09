
package com.tads.dac.gerente.repository;

import com.tads.dac.gerente.DTOs.GerenteDashboardDTO;
import com.tads.dac.gerente.model.Gerente;
import java.util.List;
import javax.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface GerenteRepository extends JpaRepository<Gerente, Long> {

    
    @Query(nativeQuery = true, value = """
           select gr.gerente_id id, ge.nome, ge.email, count(gr.gerente_id) clientes , count(CASE WHEN gr.saldo_positivo THEN 1 END) pos, count(CASE WHEN NOT gr.saldo_positivo THEN 1 END) neg 
           from tb_gerenciados gr inner join tb_gerente ge on gr.gerente_id = ge.id
           group by gr.gerente_id, ge.nome, ge.email
           """)
    List<Tuple> findDashboard();
    
    @Query("select g from Gerente g order by g.nome asc")
    public List<Gerente> findAllOrderByNome();
}
