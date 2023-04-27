
package com.tads.dac.gerente.service;

import com.tads.dac.gerente.model.Gerenciados;
import com.tads.dac.gerente.repository.GerenciadosRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;


public class GerenciadosServiceImp implements GerenciadosService{

    @Autowired
    private GerenciadosRepository rep;
    
    
    @Override
    public Gerenciados save(Gerenciados gerenciados) {
        return rep.save(gerenciados);
    }

    @Override
    public List<Gerenciados> findAll() {
        return rep.findAll();
    }

    @Override
    public List<Gerenciados> findByGerente(Long id) {
        return rep.findByGerenteId(id);
    }

    @Override
    public Optional<Gerenciados> findById(Long id) {
        return rep.findById(id);
    }

    @Override
    public Gerenciados update(Gerenciados gerenciados) {
        return rep.save(gerenciados);
    }

    @Override
    public void deleteById(Long id) {
        rep.deleteById(id);
    }
    
}
