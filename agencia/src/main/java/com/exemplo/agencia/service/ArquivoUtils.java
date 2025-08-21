package com.exemplo.agencia.service; 

import java.nio.charset.StandardCharsets; 
import java.nio.file.*; 
import java.util.ArrayList; 
import java.util.List; 
import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.IOException; 

public class ArquivoUtils { 
    static String caminhoBase = "src/java/resources/"; 
    
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
}
