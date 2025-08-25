package com.exemplo.agencia.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

        @GetMapping("/pacotesNacionais")
        public String filtrarPacotesNacionais(Model model) {
            List<PacoteViagem> nacionais  = pacoteService.getPacotesNacionais();
            model.addAttribute("pacotes", nacionais);
            return "pacotes"; 
    }

    @GetMapping("/pacotesInternacionais")
    public String filtrarPacotesInternacionais(Model model){
        List<PacoteViagem> intermacionais = pacoteService.getPacotesInternacionais();
        model.addAttribute("pacotes", intermacionais);
        return "pacotes";
    }
    @GetMapping("/pacotesAbaixo2k")
    public String filtrarPacotesAbaixo2k(Model model){
        List<PacoteViagem> abaixo2k = pacoteService.getPrecosAte2k();
        model.addAttribute("pacotes", abaixo2k);
        return "pacotes";
    }

    @GetMapping("/pacotesEntre2kE5k")
    public String filtrarPacotesEntre2kE5k(Model model){
        List<PacoteViagem> entre2kE5k = pacoteService.getPrecosAte2k();
        model.addAttribute("pacotes", entre2kE5k);
        return "pacotes";
    }

    @GetMapping("/pacotesAcima5k")
    public String filtrarPacotesAcima5k(Model model){
        List<PacoteViagem> acima5k = pacoteService.getPrecosAte2k();
        model.addAttribute("pacotes", acima5k);
        return "pacotes";
    }

    @GetMapping("/ordemAtoZ")
    public String filtrarAtoZ (Model model){
        List<PacoteViagem> filterAtoZ = pacoteService.getOrdenarAtoZ();
        model.addAttribute("pacotes", filterAtoZ);
        return "pacotes";
    }

 @GetMapping("/ordemPrecoMenorPMaior")
    public String filtrarPrecoMenorpMaior (Model model){
        List<PacoteViagem> menorPmaior = pacoteService.getMenorPreco();
        model.addAttribute("pacotes", menorPmaior);
        return "pacotes";
    }

    @GetMapping("/ordemPreco")
    public String filtrarPrecoMaiorpMenor (Model model){
        List<PacoteViagem> filterPrice = pacoteService.getMenorPreco();
        model.addAttribute("pacotes", filterPrice);
        return "pacotes";
    }

    @GetMapping("/pesquisarId")
    public String PesquisarId(String id, Model model){
        PacoteViagem pacote = pacoteService.getBuscarPorId(id);

        if(pacote != null)
        model.addAttribute("pacote", pacote);
            else
            model.addAttribute("pacote", null);
        return "pacotes";
    }

    @GetMapping("/pesquisar")
    public String PesquisarNome (String nome, Model model){
        List<PacoteViagem> filtroPesquisa = pacoteService.getPesquisarNome(nome);
        model.addAttribute("Pacotes", filtroPesquisa);
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
}
