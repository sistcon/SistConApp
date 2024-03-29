package com.app.sistconApp.service;

import com.app.sistconApp.modelo.Condominio;
import com.app.sistconApp.modelo.Ocorrencia;
import com.app.sistconApp.repository.OcorrenciaRepository;
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
public class OcorrenciaServiceImpl implements OcorrenciaService {
    @Autowired
	private OcorrenciaRepository oc;

	@Autowired
	private UsuarioService usuarioService;
        
        @Override
	public void salvar(Ocorrencia entidade) {
		if (entidade.getIdOcorrencia() == null) {
			padronizar(entidade);
			oc.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Ocorrencia ler(Long id) {
		return oc.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Ocorrencia> listar() {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return new ArrayList<>();
		}
		return condominio.getOcorrencia();
	}

	@Override
	public Page<Ocorrencia> listarPagina(Pageable pagina) {
		Condominio condominio = usuarioService.lerLogado().getCondominio();
		if (condominio == null) {
			return Page.empty(pagina);
		}
		return oc.findAllByCondominioOrderBySiglaAsc(condominio, pagina);
	}

	@Override
	public void editar(Ocorrencia entidade) {
		padronizar(entidade);
		oc.save(entidade);
	}

	@Override
	public void excluir(Ocorrencia entidade) {
		oc.delete(entidade);
	}
        
        
        
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Ocorrencia entidade, BindingResult validacao) {
		// VALIDAÇÕES NA INCLUSÃO
		if (entidade.getIdOcorrencia() == null) {
			// Sigla não pode repetir
			if (oc.existsBySiglaAndCondominio(entidade.getSigla(), usuarioService.lerLogado().getCondominio())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		// VALIDAÇÕES NA ALTERAÇÃO
		else {
			// Sigla não pode repetir
			if (oc.existsBySiglaAndCondominioAndIdOcorrenciaNot(entidade.getSigla(),
					usuarioService.lerLogado().getCondominio(), entidade.getIdOcorrencia())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		// VALIDAÇÕES EM AMBOS
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void padronizar(Ocorrencia entidade) {
		if (entidade.getCondominio() == null) {
			entidade.setCondominio(usuarioService.lerLogado().getCondominio());
		}
	}

}
