
package com.tads.dac.gerente.service;

import com.tads.dac.gerente.model.Gerenciados;
import java.util.List;
import java.util.Optional;


public interface GerenciadosService {
    
    Gerenciados save(Gerenciados gerenciados);
    
    List<Gerenciados> findAll();
    
    List<Gerenciados> findByGerente(Long id);
    
    Optional<Gerenciados> findById(Long id);
    
    Gerenciados update(Gerenciados gerenciados);
    
    void deleteById(Long id);    
}
