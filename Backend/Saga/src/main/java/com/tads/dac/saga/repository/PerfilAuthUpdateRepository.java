
package com.tads.dac.saga.repository;

import com.tads.dac.saga.model.PerfilAuthUpdateSaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilAuthUpdateRepository extends JpaRepository<PerfilAuthUpdateSaga, Long>{
    
}
