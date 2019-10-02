/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.service;

import com.app.sistconApp.modelo.Condominio;
import com.app.sistconApp.modelo.Veiculo;
import com.app.sistconApp.repository.VeiculoRepository;
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
 * @author Jadna Cavalcante
 */
@Service
@Transactional
public class VeiculoServiceImpl implements VeiculoService {
    
    @Autowired
    private VeiculoRepository vr;

    @Autowired
    private UsuarioService usuarioService;

   @Override
    public void salvar(Veiculo entidade) {
        if (entidade.getIdVeiculo() == null) {
            padronizar(entidade);
            vr.save(entidade);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Veiculo ler(Long id) {
        return vr.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Veiculo> listar() {
            Condominio condominio = usuarioService.lerLogado().getCondominio();
        if (condominio == null) {
            return new ArrayList<>();
        }
        return condominio.getVeiculo();
    }

    @Override
    public Page<Veiculo> listarPagina(Pageable pagina) {
        Condominio condominio = usuarioService.lerLogado().getCondominio();
        if (condominio == null) {
            return Page.empty(pagina);
        }
        return vr.findAllByCondominioOrderBySiglaAsc(condominio, pagina);
    }

    @Override
    public void editar(Veiculo entidade) {
        padronizar(entidade);
        vr.save(entidade);
    }

    @Override
    public void excluir(Veiculo entidade) {
        vr.delete(entidade);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void validar(Veiculo entidade, BindingResult validacao) {
        // VALIDAÇÕES NA INCLUSÃO
        if (entidade.getIdVeiculo() == null) {
            // Sigla não pode repetir
            if (vr.existsBySiglaAndCondominio(entidade.getSigla(), usuarioService.lerLogado().getCondominio())) {
                validacao.rejectValue("sigla", "Unique");
            }
        } // VALIDAÇÕES NA ALTERAÇÃO
        else {
            // Sigla não pode repetir
            if (vr.existsBySiglaAndCondominioAndIdVeiculoNot(entidade.getSigla(),
                    usuarioService.lerLogado().getCondominio(), entidade.getIdVeiculo())) {
                validacao.rejectValue("sigla", "Unique");
            }
        }
        // VALIDAÇÕES EM AMBOS
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void padronizar(Veiculo entidade) {
        if (entidade.getCondominio() == null) {
            entidade.setCondominio(usuarioService.lerLogado().getCondominio());
        }
    }

}