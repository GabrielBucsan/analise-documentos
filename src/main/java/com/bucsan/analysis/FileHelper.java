package com.bucsan.analysis;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileHelper {

    public GovDocument readXmlFile(String fileName) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }

        String xmlContent = fileContent.toString();

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

                NodeList bodies = article.getElementsByTagName("body");
                for (int j = 0; j < bodies.getLength(); j++) {
                    Node bodyNode = bodies.item(j);

                    if (bodyNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element body = (Element) bodyNode;

                        govDocument.setIdentifica(body.getElementsByTagName("Identifica").item(0).getTextContent());
                        govDocument.setTexto(body.getElementsByTagName("Texto").item(0).getTextContent());
                    }
                }
            }
        }

        return govDocument;
    }

}
