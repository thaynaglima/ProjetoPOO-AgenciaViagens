package com.exemplo.agencia.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

@Service
public class ArquivoUtils {
    static String caminhoBase = "src/main/resources/templates/";

    public static List<String[]> lerArquivo(String nomeArquivo) throws IOException {
        Path arquivo = Paths.get(caminhoBase + nomeArquivo);

        List<String[]> linhas = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(arquivo, StandardCharsets.UTF_8)) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                linhas.add(campos);
            }
        }
        return linhas;
    }

    // 🔹 Sobrescreve todo o arquivo com nova lista de linhas
    public static void salvarArquivo(String nomeArquivo, List<String[]> dados) throws IOException {
        Path arquivo = Paths.get(caminhoBase + nomeArquivo);

        try (BufferedWriter bw = Files.newBufferedWriter(arquivo, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String[] campos : dados) {
                bw.write(String.join(";", campos));
                bw.newLine();
            }
        }
    }
    // Acrescenta uma nova linha ao arquivo
    public static void adicionarLinha(String nomeArquivo, String[] dados) throws IOException{
        Path path = Paths.get(caminhoBase + nomeArquivo);

        try(BufferedWriter bw = Files.newBufferedWriter(path,StandardCharsets.UTF_8, 
                                                        StandardOpenOption.CREATE, 
                                                        StandardOpenOption.APPEND)){
            bw.write(String.join(";",dados));
            bw.newLine();
        }
    }

}
