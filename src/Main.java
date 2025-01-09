import analysis.AnalysisHelper;
import analysis.AnalysisResult;
import analysis.FileHelper;
import analysis.GovDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        String[] expressoesChave = new String[]{"aten..o b.sica", "aten..o prim.ria"};
        AnalysisHelper analysisHelper = new AnalysisHelper();
        FileHelper reader = new FileHelper();
        AnalysisResult result = new AnalysisResult(expressoesChave);

        Path dir = Paths.get("./src/resources");

        try {
            try (Stream<Path> paths = Files.list(dir)) {
                paths.filter(Files::isRegularFile)
                .forEach(file -> {

                    GovDocument documento = null;
                    try {
                        documento = reader.readXmlFile(new File("").getAbsoluteFile() + "/src/resources/" + file.getFileName().toString());
                    } catch (Exception e) {
                        System.out.println("N�o foi poss�vel processar o arquivo " + file.getFileName());
                    }

                    if(documento != null) {
                        if(analysisHelper.analyseFile(documento, expressoesChave)) {
                            result.countFileContainingKeyword(file.getFileName().toString());
                        }
                    }

                    result.countFile();
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        result.printResult();

    }
}