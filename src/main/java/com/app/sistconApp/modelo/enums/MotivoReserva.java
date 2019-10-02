/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.modelo.enums;

/**
 *
 * @author Marcelo
 */
public enum MotivoReserva {
    // @formatter:off
	A("Aniversário"),
	C("Confraternização"),
	O("Outros");
	// @formatter:on

	private final String nome;

	private MotivoReserva(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
