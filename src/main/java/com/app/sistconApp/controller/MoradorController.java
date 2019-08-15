/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.controller;

import com.app.sistconApp.modelo.Moradia;
import com.app.sistconApp.modelo.Morador;
import com.app.sistconApp.modelo.PessoaFisica;
import com.app.sistconApp.modelo.enums.Estado;
import com.app.sistconApp.modelo.enums.Genero;
import com.app.sistconApp.modelo.enums.TipoMorador;
import com.app.sistconApp.modelo.enums.TipoPessoa;
import com.app.sistconApp.modelo.enums.TipoRelacao;
import com.app.sistconApp.service.MoradiaService;
import com.app.sistconApp.service.MoradorService;
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
@RequestMapping("sindico/moradores")
public class MoradorController {

    @Autowired
    private MoradorService moradorService;

    @Autowired
    MoradiaService moradiaService;

    @ModelAttribute("ativo")
    public String[] ativo() {
        return new String[]{"condominio", "moradores"};
    }

    @ModelAttribute("generos")
    public Genero[] generos() {
        return Genero.values();
    }

    @ModelAttribute("moradias")
    public List<Moradia> moradias() {
        return moradiaService.listar();
    }

    @ModelAttribute("tiposRelacao")
    public TipoRelacao[] tiposRelacao() {
        return TipoRelacao.values();
    }

    @ModelAttribute("tipos")
    public TipoMorador[] tiposMoradores() {
        return TipoMorador.values();
    }

    @ModelAttribute("estados")
    public Estado[] estados() {
        return Estado.values();
    }

    @GetMapping({"", "/", "/lista"})
    public ModelAndView getMoradores(@RequestParam("pagina") Optional<Integer> pagina,
            @RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
        model.addAttribute("moradores",
                moradorService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
        model.addAttribute("conteudo", "moradorLista");
        return new ModelAndView("fragmentos/layoutSindico", model);
    }

    @GetMapping("/cadastro")
    public ModelAndView getMoradorCadastro(@ModelAttribute("morador") Morador morador, ModelMap model) {
        model.addAttribute("tipo", "");
        model.addAttribute("conteudo", "moradorCadastro");
        return new ModelAndView("fragmentos/layoutSindico", model);
    }

    @GetMapping("/{idMorador}/cadastro")
    public ModelAndView getMoradorEditar(@PathVariable("idMorador") Long idMorador, ModelMap model) {
        model.addAttribute("morador", moradorService.ler(idMorador));
        model.addAttribute("conteudo", "moradorCadastro");
        return new ModelAndView("fragmentos/layoutSindico", model);
    }
    @PostMapping("/cadastro")
	public ModelAndView postMoradorCadastro(@Valid @ModelAttribute("morador") Morador morador, BindingResult validacao) {
		moradorService.validar(morador, validacao);
		if (validacao.hasErrors()) {
			morador.setIdMorador(null);
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "moradorCadastro");
		}
		moradorService.salvar(morador);
		return new ModelAndView("redirect:/sindico/moradores");
	}
        @PutMapping("/cadastro")
	public ModelAndView putMoradorCadastro(@Valid @ModelAttribute("morador") Morador morador, BindingResult validacao) {
		moradorService.validar(morador, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "moradorCadastro");
		}
		moradorService.editar(morador);
		return new ModelAndView("redirect:/sindico/moradores");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteMoradorCadastro(@RequestParam("idObj") Long idObj) {
		moradorService.excluir(moradorService.ler(idObj));
		return new ModelAndView("redirect:/sindico/moradores");
	}
}
