
package com.tads.dac.gerente.service;

import com.tads.dac.gerente.DTOs.GerenciadoSaldoDTO;
import com.tads.dac.gerente.model.Gerenciados;
import com.tads.dac.gerente.repository.GerenciadosRepository;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class GerenciadosServiceImp{

    @Autowired
    private GerenciadosRepository rep;

    
    public Gerenciados save(Gerenciados gerenciados) {
        return rep.save(gerenciados);
    }

    public List<Gerenciados> findAll() {
        return rep.findAll();
    }

    public List<Gerenciados> findByGerente(Long id) {
        return rep.findByGerenteId(id);
    }

    public Optional<Gerenciados> findById(Long id) {
        return rep.findById(id);
    }

    public Gerenciados update(Gerenciados gerenciados) {
        return rep.save(gerenciados);
    }

    public void deleteById(Long id) {
        rep.deleteById(id);
    }
    
}
