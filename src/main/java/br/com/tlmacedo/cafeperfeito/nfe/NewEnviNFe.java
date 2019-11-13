package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.cafeperfeito.service.ServiceUtilXml;
import br.com.tlmacedo.nfe.v400.EnviNfe_v400;
import br.inf.portalfiscal.xsd.nfe.enviNFe.TEnviNFe;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.MY_ZONE_TIME;

public class NewEnviNFe {

    private SaidaProduto saidaProduto;
    private TEnviNFe tEnviNFe;
    private static String xmlNFe;

    public NewEnviNFe(SaidaProduto saidaProduto) throws Exception {
        setSaidaProduto(saidaProduto);

        NewNotaFiscal newNotaFiscal = new NewNotaFiscal();
        newNotaFiscal.setSaidaProduto(getSaidaProduto());
        newNotaFiscal.gerarNovaNotaFiscal();
        settEnviNFe(new EnviNfe_v400(newNotaFiscal.getEnviNfeVO(), MY_ZONE_TIME).gettEnviNFe());

        setXmlNFe(ServiceUtilXml.objectToXml(gettEnviNFe()));
    }

    /**
     * Begin and Getters and Setters
     */

    public SaidaProduto getSaidaProduto() {
        return saidaProduto;
    }

    public void setSaidaProduto(SaidaProduto saidaProduto) {
        this.saidaProduto = saidaProduto;
    }

    public TEnviNFe gettEnviNFe() {
        return tEnviNFe;
    }

    public void settEnviNFe(TEnviNFe tEnviNFe) {
        this.tEnviNFe = tEnviNFe;
    }

    public String getXmlNFe() {
        return xmlNFe;
    }

    public void setXmlNFe(String xmlNFe) {
        this.xmlNFe = xmlNFe;
    }

    /**
     * END and Getters and Setters
     */
}
