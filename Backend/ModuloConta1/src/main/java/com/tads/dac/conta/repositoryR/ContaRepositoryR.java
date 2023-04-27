
package com.tads.dac.conta.repositoryR;

import com.tads.dac.conta.modelR.ContaR;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContaRepositoryR extends JpaRepository<ContaR, Long> {
    
    public List<ContaR> findAllBySituacao(String sit);
    
    public Optional<ContaR> findByIdCliente(Long id);
}

