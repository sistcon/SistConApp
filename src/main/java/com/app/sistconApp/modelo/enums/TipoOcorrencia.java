/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.modelo.enums;

/**
 *
 * @author Marcelo Fernandes
 */
public enum TipoOcorrencia {
    // @formatter:off
    A("Advertência"),
    M("Multa");

    private final String nome;

    private TipoOcorrencia(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
