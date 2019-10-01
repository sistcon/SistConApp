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
public enum TipoVeiculo {
    
    CA("Carro"),
    MT("Moto");
        
        
        private final String nome;

	private TipoVeiculo(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
