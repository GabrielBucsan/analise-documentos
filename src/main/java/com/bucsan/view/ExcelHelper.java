package com.bucsan.view;

import com.bucsan.analysis.AnalysisResult;
import com.bucsan.analysis.FileHelper;
import com.bucsan.analysis.GovDocument;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;

public class ExcelHelper {

    public void exportResultsAsXlsx(List<AnalysisResult> results, String directoryPath, String resultFileName) {
        Workbook workbook = generateXlsx(results);
        FileHelper fileHelper = new FileHelper();
        fileHelper.saveXlsxToFile(workbook, directoryPath, resultFileName);
    }

    private Workbook generateXlsx(List<AnalysisResult> results) {
        Workbook workbook = new XSSFWorkbook();
        List<String> errors = new ArrayList<>();

        for (AnalysisResult result : results) {
            Sheet sheet = workbook.createSheet(result.getFolderName());
            createTotalRows(result, sheet);
            createBlankRow(sheet);
            int totalColumns = createDocumentHeader(sheet, result.getExpressions());
            for(int i = 3; i < result.getFiles().size() + 3; i++) {
                GovDocument document = result.getFiles().get(i - 3);
                createDocumentResultRow(document, result.getExpressions(), sheet, i);
            }
            for (int i = 0; i < totalColumns; i++) {
                sheet.autoSizeColumn(i);
            }
            errors.addAll(result.getErrors());
        }

        generateTotalSheet(workbook, results);
        generateErrorSheet(workbook, errors);

        return workbook;
    }

    private void generateTotalSheet(Workbook workbook, List<AnalysisResult> results) {
        Sheet sheet = workbook.createSheet("Totais");
        int totalSearchedFiles = 0;
        int totalMatchedFiles = 0;
        Map<String, Integer> totalMatchesByExpression = new HashMap<>();

        for(AnalysisResult result : results) {
            totalSearchedFiles += result.getTotalFiles();
            totalMatchedFiles += result.getFilesContainingKeywords();
            List<String> expressions = result.getExpressions();
            for(String expression : expressions) {
                Integer expressionCount = totalMatchesByExpression.get(expression);
                if(expressionCount == null) {
                    expressionCount = 0;
                }
                for (GovDocument document : result.getFiles()) {
                    expressionCount += document.getExpressionCount(expression);
                }
                totalMatchesByExpression.put(expression, expressionCount);
            }
        }

        Row row = sheet.createRow(0);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Arquivos analisados: ");
        Cell cell2 = row.createCell(1);
        cell2.setCellValue(totalSearchedFiles);
        Row row2 = sheet.createRow(1);
        Cell cell3 = row2.createCell(0);
        cell3.setCellValue("Arquivos contendo pelo menos uma das expressões: ");
        Cell cell4 = row2.createCell(1);
        cell4.setCellValue(totalMatchedFiles);
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
        int i = 2;
        for (Map.Entry<String, Integer> entry : totalMatchesByExpression.entrySet()) {
            Row expressionRow = sheet.createRow(i);
            Cell expressionCell = expressionRow.createCell(0);
            expressionCell.setCellValue(entry.getKey());
            Cell countCell = expressionRow.createCell(1);
            countCell.setCellValue(entry.getValue());
            i += 1;
        }
    }

    private void generateErrorSheet(Workbook workbook, List<String> errors) {
        Sheet sheet = workbook.createSheet("Erros");

        for(int i = 0; i < errors.size(); i++) {
            Row row = sheet.createRow(i);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(errors.get(i));
        }
    }

    private void createTotalRows(AnalysisResult result, Sheet sheet) {
        Row row = sheet.createRow(0);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Arquivos analisados:");
        Cell cell2 = row.createCell(1);
        cell2.setCellValue(result.getTotalFiles());
        Cell cell3 = row.createCell(2);
        cell3.setCellValue("Arquivos contendo pelo menos uma das expressões:");
        Cell cell4 = row.createCell(3);
        cell4.setCellValue(result.getFilesContainingKeywords());
    }

    private void createBlankRow(Sheet sheet) {
        sheet.createRow(1);
    }

    private int createDocumentHeader(Sheet sheet, List<String> expressions) {
        Row row = sheet.createRow(2);

        List<Object> objects = new ArrayList<>();
        objects.add("Tipo de norma");
        objects.add("Nome da norma");
        objects.add("Página do DOU");
        objects.add("Data de publicação no DOU");
        objects.add("Órgão responsável pela publicação");
        objects.add("Ementa");
        objects.add("Arquivo");
        objects.addAll(expressions);

        for(int i = 0; i < objects.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(objects.get(i).toString());
        }

        return objects.size();
    }

    private void createDocumentResultRow(GovDocument document, List<String> expressions, Sheet sheet, int rowNumber) {
        Row row = sheet.createRow(rowNumber);

        List<Object> objects = new ArrayList<>();
        objects.add(document.getName());
        objects.add(document.getIdentifica());
        objects.add(document.getNumberPage());
        objects.add(document.getPubDate());
        objects.add(document.getArtCategory());
        objects.add(document.getEmenta());
        objects.add(document.getArquivo());

        for(String expression : expressions) {
            objects.add(document.getExpressionCount(expression));
        }

        for(int i = 0; i < objects.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(objects.get(i).toString());
        }
    }

}
