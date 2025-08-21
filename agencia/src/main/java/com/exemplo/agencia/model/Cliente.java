package com.exemplo.agencia.model;

import java.time.LocalDate;

public class Cliente{
    private String nome;
    private String email;
    private String senha;
	private String cpf;
    private LocalDate dataNascimento;
    private String telefone;

    public Cliente(String nome, String email, String senha, String cpf, LocalDate dataNascimento, String telefone) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }
}