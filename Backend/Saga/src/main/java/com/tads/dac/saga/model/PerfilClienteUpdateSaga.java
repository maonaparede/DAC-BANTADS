package com.tads.dac.saga.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "saga")
public class PerfilClienteUpdateSaga implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long sagaId;
    
    @CreationTimestamp
    @Column(name = "data_tempo")
    private Date dataTempo;
    
    private Long id;
    
    private String nome;
    
    private String email;
    
    private String telefone;
    
    private BigDecimal salario;
    
    private String cpf;
    
    private String logradouro;
    
    private String complemento;
    
    private String cidade;
    
    private String estado;
    
    private String tipo;
    
    private String cep;
    
    private Integer numero;
}
