/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tads.dac.saga.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
public class RejeitaClienteContentEmail implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long sagaId;
    
    @CreationTimestamp
    @Column(name = "data_saga")
    private Date dataTempo;
    
    private String emailTo;
    
    @Lob
    private String content;
    
}
