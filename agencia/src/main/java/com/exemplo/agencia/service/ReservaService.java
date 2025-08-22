package com.exemplo.agencia.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import com.exemplo.agencia.model.PacoteViagem;
import com.exemplo.agencia.model.Reserva;

@Service
public class ReservaService {
    private final BancoDeDadosSimulado bancoReserva;
    private PacoteViagem pacoteSelecionado;

    public ReservaService() {
        this.bancoReserva = new BancoDeDadosSimulado();
    }

    public PacoteViagem getPacoteSelecionado() {
        return pacoteSelecionado;
    }

    public void setPacoteSelecionado(PacoteViagem pacote) {
        this.pacoteSelecionado = pacote;
    }

    public List<Reserva> getReserva() {
        return bancoReserva.getReservas();
    }

    public void salvarReserva(Reserva reserva) {
        bancoReserva.getReservas().add(reserva); // adiciona na lista em memória
        try {
            bancoReserva.salvarDados(); // persiste no arquivo
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar reserva.");
        }
    }

    public Reserva buscarPorId(String id) {
        return bancoReserva.getReservas().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public String buscarProximoId() {
    List<Reserva> reservas = bancoReserva.getReservas();

    if (reservas.isEmpty()) {
        return "R001";
    }

    int maxId = reservas.stream()
        .map(Reserva::getId)
        .filter(id -> id.matches("RES\\d+")) // Ex: R001
        .mapToInt(id -> Integer.parseInt(id.substring(1))) // Remove o 'R'
        .max()
        .orElse(0);

    return "RES" + String.format("%03d", maxId + 1); // Ex: R005
    }

}
