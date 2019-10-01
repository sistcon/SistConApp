/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.modelo;

import com.app.sistconApp.modelo.enums.TipoInformativo;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Jadna Cavalcante
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "informativos")
public class Informativo implements Serializable, Comparable<Informativo> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinformativo")
    private Long idInformativo;

    @Size(max = 255)
    private String assunto;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dia;

    @Size(min = 2, max = 5)
    @NotBlank
    private String sigla;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoInformativo tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcondominio")
    private Condominio condominio;

    public Long getIdInformativo() {
        return idInformativo;
    }

    public void setIdInformativo(Long idInformativo) {
        this.idInformativo = idInformativo;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public TipoInformativo getTipo() {
        return tipo;
    }

    public void setTipo(TipoInformativo tipo) {
        this.tipo = tipo;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.idInformativo);
        return hash;
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
        final Informativo other = (Informativo) obj;
        if (!Objects.equals(this.idInformativo, other.idInformativo)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(Informativo o) {
        return this.sigla.compareTo(o.getSigla());
    }


    
}
