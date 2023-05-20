
package com.tads.dac.gerente.model;

import com.tads.dac.gerente.DTOs.GerenteDashboardDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;



@NamedNativeQuery(name = "GerenteRepository.findDashboard", query = """
                                                                    select gr.gerente_id id, ge.nome nome, ge.email email, count(gr.gerente_id) clientes , count(CASE WHEN gr.saldo_positivo THEN 1 END) pos, count(CASE WHEN NOT gr.saldo_positivo THEN 1 END) neg 
                                                                    from tb_gerenciados gr inner join tb_gerente ge on gr.gerente_id = ge.id
                                                                    group by gr.gerente_id, ge.nome, ge.email
                                                                    """, resultSetMapping = "Mapping.GerenteDashboardDTO")

@SqlResultSetMapping(name = "Mapping.GerenteDashboardDTO", classes = @ConstructorResult(targetClass = GerenteDashboardDTO.class ,
        columns = {@ColumnResult(name = "id"), @ColumnResult(name = "nome"), @ColumnResult(name = "email"), @ColumnResult(name = "clientes"), @ColumnResult(name = "pos"), @ColumnResult(name = "neg")}))

@Entity
@Table(name = "tb_gerente")
public class Gerente implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Column(unique = true, length = 11, nullable = false)
    private String cpf;
    
    @Column(length = 100, nullable = false)
    private String nome;
    
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    
    @Column(length = 11, nullable = false)
    private String telefone;
    
    @OneToMany(targetEntity = Gerenciados.class)
    private List<Gerenciados> gerenciados;

    public Gerente() {
    }
    
    public Gerente(String cpf, String nome, String email, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Gerenciados> getGerenciados() {
        return gerenciados;
    }

    public void setGerenciados(List<Gerenciados> gerenciados) {
        this.gerenciados = gerenciados;
    }
    
    
    public void addGerenciado(Gerenciados ger){
        if(gerenciados ==  null){
            gerenciados = new ArrayList<>();
        }
        gerenciados.add(ger);
    }
    
    
}
