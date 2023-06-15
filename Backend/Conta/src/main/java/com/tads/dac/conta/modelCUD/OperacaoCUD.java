
package com.tads.dac.conta.modelCUD;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "tb_operacao", schema = "conta")
public class OperacaoCUD implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @CreationTimestamp
    @Column(name = "data_tempo")
    private Date dataTempo;
    
    private BigDecimal valor;
    @Column(columnDefinition="CHAR(1)")
    private String operacao;
    
    @Column(name = "de_user")
    private ContaCUD deUser;
    
    @Column(name = "para_user")
    private ContaCUD paraUser;

    @ManyToOne
    @JoinColumn(name = "id_conta")    
    public ContaCUD getDeUser() {
        return deUser;
    }
    
    @ManyToOne
    @JoinColumn(name = "id_conta")
    public ContaCUD getParaUser() {
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
