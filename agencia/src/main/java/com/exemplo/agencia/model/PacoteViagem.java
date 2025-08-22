package com.exemplo.agencia.model;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public abstract	class PacoteViagem{
	@NotBlank
	private String id;

	@NotBlank
	private String pais;

	@NotBlank
	private String clima;

	@NotBlank
	private String descricao;

	@NotNull
    @DecimalMin(value = "0.00", inclusive = true, message = "Preço não pode ser negativo")
	private BigDecimal preco;
	private String imagemUrl;
	
	public PacoteViagem() { }
	
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

	public void setId(String id) { 
		this.id = id; }

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) { 
		this.pais = pais; }

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) { 
		this.clima = clima; }

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) { 
		this.descricao = descricao; }

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getImagemUrl() { 
		return (imagemUrl == null || imagemUrl.isBlank()) ? gerarImagemUrl(this.pais) : imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) { 
		this.imagemUrl = imagemUrl; }

	private String gerarImagemUrl(String pais) {
        if (pais == null) return "https://static.todamateria.com.br/upload/pa/is/paisagem-natural-og.jpg";
        switch (pais.toLowerCase()) {
            case "frança": return "https://enoestilo.com.br/wp-content/uploads/2014/06/paris-france-revista-enoestilo.jpg";
            case "brasil": return "https://mundodeviagens.com/wp-content/uploads/2017/01/riodejaneiro.jpg";
            case "egito": return "https://excursy.net/dicas-viagem/wp-content/uploads/2019/12/1-2.jpg";
            case "eua": return "https://zerandomundo.com/wp-content/uploads/2024/10/Melhores-cidades-dos-EUA-para-turismo-New-York-1024x576.webp";
            case "áfrica do sul": return "https://www.tmf-group.com/globalassets/images/news-and-insights/articles--pr/944x540-country-landscape/incorporation-in-south-africa.jpg";
            case "itália": return "https://cdn.americachip.com/wp-content/uploads/2022/12/Italia.png";
            case "canadá": return "https://comoviaja.com.br/wp-content/uploads/Toronto-e-CN-Tower-de-noite-Foto-Ontario-Travel-Divulga%C3%A7%C3%A3o-1024x682.jpg";
            case "japão": return "https://pipolltravel.com.br/wp-content/uploads/2023/03/fuji-mountain.webp";
            case "alemanha": return "https://images.travelclass.tur.br/uploads/2020/12/rothenburg-germany.jpg";
            case "méxico": return "https://images.unsplash.com/photo-1546549030-3c5c9e4b1781?w=400&h=300&fit=crop";
            default: return "https://cdn.prod.website-files.com/6712e729148886be5f355687/67160d7aadab88ed905c0285_Cidadaos-portugueses-necessitam-de-visto-para-o-Mexico.jpeg";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PacoteViagem)) return false;
        PacoteViagem that = (PacoteViagem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}