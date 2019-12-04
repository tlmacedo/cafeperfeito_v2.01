package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.nfe.service.NFeAutorizacao;
import javafx.beans.property.ObjectProperty;

import javax.xml.stream.XMLStreamException;
import java.rmi.RemoteException;

public class NFeXmlAutorizacao {

    private static ObjectProperty<LoadCertificadoA3> loadCertificadoA3;

    public static String getXmlAutorizacao(String xmlAssinado, LoadCertificadoA3 loadCertificadoA3) throws XMLStreamException, RemoteException {
        setLoadCertificadoA3(loadCertificadoA3);
        loadCertificadoA3Property().getValue().load();
        return new NFeAutorizacao(xmlAssinado).getXmlAutorizacaoNFe();
    }

    public static String getXmlAutorizacao(SaidaProduto saidaProduto) throws Exception {
        return new NFeAutorizacao(NFeXmlAssinar.getXmlAssinado(saidaProduto).first).getXmlAutorizacaoNFe();
    }

    private static void loadCertificado() {
        if (loadCertificadoA3Property().getValue() == null)
            loadCertificadoA3Property().setValue(new LoadCertificadoA3());

        while (loadCertificadoA3Property().getValue().load())
            loadCertificadoA3Property().getValue().load();
    }

    /**
     * Begin Getters and Setters
     */

    public static LoadCertificadoA3 getLoadCertificadoA3() {
        return loadCertificadoA3.get();
    }

    public static ObjectProperty<LoadCertificadoA3> loadCertificadoA3Property() {
        return loadCertificadoA3;
    }

    public static void setLoadCertificadoA3(LoadCertificadoA3 loadCertificadoA3) {
        NFeXmlAutorizacao.loadCertificadoA3.set(loadCertificadoA3);
    }

    /**
     * END Getters and Setters
     */
}
