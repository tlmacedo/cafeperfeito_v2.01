package br.com.tlmacedo.cafeperfeito.nfe.v400;

import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.inf.portalfiscal.xsd.nfe.enviNFe.TEnviNFe;
import br.inf.portalfiscal.xsd.nfe.enviNFe.TNFe;

public class Nfe {

    TNFe tnFe;
    TEnviNFe tEnviNFe;
    SaidaProduto saidaProduto;

    public Nfe(SaidaProduto saidaProduto) {
        setSaidaProduto(saidaProduto);

        setTnFe(new TNFe());
        //ServiceVariaveisSistema.TCONFIG

        //getTnFe()
    }


    /**
     * Begin Getters and Setters
     */
    public TNFe getTnFe() {
        return tnFe;
    }

    public void setTnFe(TNFe tnFe) {
        this.tnFe = tnFe;
    }

    public TEnviNFe gettEnviNFe() {
        return tEnviNFe;
    }

    public void settEnviNFe(TEnviNFe tEnviNFe) {
        this.tEnviNFe = tEnviNFe;
    }

    public SaidaProduto getSaidaProduto() {
        return saidaProduto;
    }

    public void setSaidaProduto(SaidaProduto saidaProduto) {
        this.saidaProduto = saidaProduto;
    }
/**
 * END Getters and Setters
 */

}
