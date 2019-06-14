package com.app.sistconApp.modelo.enums;

public enum TipoContaBancaria {
	// @formatter:off
	C("Conta Corrente"),
	P("Poupança");
	// @formatter:on

	private final String nome;

	private TipoContaBancaria(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
