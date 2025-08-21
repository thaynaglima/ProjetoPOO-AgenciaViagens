package com.exemplo.agencia.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping("/teste")
  public String teste() {
    return "API funcionando!";
  }
}
