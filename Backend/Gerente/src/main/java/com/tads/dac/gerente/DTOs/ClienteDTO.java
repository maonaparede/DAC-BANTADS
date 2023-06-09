/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tads.dac.gerente.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClienteDTO {
    
    //DTO são as classes que vão servir de modelo pra transferencia de dados
    
    private Integer idConta;
    private String nome;
    private String cpf;
    private String nomeGerente;
    private Long idGerente;
    private Float saldo;
    private Float limite;

    
}
