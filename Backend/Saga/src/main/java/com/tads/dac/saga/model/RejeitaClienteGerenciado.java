
package com.tads.dac.saga.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(schema = "saga")
public class RejeitaClienteGerenciado implements Serializable{
    
    @Id
    private Long sagaId;
    
    @CreationTimestamp
    @Column(name = "data_saga")
    private Date dataTempo;
    
    private Long idConta;
    
    private Boolean saldoPositivo;
    
    private Long gerenteId;
    
    
}
