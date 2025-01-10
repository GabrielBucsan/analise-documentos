package com.bucsan;

import com.bucsan.analysis.AnalysisHelper;
import com.bucsan.analysis.AnalysisResult;
import com.bucsan.view.ExcelHelper;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String[] expressoesChave = new String[]{"aten..o b.sica", "aten..o prim.ria"};
        String directoryPath = "./src/main/resources";

        AnalysisHelper analysisHelper = new AnalysisHelper();
        List<AnalysisResult> results = analysisHelper.runAnalysis(directoryPath, expressoesChave);
        ExcelHelper excelHelper = new ExcelHelper();
        excelHelper.exportResultsAsXlsx(results);

        System.out.println("");
    }
}