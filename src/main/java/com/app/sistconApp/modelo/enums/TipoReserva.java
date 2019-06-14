/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.modelo.enums;

/**
 *
 * @author Jadna Cavalcante
 */
public enum TipoReserva {
    CH("Churrasqueira"),
	QU("Quadra"),
	SA("Salao");
        
        private final String nome;

	private TipoReserva(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
