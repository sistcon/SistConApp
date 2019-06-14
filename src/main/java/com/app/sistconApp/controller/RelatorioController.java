package com.app.sistconApp.controller;

import com.app.sistconApp.modelo.Cobranca;
import com.app.sistconApp.modelo.Moradia;
import com.app.sistconApp.modelo.Movimento;
import com.app.sistconApp.modelo.Periodo;
import com.app.sistconApp.modelo.enums.TipoCategoria;
import com.app.sistconApp.service.CategoriaService;
import com.app.sistconApp.service.CondominioService;
import com.app.sistconApp.service.PeriodoService;
import com.app.sistconApp.service.RelatorioService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("sindico/relatorios")
public class RelatorioController {

    @Autowired
    RelatorioService relatorioService;

    @Autowired
    CondominioService condominioService;

    @Autowired
    PeriodoService periodoService;

    @Autowired
    CategoriaService categoriaService;

    @ModelAttribute("ativo")
    public String[] ativo() {
        return new String[]{"relatorios", ""};
    }

    @GetMapping({"/", "", "/lista", "/todos"})
    public ModelAndView inicio() {
        return new ModelAndView("fragmentos/layoutSindico", "conteudo", "relatorioLista");
    }

    @GetMapping("/livroCaixa")
    public ModelAndView getLivroCaixa() {
        return new ModelAndView("fragmentos/layoutSindico", "conteudo", "relatorioLivroCaixa");
    }

    @PostMapping("/livroCaixa")
    public ModelAndView postLivroCaixa(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim, ModelMap model) {

        if (inicio.isAfter(fim)) {
            model.addAttribute("fimInvalido", "true");
            model.addAttribute("conteudo", "relatorioLivroCaixa");
            return new ModelAndView("fragmentos/layoutSindico", model);
        }

        BigDecimal saldoInicial = relatorioService.saldoInicialTodasContasEm(inicio);
        List<Movimento> lancamentos = relatorioService.lancamentosEntre(inicio, fim);

        model.addAttribute("condominio", condominioService.ler());
        model.addAttribute("inicio", inicio);
        model.addAttribute("fim", fim);
        model.addAttribute("saldoInicial", saldoInicial);
        model.addAttribute("lancamentos", lancamentos);
        model.addAttribute("saldos", relatorioService.saldosAposMovimentos(lancamentos, saldoInicial));
        model.addAttribute("relatorio", "relatorioLivroCaixa");
        return new ModelAndView("fragmentos/layoutRelatorio", model);
    }

    @GetMapping("/balancete")
    public ModelAndView getBalancete() {
        return new ModelAndView("fragmentos/layoutSindico", "conteudo", "relatorioBalancete");
    }

    @PostMapping("/balancete")
    public ModelAndView postBalancete(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim, ModelMap model) {

        if (inicio.isAfter(fim)) {
            model.addAttribute("fimInvalido", "true");
            model.addAttribute("conteudo", "relatorioBalancete");
            return new ModelAndView("fragmentos/layoutSindico", model);
        }

        model.addAttribute("condominio", condominioService.ler());
        model.addAttribute("inicio", inicio);
        model.addAttribute("fim", fim);
        model.addAttribute("receitas", relatorioService.somasPorTipoEntre(inicio, fim, TipoCategoria.R));
        model.addAttribute("despesas", relatorioService.somasPorTipoEntre(inicio, fim, TipoCategoria.D));
        model.addAttribute("totalReceitasDespesas", relatorioService.receitaDespesaEntre(inicio, fim));
        model.addAttribute("relatorio", "relatorioBalancete");
        return new ModelAndView("fragmentos/layoutRelatorio", model);
    }

    @GetMapping("/inadimplencia")
    public ModelAndView getInadimplencia() {
        return new ModelAndView("fragmentos/layoutSindico", "conteudo", "relatorioInadimplencia");
    }

    @PostMapping("/inadimplencia")
    public ModelAndView postInadimplencia(ModelMap model) {
        SortedMap<Moradia, List<Cobranca>> inadimplencia = relatorioService.inadimplenciaAtualDetalhada();

        model.addAttribute("condominio", condominioService.ler());
        model.addAttribute("fim", LocalDate.now());
        model.addAttribute("inadimplencia", inadimplencia);
        model.addAttribute("subtotais", relatorioService.somaCobrancas(inadimplencia));
        model.addAttribute("total", relatorioService.inadimplenciaAtual());
        model.addAttribute("relatorio", "relatorioInadimplencia");
        return new ModelAndView("fragmentos/layoutRelatorio", model);
    }

    @GetMapping("/orcamento")
    public ModelAndView getOrcamento(ModelMap model) {
        model.addAttribute("periodos", periodoService.listar());
        model.addAttribute("conteudo", "relatorioOrcamento");
        return new ModelAndView("fragmentos/layoutSindico", "conteudo", "relatorioOrcamento");
    }

    @PostMapping("/orcamento")
    public ModelAndView postOrcamento(@RequestParam("periodo") Periodo periodo, ModelMap model) {
        model.addAttribute("condominio", condominioService.ler());
        model.addAttribute("periodo", periodo.toString());
        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("orcadoRealizadoSintetico", relatorioService.somaOrcadoRealizadoCategorias(periodo));
        model.addAttribute("orcadoRealizadoAnalitico", relatorioService.somaOrcadoRealizadoSubcategorias(periodo));
        model.addAttribute("relatorio", "relatorioOrcamento");
        return new ModelAndView("fragmentos/layoutRelatorio", model);
    }

}
