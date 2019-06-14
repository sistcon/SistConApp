/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.modelo;

import com.app.sistconApp.modelo.enums.TipoMoradia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Marcelo Fernandes
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "moradias")
public class Moradia implements Serializable, Comparable<Moradia> {
   @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idmoradia")
	private Long idMoradia;

	@Size(min = 1, max = 10)
	@NotBlank
	private String sigla;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoMoradia tipo;

	private Float area;

	@Max(100)
	@Min(0)
	@Column(name = "fracaoideal")
	private Float fracaoIdeal;

	@Size(max = 30)
	private String matricula;

	private Integer vagas;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idbloco")
	private Bloco bloco;

	@OneToMany(mappedBy = "moradia", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy(value = "dataEntrada")
	@Valid
	private List<Relacao> relacoes = new ArrayList<>();

	@OneToMany(mappedBy = "moradia", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "numero, parcela")
	private List<Cobranca> cobrancas = new ArrayList<>();

	public Long getIdMoradia() {
		return idMoradia;
	}

	public void setIdMoradia(Long idMoradia) {
		this.idMoradia = idMoradia;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public TipoMoradia getTipo() {
		return tipo;
	}

	public void setTipo(TipoMoradia tipo) {
		this.tipo = tipo;
	}

	public Float getArea() {
		return area;
	}

	public void setArea(Float area) {
		this.area = area;
	}

	public Float getFracaoIdeal() {
		return fracaoIdeal;
	}

	public void setFracaoIdeal(Float fracaoIdeal) {
		this.fracaoIdeal = fracaoIdeal;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Integer getVagas() {
		return vagas;
	}

	public void setVagas(Integer vagas) {
		this.vagas = vagas;
	}

	public Bloco getBloco() {
		return bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}

	public List<Relacao> getRelacoes() {
		return relacoes;
	}

	public void setRelacoes(List<Relacao> relacoes) {
		this.relacoes = relacoes;
	}

	public List<Cobranca> getCobrancas() {
		return cobrancas;
	}

	public void setCobrancas(List<Cobranca> cobrancas) {
		this.cobrancas = cobrancas;
	}

	@Override
	public String toString() {
		return bloco.toString() + " - " + sigla;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMoradia == null) ? 0 : idMoradia.hashCode());
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
		Moradia other = (Moradia) obj;
		if (idMoradia == null) {
			if (other.idMoradia != null) {
				return false;
			}
		} else if (!idMoradia.equals(other.idMoradia)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Moradia arg0) {
		return this.toString().compareTo(arg0.toString());
	} 
}
