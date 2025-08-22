package com.exemplo.agencia.model;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidade de domínio para o cliente do sistema.
 * Observação: senha em texto puro é apenas para fins didáticos.
 */
public class Cliente {

    @NotBlank
    private String nome;

    @NotBlank @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "A senha deve ter ao menos 8 caracteres")
    private String senha;

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @Past
    private LocalDate dataNascimento;

    @NotBlank
    // Aceita números, +, espaço, parênteses e hífen (simplificado)
    @Pattern(regexp = "\\\\d{11}", message = "Telefone deve conter 11 dígitos numéricos")
    private String telefone;

    public Cliente() { }

    public Cliente(String nome, String email, String senha, String cpf, LocalDate dataNascimento, String telefone) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
    }

    public String getNome() { 
        return nome; }

    public void setNome(String nome) { 
        this.nome = nome; }

    public String getEmail() { 
        return email; }

    public void setEmail(String email) { 
        this.email = email; }

    public String getSenha() { 
        return senha; }

    public void setSenha(String senha) { 
        this.senha = senha; }

    public String getCpf() { 
        return cpf; }

    public void setCpf(String cpf) { 
        this.cpf = cpf; }

    public LocalDate getDataNascimento() { 
        return dataNascimento; }

    public void setDataNascimento(LocalDate dataNascimento) { 
        this.dataNascimento = dataNascimento; }

    public String getTelefone() { 
        return telefone; }

    public void setTelefone(String telefone) { 
        this.telefone = telefone; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
