/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tads.dac.cliente.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cliente implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String telefone;
    
    @Column(nullable = false)
    private BigDecimal salario;
    
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;
  
    @Column(name = "nome_gerente")
    private String nomeGerente;
    
    @Column(name = "id_gerente")
    private Long idGerente;
    
    @Column(length = 100, nullable = false, name = "end_logradouro")
    private String logradouro;
    
    @Column(name = "end_complemento")
    private String complemento;
    
    @Column(nullable = false, name = "end_cidade")
    private String cidade;
    
    @Column(length = 2, nullable = false, name = "end_estado")
    private String estado;
    
    @Column(nullable = false, name = "end_tipo")
    private String tipo;
    
    @Column(length = 8, nullable = false, name = "end_cep")
    private String cep;
    
    @Column(nullable = false, name = "end_numero")
    private Integer numero;
}
