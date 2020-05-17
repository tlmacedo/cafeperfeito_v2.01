package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.cafeperfeito.service.ServiceUtilXml;
import br.com.tlmacedo.nfe.model.vo.EnviNfeVO;
import br.com.tlmacedo.nfe.v400.EnviNfe_v400;
import br.inf.portalfiscal.xsd.nfe.enviNFe.TEnviNFe;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.MY_ZONE_TIME;

public class NFeXml {

    private static SaidaProduto saidaProduto;
    private static TEnviNFe tEnviNFe;
    private static EnviNfeVO enviNfeVO;
    private static String xmlNFe;

    public static String getXml(EnviNfeVO enviNfeVO) throws Exception {
        setEnviNfeVO(enviNfeVO);
        return ServiceUtilXml.objectToXml(gerartEnviNFe());
    }

    public static String getXml(SaidaProduto saidaProduto) throws Exception {
        return getXml(NotaFiscal.getEnviNfeVO(saidaProduto));
    }

    private static TEnviNFe gerartEnviNFe() throws Exception {
        return new EnviNfe_v400(getEnviNfeVO(), MY_ZONE_TIME).gettEnviNFe();
    }

    /**
     * Begin and Getters and Setters
     */

    public static SaidaProduto getSaidaProduto() {
        return saidaProduto;
    }

    public static void setSaidaProduto(SaidaProduto saidaProduto) {
        NFeXml.saidaProduto = saidaProduto;
    }

    public static TEnviNFe gettEnviNFe() {
        return tEnviNFe;
    }

    public static void settEnviNFe(TEnviNFe tEnviNFe) {
        NFeXml.tEnviNFe = tEnviNFe;
    }

    public static EnviNfeVO getEnviNfeVO() {
        return enviNfeVO;
    }

    public static void setEnviNfeVO(EnviNfeVO enviNfeVO) {
        NFeXml.enviNfeVO = enviNfeVO;
    }

    public static String getXmlNFe() {
        return xmlNFe;
    }

    public static void setXmlNFe(String xmlNFe) {
        NFeXml.xmlNFe = xmlNFe;
    }

    /**
     * END and Getters and Setters
     */
}
