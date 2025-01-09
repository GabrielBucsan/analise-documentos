package com.bucsan.analysis;

import java.util.ArrayList;
import java.util.List;

public class AnalysisResult {

    int totalFiles = 0;
    int filesContainingKeywords = 0;
    List<String> fileNames = new ArrayList<>();
    String[] expressoesChave;

    public AnalysisResult(String[] expressoesChave) {
        this.expressoesChave = expressoesChave;
    }

    public void countFile() {
        totalFiles++;
    }

    public void countFileContainingKeyword(String fileName) {
        filesContainingKeywords++;
        fileNames.add(fileName);
    }

    public void printResult() {
        System.out.println("==============================================");
        System.out.println("Pesquisa concluída");
        System.out.println("Expressões utilizadas na busca: ");
        for(String expressao : expressoesChave) {
            System.out.println("     - " + expressao);
        }
        System.out.println("Arquivos analisados: " + totalFiles);
        System.out.println("Arquivos que contém pelo menos uma das expressões pesquisadas: " + filesContainingKeywords);
        for(String fileName : fileNames) {
            System.out.println("     - " + fileName);
        }
        System.out.println("==============================================");
    }

}
