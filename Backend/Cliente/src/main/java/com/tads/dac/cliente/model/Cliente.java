
package com.tads.dac.cliente.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(schema = "cliente")
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
