package com.exemplo.agencia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginasController {
  @GetMapping("/")
  public String index(){
    return "index"; // carrega index.html
  }

  @GetMapping("/pacotes")
  public String pacotes(){
    return "pacotes"; // carrega pacotes.html
  }

  @GetMapping("/detalhes-pacote")
  public String detalhesPacote(){
    return "detalhes-pacote"; // carrega detalhes-pacote.html
  }

  @GetMapping("/historico-reservas")
  public String historicoReservas(){
    return "historico-reservas"; // carrega historico-reservas.html
  }

  @GetMapping("/login")
  public String login(){
    return "login"; // carrega login.html
  }

  @GetMapping("/perfil")
  public String perfil(){
    return "perfil"; // carrega perfil.html
  }

  @GetMapping("/reserva")
  public String reserva(){
    return "reserva"; // carrega reserva.html
  }
}
