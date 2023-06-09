
package com.tads.dac.saga.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AutocadastroCliente {
   
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long sagaId;
    
    @CreationTimestamp
    @Column(name = "data_saga")
    private Date dataTempo;
    
    private Long id;
    private String nome;  
    private String email;   
    private String telefone;
    private BigDecimal salario;
    private String cpf;
    private String logradouro;
    
    @Column(name = "end_complemento")
    private String complemento;
    
    @Column(name = "end_cidade")
    private String cidade;
    
    @Column(name = "end_estado")
    private String estado;
    
    @Column(name = "end_tipo")
    private String tipo;
    
    @Column(name = "end_cep")
    private String cep;
    
    @Column(name = "end_numero")
    private Integer numero;
    
}
