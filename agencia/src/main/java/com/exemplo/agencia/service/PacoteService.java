package com.exemplo.agencia.service;

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

}
