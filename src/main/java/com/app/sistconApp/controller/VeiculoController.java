/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.controller;

import com.app.sistconApp.modelo.Moradia;

import com.app.sistconApp.modelo.Veiculo;
import com.app.sistconApp.modelo.enums.TipoVeiculo;

import com.app.sistconApp.service.MoradiaService;

import com.app.sistconApp.service.VeiculoService;
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
 * @author Jadna Cavalcante
 */
@Controller
@RequestMapping("sindico/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    MoradiaService moradiaService;

    @ModelAttribute("ativo")
    public String[] ativo() {
        return new String[]{"condominio", "veiculos"};
    }

    @ModelAttribute("tipos")
    public TipoVeiculo[] tipos() {
        return TipoVeiculo.values();
    }

    @ModelAttribute("moradias")
    public List<Moradia> moradias() {
        return moradiaService.listar();
    }

    @GetMapping({"", "/", "/lista"})
    public ModelAndView getVeiculos(@RequestParam("pagina") Optional<Integer> pagina,
            @RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
        model.addAttribute("veiculos",
                veiculoService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
        model.addAttribute("conteudo", "veiculoLista");
        return new ModelAndView("fragmentos/layoutSindico", model);
    }

    @GetMapping("/cadastro")
    public ModelAndView getVeiculoCadastro(@ModelAttribute("veiculo") Veiculo veiculo) {
        return new ModelAndView("fragmentos/layoutSindico", "conteudo", "veiculoCadastro");
    }

    @GetMapping("/{idVeiculo}/cadastro")
    public ModelAndView getVeiculoEditar(@PathVariable("idVeiculo") Long idVeiculo, ModelMap model) {
        model.addAttribute("veiculo", veiculoService.ler(idVeiculo));
        model.addAttribute("conteudo", "veiculoCadastro");
        return new ModelAndView("fragmentos/layoutSindico", model);
    }

    @PostMapping("/cadastro")
    public ModelAndView postVeiculoCadastro(@Valid @ModelAttribute("veiculo") Veiculo veiculo, BindingResult validacao) {
        veiculoService.validar(veiculo, validacao);
        if (validacao.hasErrors()) {
            veiculo.setIdVeiculo(null);
            return new ModelAndView("fragmentos/layoutSindico", "conteudo", "veiculoCadastro");
        }
        veiculoService.salvar(veiculo);
        return new ModelAndView("redirect:/sindico/veiculos");
    }

    @PutMapping("/cadastro")
    public ModelAndView putVeiculoCadastro(@Valid @ModelAttribute("veiculo") Veiculo veiculo, BindingResult validacao) {
        veiculoService.validar(veiculo, validacao);
        if (validacao.hasErrors()) {
            return new ModelAndView("fragmentos/layoutSindico", "conteudo", "veiculoCadastro");
        }
        veiculoService.editar(veiculo);
        return new ModelAndView("redirect:/sindico/veiculos");
    }

    @DeleteMapping("/excluir")
    public ModelAndView deleteVeiculoCadastro(@RequestParam("idObj") Long idObj) {
        veiculoService.excluir(veiculoService.ler(idObj));
        return new ModelAndView("redirect:/sindico/veiculos");
    }
}
