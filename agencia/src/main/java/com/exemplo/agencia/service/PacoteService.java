package com.exemplo.agencia.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.agencia.model.PacoteInternacional;
import com.exemplo.agencia.model.PacoteNacional;
import com.exemplo.agencia.model.PacoteViagem;

@Service
public class PacoteService {
    private final BancoDeDadosSimulado banco;

    public PacoteService() {
        this.banco = new BancoDeDadosSimulado();
    }

    public List<PacoteViagem> getPacotes() {
        return banco.getPacotes();
    }

    public List<PacoteViagem> getPacotesLimitados(int quantidade) {
        return banco.getPacotes().stream()
                .limit(quantidade)
                .toList();
    }

    public List<PacoteViagem> getPacotesNacionais() {
        return banco.getPacotes().stream()
                .filter(p -> p instanceof PacoteNacional)
                .toList();
    }

    public List<PacoteViagem> getPacotesInternacionais() {
        return banco.getPacotes().stream()
                .filter(p -> p instanceof PacoteInternacional)
                .toList();
    }

}
