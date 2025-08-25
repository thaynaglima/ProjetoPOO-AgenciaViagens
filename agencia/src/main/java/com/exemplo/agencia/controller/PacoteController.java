package com.exemplo.agencia.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.exemplo.agencia.model.PacoteViagem;
import com.exemplo.agencia.service.PacoteService;

@Controller
public class PacoteController {

    private final PacoteService pacoteService;

    public PacoteController(PacoteService pacoteService) {
        this.pacoteService = pacoteService;
    }

    @GetMapping("/pacotesTodos")
    public String listarPacotes(Model model) {
        List<PacoteViagem> pacotes = pacoteService.getPacotes();
        model.addAttribute("pacotes", pacotes);
        return "pacotes"; // procura o pacotes.html em /src/webapp/templates/
    }

    @GetMapping("/pacotesReserva")
    public String listarPacotesReserva(Model model) {
        List<PacoteViagem> pacotes = pacoteService.getPacotes();
        model.addAttribute("pacotes", pacotes);
        return "reserva"; // procura o pacotes.html em /src/webapp/templates/
    }

    @GetMapping("/3pacotes")
    public String listarPacotesDestaque(Model model) {
        List<PacoteViagem> pacotesLimitados = pacoteService.getPacotesLimitados(3);
        System.out.println(pacotesLimitados);
        model.addAttribute("pacotes", pacotesLimitados);
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
            pacotes = pacoteService.getPesquisarNome(nome);
        }

        // Filtro por categoria
        if ("nacional".equals(categoria)) {
            pacotes = pacoteService.getPacotesNacionais();
        } else if ("internacional".equals(categoria)) {
            pacotes = pacoteService.getPacotesInternacionais();
        }

        // Filtro por preço
        if ("baixo".equals(preco)) {
            pacotes = pacoteService.getPrecosAte2k();
        } else if ("medio".equals(preco)) {
            pacotes = pacoteService.getPrecosAte5k();
        } else if ("alto".equals(preco)) {
            pacotes = pacoteService.getPrecosMaior5k();
        }

        // Ordenação
        if ("nome".equals(ordem)) {
            pacotes = pacoteService.getOrdenarAtoZ();
        } else if ("menor".equals(ordem)) {
            pacotes = pacoteService.getMenorPreco();
        } else if ("maior".equals(ordem)) {
            pacotes = pacoteService.getMaiorPreco();
        }

        model.addAttribute("pacotes", pacotes);
        return "pacotes";
    }
    // Endpoint para obter preço na alta temporada pelo ID do pacote
    @GetMapping("/{id}/preco-alta-temporada")
    public ResponseEntity<BigDecimal> getPrecoAltaTemporada(@PathVariable("id") String pacoteId) {
        BigDecimal preco = pacoteService.getPrecoAltaTemp(pacoteId);
        if (preco == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(preco);
    }

    // Endpoint para obter preço na baixa temporada pelo ID do pacote
    @GetMapping("/{id}/preco-baixa-temporada")
    public ResponseEntity<BigDecimal> getPrecoBaixaTemporada(@PathVariable("id") String pacoteId) {
        BigDecimal preco = pacoteService.getPrecoBaixaTemp(pacoteId);
        if (preco == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(preco);
    }

    @GetMapping("/detalhes-pacote/{id}")
    public String detalhesPacote(@PathVariable String id, Model model) {
        PacoteViagem pacote = pacoteService.getBuscarPorId(id);
        if (pacote == null) {
            return "erro-pacote"; // página de erro se o pacote não existir
        }
        model.addAttribute("pacote", pacote);
        return "detalhes-pacote";
    }
}
