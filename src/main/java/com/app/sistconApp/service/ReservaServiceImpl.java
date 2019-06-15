/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.service;

import com.app.sistconApp.modelo.Condominio;
import com.app.sistconApp.modelo.Reserva;
import com.app.sistconApp.repository.ReservaRepository;
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
public class ReservaServiceImpl implements ReservaService {
       
        @Autowired
	private ReservaRepository rr;

	@Autowired
	private UsuarioService usuarioService;
        
        @Override
	public void salvar(Reserva entidade) {
		if (entidade.getIdReserva() == null) {
			padronizar(entidade);
			rr.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Reserva ler(Long id) {
		return rr.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Reserva> listar() {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return new ArrayList<>();
		}
		return condominio.getReserva();
	}

	@Override
	public Page<Reserva> listarPagina(Pageable pagina) {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return Page.empty(pagina);
		}
		return rr.findAllByCondominioOrderBySiglaAsc(condominio, pagina);
	}

	@Override
	public void editar(Reserva entidade) {
		padronizar(entidade);
		rr.save(entidade);
	}

	@Override
	public void excluir(Reserva entidade) {
		rr.delete(entidade);
	}
        
        
        
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Reserva entidade, BindingResult validacao) {
		// VALIDAÇÕES NA INCLUSÃO
		if (entidade.getIdReserva() == null) {
			// Sigla não pode repetir
			if (rr.existsBySiglaAndCondominio(entidade.getSigla(), usuarioService.lerLogado().getCondominio())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		// VALIDAÇÕES NA ALTERAÇÃO
		else {
			// Sigla não pode repetir
			if (rr.existsBySiglaAndCondominioAndIdReservaNot(entidade.getSigla(),
					usuarioService.lerLogado().getCondominio(), entidade.getIdReserva())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		// VALIDAÇÕES EM AMBOS
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Reserva entidade) {
		if (entidade.getCondominio() == null) {
			entidade.setCondominio(usuarioService.lerLogado().getCondominio());
		}
	}

}
