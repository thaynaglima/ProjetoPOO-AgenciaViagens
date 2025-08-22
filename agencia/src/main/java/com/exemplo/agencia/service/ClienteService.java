package com.exemplo.agencia.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.agencia.model.Cliente;

@Service
public class ClienteService {
  private final BancoDeDadosSimulado bancoCliente;
  private Cliente clienteLogado;

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
    Cliente encontrado = bancoCliente.getClientes()
                .stream()
                .filter(c -> c.getEmail().equals(email) && c.getSenha().equals(senha))
                .findFirst()
                .orElse(null);

        if (encontrado != null) {
            this.clienteLogado = encontrado;
            return true;
        }
        return false;
  }

   public Cliente getClienteLogado() {
        if (clienteLogado == null) {
            throw new IllegalStateException("Nenhum cliente está logado no momento!");
        }
        return clienteLogado;
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
