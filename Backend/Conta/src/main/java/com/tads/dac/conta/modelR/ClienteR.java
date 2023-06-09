
package com.tads.dac.conta.modelR;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class ClienteR implements Serializable{
    
    @Id
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private BigDecimal salario;
    
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;
    
    private String cidade;
    
    private String estado;   
      
}
