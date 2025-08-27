package com.exemplo.agencia.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/reserva/{pacoteId}")
    public String selecionarPacote(@PathVariable("pacoteId") String pacoteId, Model model) {
        PacoteViagem pacoteSelecionado = pacoteService.getBuscarPorId(pacoteId);
        
        if (pacoteSelecionado == null) {
            // Se não existir, volta pra lista
            return "redirect:/pacotes";
        }

        // guarda no sistema de reservas (poderia ser em sessão)
        reservaService.setPacoteSelecionado(pacoteSelecionado);
        // Inicializa reserva temporária
        Reserva reserva = new Reserva();
        reserva.setPacoteId(pacoteSelecionado.getId());

        model.addAttribute("pacoteSelecionado", pacoteSelecionado);
        model.addAttribute("reserva", reserva);
        return "reserva"; 
    }

    // Processar o formulário e ir para próxima etapa
     @PostMapping("/dados")
    public String salvarDados(@ModelAttribute Reserva reserva,
        @RequestParam int pessoasViagem,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
        @RequestParam int quantidadeDias,
        Model model
    ) {
        reserva.setPessoasViagem(pessoasViagem);
        reserva.setDataInicio(dataInicio);
        reserva.setQuantidadeDias(quantidadeDias);

        reservaService.setReservaTemporaria(reserva);

        // Adiciona atributos para a View de confirmação
        model.addAttribute("reserva", reserva);
        return "reserva";
    }

    // Etapa 3 - pagamento -> cria a Reserva de verdade
    @PostMapping("/finalizar")
    public String finalizarReserva(
            @RequestParam("paymentMethod") String formaPagamento,
            Model model) {

        PacoteViagem pacoteSelecionado = reservaService.getPacoteSelecionado();
        Cliente clienteLogado = clienteService.getClienteLogado();
        Reserva reserva = reservaService.getReservaTemporaria();

        if (pacoteSelecionado == null || reserva == null) {
            model.addAttribute("erro", "Dados da reserva incompletos.");
            return "erro";
        }

        reserva.setDataReserva(LocalDate.now());
        reserva.setId(reservaService.buscarProximoId());
        reserva.setClienteNome(clienteLogado.getNome());
        reserva.setFormaPagamento(Reserva.FormaPagamento.valueOf(formaPagamento));

        reservaService.salvarReserva(reserva);

        model.addAttribute("reserva", reserva);
        model.addAttribute("pacote", pacoteSelecionado);
        return "redirect:/historico-reservas";
    }

        @GetMapping("/historico-reservas")
    public String listarReservas(
            @RequestParam(name = "status", required = false, defaultValue = "all") String status,
            @RequestParam(name = "search", required = false) String search,
            Model model) {

        Cliente clienteLogado = clienteService.getClienteLogado();

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

        model.addAttribute("reservas", reservas);
        model.addAttribute("totalReservas", total);
        model.addAttribute("reservasConfirmadas", confirmadas);
        model.addAttribute("reservasConcluidas", concluidas);
        model.addAttribute("totalInvestido", totalInvestido);
        model.addAttribute("statusSelecionado", status);
        model.addAttribute("searchTerm", search);

        return "historico-reservas"; 
    }
}
