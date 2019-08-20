/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.service;

import com.app.sistconApp.modelo.Condominio;
import com.app.sistconApp.modelo.Evento;
import com.app.sistconApp.repository.EventoRepository;
import java.util.ArrayList;
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
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository er;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void salvar(Evento entidade) {
        if (entidade.getIdEvento() == null) {
            padronizar(entidade);
            er.save(entidade);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Evento ler(Long id) {
        return er.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Evento> listar() {
            Condominio condominio = usuarioService.lerLogado().getCondominio();
        if (condominio == null) {
            return new ArrayList<>();
        }
        return condominio.getEvento();
    }

    @Override
    public Page<Evento> listarPagina(Pageable pagina) {
        Condominio condominio = usuarioService.lerLogado().getCondominio();
        if (condominio == null) {
            return Page.empty(pagina);
        }
        return er.findAllByCondominioOrderBySiglaAsc(condominio, pagina);
    }

    @Override
    public void editar(Evento entidade) {
        padronizar(entidade);
        er.save(entidade);
    }

    @Override
    public void excluir(Evento entidade) {
        er.delete(entidade);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void validar(Evento entidade, BindingResult validacao) {
        // VALIDAÇÕES NA INCLUSÃO
        if (entidade.getIdEvento() == null) {
            // Sigla não pode repetir
            if (er.existsBySiglaAndCondominio(entidade.getSigla(), usuarioService.lerLogado().getCondominio())) {
                validacao.rejectValue("sigla", "Unique");
            }
        } // VALIDAÇÕES NA ALTERAÇÃO
        else {
            // Sigla não pode repetir
            if (er.existsBySiglaAndCondominioAndIdEventoNot(entidade.getSigla(),
                    usuarioService.lerLogado().getCondominio(), entidade.getIdEvento())) {
                validacao.rejectValue("sigla", "Unique");
            }
        }
        // VALIDAÇÕES EM AMBOS
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void padronizar(Evento entidade) {
        if (entidade.getCondominio() == null) {
            entidade.setCondominio(usuarioService.lerLogado().getCondominio());
        }
    }

}
