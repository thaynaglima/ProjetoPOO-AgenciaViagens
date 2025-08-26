package com.exemplo.agencia.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemplo.agencia.model.Cliente;
import com.exemplo.agencia.util.ArquivoUtils;
import com.exemplo.agencia.util.BancoDeDadosSimulado;
import com.exemplo.agencia.util.SessaoUsuario;

@Service
public class ClienteService {
  private final BancoDeDadosSimulado bancoCliente;
  @Autowired
  private SessaoUsuario sessaoUsuario;

  // Constantes para validação
  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@[\\w-]+(\\.[\\w-]+)+$");
  private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");
  private static final Pattern TELEFONE_PATTERN = Pattern.compile("\\d{10,11}"); // 10 ou 11 dígitos

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

    try {
       // Salva o estado completo da lista no arquivo
        bancoCliente.salvarDados(); // sobrescreve clientes.txt
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Erro ao adicionar cliente no arquivo.", e);
    }
  }

  public boolean autenticar(String email, String senha) {
   Cliente cliente = bancoCliente.getClientes()
        .stream()
        .filter(c -> c.getEmail().equals(email) && c.getSenha().equals(senha))
        .findFirst()
        .orElse(null);

    if (cliente != null) {
            sessaoUsuario.logar(cliente);
            return true;
        }
    return false;
  }

  public void logout() {
    sessaoUsuario.logout();
  }

  public Cliente getClienteLogado() {
    return sessaoUsuario.getClienteAtual();
  }

  // buscar por cpf do cliente *
  public Cliente buscarPorCpf(String cpf) {
    return bancoCliente.getClientes().stream().filter(c -> c.getCpf().equals(cpf)).findFirst().orElse(null);
  }

  // buscar por cpf do cliente *
  public Cliente buscarPorEmail(String email) {
    return bancoCliente.getClientes().stream().filter(c -> c.getEmail().equals(email)).findFirst().orElse(null);
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

   // Normaliza CPF (remove tudo que não é número)
    String cpfNumeros = cliente.getCpf().replaceAll("\\D", "");
    if (!CPF_PATTERN.matcher(cpfNumeros).matches()) {
        throw new IllegalArgumentException("CPF inválido");
    }
    cliente.setCpf(cpfNumeros);


    if (cliente.getDataNascimento() == null || cliente.getDataNascimento().isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Data de nascimento inválida");
    }
   String telefoneNumeros = cliente.getTelefone().replaceAll("\\D", "");
    if (!TELEFONE_PATTERN.matcher(telefoneNumeros).matches()) {
        throw new IllegalArgumentException("Telefone inválido");
    }
    cliente.setTelefone(telefoneNumeros);
  }
   private void salvarClientesNoArquivo() {
    List<String[]> dados = new ArrayList<>();
    for (Cliente c : bancoCliente.getClientes()) {
        dados.add(new String[] {
            c.getNome(),
            c.getEmail(),
            c.getCpf(),
            c.getTelefone(),
            c.getSenha()
        });
    }

    try {
        ArquivoUtils.salvarArquivo("clientes.txt", dados);
    } catch (IOException e) {
        throw new RuntimeException("Erro ao salvar clientes no arquivo", e);
    }
}
//alterar email
  public Cliente alterarEmail(String cpf, String novoEmail){
    Cliente cliente = buscarPorCpf(cpf);


    if(cliente == null){
      throw new IllegalArgumentException("Cliente não encontrado!");
    }
    if(novoEmail == null || !EMAIL_PATTERN.matcher(novoEmail).matches()){
      throw new IllegalArgumentException("Email invalido");
    }

    cliente.setEmail(novoEmail);
    salvarClientesNoArquivo();
    return cliente;
    
  }

 /**
 * @param cpf
 * @param novaSenha
 * @return
 */
public Cliente alterarSenha(String cpf, String novaSenha){
    Cliente cliente = buscarPorCpf(cpf);

    if(cliente == null){
      throw new IllegalArgumentException("Cliente não encontrado!");
    }
    if(novaSenha == null || novaSenha.length() < 8){
      throw new IllegalArgumentException("Senha deve ter no mínimo 8 caracteres");
    }
    cliente.setSenha(novaSenha);
    salvarClientesNoArquivo();
    return cliente;
  }

}
