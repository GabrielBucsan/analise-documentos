package analysis;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class FileReader {

    public Document readXmlFile(String fileName) throws Exception {
        // Create a new DocumentBuilderFactory and DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Read the file into a String
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }

        // Convert the file content to XML
        String xmlContent = fileContent.toString();

        // Create a new input stream from the string content
        InputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes("UTF-8"));

        // Parse the content and return the Document object
        return builder.parse(inputStream);
    }

}
