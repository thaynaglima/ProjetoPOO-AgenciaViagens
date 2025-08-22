package com.exemplo.agencia.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exemplo.agencia.model.PacoteViagem;
import com.exemplo.agencia.model.Reserva;
import com.exemplo.agencia.service.PacoteService;
import org.springframework.ui.Model;
import com.exemplo.agencia.service.ReservaService;

@Controller
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private PacoteService pacoteService;

    public ReservaController(ReservaService reservaService, PacoteService pacoteService) {
        this.reservaService = reservaService;
        this.pacoteService = pacoteService;
    }

    @PostMapping("/selecionar")
    public String selecionarPacote(@RequestParam("pacoteId") String pacoteId, Model model) {
        PacoteViagem pacoteSelecionado = pacoteService.buscarPorId(pacoteId);
        
        // guarda no sistema de reservas (poderia ser em sessão)
        reservaService.setPacoteSelecionado(pacoteSelecionado);

        model.addAttribute("pacoteSelecionado", pacoteSelecionado);
        return "redirect:/reservas/etapa2"; 
    }

    // Processar o formulário e ir para próxima etapa
    @ResponseBody
    public String processarReserva(
        @RequestParam String nome,
        @RequestParam int pessoasViagem,
        @RequestParam String pacoteId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
        @RequestParam String formaPagamento,
        @RequestParam String status,
        @RequestParam BigDecimal precoFinal
    ) {
        Reserva reserva = new Reserva(reservaService.buscarProximoId(),nome,pessoasViagem,pacoteId,LocalDate.now(), dataInicio, dataFim, 
                                        formaPagamento, status, precoFinal);

        reservaService.salvarReserva(reserva);

        return "Reserva cadastrada com sucesso!";
    }
}
