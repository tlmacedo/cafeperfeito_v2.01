package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.cafeperfeito.service.ServiceUtilXml;
import br.com.tlmacedo.nfe.service.NFeProc;
import br.inf.portalfiscal.xsd.nfe.consReciNFe.TConsReciNFe;
import br.inf.portalfiscal.xsd.nfe.procNFe.TNFe;
import br.inf.portalfiscal.xsd.nfe.procNFe.TProtNFe;

import javax.xml.bind.JAXBException;

public class NewProcNFe {

    private String xmlNfe_ProcNFe;

    public NewProcNFe(String xmlNfe_Assinado, String xmlNfe_RetAutorizacao, TConsReciNFe tConsReciNFe) throws JAXBException {

        NFeProc nFeProc = new NFeProc(xmlNfe_Assinado, xmlNfe_RetAutorizacao);
        nFeProc.setStrVersao(tConsReciNFe.getVersao());
        nFeProc.setTnFe(ServiceUtilXml.xmlToObject(nFeProc.getStringTNFe(), TNFe.class));
        nFeProc.settProtNFe(ServiceUtilXml.xmlToObject(nFeProc.getStringTProtNFe(), TProtNFe.class));
        setXmlNfe_ProcNFe(ServiceUtilXml.objectToXml(nFeProc.getResultNFeProc()));
    }

    /**
     * Begin Getters and Setters
     */

    public String getXmlNfe_ProcNFe() {
        return xmlNfe_ProcNFe;
    }

    public void setXmlNfe_ProcNFe(String xmlNfe_ProcNFe) {
        this.xmlNfe_ProcNFe = xmlNfe_ProcNFe;
    }

    /**
     * END Getters and Setters
     */
}
