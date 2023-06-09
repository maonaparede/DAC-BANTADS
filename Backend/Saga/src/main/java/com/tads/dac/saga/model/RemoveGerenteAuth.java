
package com.tads.dac.saga.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
public class RemoveGerenteAuth implements Serializable{
    
    @Id
    private Long sagaId;
    
    @CreationTimestamp
    private Date sagaTempo;
        
    private String email;
    private String senha;
    private String salt;
    private String tipoUser;
    
}
