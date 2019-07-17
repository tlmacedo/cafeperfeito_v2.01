package br.com.tlmacedo.cafeperfeito.service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class ServiceXmlUtil {

    public static String FileXml4String(FileReader arquivo) {
        StringBuilder xml = new StringBuilder();
        BufferedReader in;
        try {
            in = new BufferedReader(arquivo);
            //in = new BufferedReader(new InputStreamReader(arquivo, "UTF-8"));
            String linha;
            while ((linha = in.readLine()) != null)
                xml.append(linha);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException("Ler Xml: " + e.getMessage());
        }
        return xml.toString();
    }


    public static <T> T xmlToObject(String xml, Class<T> classe) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(classe);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return unmarshaller.unmarshal(new StreamSource(new StringReader(xml)), classe).getValue();
    }
}
