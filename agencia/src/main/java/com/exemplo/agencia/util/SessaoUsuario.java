package com.exemplo.agencia.util;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.exemplo.agencia.model.Cliente;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessaoUsuario {
    private Cliente clienteAtual;

    // Construtor público sem argumentos (necessário para o Spring)
    public SessaoUsuario() {}

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
