package com.exemplo.agencia.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.exemplo.agencia.model.Cliente;

@Service
public class ClienteService {
  private final BancoDeDadosSimulado bancoCliente;
  private Cliente clienteLogado;

  // Constantes para validação
  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@[\\w-]+(\\.[\\w-]+)+$");
  private static final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
  private static final Pattern TELEFONE_PATTERN = Pattern.compile("\\(\\d{2}\\) \\d{4,5}-\\d{4}");

  public ClienteService() {
    this.bancoCliente = new BancoDeDadosSimulado();
  }

  // Retorna todos os clientes
  public List<Cliente> getClientes() {
    return bancoCliente.getClientes();
  }

  public void salvarCliente(Cliente cliente) {
    validarCliente(cliente); // Valida antes de salvar
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

  // buscar por cpf do cliente *
  public Cliente buscaCpf(String cpf) {
    return bancoCliente.getClientes().stream().filter(c -> c.getCpf().equals(cpf)).findFirst().orElse(null);
  }

  // Valida todos os dados do cliente
  private void validarCliente(Cliente cliente) {
    if (cliente.getNome() == null || cliente.getNome().isBlank()) {
      throw new IllegalArgumentException("Nome é obrigatório");
    }
    if (cliente.getEmail() == null || !EMAIL_PATTERN.matcher(cliente.getEmail()).matches()) {
      throw new IllegalArgumentException("Email inválido");
    }
    if (cliente.getSenha() == null || cliente.getSenha().length() < 8) {
      throw new IllegalArgumentException("Senha deve ter no mínimo 8 caracteres");
    }
    if (cliente.getCpf() == null || !CPF_PATTERN.matcher(cliente.getCpf()).matches()) {
      throw new IllegalArgumentException("CPF inválido");
    }
    if (cliente.getDataNascimento() == null || cliente.getDataNascimento().isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Data de nascimento inválida");
    }
    if (cliente.getTelefone() == null || !TELEFONE_PATTERN.matcher(cliente.getTelefone()).matches()) {
      throw new IllegalArgumentException("Telefone inválido");
    }
  }

}
