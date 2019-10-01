/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.service;

import com.app.sistconApp.modelo.Condominio;
import com.app.sistconApp.modelo.Informativo;
import com.app.sistconApp.repository.InformativoRepository;
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
public class InformativoServiceImpl implements InformativoService {
    
    @Autowired
	private InformativoRepository ir;

	@Autowired
	private UsuarioService usuarioService;
        
        @Override
	public void salvar(Informativo entidade) {
		if (entidade.getIdInformativo() == null) {
			padronizar(entidade);
			ir.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Informativo ler(Long id) {
		return ir.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Informativo> listar() {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return new ArrayList<>();
		}
		return condominio.getInformativo();
	}

	@Override
	public Page<Informativo> listarPagina(Pageable pagina) {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return Page.empty(pagina);
		}
		return ir.findAllByCondominioOrderBySiglaAsc(condominio, pagina);
	}

	@Override
	public void editar(Informativo entidade) {
		padronizar(entidade);
		ir.save(entidade);
	}

	@Override
	public void excluir(Informativo entidade) {
		ir.delete(entidade);
	}
        
        
        
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Informativo entidade, BindingResult validacao) {
		// VALIDAÇÕES NA INCLUSÃO
		if (entidade.getIdInformativo() == null) {
			// Sigla não pode repetir
			if (ir.existsBySiglaAndCondominio(entidade.getSigla(), usuarioService.lerLogado().getCondominio())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		// VALIDAÇÕES NA ALTERAÇÃO
		else {
			// Sigla não pode repetir
			if (ir.existsBySiglaAndCondominioAndIdInformativoNot(entidade.getSigla(),
					usuarioService.lerLogado().getCondominio(), entidade.getIdInformativo())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		// VALIDAÇÕES EM AMBOS
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Informativo entidade) {
		if (entidade.getCondominio() == null) {
			entidade.setCondominio(usuarioService.lerLogado().getCondominio());
		}
	}

}
