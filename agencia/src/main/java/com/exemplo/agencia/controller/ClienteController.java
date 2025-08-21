package com.exemplo.agencia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.exemplo.agencia.model.Cliente;

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
}

