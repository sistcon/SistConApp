/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Marcelo Fernandes
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "blocos")
public class Bloco implements Serializable, Comparable<Bloco>{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idbloco")
	private Long idBloco;

	@Size(min = 1, max = 5)
	@NotBlank
	private String sigla;

	@Size(max = 30)
	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcondominio")
	private Condominio condominio;

	@OneToMany(mappedBy = "bloco", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "sigla")
	private List<Moradia> moradias = new ArrayList<>();

	public Long getIdBloco() {
		return idBloco;
	}

	public void setIdBloco(Long idBloco) {
		this.idBloco = idBloco;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public List<Moradia> getMoradias() {
		return moradias;
	}

	public void setMoradias(List<Moradia> moradias) {
		this.moradias = moradias;
	}

	@Override
	public String toString() {
		return sigla;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idBloco == null) ? 0 : idBloco.hashCode());
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
		Bloco other = (Bloco) obj;
		if (idBloco == null) {
			if (other.idBloco != null) {
				return false;
			}
		} else if (!idBloco.equals(other.idBloco)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Bloco o) {
		return this.sigla.compareTo(o.getSigla());
	}
    
}
