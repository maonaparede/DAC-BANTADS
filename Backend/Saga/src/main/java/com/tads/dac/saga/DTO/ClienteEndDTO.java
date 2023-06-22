/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tads.dac.saga.DTO;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteEndDTO {
 
    private Long id;
    
    private String nome;
    
    private String email;
    
    private String telefone;
    
    private BigDecimal salario;
    
    private String cpf;
    
    private String logradouro;
    
    private String complemento;
    
    private String cidade;
    
    private String estado;
    
    private String tipo;
    
    private String cep;
    
    private Integer numero;
    
}
