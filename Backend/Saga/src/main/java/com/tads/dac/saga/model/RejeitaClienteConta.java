
package com.tads.dac.saga.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
public class RejeitaClienteConta implements Serializable{
    
    @Id
    private Long sagaId;
    
    @CreationTimestamp
    @Column(name = "data_saga")
    private Date dataTempo;    
    
    @Column(name = "id_conta")
    private Long idConta;
    
    private BigDecimal saldo;
    private BigDecimal limite;
    
    private String situacao;
       
    private Long idCliente;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAproRep;

    private Long idGerente;
    
    private String nomeGerente;    
}
