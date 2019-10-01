/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.service;

import com.app.sistconApp.modelo.Condominio;
import com.app.sistconApp.modelo.Visitante;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import com.app.sistconApp.repository.VisitanteRepository;

/**
 *
 * @author Jadna Cavalcante
 */
@Service
@Transactional
public class VisitanteServiceImpl implements VisitanteService {
    
        @Autowired
	private VisitanteRepository vs;
        
        @Autowired
	private UsuarioService usuarioService;
        
        @Override
	public void salvar(Visitante entidade) {
		if (entidade.getIdVisitante() == null) {
			padronizar(entidade);
			vs.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Visitante ler(Long id) {
		return vs.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Visitante> listar() {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return new ArrayList<>();
		}
		return condominio.getConvidado();
	}

	@Override
	public Page<Visitante> listarPagina(Pageable pagina) {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return Page.empty(pagina);
		}
		return vs.findAllByCondominioOrderBySiglaAsc(condominio, pagina);
	}

	@Override
	public void editar(Visitante entidade) {
		padronizar(entidade);
		vs.save(entidade);
	}

	@Override
	public void excluir(Visitante entidade) {
		vs.delete(entidade);
	}
        
        
        
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Visitante entidade, BindingResult validacao) {
		// VALIDAÇÕES NA INCLUSÃO
		if (entidade.getIdVisitante() == null) {
			// Sigla não pode repetir
			if (vs.existsBySiglaAndCondominio(entidade.getSigla(), usuarioService.lerLogado().getCondominio())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		// VALIDAÇÕES NA ALTERAÇÃO
		else {
			// Sigla não pode repetir
			if (vs.existsBySiglaAndCondominioAndIdVisitanteNot(entidade.getSigla(),
					usuarioService.lerLogado().getCondominio(), entidade.getIdVisitante())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		// VALIDAÇÕES EM AMBOS
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Visitante entidade) {
		if (entidade.getCondominio() == null) {
			entidade.setCondominio(usuarioService.lerLogado().getCondominio());
		}
	}

}


