package com.exemplo.agencia.model;

import java.math.BigDecimal;

public class PacoteNacional extends PacoteViagem {
    private String estadoOrigem;
    private String incluiTransporte;
    private String cidade;

    
    public PacoteNacional(String id, String pais, String clima, String descricao, BigDecimal preco, String estadoOrigem, String incluiTransporte, String cidade) {
        super(cidade, pais, clima, descricao, preco);
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


    public String getIncluiTransporte() {
        return incluiTransporte;
    }


    public void setIncluiTransporte(String incluiTransporte) {
        this.incluiTransporte = incluiTransporte;
    }


    public String getCidade() {
        return cidade;
    }


    public void setCidade(String cidade) {
        this.cidade = cidade;
    }


}