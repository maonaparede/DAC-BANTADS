
package com.tads.dac.conta.modelR;

import com.tads.dac.conta.modelCUD.*;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_operacao")
public class OperacaoR {
    
    @Id
    private Long id;
    
    @CreationTimestamp
    @Column(name = "data_tempo")
    private Date dataTempo;
    
    private BigDecimal valor;
    @Column(columnDefinition="CHAR(1)")
    private String operacao;
    
    @ManyToOne
    @JoinColumn(name = "de_user")
    private ContaR deUser;
    
    @ManyToOne
    @JoinColumn(name = "para_user")
    private ContaR paraUser;
  
    
}
