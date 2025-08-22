package com.exemplo.agencia.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.exemplo.agencia.model.Cliente;
import com.exemplo.agencia.model.PacoteInternacional;
import com.exemplo.agencia.model.PacoteNacional;
import com.exemplo.agencia.model.PacoteViagem;
import com.exemplo.agencia.model.Reserva;
import com.exemplo.agencia.model.PacoteInternacional.Visto;
import com.exemplo.agencia.model.PacoteNacional.Transporte;
import com.exemplo.agencia.model.Reserva.FormaPagamento;
import com.exemplo.agencia.model.Reserva.StatusReserva;

public class BancoDeDadosSimulado {
    List<Cliente> clientes;
    List<PacoteViagem> pacotes;
    List<Reserva> reservas;

    public BancoDeDadosSimulado() {
        carregarDados();
    }

    private void carregarDados() {
        // Converter arrays de strings em objetos
        try {
            // Clientes
            this.clientes = ArquivoUtils.lerArquivo("clientes.txt")
                    .stream()
                    .map(this::stringParaCliente)
                    .collect(Collectors.toList());
            // Pacotes
            this.pacotes = ArquivoUtils.lerArquivo("pacotes.txt")
                    .stream()
                    .map(this::stringParaPacote)
                    .collect(Collectors.toList());
            // Reservas
            this.reservas = ArquivoUtils.lerArquivo("reservas.txt")
                    .stream()
                    .map(this::stringParaReserva)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao carregar dados dos arquivos!", e);
        }
    }

    public void salvarDados() throws IOException {
        List<String[]> dadosClientes = clientes.stream()
                .map(this::clienteParaString)
                .collect(Collectors.toList());
        ArquivoUtils.salvarArquivo("clientes.txt", dadosClientes);

        List<String[]> dadosPacotes = pacotes.stream()
                .map(this::pacoteParaString)
                .collect(Collectors.toList());
        ArquivoUtils.salvarArquivo("pacotes.txt", dadosPacotes);

        List<String[]> dadosReservas = reservas.stream()
                .map(this::reservaParaString)
                .collect(Collectors.toList());
        ArquivoUtils.salvarArquivo("reservas.txt", dadosReservas);
    }

    // 🔹 Converte uma linha do arquivo em um objeto Cliente
    public Cliente stringParaCliente(String[] dados) {
        return new Cliente(dados[0], dados[1], dados[2], dados[3], LocalDate.parse(dados[4]), dados[5]);
    }

    // 🔹 Converte Cliente em String[] para salvar no arquivo
    public String[] clienteParaString(Cliente cliente) {
        return new String[] {
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getSenha(),
                cliente.getCpf(),
                cliente.getDataNascimento().toString(), // yyyy-MM-dd
                cliente.getTelefone() };
    }

    // 🔹 Converte uma linha do arquivo em um objeto Pacote
    public PacoteViagem stringParaPacote(String[] dados) {
        if (dados[1].equals("Brasil"))
            return new PacoteNacional(dados[0], dados[1], dados[2], dados[3],
                    BigDecimal.valueOf(Double.parseDouble(dados[4])), dados[5], Transporte.valueOf(dados[6]), dados[7]);
        else
            return new PacoteInternacional(dados[0], dados[1], dados[2], dados[3],
                    BigDecimal.valueOf(Double.parseDouble(dados[4])), Visto.valueOf(dados[5]), dados[6], dados[7]);
    };

    // 🔹 Converte Pacote em String[] para salvar no arquivo
    public String[] pacoteParaString(PacoteViagem pacote) {
        if (pacote.getPais().equals("Brasil"))
            return new String[] {
                    pacote.getId(), pacote.getPais(),
                    pacote.getClima(), pacote.getDescricao(),
                    pacote.getPreco().toString(), ((PacoteNacional) pacote).getEstadoOrigem(),
                    ((PacoteNacional) pacote).getIncluiTransporte().toString(), ((PacoteNacional) pacote).getCidade()};
        else
            return new String[] {
                    pacote.getId(), pacote.getPais(),
                    pacote.getClima(), pacote.getDescricao(),
                    pacote.getPreco().toString(), ((PacoteInternacional) pacote).getNecessitaVisto().toString(),
                    ((PacoteInternacional) pacote).getIdioma(), ((PacoteInternacional) pacote).getMoedaLocal()
            };
    }

    // 🔹 Converte uma linha do arquivo em um objeto Reserva
    public Reserva stringParaReserva(String[] dados) {
        return new Reserva(dados[0], dados[1], Integer.parseInt(dados[2]), dados[3],
                LocalDate.parse(dados[4]), LocalDate.parse(dados[5]), Integer.parseInt(dados[6]),
                FormaPagamento.valueOf(dados[7]), StatusReserva.valueOf(dados[7]), BigDecimal.valueOf(Double.parseDouble(dados[9])));
    }

    // 🔹 Converte Pacote em String[] para salvar no arquivo
    public String[] reservaParaString(Reserva reserva) {
        return new String[] {
                reserva.getId(), reserva.getClienteNome(),
                "" + reserva.getPessoasViagem(),
                reserva.getPacoteId(), reserva.getDataReserva().toString(),
                reserva.getDataInicio().toString(), "" + reserva.getQuantidadeDias(),
                reserva.getFormaPagamento().toString(), reserva.getStatus().toString(), reserva.getPrecoFinal().toString()
        };
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<PacoteViagem> getPacotes() {
        return pacotes;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }
}