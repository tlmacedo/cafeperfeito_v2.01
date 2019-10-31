package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.nfe.service.NFeAutorizacao;

import javax.xml.stream.XMLStreamException;
import java.rmi.RemoteException;

import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

public class NewAutorizacaoNFe {

    private String xmlNfe_AutorizacaoNFe;

    public NewAutorizacaoNFe(String xmlNfe_Assinado) throws XMLStreamException, RemoteException {

        NFeAutorizacao nFeAutorizacao = new NFeAutorizacao(xmlNfe_Assinado, TCONFIG.getNfe().getTpAmb());
    }

    /**
     * Begin Getters and Setters
     */
    public String getXmlNfe_AutorizacaoNFe() {
        return xmlNfe_AutorizacaoNFe;
    }

    public void setXmlNfe_AutorizacaoNFe(String xmlNfe_AutorizacaoNFe) {
        this.xmlNfe_AutorizacaoNFe = xmlNfe_AutorizacaoNFe;
    }

/**
 * END Getters and Setters
 */
}
