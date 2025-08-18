package model;

import java.math.BigDecimal;

public class PacoteViagem{
	private String id;
	private String pais;
	private String clima;
	private String descricao;
	private BigDecimal preco;
	
	public PacoteViagem(String id, String pais, String clima, String descricao, BigDecimal preco) {
		this.id = id;
		this.pais = pais;
		this.clima = clima;
		this.descricao = descricao;
		this.preco = preco;
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
	
}