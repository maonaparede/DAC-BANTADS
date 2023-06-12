
package com.tads.dac.saga.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@Entity
public class AlteraGerenteAuth implements Serializable{
 
    @Id
    private Long sagaId;
    
    @CreationTimestamp
    @Column(name = "data_saga")
    private Date dataTempo;
    
    private String oldEmail;
    private String newEmail;
    
}
