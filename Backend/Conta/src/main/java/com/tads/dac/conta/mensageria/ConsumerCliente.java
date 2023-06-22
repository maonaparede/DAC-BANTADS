/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tads.dac.conta.mensageria;
import com.tads.dac.conta.DTOs.ClienteContaDTO;
import com.tads.dac.conta.exception.ClienteNotFoundException;
import com.tads.dac.conta.exception.ContaConstraintViolation;
import com.tads.dac.conta.exception.NegativeSalarioException;
import com.tads.dac.conta.modelR.ClienteR;
import com.tads.dac.conta.modelR.ContaR;
import com.tads.dac.conta.repositoryR.ClienteRepositoryR;
import com.tads.dac.conta.repositoryR.ContaRepositoryR;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerCliente {
    
    @Autowired
    private ClienteRepositoryR repCliente;
    
   @Autowired
    private ModelMapper mapper;
    
   //Só serve para atualizar o bd de R que tem algumas tabelas a Mais que o de CUD
    @RabbitListener(queues = "cliente")
    public void syncModuloCliente(@Payload ClienteContaDTO dto) throws ClienteNotFoundException, NegativeSalarioException, ContaConstraintViolation{

            ClienteR cliente = mapper.map(dto, ClienteR.class);    
            repCliente.save(cliente); //Salva o cliente no bd R (Read)
    }
    
}