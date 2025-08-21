package com.exemplo.agencia.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
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
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNascimento,
        @RequestParam String telefone
    ) {
        Cliente cliente = new Cliente(nome,email,senha,cpf, dataNascimento, telefone);

        clienteService.salvarCliente(cliente);

        return "Cliente cadastrado com sucesso!";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam String email, @RequestParam String senha) {
        boolean autenticado = clienteService.autenticar(email, senha);
        if (autenticado) {
            return "Login realizado com sucesso!";
        } else {
            return "Email ou senha inválidos!";
        }
    }
}

