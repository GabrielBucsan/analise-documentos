package com.bucsan.view;

import com.bucsan.analysis.AnalysisResult;
import com.bucsan.utils.FileHelper;
import com.bucsan.analysis.GovDocument;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {

    public void exportResultsAsXlsx(List<AnalysisResult> results, String directoryPath, String resultFileName) {
        Workbook workbook = generateXlsx(results);
        FileHelper fileHelper = new FileHelper();
        fileHelper.saveXlsxToFile(workbook, directoryPath, resultFileName);
    }

    private Workbook generateXlsx(List<AnalysisResult> results) {
        Workbook workbook = new XSSFWorkbook();
        List<String> errors = new ArrayList<>();

        generateTotalSheet(workbook, results);
        generateMonthSheets(results, workbook, errors);
        generateErrorSheet(workbook, errors);

        return workbook;
    }



    private void generateMonthSheets(List<AnalysisResult> results, Workbook workbook, List<String> errors) {
        for (AnalysisResult result : results) {
            Sheet sheet = workbook.createSheet(result.getFolderName());
            printResultOnSheet(result, sheet);
            errors.addAll(result.getErrors());
        }
    }

    private void printResultOnSheet(AnalysisResult result, Sheet sheet) {
        Integer row = 0;
        row = createTotalColumns(result, sheet, row);
        row = createBlankRow(sheet, row);
        int totalColumns = createDocumentHeader(sheet, result.getSearchExpressions(), row++);
        for(int i = 0; i < result.getFiles().size(); i++) {
            GovDocument document = result.getFiles().get(i);
            row = createDocumentResultRow(document, result.getSearchExpressions(), sheet, row);
        }
        for (int i = 0; i < totalColumns; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void generateTotalSheet(Workbook workbook, List<AnalysisResult> results) {
        Sheet sheet = workbook.createSheet("Totais");
        AnalysisResult totalResult = AnalysisResult.totalizeResults(results);
        printResultOnSheet(totalResult, sheet);
        createHTMLFiles(totalResult);
    }

    private void createHTMLFiles(AnalysisResult result) {
        FileHelper helper = new FileHelper();
        helper.clearHTMLFiles(result.getFolderName());
        for(GovDocument document : result.getFiles()) {
            helper.createViewFile(result.getFolderName(), document.getIdentifica(), document.getTexto());
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

    private Integer createTotalColumns(AnalysisResult result, Sheet sheet, Integer line) {
        Row row = sheet.createRow(line++);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Arquivos analisados:");
        Cell cell2 = row.createCell(1);
        cell2.setCellValue(result.getTotalFiles());

        Row row2 = sheet.createRow(line++);
        Cell cell3 = row2.createCell(0);
        cell3.setCellValue("Arquivos contendo pelo menos uma das expressões:");
        Cell cell4 = row2.createCell(1);
        cell4.setCellValue(result.getFilesContainingKeywords());

        Row row3 = sheet.createRow(line++);
        Cell cell5 = row3.createCell(0);
        cell5.setCellValue("Arquivos com o campo Ementa vazio ou em branco:");
        Cell cell6 = row3.createCell(1);
        cell6.setCellValue(result.getFilesWithoutEmenta());

        for(String expression : result.getSearchExpressions()) {
            Row expressionRow = sheet.createRow(line++);
            Cell expressionCell = expressionRow.createCell(0);
            expressionCell.setCellValue(expression);
            Cell countCell = expressionRow.createCell(1);
            countCell.setCellValue(result.getExpressionCount(expression));
        }

        return line;
    }

    private Integer createBlankRow(Sheet sheet, Integer row) {
        sheet.createRow(row++);
        return row;
    }

    private int createDocumentHeader(Sheet sheet, List<String> expressions, Integer line) {
        Row row = sheet.createRow(line);

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

        sheet.createFreezePane(0, line + 1);

        return objects.size();
    }

    private Integer createDocumentResultRow(GovDocument document, List<String> expressions, Sheet sheet, Integer rowNumber) {
        Row row = sheet.createRow(rowNumber++);

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

        return rowNumber;
    }

}
