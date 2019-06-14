/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Marcelo Fernandes
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "movimentos")
@Inheritance(strategy = InheritanceType.JOINED)
public class Movimento implements Serializable {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idmovimento")
	private Long idMovimento;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate data;

	@NotNull
	@Min(0)
	private BigDecimal valor;

	@Size(max = 20)
	private String documento;

	@Size(max = 255)
	private String descricao;

	private Boolean reducao;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idconta")
	private Conta conta;

	public Long getIdMovimento() {
		return idMovimento;
	}

	public void setIdMovimento(Long idMovimento) {
		this.idMovimento = idMovimento;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Boolean getReducao() {
		return reducao;
	}

	public void setReducao(Boolean reducao) {
		this.reducao = reducao;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public String detalhe() {
		if (reducao) {
			return "Saída";
		} else {
			return "Entrada";
		}
	}

	@Override
	public String toString() {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String s = data.format(formato) + " - ";
		if (documento != null) {
			s += documento + " - ";
		}
		s += "R$ " + valor;
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMovimento == null) ? 0 : idMovimento.hashCode());
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
		Movimento other = (Movimento) obj;
		if (idMovimento == null) {
			if (other.idMovimento != null) {
				return false;
			}
		} else if (!idMovimento.equals(other.idMovimento)) {
			return false;
		}
		return true;
	}
}
