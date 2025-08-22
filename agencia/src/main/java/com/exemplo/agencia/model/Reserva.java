package com.exemplo.agencia.model;

import java.time.LocalDate;

import java.math.BigDecimal;

public class Reserva{
	private String id;
	private String clienteNome;
	private int pessoasViagem;
	private String pacoteId;
	private LocalDate dataReserva;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private String formaPagamento;
	private String status;
	private BigDecimal precoFinal;

	public Reserva(String id, String clienteNome, int pessoasViagem, String pacoteId, LocalDate dataReserva,
			LocalDate dataInicio, LocalDate dataFim, String formaPagamento, String status, BigDecimal precoFinal) {
		this.id = id;
		this.clienteNome = clienteNome;
		this.pessoasViagem = pessoasViagem;
		this.pacoteId = pacoteId;
		this.dataReserva = dataReserva;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.formaPagamento = formaPagamento;
		this.status = status;
		this.precoFinal = precoFinal;
	}

	public String getId() {
		return id;
	}

	public String getClienteNome() {
		return clienteNome;
	}
	public int getPessoasViagem() {
		return pessoasViagem;
	}

	public void setPessoasViagem(int pessoasViagem) {
		this.pessoasViagem = pessoasViagem;
	}

	public String getPacoteId() {
		return pacoteId;
	}
	public void setPacoteId(String pacoteId) {
		this.pacoteId = pacoteId;
	}
	public LocalDate getDataReserva() {
		return dataReserva;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public BigDecimal getPrecoFinal() {
		return precoFinal;
	}

	public void setPrecoFinal(BigDecimal precoFinal) {
		this.precoFinal = precoFinal;
	}


}