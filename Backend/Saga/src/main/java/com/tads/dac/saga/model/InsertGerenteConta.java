
package com.tads.dac.saga.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InsertGerenteConta {

    @Id
    private Long sagaId;
    
    @CreationTimestamp
    @Column(name = "data_saga")
    private Date dataTempo;
    
    private Long idConta;
    private Long gerenteIdNew;
    private String gerenteNomeNew;
    private Long gerenteIdOld;
    private String gerenteNomeOld;    
}
