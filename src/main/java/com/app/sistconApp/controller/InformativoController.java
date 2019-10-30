/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.controller;


import com.app.sistconApp.modelo.Informativo;
import com.app.sistconApp.modelo.enums.TipoInformativo;

import com.app.sistconApp.service.InformativoService;

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
 * @author Jadna Cavalcante
 */
@Controller
@RequestMapping({"sindico/informativos","condomino/informativos"})
public class InformativoController {
    
    @Autowired
	private InformativoService informativoService;
    
    @ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "condominio", "informativos" };
	}
        
        @ModelAttribute("tipos")
        public TipoInformativo[] tipos() {
        return TipoInformativo.values();
    }

        
        @GetMapping({ "", "/", "/lista" })
	public ModelAndView getInformativos(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("informativos",
				informativoService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "informativoLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getInformativoCadastro(@ModelAttribute("informativo") Informativo informativo) {
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "informativoCadastro");
	}

	@GetMapping("/{idInformativo}/cadastro")
	public ModelAndView getInformativoEditar(@PathVariable("idInformativo") Long idInformativo, ModelMap model) {
		model.addAttribute("informativo", informativoService.ler(idInformativo));
		model.addAttribute("conteudo", "informativoCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postInformativoCadastro(@Valid @ModelAttribute("informativo") Informativo informativo, BindingResult validacao) {
		informativoService.validar(informativo, validacao);
		if (validacao.hasErrors()) {
			informativo.setIdInformativo(null);
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "informativoCadastro");
		}
		informativoService.salvar(informativo);
		return new ModelAndView("redirect:/sindico/informativos");
	}

	@PutMapping("/cadastro")
	public ModelAndView putInformativoCadastro(@Valid @ModelAttribute("informativo") Informativo informativo, BindingResult validacao) {
		informativoService.validar(informativo, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "informativoCadastro");
		}
		informativoService.editar(informativo);
		return new ModelAndView("redirect:/sindico/informativos");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteInformativoCadastro(@RequestParam("idObj") Long idObj) {
		informativoService.excluir(informativoService.ler(idObj));
		return new ModelAndView("redirect:/sindico/informativos");
	}

    
}
