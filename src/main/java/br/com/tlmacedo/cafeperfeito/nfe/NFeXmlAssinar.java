package br.com.tlmacedo.cafeperfeito.nfe;

import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.nfe.service.NFeAssinarXml;
import com.google.gson.internal.Pair;
import javafx.beans.property.ObjectProperty;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;

public class NFeXmlAssinar {

    private static ObjectProperty<LoadCertificadoA3> loadCertificadoA3;

    public static Pair<String, LoadCertificadoA3> getXmlAssinado(String xmlNfe) throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {

        loadCertificado();

        return gerarPair(new NFeAssinarXml(xmlNfe, loadCertificadoA3Property().getValue().getCertificates()).getXmlAssinadoNFe());
    }

    public static Pair<String, LoadCertificadoA3> getXmlAssinado(SaidaProduto saidaProduto, LoadCertificadoA3 loadCertificadoA3) throws Exception {

        setLoadCertificadoA3(loadCertificadoA3);

        return gerarPair(new NFeAssinarXml(NFeXml.getXml(saidaProduto), loadCertificadoA3Property().getValue().getCertificates()).getXmlAssinadoNFe());
    }

    public static Pair<String, LoadCertificadoA3> getXmlAssinado(SaidaProduto saidaProduto) throws Exception {

        loadCertificado();

        return gerarPair(new NFeAssinarXml(NFeXml.getXml(saidaProduto), loadCertificadoA3Property().getValue().getCertificates()).getXmlAssinadoNFe());
    }

    private static void loadCertificado() {
        if (loadCertificadoA3Property().getValue() == null)
            loadCertificadoA3Property().setValue(new LoadCertificadoA3());

        while (loadCertificadoA3Property().getValue().load())
            loadCertificadoA3Property().getValue().load();
    }

    private static Pair<String, LoadCertificadoA3> gerarPair(String xml) {
        return new Pair(xml, loadCertificadoA3Property().getValue().getCertificates());
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
        NFeXmlAssinar.loadCertificadoA3.set(loadCertificadoA3);
    }

    /**
     * END Getters and Setters
     */
}
