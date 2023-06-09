

package com.tads.dac.saga.repository;

import com.tads.dac.saga.model.PerfilClienteUpdateSaga;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PerfilClienteUpdateRepository extends JpaRepository<PerfilClienteUpdateSaga, Long>{
    
}
