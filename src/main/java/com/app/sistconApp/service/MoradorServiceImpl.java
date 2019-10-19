/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.service;

import com.app.sistconApp.modelo.Condominio;
import com.app.sistconApp.modelo.Morador;
import com.app.sistconApp.modelo.Relacao;
import com.app.sistconApp.repository.MoradorRepository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

/**
 *
 * @author Marcelo Fernandes
 */
@Service
@Transactional
public class MoradorServiceImpl implements MoradorService {

    @Autowired
    private MoradorRepository moradorRep;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void salvar(Morador entidade) {
        if (entidade.getIdMorador() == null) {
            padronizar(entidade);
            moradorRep.save(entidade);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Morador ler(Long id) {
        return moradorRep.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Morador> listar() {

        Condominio condominio = usuarioService.lerLogado().getCondominio();
        if (condominio == null) {
            return new ArrayList<>();
        }
        return condominio.getMoradores();
    }

    @Override
    public Page<Morador> listarPagina(Pageable pagina) {
        Condominio condominio = usuarioService.lerLogado().getCondominio();
        if (condominio == null) {
            return Page.empty(pagina);
        }
        return moradorRep.findAllByCondominioOrderByNome(condominio, pagina);
    }

    @Override
    public void editar(Morador entidade) {
        padronizar(entidade);
        moradorRep.save(entidade);
    }

    @Override
    public void excluir(Morador entidade) {
        moradorRep.delete(entidade);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void validar(Morador entidade, BindingResult validacao) {
        // VALIDAÇÕES NA INCLUSÃO
        if (entidade.getIdMorador() == null) {
            // Sigla não pode repetir
            if (moradorRep.existsBySiglaAndCondominio(entidade.getSigla(), usuarioService.lerLogado().getCondominio())) {
                validacao.rejectValue("sigla", "Unique");
            }
        } // VALIDAÇÕES NA ALTERAÇÃO
        else {
            // Sigla não pode repetir
            if (moradorRep.existsBySiglaAndCondominioAndIdMoradorNot(entidade.getSigla(),
                    usuarioService.lerLogado().getCondominio(), entidade.getIdMorador())) {
                validacao.rejectValue("sigla", "Unique");
            }
        }
        // VALIDAÇÕES EM AMBOS
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void padronizar(Morador entidade) {
        if (entidade.getCondominio() == null) {
            entidade.setCondominio(usuarioService.lerLogado().getCondominio());
        }
    }

}
