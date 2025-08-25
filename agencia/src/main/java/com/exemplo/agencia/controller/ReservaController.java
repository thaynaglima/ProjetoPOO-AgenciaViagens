package com.exemplo.agencia.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exemplo.agencia.model.Cliente;
import com.exemplo.agencia.model.PacoteViagem;
import com.exemplo.agencia.model.Reserva;
import com.exemplo.agencia.model.Reserva.FormaPagamento;
import com.exemplo.agencia.service.ClienteService;
import com.exemplo.agencia.service.PacoteService;
import org.springframework.ui.Model;
import com.exemplo.agencia.service.ReservaService;

@Controller
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private PacoteService pacoteService;

    @Autowired
    private ClienteService clienteService;

    public ReservaController(ReservaService reservaService, PacoteService pacoteService) {
        this.reservaService = reservaService;
        this.pacoteService = pacoteService;
    }

    @PostMapping("/selecionar")
    public String selecionarPacote(@RequestParam("pacoteId") String pacoteId, Model model) {
        PacoteViagem pacoteSelecionado = pacoteService.getBuscarPorId(pacoteId);
        
        // guarda no sistema de reservas (poderia ser em sessão)
        reservaService.setPacoteSelecionado(pacoteSelecionado);

        model.addAttribute("pacoteSelecionado", pacoteSelecionado);
        return "redirect:/reservas/etapa2"; 
    }

    // Processar o formulário e ir para próxima etapa
     @PostMapping("/reservas/processar")
    public String processarReserva(
        @RequestParam int pessoasViagem,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
        @RequestParam int quantidadeDias,
        @RequestParam FormaPagamento formaPagamento,
        Model model
    ) {
        // pega o cliente logado
        Cliente clienteLogado = clienteService.getClienteLogado();
        PacoteViagem pacoteSelecionado = reservaService.getPacoteSelecionado();
        if (pacoteSelecionado == null) {
            model.addAttribute("erro", "Nenhum pacote selecionado!");
            return "erro"; // ou página de erro
        }
        // Cria a reserva sem informar precoFinal ou status, o Service faz isso
        Reserva reserva = new Reserva(reservaService.buscarProximoId(),clienteLogado.getNome(),pessoasViagem,pacoteSelecionado.getId(),LocalDate.now(), dataInicio, quantidadeDias, 
                                        formaPagamento, null, null);

        // Salva a reserva usando o Service (gera id, calcula precoFinal, define status)
        reservaService.salvarReserva(reserva);

        // Adiciona atributos para a View de confirmação
        model.addAttribute("reserva", reserva);
        model.addAttribute("pacote", pacoteSelecionado);
        return "Reserva cadastrada com sucesso!";
    }
}
