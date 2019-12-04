package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.cafeperfeito.service.ServiceUtilXml;
import br.com.tlmacedo.nfe.service.NFeRetAutorizacao;
import br.com.tlmacedo.nfe.service.NFeRetConsReciNfe;
import br.inf.portalfiscal.xsd.nfe.consReciNFe.TConsReciNFe;
import br.inf.portalfiscal.xsd.nfe.retEnviNFe.TRetEnviNFe;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.rmi.RemoteException;

public class NFeXmlRetAutorizacao {

    private String xmlNfe_RetAutorizacaoNFe;

    public static String getXmlRetAutorizacao() {
//        nFev400Property().getValue().setConsReciNFe(new NFeRetConsReciNfe(ServiceUtilXml.xmlToObject(xmlNFeAutorizacaoProperty().getValue(), TRetEnviNFe.class)));
//        TConsReciNFe tConsReciNFe = nFev400Property().getValue().getConsReciNFe().gettConsReciNFe();
//
//        nFev400Property().getValue().setRetAutorizacaoNFe(new NFeRetAutorizacao(ServiceUtilXml.objectToXml(tConsReciNFe)));
//        xmlNFeRetAutorizacaoProperty().setValue(nFev400Property().getValue().getRetAutorizacaoNFe().getXmlRetAutorizacaoNFe());
        return null;
    }

    public NFeXmlRetAutorizacao(String xmlNfe_Autorizacao) throws JAXBException, InterruptedException, XMLStreamException, RemoteException {

        TConsReciNFe tConsReciNFe =
                new NFeRetConsReciNfe(ServiceUtilXml.xmlToObject(xmlNfe_Autorizacao, TRetEnviNFe.class))
                        .gettConsReciNFe();
        NFeRetAutorizacao nFeRetAutorizacao = new NFeRetAutorizacao(ServiceUtilXml.objectToXml(tConsReciNFe));
        setXmlNfe_RetAutorizacaoNFe(nFeRetAutorizacao.getXmlRetAutorizacaoNFe());
    }


    /**
     * Begin Getters and Setters
     */

    public String getXmlNfe_RetAutorizacaoNFe() {
        return xmlNfe_RetAutorizacaoNFe;
    }

    public void setXmlNfe_RetAutorizacaoNFe(String xmlNfe_RetAutorizacaoNFe) {
        this.xmlNfe_RetAutorizacaoNFe = xmlNfe_RetAutorizacaoNFe;
    }

    /**
     * END Getters and Setters
     */
}
