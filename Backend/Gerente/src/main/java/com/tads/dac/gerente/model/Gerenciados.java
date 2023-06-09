
package com.tads.dac.gerente.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "tb_gerenciados")
public class Gerenciados implements Serializable{
    
    @Id
    @Column(name = "id_conta")
    private Long idConta;
    
    
    @Column(nullable = false, name = "saldo_positivo")
    private Boolean saldoPositivo;
    
    @ManyToOne
    @JoinColumn(name = "gerente_id", nullable = false)
    private Gerente gerenteId;
    
    public Gerenciados() {
    }

    public Gerenciados(Long idConta, Boolean saldoPositivo, Gerente gerenteId) {
        this.idConta = idConta;
        this.saldoPositivo = saldoPositivo;
        this.gerenteId = gerenteId;
    }



    public Long getIdConta() {
        return idConta;
    }

    public void setIdConta(Long idConta) {
        this.idConta = idConta;
    }

    public Boolean getSaldoPositivo() {
        return saldoPositivo;
    }

    public void setSaldoPositivo(Boolean saldoPositivo) {
        this.saldoPositivo = saldoPositivo;
    }

    public Gerente getGerenteId() {
        return gerenteId;
    }

    public void setGerenteId(Gerente gerenteId) {
        this.gerenteId = gerenteId;
    }


    
}
