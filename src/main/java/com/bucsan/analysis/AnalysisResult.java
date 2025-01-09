package com.bucsan.analysis;

import java.util.ArrayList;
import java.util.List;

public class AnalysisResult {

    String folderName;
    int totalFiles = 0;
    int filesContainingKeywords = 0;
    List<GovDocument> files = new ArrayList<>();
    String[] expressoesChave;

    public AnalysisResult(String folderName, String[] expressoesChave) {
        this.folderName = folderName;
        this.expressoesChave = expressoesChave;
    }

    public void countFile() {
        totalFiles++;
    }

    public void countFileContainingKeyword(GovDocument document) {
        filesContainingKeywords++;
        files.add(document);
    }

    public void toXlsx() {

    }

}
