package analysis;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalysisHelper {

    public boolean analyseFile(String fileName, String[] expressoesChave) {
        FileHelper reader = new FileHelper();
        AnalysisHelper analysisHelper = new AnalysisHelper();
        GovDocument documento = null;
        try {
            documento = reader.readXmlFile(new File("").getAbsoluteFile() + "/src/resources/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(documento == null) {
            return false;
        }

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
