
package com.tads.dac.saga.repository;

import com.tads.dac.saga.model.RejeitaClienteConta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejeitaClienteContaRepository extends JpaRepository<RejeitaClienteConta, Long> {
    
}
