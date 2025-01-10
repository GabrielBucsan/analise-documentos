package com.bucsan.view;

import com.bucsan.analysis.AnalysisHelper;
import com.bucsan.analysis.AnalysisResult;
import com.bucsan.analysis.FileHelper;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.util.List;

public class ApplicationGUI {

    AnalysisHelper analysisHelper = new AnalysisHelper();
    FileHelper fileHelper = new FileHelper();

    public void startGui() {
        JFrame frame = createMainFrame();
        GridBagConstraints gbc = createLayout();
        JTextArea searchField = createSearchField(frame, gbc);
        JTextField directoryField = createDirectoryField(frame, gbc);
        createDirectoryButton(gbc, frame, directoryField);
        createExecutionButton(gbc, frame, searchField, directoryField);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createExecutionButton(GridBagConstraints gbc, JFrame frame, JTextArea searchField, JTextField directoryField) {
        JButton executeButton = new JButton("Executar");
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(executeButton, gbc);

        executeButton.addActionListener(e -> {
            String searchExpression = searchField.getText();
            String directoryPath = directoryField.getText();

            if (searchExpression.isEmpty() || directoryPath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                String[] expressoesChave = searchExpression.split(",");
                List<AnalysisResult> results = analysisHelper.runAnalysis(directoryPath, expressoesChave);
                ExcelHelper excelHelper = new ExcelHelper();
                excelHelper.exportResultsAsXlsx(results, "./");
                fileHelper.clearErros(directoryPath);
                fileHelper.saveExpressions(searchExpression, directoryPath);
                fileHelper.saveErrorsToFile(results);
            }
        });
    }

    private void createDirectoryButton(GridBagConstraints gbc, JFrame frame, JTextField directoryField) {
        JButton browseButton = new JButton("Procurar");
        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(browseButton, gbc);

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                directoryField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
    }

    private static GridBagConstraints createLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private JFrame createMainFrame() {
        JFrame frame = new JFrame("Busca de expressões");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 300);
        frame.setLayout(new GridBagLayout());
        return frame;
    }

    private JTextArea createSearchField(JFrame frame, GridBagConstraints gbc) {
        JLabel searchLabel = new JLabel("Expressões para pesquisa (separados por vírgula):");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(searchLabel, gbc);

        JTextArea searchField = new JTextArea(5, 30);
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchField.setLineWrap(true);
        searchField.setWrapStyleWord(true);
        frame.add(searchField, gbc);

        String loadedExpressions = fileHelper.loadExpressions();
        if(loadedExpressions != null) {
            searchField.setText(loadedExpressions);
        }

        return searchField;
    }

    private JTextField createDirectoryField(JFrame frame, GridBagConstraints gbc) {
        JLabel directoryLabel = new JLabel("Selecionar diretório:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(directoryLabel, gbc);

        JTextField directoryField = new JTextField(20);
        directoryField.setEditable(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(directoryField, gbc);

        String loadedDirectoryPath = fileHelper.loadDirectoryPath();
        if(loadedDirectoryPath != null) {
            directoryField.setText(loadedDirectoryPath);
        }

        return directoryField;
    }

}
