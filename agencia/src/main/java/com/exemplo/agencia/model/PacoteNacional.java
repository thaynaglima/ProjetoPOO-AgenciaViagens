package com.exemplo.agencia.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PacoteNacional extends PacoteViagem {
    @NotBlank
    private String estadoOrigem;

    @NotNull
    private Transporte incluiTransporte;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$", message = "Cidade deve conter apenas letras")
    private String cidade;

    public enum Transporte { SIM, NAO }

    public PacoteNacional() { }

    
    public PacoteNacional(String id, String clima, String descricao, BigDecimal preco, 
                          String estadoOrigem, Transporte incluiTransporte, String cidade) {
        super(id, "Brasil", clima, descricao, preco);
        this.estadoOrigem = estadoOrigem;
        this.incluiTransporte = incluiTransporte;
        this.cidade = cidade;
    }

    public String getEstadoOrigem() {
        return estadoOrigem;
    }


    public void setEstadoOrigem(String estadoOrigem) {
        this.estadoOrigem = estadoOrigem;
    }


    public Transporte getIncluiTransporte() {
        return incluiTransporte;
    }


    public void setIncluiTransporte(Transporte incluiTransporte) {
        this.incluiTransporte = incluiTransporte;
    }


    public String getCidade() {
        return cidade;
    }


    public void setCidade(String cidade) {
        this.cidade = cidade;
    }


}