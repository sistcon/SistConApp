package com.app.sistconApp.controller;

import com.app.sistconApp.modelo.Conta;
import com.app.sistconApp.modelo.ContaBancaria;
import com.app.sistconApp.modelo.enums.TipoConta;
import com.app.sistconApp.modelo.enums.TipoContaBancaria;
import com.app.sistconApp.service.ContaService;
import java.math.BigDecimal;
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



@Controller
@RequestMapping("sindico/contas")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "financeiro", "contas" };
	}

	@ModelAttribute("tipos")
	public TipoConta[] tiposConta() {
		return TipoConta.values();
	}

	@ModelAttribute("tiposBc")
	public TipoContaBancaria[] tiposContaBancaria() {
		return TipoContaBancaria.values();
	}

	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getContas(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("contas",
				contaService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "contaLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getContaCadastro(@ModelAttribute("conta") Conta conta, ModelMap model) {
		conta.setSaldoInicial(BigDecimal.ZERO);
		model.addAttribute("tipo", "");
		model.addAttribute("conteudo", "contaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/{idConta}/cadastro")
	public ModelAndView getContaEditar(@PathVariable("idConta") Long idConta, ModelMap model) {
		Conta conta = contaService.ler(idConta);
		if (conta instanceof ContaBancaria) {
			model.addAttribute("conta", conta);
			model.addAttribute("tipo", TipoConta.BC);
		} else {
			model.addAttribute("conta", conta);
			model.addAttribute("tipo", TipoConta.CX);
		}
		model.addAttribute("conteudo", "contaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping(value = "/cadastro", params = { "CX" })
	public ModelAndView postContaCadastro(@Valid @ModelAttribute("conta") Conta conta, BindingResult validacao,
			ModelMap model) {
		contaService.validar(conta, validacao);
		if (validacao.hasErrors()) {
			conta.setIdConta(null);
			model.addAttribute("tipo", TipoConta.CX);
			model.addAttribute("conteudo", "contaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		contaService.salvar(conta);
		return new ModelAndView("redirect:/sindico/contas");
	}

	@PostMapping(value = "/cadastro", params = { "BC" })
	public ModelAndView postContaBancariaCadastro(@Valid @ModelAttribute("conta") ContaBancaria conta,
			BindingResult validacao, ModelMap model) {
		contaService.validar(conta, validacao);
		if (validacao.hasErrors()) {
			conta.setIdConta(null);
			model.addAttribute("tipo", TipoConta.BC);
			model.addAttribute("conteudo", "contaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		contaService.salvar(conta);
		return new ModelAndView("redirect:/sindico/contas");
	}

	// @PutMapping({ "/cadastro/CX", "/cadastro" })
	@PutMapping(value = "/cadastro", params = { "CX" })
	public ModelAndView putContaCadastro(@Valid @ModelAttribute("conta") Conta conta, BindingResult validacao,
			ModelMap model) {
		contaService.validar(conta, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoConta.CX);
			model.addAttribute("conteudo", "contaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		contaService.editar(conta);
		return new ModelAndView("redirect:/sindico/contas");
	}

	// @PutMapping("/cadastro/BC")
	@PutMapping(value = "/cadastro", params = { "BC" })
	public ModelAndView putContaBancariaCadastro(@Valid @ModelAttribute("conta") ContaBancaria conta,
			BindingResult validacao, ModelMap model) {
		contaService.validar(conta, validacao);
		if (validacao.hasErrors()) {
			model.addAttribute("tipo", TipoConta.BC);
			model.addAttribute("conteudo", "contaCadastro");
			return new ModelAndView("fragmentos/layoutSindico", model);
		}
		contaService.editar(conta);
		return new ModelAndView("redirect:/sindico/contas");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteContaCadastro(@RequestParam("idObj") Long idObj) {
		contaService.excluir(contaService.ler(idObj));
		return new ModelAndView("redirect:/sindico/contas");
	}

}
