/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.controller;


import com.app.sistconApp.modelo.Moradia;
import com.app.sistconApp.modelo.Ocorrencia;
import com.app.sistconApp.modelo.enums.TipoOcorrencia;
import com.app.sistconApp.service.MoradiaService;

import com.app.sistconApp.service.OcorrenciaService;
import java.util.List;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Marcelo Fernandes
 */
@Controller
@RequestMapping("sindico/ocorrencias")
public class OcorrenciaController {
    
    @Autowired
	private OcorrenciaService ocorrenciaService;
    
        @Autowired
        MoradiaService moradiaService;
    
        @ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "condominio", "ocorrencias" };
	}
        
        @ModelAttribute("tipos")
        public TipoOcorrencia[] tipos() {
        return TipoOcorrencia.values();
    }
        @ModelAttribute("moradias")
        public List<Moradia> moradias() {
        return moradiaService.listar();
    }

        
        @GetMapping({ "", "/", "/lista" })
	public ModelAndView getOcorrencias(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("ocorrencias",
				ocorrenciaService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "ocorrenciaLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getOcorrenciaCadastro(@ModelAttribute("ocorrencia") Ocorrencia ocorrencia) {
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "ocorrenciaCadastro");
	}

	@GetMapping("/{idOcorrencia}/cadastro")
	public ModelAndView getOcorrenciaEditar(@PathVariable("idOcorrencia") Long idOcorrencia, ModelMap model) {
		model.addAttribute("ocorrencia", ocorrenciaService.ler(idOcorrencia));
		model.addAttribute("conteudo", "ocorrenciaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postOcorrenciaCadastro(@Valid @ModelAttribute("ocorrencia") Ocorrencia ocorrencia, BindingResult validacao) {
		ocorrenciaService.validar(ocorrencia, validacao);
		if (validacao.hasErrors()) {
			ocorrencia.setIdOcorrencia(null);
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "ocorrenciaCadastro");
		}
		ocorrenciaService.salvar(ocorrencia);
		return new ModelAndView("redirect:/sindico/ocorrencias");
	}

	@PutMapping("/cadastro")
	public ModelAndView putOcorrenciaCadastro(@Valid @ModelAttribute("ocorrencia") Ocorrencia ocorrencia, BindingResult validacao) {
		ocorrenciaService.validar(ocorrencia, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "ocorrenciaCadastro");
		}
		ocorrenciaService.editar(ocorrencia);
		return new ModelAndView("redirect:/sindico/ocorrencias");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteOcorrenciaCadastro(@RequestParam("idObj") Long idObj) {
		ocorrenciaService.excluir(ocorrenciaService.ler(idObj));
		return new ModelAndView("redirect:/sindico/ocorrencias");
	}

    
}
