package com.exemplo.agencia.service;

import java.util.List;

import org.springframework.stereotype.Service;
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
}
