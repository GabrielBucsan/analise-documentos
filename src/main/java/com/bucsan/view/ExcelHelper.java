package com.bucsan.view;

import com.bucsan.analysis.AnalysisResult;
import com.bucsan.analysis.FileHelper;
import com.bucsan.analysis.GovDocument;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {

    public void exportResultsAsXlsx(List<AnalysisResult> results, String directoryPath) {
        Workbook workbook = new XSSFWorkbook();

        for (AnalysisResult result : results) {
            Sheet sheet = workbook.createSheet(result.getFolderName());
            createTotalRows(result, sheet);
            createBlankRow(sheet);
            createDocumentHeader(sheet);
            for(int i = 3; i < result.getFiles().size() + 3; i++) {
                GovDocument document = result.getFiles().get(i - 3);
                createDocumentResultRow(document, sheet, i);
            }
        }

        FileHelper fileHelper = new FileHelper();
        fileHelper.saveXlsxToFile(workbook, directoryPath);
    }

    private void createTotalRows(AnalysisResult result, Sheet sheet) {
        Row row = sheet.createRow(0);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Arquivos analisados:");
        Cell cell2 = row.createCell(1);
        cell2.setCellValue(result.getTotalFiles());
        Cell cell3 = row.createCell(2);
        cell3.setCellValue("Arquivos contendo as expressões:");
        Cell cell4 = row.createCell(3);
        cell4.setCellValue(result.getFilesContainingKeywords());
    }

    private void createBlankRow(Sheet sheet) {
        sheet.createRow(1);
    }

    private void createDocumentHeader(Sheet sheet) {
        Row row = sheet.createRow(2);

        List<Object> objects = new ArrayList<>();
        objects.add("Tipo de norma");
        objects.add("Nome da norma");
        objects.add("Página do DOU");
        objects.add("Seção do DOU");
        objects.add("Data de publicação no DOU");
        objects.add("Órgão responsável pela publicação");
        objects.add("Ementa");
        objects.add("Arquivo");

        for(int i = 0; i < objects.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(objects.get(i).toString());
        }
    }

    private void createDocumentResultRow(GovDocument document, Sheet sheet, int rowNumber) {
        Row row = sheet.createRow(rowNumber);

        List<Object> objects = new ArrayList<>();
        objects.add(document.getName());
        objects.add(document.getIdentifica());
        objects.add(document.getNumberPage());
        objects.add(document.getPubName());
        objects.add(document.getPubDate());
        objects.add(document.getArtCategory());
        objects.add(document.getEmenta());
        objects.add(document.getArquivo());

        for(int i = 0; i < objects.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(objects.get(i).toString());
        }
    }

}
