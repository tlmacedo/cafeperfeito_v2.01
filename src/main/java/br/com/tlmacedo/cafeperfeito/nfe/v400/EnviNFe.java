package br.com.tlmacedo.cafeperfeito.nfe.v400;

import br.inf.portalfiscal.xsd.nfe.enviNFe.TEnviNFe;
import br.inf.portalfiscal.xsd.nfe.enviNFe.TNFe;

public class EnviNFe {

    TEnviNFe tEnviNFe;
    TNFe tnFe;

    public EnviNFe(TNFe tnFe) {
        setTnFe(tnFe);
        //newE
    }

    /**
     * Begin Getters and Setters
     */
    public TEnviNFe gettEnviNFe() {
        return tEnviNFe;
    }

    public void settEnviNFe(TEnviNFe tEnviNFe) {
        this.tEnviNFe = tEnviNFe;
    }

    public TNFe getTnFe() {
        return tnFe;
    }

    public void setTnFe(TNFe tnFe) {
        this.tnFe = tnFe;
    }
    /**
     * END Getters and Setters
     */
}
