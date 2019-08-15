
package com.app.sistconApp.modelo.enums;

/**
 *
 * @author Marcelo Fernandes
 */
public enum TipoMorador {
        F("Pessoa Física"),
	J("Pessoa Jurídica");
	// @formatter:on

	private final String nome;

	private TipoMorador(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
