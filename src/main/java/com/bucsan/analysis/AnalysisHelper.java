package com.bucsan.analysis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class AnalysisHelper {

    public AnalysisResult analyseFolder(Path folderPath, String[] expressoesChave) {
        FileHelper reader = new FileHelper();
        AnalysisResult result = new AnalysisResult(folderPath.getFileName().toString(), expressoesChave);

        Path dir = Paths.get(folderPath.toUri());

        try {
            try (Stream<Path> paths = Files.list(dir)) {
                paths.filter(Files::isRegularFile)
                    .forEach(file -> {

                        GovDocument documento = null;
                        try {
                            documento = reader.readXmlFile(file);
                        } catch (Exception e) {
                            System.out.println("Não foi possível processar o arquivo " + file.getFileName());
                        }

                        if(documento != null) {
                            if(analyseFile(documento, expressoesChave)) {
                                result.countFileContainingKeyword(documento);
                            }
                        }

                        result.countFile();
                    });
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private boolean analyseFile(GovDocument documento, String[] expressoesChave) {
        AnalysisHelper analysisHelper = new AnalysisHelper();
        return analysisHelper.hasExpressionInText(documento.getTexto(), expressoesChave);
    }

    public boolean hasExpressionInText(String text, String[] expressoesChave) {

        for(String expressao : expressoesChave) {
            Pattern pattern = Pattern.compile(expressao);
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
                return true;
            }
        }

        return false;
    }

}
