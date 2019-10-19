/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.controller;

import com.app.sistconApp.modelo.Bloco;
import com.app.sistconApp.modelo.Evento;
import com.app.sistconApp.modelo.Moradia;
import com.app.sistconApp.service.BlocoService;
import com.app.sistconApp.service.EventoService;
import com.app.sistconApp.service.MoradiaService;
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
@RequestMapping({"sindico/eventos","condomino/eventos"})
public class EventoController {

    @Autowired
    private EventoService eventoService;
    
    @Autowired
    MoradiaService moradiaService;
    
   

    @ModelAttribute("ativo")
    public String[] ativo() {
        return new String[]{"condominio", "eventos"};
    }
    @ModelAttribute("moradias")
    public List<Moradia> moradias() {
        return moradiaService.listar();
    }
    
    

    @GetMapping({"", "/", "/lista"})
    public ModelAndView getEventos(@RequestParam("pagina") Optional<Integer> pagina,
            @RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
        model.addAttribute("eventos",
                eventoService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
        model.addAttribute("conteudo", "eventoLista");
        return new ModelAndView("fragmentos/layoutSindico", model);
    }

    @GetMapping("/cadastro")
    public ModelAndView getEventoCadastro(@ModelAttribute("evento") Evento evento) {
        return new ModelAndView("fragmentos/layoutSindico", "conteudo", "eventoCadastro");
    }

    @GetMapping("/{idEvento}/cadastro")
    public ModelAndView getEventoEditar(@PathVariable("idEvento") Long idEvento, ModelMap model) {
        model.addAttribute("evento", eventoService.ler(idEvento));
        model.addAttribute("conteudo", "eventoCadastro");
        return new ModelAndView("fragmentos/layoutSindico", model);
    }

    @PostMapping("/cadastro")
    public ModelAndView postEventoCadastro(@Valid @ModelAttribute("evento") Evento evento, BindingResult validacao) {
        eventoService.validar(evento, validacao);
        if (validacao.hasErrors()) {
            evento.setIdEvento(null);
            return new ModelAndView("fragmentos/layoutSindico", "conteudo", "eventoCadastro");
        }
        eventoService.salvar(evento);
        return new ModelAndView("redirect:/sindico/eventos");
    }

    @PutMapping("/cadastro")
    public ModelAndView putEventoCadastro(@Valid @ModelAttribute("evento") Evento evento, BindingResult validacao) {
        eventoService.validar(evento, validacao);
        if (validacao.hasErrors()) {
            return new ModelAndView("fragmentos/layoutSindico", "conteudo", "eventoCadastro");
        }
        eventoService.editar(evento);
        return new ModelAndView("redirect:/sindico/eventos");
    }

    @DeleteMapping("/excluir")
    public ModelAndView deleteEventoCadastro(@RequestParam("idObj") Long idObj) {
        eventoService.excluir(eventoService.ler(idObj));
        return new ModelAndView("redirect:/sindico/eventos");
    }
}
