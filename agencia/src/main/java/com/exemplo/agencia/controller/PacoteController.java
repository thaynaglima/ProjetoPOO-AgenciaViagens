package com.exemplo.agencia.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.exemplo.agencia.model.PacoteViagem;
import com.exemplo.agencia.service.PacoteService;

@Controller
public class PacoteController {

    private final PacoteService pacoteService;

    public PacoteController(PacoteService pacoteService) {
        this.pacoteService = pacoteService;
    }

    @GetMapping("/pacotes")
    public String listarPacotes(Model model) {
        List<PacoteViagem> pacotes = pacoteService.getPacotes();
        model.addAttribute("pacotes", pacotes);
        return "pacotes"; // procura o pacotes.html em /src/webapp/templates/
    }

    @GetMapping("/")
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
}
