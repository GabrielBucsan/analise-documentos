package com.bucsan.analysis;

import java.util.ArrayList;
import java.util.List;

public class AnalysisResult {

    String folderName;
    int totalFiles = 0;
    int filesContainingKeywords = 0;
    List<GovDocument> files = new ArrayList<>();
    String[] expressoesChave;
    List<String> errors = new ArrayList<>();

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

    public void addError(String error) {
        this.errors.add(error);
    }

    public String getFolderName() {
        return this.folderName;
    }

    public List<GovDocument> getFiles() {
        return this.files;
    }

    public int getTotalFiles() {
        return this.totalFiles;
    }

    public int getFilesContainingKeywords() {
        return this.filesContainingKeywords;
    }

    public List<String> getErrors() {
        return errors;
    }

}
