package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.cafeperfeito.service.ServiceUtilXml;
import br.com.tlmacedo.nfe.service.NFeRetAutorizacao;
import br.com.tlmacedo.nfe.service.NFeRetConsReciNfe;
import br.inf.portalfiscal.xsd.nfe.consReciNFe.TConsReciNFe;
import br.inf.portalfiscal.xsd.nfe.retEnviNFe.TRetEnviNFe;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.rmi.RemoteException;

public class NewRetAutorizacaoNFe {

    private String xmlNfe_RetAutorizacaoNFe;

    public NewRetAutorizacaoNFe(String xmlNfe_Autorizacao) throws JAXBException, InterruptedException, XMLStreamException, RemoteException {

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
