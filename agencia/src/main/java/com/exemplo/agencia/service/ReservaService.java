package com.exemplo.agencia.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import com.exemplo.agencia.model.PacoteViagem;
import com.exemplo.agencia.model.Reserva;
import com.exemplo.agencia.util.BancoDeDadosSimulado;

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
        // Gera ID automático se não tiver
        if (reserva.getId() == null || reserva.getId().isBlank()) {
            reserva.setId(buscarProximoId());
        }

        // Inicializa status como PENDENTE se não definido
        if (reserva.getStatus() == null) {
            reserva.setStatus(Reserva.StatusReserva.PENDENTE);
        }

        // Calcula precoFinal usando o pacote selecionado
        if (pacoteSelecionado != null) {
            BigDecimal precoCalculado = calcularPrecoFinal(reserva, pacoteSelecionado);
            reserva.setPrecoFinal(precoCalculado);
        }
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

    Set<Month> mesesAltaTemporada = Set.of(
        Month.NOVEMBER,
        Month.DECEMBER,
        Month.JANUARY,
        Month.JULY
    );
    Set<Month> mesesBaixaTemporada = Set.of(
        Month.MARCH,
        Month.APRIL,
        Month.MAY,
        Month.SEPTEMBER
    );

    public BigDecimal calcularPrecoFinal(Reserva reserva, PacoteViagem pacote) {
    if (pacote == null) return BigDecimal.ZERO;
    BigDecimal precoBase = pacote.getPreco();
        if(mesesAltaTemporada.contains(reserva.getDataInicio().getMonth()))
            precoBase = pacote.getPrecoAltaTemporada();
        else if(mesesBaixaTemporada.contains(reserva.getDataInicio().getMonth()))
            precoBase = pacote.getPrecoBaixaTemporada();
            
        BigDecimal preco = precoBase
                .multiply(BigDecimal.valueOf(reserva.getPessoasViagem()))
                .multiply(BigDecimal.valueOf(reserva.getQuantidadeDias()));

        return preco;
    }
}
