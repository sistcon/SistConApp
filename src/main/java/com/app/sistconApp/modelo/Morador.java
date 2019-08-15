/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.modelo;

import com.app.sistconApp.modelo.enums.Estado;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author Marcelo farnandes
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "morador")
public class Morador implements Serializable, Comparable<Morador>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmorador")
    private Long idMorador;
    
    @NotBlank
    @Size(min = 1, max = 50)
    private String nome;
    @CPF
    private String cpf;
    @Size(min = 2, max = 5)
    @NotBlank
    private String sigla;

    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 15)
    private String telefone;

    @Size(max = 15)
    private String celular;

    @Size(max = 100)
    private String endereco;

    @Size(max = 6)
    @Column(name = "numeroend")
    private String numeroEnd;

    @Size(max = 30)
    @Column(name = "complementoend")
    private String complementoEnd;

    @Size(max = 30)
    private String bairro;

    @Size(max = 30)
    private String cidade;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Size(max = 8)
    private String cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcondominio")
    private Condominio condominio;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idbloco")
    private Bloco bloco;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmoradia")
    private Moradia moradia;
    
    public Long getIdMorador() {
        return idMorador;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public void setIdMorador(Long idMorador) {
        this.idMorador = idMorador;
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumeroEnd() {
        return numeroEnd;
    }

    public void setNumeroEnd(String numeroEnd) {
        this.numeroEnd = numeroEnd;
    }

    public String getComplementoEnd() {
        return complementoEnd;
    }

    public void setComplementoEnd(String complementoEnd) {
        this.complementoEnd = complementoEnd;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Bloco getBloco() {
        return bloco;
    }

    public void setBloco(Bloco bloco) {
        this.bloco = bloco;
    }

    public Moradia getMoradia() {
        return moradia;
    }

    public void setMoradia(Moradia moradia) {
        this.moradia = moradia;
    }

    public String cpfCnpj() {
        return null;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idMorador == null) ? 0 : idMorador.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Morador other = (Morador) obj;
        if (idMorador == null) {
            if (other.idMorador != null) {
                return false;
            }
        } else if (!idMorador.equals(other.idMorador)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Morador o) {
        return this.toString().compareTo(o.toString());
    }

}
