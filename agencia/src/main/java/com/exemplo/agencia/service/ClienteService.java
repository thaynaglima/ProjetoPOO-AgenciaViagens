package com.exemplo.agencia.service;

import java.io.IOException;
import java.util.List;

import com.exemplo.agencia.model.Cliente;

public class ClienteService {
  private final BancoDeDadosSimulado bancoCliente;

  public ClienteService() {
    this.bancoCliente = new BancoDeDadosSimulado();
  }

  public List<Cliente> getClientes() {
    return bancoCliente.getClientes();
  }

  public void salvarCliente(Cliente cliente) {
    // Adiciona o cliente na lista em memória
    bancoCliente.getClientes().add(cliente);

    // Converte cliente para String[] usando método do BancoDeDadosSimulado
    String[] clienteArray = bancoCliente.clienteParaString(cliente);

    try {
      // Usa o método para adicionar uma linha no arquivo (append)
      ArquivoUtils.adicionarLinha("clientes.txt", clienteArray);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Erro ao adicionar cliente no arquivo.", e);
    }
  }

  public boolean autenticar(String email, String senha) {
    return bancoCliente.getClientes()
            .stream()
            .anyMatch(c -> c.getEmail().equals(email) && c.getSenha().equals(senha));
  }
}
