
package com.tads.dac.conta.modelR;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_operacao", schema = "contar")
public class OperacaoR implements Serializable {
    
    @Id
    private Long id;
    
    @CreationTimestamp
    @Column(name = "data_tempo")
    private Date dataTempo;
    
    private BigDecimal valor;
    @Column(columnDefinition="CHAR(1)")
    private String operacao;
    
    @Column(name = "de_user")
    private ContaR deUser;
    
    @Column(name = "para_user")
    private ContaR paraUser;
    
    @ManyToOne
    @JoinColumn(name = "id_conta")
    public ContaR getDeUser() {
        return deUser;
    }
    
    @ManyToOne
    @JoinColumn(name = "id_conta")
    public ContaR getParaUser() {
        return paraUser;
    }

    
    
    public Long getId() {
        return id;
    }

    public Date getDataTempo() {
        return dataTempo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getOperacao() {
        return operacao;
    }
  
  
}
