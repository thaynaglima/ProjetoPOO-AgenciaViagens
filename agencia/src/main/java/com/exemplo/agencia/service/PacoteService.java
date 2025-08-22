package com.exemplo.agencia.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.agencia.model.PacoteInternacional;
import com.exemplo.agencia.model.PacoteNacional;
import com.exemplo.agencia.model.PacoteViagem;

@Service
public class PacoteService {
    private final BancoDeDadosSimulado bancoPacote;

    public PacoteService() {
        this.bancoPacote = new BancoDeDadosSimulado();
    }

    public List<PacoteViagem> getPacotes() {
        return bancoPacote.getPacotes();
    }

    public List<PacoteViagem> getPacotesLimitados(int quantidade) {
        return bancoPacote.getPacotes().stream()
                .limit(quantidade)
                .toList();
    }

    public List<PacoteViagem> getPacotesNacionais() {
        return bancoPacote.getPacotes().stream()
                .filter(p -> p instanceof PacoteNacional)
                .toList();
    }

    public List<PacoteViagem> getPacotesInternacionais() {
        return bancoPacote.getPacotes().stream()
                .filter(p -> p instanceof PacoteInternacional)
                .toList();
    }

    public List<PacoteViagem> getPrecosAte2k() {
        return bancoPacote.getPacotes().stream().filter(p -> p.getPreco().compareTo(new BigDecimal("2000")) < 0)
                .toList();
    }

    public List<PacoteViagem> getPrecosAte5k() {
        return bancoPacote.getPacotes().stream().filter(p -> p.getPreco().compareTo(new BigDecimal("2000")) >= 0 &&
                p.getPreco().compareTo(new BigDecimal("5000")) <= 0).toList();
    }

    public List<PacoteViagem> getPrecosMaior5k() {
        return bancoPacote.getPacotes().stream().filter(p -> p.getPreco().compareTo(new BigDecimal("5000")) > 0)
                .toList();
    }

    public List<PacoteViagem> getOrdenarAtoZ() {
        return bancoPacote.getPacotes().stream()
                .sorted(Comparator.comparing(PacoteViagem::getPais))
                .toList();
    }

    public List<PacoteViagem> getMenorPreco() {
        return bancoPacote.getPacotes().stream().sorted(Comparator.comparing(PacoteViagem::getPreco)).toList();
    }

    public List<PacoteViagem> getMaiorPreco() {
        return bancoPacote.getPacotes().stream().sorted(Comparator.comparing(PacoteViagem::getPreco).reversed())
                .toList();
    }

    public PacoteViagem buscarPorId(String pacoteId) {
        return bancoPacote.getPacotes().stream()
                .filter(p -> p.getId().equals(pacoteId))
                .findFirst()
                .orElse(null); // se não achar, retorna null
    }
}
