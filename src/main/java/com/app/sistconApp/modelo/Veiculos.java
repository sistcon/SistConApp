/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Marcelo Fernandes
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "veiculos")
public class Veiculos implements Serializable, Comparable<Veiculos> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idveiculos")
    private Long idVeiculos;

    @NotBlank
    @Size(min = 1, max = 50)
    private String modelo;

    @Size(max = 100)
    private String marca;

    @Size(max = 15)
    private String placa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idbloco")
    private Bloco bloco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmorador")
    private Morador morador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmoradia")
    private Moradia moradia;

    public Long getIdVeiculos() {
        return idVeiculos;
    }

    public void setIdVeiculos(Long idVeiculos) {
        this.idVeiculos = idVeiculos;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Bloco getBloco() {
        return bloco;
    }

    public void setBloco(Bloco bloco) {
        this.bloco = bloco;
    }

    public Morador getMorador() {
        return morador;
    }

    public void setMorador(Morador morador) {
        this.morador = morador;
    }

    public Moradia getMoradia() {
        return moradia;
    }

    public void setMoradia(Moradia moradia) {
        this.moradia = moradia;
    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.idVeiculos);
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
        final Veiculos other = (Veiculos) obj;
        if (!Objects.equals(this.idVeiculos, other.idVeiculos)) {
            return false;
        }
        return true;
    }
    @Override
    public int compareTo(Veiculos o) {
        return this.placa.compareTo(o.getPlaca());
    }
}
