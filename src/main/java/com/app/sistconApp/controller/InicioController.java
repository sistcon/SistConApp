package com.app.sistconApp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InicioController {

	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "inicio", "" }; 
	}

	@GetMapping({ "/", "", "/home", "/inicio"})
	public ModelAndView inicio() {
		return new ModelAndView("fragmentos/layoutSite", "conteudo", "inicio");
	}

	@GetMapping({ "/entrar", "/login" })
	public ModelAndView preLogin() {
		return new ModelAndView("fragmentos/layoutSite", "conteudo", "login");
	}

	@GetMapping("/autenticado")
	public String posLogin(Authentication authentication) {
		String retorno = "redirect:/login?erro";
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("SINDICO"))) {
			retorno = "redirect:/sindico";
		} else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("MORADOR"))) {
			retorno = "redirect:/condomino";
		} else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
			retorno = "redirect:/admin";
		}
		return retorno;
	}
}
