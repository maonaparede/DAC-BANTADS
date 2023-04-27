
package com.tads.dac.conta.repositoryCUD;

import com.tads.dac.conta.modelCUD.ContaCUD;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContaRepositoryCUD extends JpaRepository<ContaCUD, Long> {
    
    public List<ContaCUD> findAllBySituacao(String sit);
    
    public Optional<ContaCUD> findByIdCliente(Long id);
}

