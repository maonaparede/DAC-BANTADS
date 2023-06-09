package com.tads.dac.gerente.DTOs;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GerenteDashboardDTO implements Serializable {
    
    private Long id;
    private String nome;
    private String email;
    private Long clientes;
    private Long pos;
    private Long neg;

   
}
