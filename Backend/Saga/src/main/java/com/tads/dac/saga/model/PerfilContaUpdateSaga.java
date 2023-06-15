
package com.tads.dac.saga.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "saga")
public class PerfilContaUpdateSaga implements Serializable {
    
    @Id
    private Long sagaId;
    
    @CreationTimestamp
    @Column(name = "data_tempo")
    private Date dataTempo; 
    
    private Long idConta;
    
    private BigDecimal saldo;
    
    private BigDecimal limite;
    
    private String situacao;
    
    private Long idCliente;
    
    private Date dataCriacao;
    
    private Date dataAproRep;
}
