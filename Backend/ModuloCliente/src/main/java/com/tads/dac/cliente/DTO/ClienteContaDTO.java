/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tads.dac.cliente.DTO;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteContaDTO {

    private Long id;
    
    private String nome;
    
    private BigDecimal salario;
    
    private String cpf;
  
    private String nomeGerente;
    
    private Long idGerente;
    
}
