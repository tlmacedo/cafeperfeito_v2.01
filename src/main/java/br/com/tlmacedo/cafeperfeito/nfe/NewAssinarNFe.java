package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.nfe.service.NFeAssinarXml;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;

public class NewAssinarNFe {

    private String xmlNfe_AssinadoNFe;

    public NewAssinarNFe(String xmlNfe, LoadCertificadoA3 loadCertificadoA3) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {

        NFeAssinarXml nFeAssinarXml = new NFeAssinarXml(xmlNfe, loadCertificadoA3.getCertificates());
        setXmlNfe_AssinadoNFe(nFeAssinarXml.getXmlAssinadoNFe());
    }

    /**
     * Begin Getters and Setters
     */
    public String getXmlNfe_AssinadoNFe() {
        return xmlNfe_AssinadoNFe;
    }

    public void setXmlNfe_AssinadoNFe(String xmlNfe_AssinadoNFe) {
        this.xmlNfe_AssinadoNFe = xmlNfe_AssinadoNFe;
    }

/**
 * END Getters and Setters
 */
}
