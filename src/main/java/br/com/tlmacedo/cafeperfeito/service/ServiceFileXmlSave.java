package br.com.tlmacedo.cafeperfeito.service;

import br.inf.portalfiscal.xsd.nfe.enviNFe.TEnviNFe;
import br.inf.portalfiscal.xsd.nfe.enviNFe.TNFe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

public class ServiceFileXmlSave {

    private static FileWriter arqXml;
    private static String conteudoXml;

    public static boolean saveTEnviNFeToFile(TEnviNFe tEnviNFe) throws IOException {
        TNFe tnFe = tEnviNFe.getNFe().get(0);

        setArqXml(new FileWriter(new File(
                String.format("%s%s%s%s.xml",
                        System.getProperty("user.dir"),
                        TCONFIG.getPaths().getPathNFeSaveXmlOut().trim(),
                        tnFe.getInfNFe().getId(),
                        (tnFe.getSignature() != null) ? "-assinada" : "")
        )));
        return salvaArquivo();
    }

    private static boolean salvaArquivo() {
        try {
            getArqXml().write(getConteudoXml());
            getArqXml().close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Begin Getters and Setters
     */
    private static FileWriter getArqXml() {
        return arqXml;
    }

    private static void setArqXml(FileWriter arqXml) {
        ServiceFileXmlSave.arqXml = arqXml;
    }

    private static String getConteudoXml() {
        return conteudoXml;
    }

    private static void setConteudoXml(String conteudoXml) {
        ServiceFileXmlSave.conteudoXml = conteudoXml;
    }

    /**
     * END Getters and Setters
     */
}
