package com.bucsan.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalysisHelper {

    public boolean analyseFile(GovDocument documento, String[] expressoesChave) {
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
