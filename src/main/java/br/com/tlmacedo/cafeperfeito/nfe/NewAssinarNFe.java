package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.nfe.service.NFeAssinarXml;

public class NewAssinarNFe {

    private String xmlNfe_AssinadoNFe;

    public NewAssinarNFe(String xmlNfe, MeuCertificado meuCertificado) {

        NFeAssinarXml nFeAssinarXml = new NFeAssinarXml(xmlNfe, meuCertificado.getCertificates());
        setXmlNfe_AssinadoNFe(nFeAssinarXml.getXmlAssinado());
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
