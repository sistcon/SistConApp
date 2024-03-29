/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.modelo;

import com.app.sistconApp.modelo.enums.MotivoReserva;
import com.app.sistconApp.modelo.enums.MotivoReservaIndeferida;
import com.app.sistconApp.modelo.enums.SituacaoReserva;
import com.app.sistconApp.modelo.enums.TipoReserva;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
import javax.validation.constraints.Min;
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
@Table(name = "reservas")
public class Reserva implements Serializable, Comparable<Reserva> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreserva")
    private Long idReserva;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dia;

    @Size(min = 2, max = 5)
    @NotBlank
    private String sigla;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoReserva tipo;

    @Size(min = 2, max = 100)
    @NotBlank
    private String convidado;

    @NotNull
    @Min(0)
    private BigDecimal valor;

    @Size(max = 255)
    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacoreserva")
    private SituacaoReserva situacaoReserva;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivoreserva")
    private MotivoReserva motivoReserva;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivoreservaindeferida")
    private MotivoReservaIndeferida motivoReservaIndeferida;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "dataconfirmacao")
    private LocalDate dataConfirmacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcondominio")
    private Condominio condominio;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmoradia")
    private Moradia moradia;

    public Moradia getMoradia() {
        return moradia;
    }

    public void setMoradia(Moradia moradia) {
        this.moradia = moradia;
    }

    public MotivoReservaIndeferida getMotivoReservaIndeferida() {
        return motivoReservaIndeferida;
    }

    public void setMotivoReservaIndeferida(MotivoReservaIndeferida motivoReservaIndeferida) {
        this.motivoReservaIndeferida = motivoReservaIndeferida;
    }

    public MotivoReserva getMotivoReserva() {
        return motivoReserva;
    }

    public void setMotivoReserva(MotivoReserva motivoReserva) {
        this.motivoReserva = motivoReserva;
    }

    public SituacaoReserva getSituacaoReserva() {
        return situacaoReserva;
    }

    public void setSituacaoReserva(SituacaoReserva situacaoReserva) {
        this.situacaoReserva = situacaoReserva;
    }

    public LocalDate getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(LocalDate dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
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

    public TipoReserva getTipo() {
        return tipo;
    }

    public void setTipo(TipoReserva tipo) {
        this.tipo = tipo;
    }

    public String getConvidado() {
        return convidado;
    }

    public void setConvidado(String convidado) {
        this.convidado = convidado;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dia.format(formato);
    }

    /*@Override
	public String toString() {
		return sigla;
	}*/
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.idReserva);
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
        final Reserva other = (Reserva) obj;
        if (!Objects.equals(this.idReserva, other.idReserva)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Reserva o) {
        return this.dia.compareTo(o.getDia());
    }
}
