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
	private OcorrenciaRepository or;

	@Autowired
	private UsuarioService usuarioService;
        
        @Override
	public void salvar(Ocorrencia entidade) {
		if (entidade.getIdOcorrencia() == null) {
			padronizar(entidade);
			or.save(entidade);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Ocorrencia ler(Long id) {
		return or.findById(id).get();
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
		return or.findAllByCondominioOrderBySiglaAsc(condominio, pagina);
	}

	@Override
	public void editar(Ocorrencia entidade) {
		padronizar(entidade);
		or.save(entidade);
	}

	@Override
	public void excluir(Ocorrencia entidade) {
		or.delete(entidade);
	}
        
        
        
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validar(Ocorrencia entidade, BindingResult validacao) {
		
		if (entidade.getIdOcorrencia() == null) {
			
			if (or.existsBySiglaAndCondominio(entidade.getSigla(), usuarioService.lerLogado().getCondominio())) {
				validacao.rejectValue("sigla", "Unique");
			}
		}
		
		else {
			
			if (or.existsBySiglaAndCondominioAndIdOcorrenciaNot(entidade.getSigla(),
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
