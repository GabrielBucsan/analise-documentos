package com.bucsan.analysis;

import org.apache.poi.ss.usermodel.Workbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class FileHelper {

    public GovDocument readXmlFile(Path file) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }

        String xmlContent = fileContent.toString().replace("</Identifica>\"","\"");

        InputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.ISO_8859_1));

        Document document = builder.parse(inputStream);
        document.getDocumentElement().normalize();

        GovDocument govDocument = new GovDocument();

        NodeList articles = document.getElementsByTagName("article");

        for (int i = 0; i < articles.getLength(); i++) {
            Node articleNode = articles.item(i);

            if (articleNode.getNodeType() == Node.ELEMENT_NODE) {
                Element article = (Element) articleNode;

                govDocument.setNumberPage(article.getAttribute("numberPage"));
                govDocument.setPubName(article.getAttribute("pubName"));
                govDocument.setName(article.getAttribute("name"));
                govDocument.setArtType(article.getAttribute("artType"));
                govDocument.setPubDate(article.getAttribute("pubDate"));
                govDocument.setArtCategory(article.getAttribute("artCategory"));
                govDocument.setArquivo(file.toString());

                NodeList bodies = article.getElementsByTagName("body");
                for (int j = 0; j < bodies.getLength(); j++) {
                    Node bodyNode = bodies.item(j);

                    if (bodyNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element body = (Element) bodyNode;

                        govDocument.setIdentifica(body.getElementsByTagName("Identifica").item(0).getTextContent());
                        govDocument.setTexto(body.getElementsByTagName("Texto").item(0).getTextContent());
                        govDocument.setEmenta(body.getElementsByTagName("Ementa").item(0).getTextContent());
                    }
                }
            }
        }

        return govDocument;
    }

    public void saveXlsxToFile(Workbook workbook) {
        try (FileOutputStream fileOut = new FileOutputStream("./src/out/resultado.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveExpressions(String expressions, String directoryPath) {
        try (FileWriter writer = new FileWriter("searchData.sav")) {
            writer.write(expressions + System.lineSeparator());
            writer.write(directoryPath + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadDirectoryPath() {
        return loadSavedConfig(2);
    }

    public String loadExpressions() {
        return loadSavedConfig(1);
    }

    private String loadSavedConfig(int line) {
        try (BufferedReader reader = new BufferedReader(new FileReader("searchData.sav"))) {
            String linha;
            int linhaAtual = 1;
            while ((linha = reader.readLine()) != null) {
                if(linhaAtual == line) {
                    return linha;
                }
                linhaAtual++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
