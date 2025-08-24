package com.exemplo.agencia.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PacoteInternacional extends PacoteViagem {

  @NotNull
  private Visto necessitaVisto;

  @NotBlank
  private String idioma;

  @NotBlank
  private String moedaLocal;


  public enum Visto { SIM, NAO }

  public PacoteInternacional() { }


  // Construtor, getters e setters
  public PacoteInternacional(String id, String pais, String clima, String descricao, BigDecimal preco,
      Visto necessitaVisto, String idioma, String moedaLocal) {
    super(idioma, pais, clima, descricao, preco);

    this.necessitaVisto = necessitaVisto;
    this.idioma = idioma;
    this.moedaLocal = moedaLocal;
  }

  @Override
  public PacoteViagem getClonePreco(BigDecimal novoPreco){
    return new PacoteInternacional(getId(), getPais(), getClima(), getDescricao(), novoPreco,
                                   getNecessitaVisto(), getIdioma(), getMoedaLocal());
  }

  public Visto getNecessitaVisto() {
    return necessitaVisto;
  }

  public void setNecessitaVisto(Visto necessitaVisto) {
    this.necessitaVisto = necessitaVisto;
  }

  public String getIdioma() {
    return idioma;
  }

  public void setIdioma(String idioma) {
    this.idioma = idioma;
  }

  public String getMoedaLocal() {
    return moedaLocal;
  }

  public void setMoedaLocal(String moedaLocal) {
    this.moedaLocal = moedaLocal;
  }

}