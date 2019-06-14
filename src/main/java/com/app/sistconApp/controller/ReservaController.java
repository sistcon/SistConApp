/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.sistconApp.controller;


import com.app.sistconApp.modelo.Reserva;
import com.app.sistconApp.modelo.enums.TipoReserva;
import com.app.sistconApp.service.ReservaService;
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
@RequestMapping("sindico/reservas")
public class ReservaController {
    
    @Autowired
	private ReservaService reservaService;
    
    @ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "condominio", "reservas" };
	}
        
        @ModelAttribute("tipos")
    public TipoReserva[] tipos() {
        return TipoReserva.values();
    }

        
        @GetMapping({ "", "/", "/lista" })
	public ModelAndView getReservas(@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("tamanho") Optional<Integer> tamanho, ModelMap model) {
		model.addAttribute("reservas",
				reservaService.listarPagina(PageRequest.of(pagina.orElse(1) - 1, tamanho.orElse(20))));
		model.addAttribute("conteudo", "reservaLista");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@GetMapping("/cadastro")
	public ModelAndView getReservaCadastro(@ModelAttribute("reserva") Reserva reserva) {
		return new ModelAndView("fragmentos/layoutSindico", "conteudo", "reservaCadastro");
	}

	@GetMapping("/{idReserva}/cadastro")
	public ModelAndView getReservaEditar(@PathVariable("idReserva") Long idReserva, ModelMap model) {
		model.addAttribute("reserva", reservaService.ler(idReserva));
		model.addAttribute("conteudo", "reservaCadastro");
		return new ModelAndView("fragmentos/layoutSindico", model);
	}

	@PostMapping("/cadastro")
	public ModelAndView postReservaCadastro(@Valid @ModelAttribute("reserva") Reserva reserva, BindingResult validacao) {
		reservaService.validar(reserva, validacao);
		if (validacao.hasErrors()) {
			reserva.setIdReserva(null);
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "reservaCadastro");
		}
		reservaService.salvar(reserva);
		return new ModelAndView("redirect:/sindico/reservas");
	}

	@PutMapping("/cadastro")
	public ModelAndView putReservaCadastro(@Valid @ModelAttribute("reserva") Reserva reserva, BindingResult validacao) {
		reservaService.validar(reserva, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("fragmentos/layoutSindico", "conteudo", "reservaCadastro");
		}
		reservaService.editar(reserva);
		return new ModelAndView("redirect:/sindico/reservas");
	}

	@DeleteMapping("/excluir")
	public ModelAndView deleteReservaCadastro(@RequestParam("idObj") Long idObj) {
		reservaService.excluir(reservaService.ler(idObj));
		return new ModelAndView("redirect:/sindico/reservas");
	}

    
}
