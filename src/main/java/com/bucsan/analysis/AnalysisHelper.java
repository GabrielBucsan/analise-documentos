package com.bucsan.analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class AnalysisHelper {

    public List<AnalysisResult> runAnalysis(String directoryPath, String[] expressoesChave) {
        AnalysisHelper analysisHelper = new AnalysisHelper();
        List<AnalysisResult> results = new ArrayList<>();
        Path dir = Paths.get(directoryPath);

        try {
            try (Stream<Path> paths = Files.list(dir)) {
                paths.filter(Files::isDirectory)
                        .forEach(folderPath -> results.add(analysisHelper.analyseFolder(folderPath, expressoesChave)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

    private AnalysisResult analyseFolder(Path folderPath, String[] expressoesChave) {
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
                            result.addError(file.getFileName() + " - " + e.getMessage());
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

    private boolean hasExpressionInText(String text, String[] expressoesChave) {

        for(String expressao : expressoesChave) {
            Pattern pattern = Pattern.compile(expressao.trim());
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
                return true;
            }
        }

        return false;
    }

}
