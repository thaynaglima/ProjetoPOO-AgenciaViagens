package com.exemplo.agencia.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.exemplo.agencia.model.PacoteViagem;
import com.exemplo.agencia.service.ClienteService;
import com.exemplo.agencia.service.PacoteService;

@Controller
public class PacoteController {
    @Autowired
    private ClienteService clienteService;
    private final PacoteService pacoteService;

    public PacoteController(PacoteService pacoteService) {
        this.pacoteService = pacoteService;
    }

    @GetMapping("/pacotesTodos")
    public String listarPacotes(Model model) {
        List<PacoteViagem> pacotes = pacoteService.getPacotes();
        model.addAttribute("pacotes", pacotes);
        if (clienteService.getClienteLogado() != null)
            model.addAttribute("cliente", clienteService.getClienteLogado());
        return "pacotes"; 
    }

    @GetMapping("/pacotesReserva")
    public String listarPacotesReserva(Model model) {
        List<PacoteViagem> pacotes = pacoteService.getPacotes();
        model.addAttribute("pacotes", pacotes);
        if (clienteService.getClienteLogado() != null)
            model.addAttribute("cliente", clienteService.getClienteLogado());
        return "reserva";
    }

    @GetMapping("/")
    public String listarPacotesDestaque(Model model) {
        List<PacoteViagem> pacotesLimitados = pacoteService.getPacotesLimitados(3);
        System.out.println(pacotesLimitados);
        model.addAttribute("pacotesLimitados", pacotesLimitados);
        if (clienteService.getClienteLogado() != null)
            model.addAttribute("cliente", clienteService.getClienteLogado());
        return "index"; // -> index.html
    }

    @GetMapping("/pacotesFiltrados")
    public String filtrarPacotes(@RequestParam(required = false) String nome,
                                @RequestParam(required = false) String categoria,
                                @RequestParam(required = false) String preco,
                                @RequestParam(required = false) String ordem,
                                Model model) {

        List<PacoteViagem> pacotes = pacoteService.getPacotes();
        // Filtro por nome
        if (nome != null && !nome.isBlank()) {
            pacotes = pacoteService.getPesquisarNome(nome);}
        // Filtro por categoria
        if ("nacional".equals(categoria)) {
            pacotes = pacoteService.getPacotesNacionais();
        } else if ("internacional".equals(categoria)) {
            pacotes = pacoteService.getPacotesInternacionais();}
        // Filtro por preço
        if ("baixo".equals(preco)) {
            pacotes = pacoteService.getPrecosAte2k();
        } else if ("medio".equals(preco)) {
            pacotes = pacoteService.getPrecosAte5k();
        } else if ("alto".equals(preco)) {
            pacotes = pacoteService.getPrecosMaior5k();}
        // Ordenação
        if ("nome".equals(ordem)) {
            pacotes = pacoteService.getOrdenarAtoZ();
        } else if ("menor".equals(ordem)) {
            pacotes = pacoteService.getMenorPreco();
        } else if ("maior".equals(ordem)) {
            pacotes = pacoteService.getMaiorPreco();}
        model.addAttribute("pacotes", pacotes);
        if (clienteService.getClienteLogado() != null)
            model.addAttribute("cliente", clienteService.getClienteLogado());
        return "pacotes";
    }

    @GetMapping("/{id}")
    public String getPacoteDetalhes(@PathVariable String id, Model model) {
        PacoteViagem pacote = pacoteService.getBuscarPorId(id);
        if (pacote == null) {
            return "redirect:/pacotes";
        }

        BigDecimal precoAlta = pacoteService.getPrecoAltaTemp(id);
        BigDecimal precoBaixa = pacoteService.getPrecoBaixaTemp(id);
      
        model.addAttribute("pacote", pacote);
        model.addAttribute("precoAlta", precoAlta);
        model.addAttribute("precoBaixa", precoBaixa);
        if (clienteService.getClienteLogado() != null)
            model.addAttribute("cliente", clienteService.getClienteLogado());

        return "detalhes-pacote";
    }

    @GetMapping("/detalhes-pacote/{id}")
    public String detalhesPacote(@PathVariable String id, Model model) {
        PacoteViagem pacote = pacoteService.getBuscarPorId(id);
        model.addAttribute("pacote", pacote);
        if (clienteService.getClienteLogado() != null)
            model.addAttribute("cliente", clienteService.getClienteLogado());
        return "detalhes-pacote";
    }
}
