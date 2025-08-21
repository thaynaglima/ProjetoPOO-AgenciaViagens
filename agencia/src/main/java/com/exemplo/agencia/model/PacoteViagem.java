package com.exemplo.agencia.model;

import java.math.BigDecimal;

public class PacoteViagem{
	private String id;
	private String pais;
	private String clima;
	private String descricao;
	private BigDecimal preco;
	private String imagemUrl;
	
	public PacoteViagem(String id, String pais, String clima, String descricao, BigDecimal preco) {
		this.id = id;
		this.pais = pais;
		this.clima = clima;
		this.descricao = descricao;
		this.preco = preco;
		this.imagemUrl = gerarImagemUrl(pais); // seta imagem automaticamente
	}

	public String getId() {
		return id;
	}

	public String getPais() {
		return pais;
	}

	public String getClima() {
		return clima;
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getImagemUrl() { 
		return imagemUrl; 
	}

	 // Método para associar imagens por país
    private String gerarImagemUrl(String pais) {
        switch (pais.toLowerCase()) {
            case "frança": return "https://images.unsplash.com/photo-1522098543979-ffc7f79d5b1b?w=400&h=300&fit=crop";
            case "brasil": return "https://images.unsplash.com/photo-1508672019048-805c876b67e2?w=400&h=300&fit=crop";
            case "egito": return "https://images.unsplash.com/photo-1536697243480-2d4a5a5e9f3e?w=400&h=300&fit=crop";
            case "eua": return "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=400&h=300&fit=crop";
            case "áfrica do sul": return "https://images.unsplash.com/photo-1518659526055-2b7d7cfb7a4b?w=400&h=300&fit=crop";
            case "itália": return "https://images.unsplash.com/photo-1506224773703-3b4a4f479a0c?w=400&h=300&fit=crop";
            case "canadá": return "https://images.unsplash.com/photo-1549924231-f129b911e442?w=400&h=300&fit=crop";
            case "japão": return "https://images.unsplash.com/photo-1556514767-8c9c23f6c46b?w=400&h=300&fit=crop";
            case "alemanha": return "https://images.unsplash.com/photo-1508596463-9c456f1a5e1b?w=400&h=300&fit=crop";
            case "méxico": return "https://images.unsplash.com/photo-1546549030-3c5c9e4b1781?w=400&h=300&fit=crop";
            default: return "https://images.unsplash.com/photo-1502602898536-47ad22581b52?w=400&h=300&fit=crop";
        }
    }
}