package br.com.tlmacedo.cafeperfeito.service;

import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.cafeperfeito.nfe.v400.Nfe;
import br.inf.portalfiscal.xsd.nfe.consStatServ.TConsStatServ;
import br.inf.portalfiscal.xsd.nfe.enviNFe.TEnviNFe;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.FileNotFoundException;

import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

public class ServiceNfe {

    private ObjectProperty<SaidaProduto> saidaProduto = new SimpleObjectProperty<>();

    public ServiceNfe() throws FileNotFoundException {
        if (TCONFIG == null)
            new ServiceVariaveisSistema().getVariaveisSistema();
        //this.saidaProduto.setValue(saidaProduto);
    }

    public String gerarNovaNfe() {
        TEnviNFe tEnviNFe = new Nfe(getSaidaProduto()).gettEnviNFe();

        //return ServiceXmlUtil.objectToXml();
        return null;
    }

    public String getStatusServico() throws Exception {
        TConsStatServ consStatServ = new TConsStatServ();
        System.out.printf("tpAmb: [%s]\n", TCONFIG.getNfe().getTpAmb());
        consStatServ.setTpAmb(String.valueOf(TCONFIG.getNfe().getTpAmb()));
        consStatServ.setCUF(String.valueOf(TCONFIG.getInfLoja().getCUF()));
        consStatServ.setVersao(TCONFIG.getNfe().getVersao());
        consStatServ.setXServ("STATUS");
        String xml = ServiceXmlUtil.objectToXml(consStatServ);

        System.out.printf("xml: [%s]\n", xml);

        return xml;
    }


    /**
     * Begin Getters and Setters
     */

    public SaidaProduto getSaidaProduto() {
        return saidaProduto.get();
    }

    public ObjectProperty<SaidaProduto> saidaProdutoProperty() {
        return saidaProduto;
    }

    public void setSaidaProduto(SaidaProduto saidaProduto) {
        this.saidaProduto.set(saidaProduto);
    }
/**
 * END Getters and Setters
 */

}
