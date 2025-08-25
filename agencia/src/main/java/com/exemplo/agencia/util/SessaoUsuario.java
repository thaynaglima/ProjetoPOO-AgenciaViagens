package com.exemplo.agencia.util;

import org.springframework.stereotype.Component;

import com.exemplo.agencia.model.Cliente;

@Component
public class SessaoUsuario {
    private static SessaoUsuario instancia = new SessaoUsuario();
    private Cliente clienteAtual;

    private SessaoUsuario() {}

    public static SessaoUsuario getInstancia() {
        return instancia;
    }

    public Cliente getClienteAtual() {
        return clienteAtual;
    }

    public void logar(Cliente cliente) {
        this.clienteAtual = cliente;
    }

    public void logout() {
        this.clienteAtual = null;
    }

    public boolean estaLogado() {
        return clienteAtual != null;
    }
}
