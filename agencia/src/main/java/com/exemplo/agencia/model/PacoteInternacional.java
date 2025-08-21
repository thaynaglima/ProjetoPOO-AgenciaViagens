package com.exemplo.agencia.model;

import java.math.BigDecimal;

public class PacoteInternacional extends PacoteViagem {
  private String necessitaVisto;
  private String idioma;
  private String moedaLocal;

  // Construtor, getters e setters
  public PacoteInternacional(String id, String pais, String clima, String descricao, BigDecimal preco,
      String necessitaVisto, String idioma, String moedaLocal) {
    super(idioma, pais, clima, descricao, preco);

    this.necessitaVisto = necessitaVisto;
    this.idioma = idioma;
    this.moedaLocal = moedaLocal;
  }

  public String getNecessitaVisto() {
    return necessitaVisto;
  }

  public void setNecessitaVisto(String necessitaVisto) {
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