import analysis.AnalysisHelper;

public class Main {
    public static void main(String[] args) {

        String[] expressoesChave = new String[]{"aten..o b.sica", "aten..o prim.ria"};
        AnalysisHelper analysisHelper = new AnalysisHelper();


        if(analysisHelper.analyseFile("1200601312.xml", expressoesChave)) {
            System.out.println("achou");
        } else {
            System.out.println("não achou");
        }

    }
}