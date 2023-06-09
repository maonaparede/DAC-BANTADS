/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tads.dac.saga.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PerfilContaUpdateSaga {
    
    @Id
    private Long sagaId;
    
    @CreationTimestamp
    @Column(name = "data_tempo")
    private Date dataTempo; 
    
    private Long idConta;
    
    private BigDecimal saldo;
    
    private BigDecimal limite;
    
    private String situacao;
    
    private Long idCliente;
    
    private Date dataCriacao;
    
    private Date dataAproRep;
}
