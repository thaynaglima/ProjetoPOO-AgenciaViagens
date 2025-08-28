package com.exemplo.agencia.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exemplo.agencia.model.Cliente;
import com.exemplo.agencia.model.PacoteViagem;
import com.exemplo.agencia.model.Reserva;
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

    @PostMapping("/reservar/{pacoteId}")

    public String finalizarReserva(@PathVariable("pacoteId") String pacoteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam int pessoasViagem,
            @RequestParam int quantidadeDias,
            @RequestParam("formaPagamento") String formaPagamento,
            Model model) {
        PacoteViagem pacoteSelecionado = pacoteService.getBuscarPorId(pacoteId);
        if (pacoteSelecionado == null) {
            // Se não existir, volta pra lista
            return "redirect:/pacotes";
        }

        // Validações básicas no Controller
        if (pessoasViagem < 0) {
            return "Mínimo 1 pessoa";
        }
        if (quantidadeDias < 0) {
            return "Mínimo 1 pessoa";
        }
        if (dataInicio == null || dataInicio.isBefore(LocalDate.now())) {
            return "Data de nascimento inválida";
        }
        if (formaPagamento == null) {
            return "Você deve escolher uma forma de pagamento";
        }
        Cliente clienteLogado = clienteService.getClienteLogado();

        if (pacoteSelecionado == null || clienteLogado == null) {
            return "Dados da reserva incompletos.";
        }
        // Se passou nas validações básicas, delega para o service salvar
        Reserva reserva = new Reserva();
        reserva.setClienteNome(clienteLogado.getNome());
        reserva.setPessoasViagem(pessoasViagem);
        reserva.setPacoteId(pacoteSelecionado.getId());
        reserva.setDataReserva(LocalDate.now());
        reserva.setDataInicio(dataInicio);
        reserva.setQuantidadeDias(quantidadeDias);
        reserva.setFormaPagamento(Reserva.FormaPagamento.valueOf(formaPagamento));

        reservaService.salvarReserva(reserva);

        model.addAttribute("reserva", reserva);
        model.addAttribute("pacote", pacoteSelecionado);
        model.addAttribute("cliente", clienteService.getClienteLogado());
        return "redirect:/historico-reservas";
    }

    @GetMapping("/historico-reservas")
    public String listarReservas(
            @RequestParam(name = "status", required = false, defaultValue = "all") String status,
            @RequestParam(name = "search", required = false) String search,
            Model model) {
        Cliente clienteLogado = clienteService.getClienteLogado();
        if (clienteLogado == null) {
            // Se não estiver logado, redireciona para login
            return "redirect:/login";
        }
        // Buscar apenas reservas do cliente logado
        var reservas = reservaService.getReserva().stream()
                .filter(r -> r.getClienteNome().equalsIgnoreCase(clienteLogado.getNome()))
                .toList();

        // filtro por status
        if (!"all".equalsIgnoreCase(status)) {
            reservas = reservas.stream()
                    .filter(r -> r.getStatus().name().equalsIgnoreCase(status))
                    .toList();
        }

        // filtro por busca (cliente ou pacote)
        if (search != null && !search.isBlank()) {
            String termo = search.toLowerCase();
            reservas = reservas.stream()
                    .filter(r -> r.getClienteNome().toLowerCase().contains(termo)
                            || r.getPacoteId().toLowerCase().contains(termo))
                    .toList();
        }

        // Estatísticas
        long total = reservas.size();
        long confirmadas = reservas.stream()
                .filter(r -> r.getStatus() == Reserva.StatusReserva.CONFIRMADA)
                .count();
        long concluidas = reservas.stream()
                .filter(r -> r.getStatus() == Reserva.StatusReserva.FINALIZADA)
                .count();
        BigDecimal totalInvestido = reservas.stream()
                .map(Reserva::getPrecoFinal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Adicionar atributos para a view
        model.addAttribute("reservas", reservas);
        model.addAttribute("totalReservas", total);
        model.addAttribute("reservasConfirmadas", confirmadas);
        model.addAttribute("reservasConcluidas", concluidas);
        model.addAttribute("totalInvestido", totalInvestido);
        model.addAttribute("statusSelecionado", status);
        model.addAttribute("searchTerm", search);
        if (clienteService.getClienteLogado() != null)
            model.addAttribute("cliente", clienteLogado);

        return "historico-reservas";
    }


    @GetMapping("/reserva/{pacoteId}")
    public String mostraFormRes(@PathVariable("pacoteId") String pacoteId, Model model) {
        var pacote = pacoteService.getBuscarPorId(pacoteId);
        if (pacote == null)
            return "redirect:/pacotes";

        model.addAttribute("pacote", pacote);
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("cliente", clienteService.getClienteLogado());

        return "reserva";
    }
}
    
