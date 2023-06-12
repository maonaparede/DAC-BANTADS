
package com.tads.dac.saga.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@Entity
public class AlteraGerenteGerente implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long sagaId;
    
    @CreationTimestamp
    private Date sagaTempo;
        
    private Long id;
    
    private String cpf;
    
    private String nome;
    
    private String email;
    
    private String telefone;
    
}
