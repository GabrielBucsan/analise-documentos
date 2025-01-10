package com.bucsan;

import com.bucsan.analysis.AnalysisHelper;
import com.bucsan.analysis.AnalysisResult;
import com.bucsan.analysis.FileHelper;
import com.bucsan.analysis.GovDocument;
import com.bucsan.view.ExcelHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        String[] expressoesChave = new String[]{"aten..o b.sica", "aten..o prim.ria"};
        AnalysisHelper analysisHelper = new AnalysisHelper();
        ExcelHelper excelHelper = new ExcelHelper();
        Path dir = Paths.get("./src/main/resources");
        List<AnalysisResult> results = new ArrayList<>();

        try {
            try (Stream<Path> paths = Files.list(dir)) {
                paths.filter(Files::isDirectory)
                        .forEach(folderPath -> results.add(analysisHelper.analyseFolder(folderPath, expressoesChave)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        excelHelper.exportResultsAsXlsx(results);

    }
}