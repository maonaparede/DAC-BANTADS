
package com.tads.dac.saga.repository;

import com.tads.dac.saga.model.InsertGerenteConta;
import com.tads.dac.saga.model.InsertGerenteGerente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsertGerenteContaRepository extends JpaRepository<InsertGerenteConta, Long>{
    
}
