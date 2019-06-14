package com.app.sistconApp.service;

import com.app.sistconApp.modelo.Bloco;
import com.app.sistconApp.modelo.Condominio;
import com.app.sistconApp.repository.BlocoRepository;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;



@Service
@Transactional
public class BlocoServiceImpl implements BlocoService {

	@Autowired
	private BlocoRepository br;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void salvar(Bloco entidade) {
		if (entidade.getIdBloco() == null) {
			padronizar(entidade);
			br.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Bloco ler(Long id) {
		return br.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Bloco> listar() {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return new ArrayList<>();
		}
		return condominio.getBlocos();
	}

	@Override
	public Page<Bloco> listarPagina(Pageable pagina) {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return Page.empty(pagina);
		}
		return br.findAllByCondominioOrderBySiglaAsc(condominio, pagina);
	}

	@Override
	public void editar(Bloco entidade) {
		padronizar(entidade);
		br.save(entidade);
	}

	@Override
	public void excluir(Bloco entidade) {
		br.delete(entidade);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Bloco entidade, BindingResult validacao) {
		// VALIDAÇÕES NA INCLUSÃO
		if (entidade.getIdBloco() == null) {
			// Sigla não pode repetir
			if (br.existsBySiglaAndCondominio(entidade.getSigla(), usuarioService.lerLogado().getCondominio())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		// VALIDAÇÕES NA ALTERAÇÃO
		else {
			// Sigla não pode repetir
			if (br.existsBySiglaAndCondominioAndIdBlocoNot(entidade.getSigla(),
					usuarioService.lerLogado().getCondominio(), entidade.getIdBloco())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		// VALIDAÇÕES EM AMBOS
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Bloco entidade) {
		if (entidade.getCondominio() == null) {
			entidade.setCondominio(usuarioService.lerLogado().getCondominio());
		}
	}

}
