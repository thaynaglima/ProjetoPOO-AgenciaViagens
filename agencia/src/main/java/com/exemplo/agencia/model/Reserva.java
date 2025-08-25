package com.exemplo.agencia.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public class Reserva{
	@NotBlank
	@Pattern(regexp = "RES\\d+", message = "ID deve seguir o formato RES123")
	private String id;

	@NotBlank
	private String clienteNome;

	@Min(value = 1, message = "Pelo menos 1 pessoa na reserva")
	private int pessoasViagem;

	@NotBlank
	private String pacoteId;

	@NotNull
	private LocalDate dataReserva;
	
	@NotNull
	@FutureOrPresent(message = "A data de início deve ser igual ou posterior à data atual")
	private LocalDate dataInicio;

	@Min(value = 1, message = "Pelo menos 1 dia de reserva")
    private int quantidadeDias;
	
	 @NotNull
    private FormaPagamento formaPagamento;

    @NotNull
    private StatusReserva status;

    @NotNull @DecimalMin("0.00")
    private BigDecimal precoFinal = BigDecimal.ZERO;

    public enum FormaPagamento { CARTAOCREDITO, CARTAODEBITO, PIX, BOLETO }
    public enum StatusReserva { CONFIRMADA, PENDENTE, CANCELADA, FINALIZADA }

	public Reserva() { }

	public Reserva(String id, String clienteNome, int pessoasViagem, String pacoteId, LocalDate dataReserva,
			LocalDate dataInicio, int quantidadeDias, FormaPagamento formaPagamento, StatusReserva status, BigDecimal precoFinal) {
		this.id = id;
		this.clienteNome = clienteNome;
		this.pessoasViagem = pessoasViagem;
		this.pacoteId = pacoteId;
		this.dataReserva = dataReserva;

		if (dataInicio != null && dataReserva != null && dataInicio.isBefore(dataReserva)) {
            throw new IllegalArgumentException("A data de início não pode ser anterior à data da reserva");
        }
		this.dataInicio = dataInicio;
		this.quantidadeDias = quantidadeDias;
		this.formaPagamento = formaPagamento;
		this.status = status;
		this.precoFinal = Objects.requireNonNullElse(precoFinal, BigDecimal.ZERO);
	}

	public String getId() { 
		return id; }

    public void setId(String id) { 
		this.id = id; }

    public String getClienteNome() { 
		return clienteNome; }

    public void setClienteNome(String clienteNome) { 
		this.clienteNome = clienteNome; }

    public int getPessoasViagem() { 
		return pessoasViagem; }

    public void setPessoasViagem(int pessoasViagem) { 
		this.pessoasViagem = pessoasViagem; }

    public String getPacoteId() { 
		return pacoteId; }

    public void setPacoteId(String pacoteId) { 
		this.pacoteId = pacoteId; }

    public LocalDate getDataReserva() { 
		return dataReserva; }

    public void setDataReserva(LocalDate dataReserva) { 
		this.dataReserva = dataReserva; }

    public LocalDate getDataInicio() { 
		return dataInicio; }

    public void setDataInicio(LocalDate dataInicio) { 
		if (dataReserva != null && dataInicio != null && dataInicio.isBefore(dataReserva)) {
            throw new IllegalArgumentException("A data de início não pode ser anterior à data da reserva");
        }
        this.dataInicio = dataInicio;}

	public int getQuantidadeDias() {
		return quantidadeDias;
	}

	public void setQuantidadeDias(int quantidadeDias) {
		this.quantidadeDias = quantidadeDias;
	}

    public FormaPagamento getFormaPagamento() { 
		return formaPagamento; }

    public void setFormaPagamento(FormaPagamento formaPagamento) { 
		this.formaPagamento = formaPagamento; }

    public StatusReserva getStatus() { 
		return status; }
    public void setStatus(StatusReserva status) { 
		this.status = status; }

    public BigDecimal getPrecoFinal() { 
		return precoFinal; }

    public void setPrecoFinal(BigDecimal precoFinal) { 
		this.precoFinal = Objects.requireNonNullElse(precoFinal, BigDecimal.ZERO); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reserva)) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}