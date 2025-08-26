package com.exemplo.agencia.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.exemplo.agencia.model.Cliente;
import com.exemplo.agencia.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @PostMapping("/salvar")
    public String salvarCliente(Cliente cliente) {
        // O Spring automaticamente converte os campos do form em um objeto Cliente
        System.out.println("Cliente recebido: " + cliente.getNome());
        // salvar no "banco"
        return "redirect:/clientes/listar";
    }

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar")
    @ResponseBody
    public String cadastrarCliente(
        @RequestParam String nome,
        @RequestParam String email,
        @RequestParam String senha,
        @RequestParam String cpf,
        @RequestParam String confirmPassword,
        @RequestParam boolean aceitarTermos,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNascimento,
        @RequestParam String telefone
    ) {
        // Validações básicas no Controller
    if (nome == null || nome.isBlank()) {
        return "Nome é obrigatório";
    }
    if (!email.matches("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$")) {
        return "Email inválido";
    }
    if (senha.length() < 8) {
        return "Senha deve ter no mínimo 8 caracteres";
    }
    if (!senha.equals(confirmPassword)) {
        return "As senhas não conferem";
    }
    if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
        return "CPF inválido";
    }
    if (dataNascimento == null || dataNascimento.isAfter(LocalDate.now())) {
        return "Data de nascimento inválida";
    }
    if (!telefone.matches("\\(\\d{2}\\) \\d{4,5}-\\d{4}")) {
        return "Telefone inválido";
    }
    if (!aceitarTermos) {
        return "Você deve aceitar os termos";
    }

    // Se passou nas validações básicas, delega para o service salvar
        Cliente cliente = new Cliente(nome,email,senha,cpf, dataNascimento, telefone);

        clienteService.salvarCliente(cliente);

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String senha, Model model) {
        boolean autenticado = clienteService.autenticar(email, senha);
        if (autenticado) {
            return "redirect:/clientes/perfil/" + clienteService.buscarPorEmail(email).getCpf();
        } else {
            model.addAttribute("erro", "Email ou senha inválidos!");
        return "login"; // volta para a página de login com mensagem de erro
        }
    }
    
     //buscar os dados do cliente
     @GetMapping("/perfil/{cpf}")
    public String identificarPerfil(@PathVariable String cpf, Model model){
    Cliente cliente = clienteService.buscarPorCpf(cpf);
    if (cliente == null) {
        // redireciona para uma página de erro ou login
        return "redirect:/login";
    }
    model.addAttribute("cliente", cliente);
    return "perfil";
}


    // Alterar Email
    @PostMapping("/{cpf}/alterar-email")
    @ResponseBody
    public String alterarEmail(
        @PathVariable String cpf,
        @RequestParam String novoEmail) {
        try {
            Cliente atualizado = clienteService.alterarEmail(cpf, novoEmail);
            return "Email alterado com sucesso! Novo email: " + atualizado.getEmail();
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        }
    }

    // Alterar Senha
    @PostMapping("/{cpf}/alterar-senha")
    @ResponseBody
    public String alterarSenha(
        @PathVariable String cpf,
        @RequestParam String novaSenha) {
        try {
            Cliente atualizado = clienteService.alterarSenha(cpf, novaSenha);
            return "Senha alterada com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        }
    }

}


