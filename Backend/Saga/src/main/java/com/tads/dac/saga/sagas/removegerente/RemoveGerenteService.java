/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tads.dac.saga.sagas.removegerente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveGerenteService {
    
    @Autowired
    private Saga1RemGerConsultaProducerConsumer saga;
    
    public void initSaga(Long id){
        saga.requestConsulta(id);
    }
    
}
