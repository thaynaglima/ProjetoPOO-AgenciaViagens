package com.exemplo.agencia.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.agencia.model.Cliente;

@Service
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

  public Cliente buscarPorCpf(String cpf) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'buscarPorCpf'");
  }

  //buscar por cpf do cliente *
  public Cliente buscaCpf(String cpf){
    return bancoCliente.getClientes().stream().filter(c -> c.getCpf().equals(cpf)).findFirst().orElse(null);
  }
}
