
package com.tads.dac.conta.repositoryR;

import com.tads.dac.conta.modelR.OperacaoR;
import java.util.Date;
import java.util.List;
import javax.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface OperacaoRepositoryR extends JpaRepository<OperacaoR, Long> {
    
    @Query(nativeQuery = true,  
        value = "select id, data_tempo, operacao, valor, de_user, para_user from tb_operacao where" +
                " de_user = ?1 or para_user = ?1 " +
            " and data_tempo between date( ?2 ) and date( ?3 ) ")
    List<Tuple> getExtrato(Long id, Date dtinicio, Date dtfim);
       
}
