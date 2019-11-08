import br.com.tlmacedo.cafeperfeito.nfe.LoadCertificadoA3;
import br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Testes {

    ObjectProperty<LoadCertificadoA3> meuCertificado = new SimpleObjectProperty<>();

    private boolean errLoadCertificadoA3() {
        if (meuCertificadoProperty().getValue() == null)
            meuCertificadoProperty().setValue(new LoadCertificadoA3());

        try {
            meuCertificadoProperty().getValue().getCertificates().loadToken();
            meuCertificadoProperty().getValue().getCertificates().loadSocketDinamico();
        } catch (Exception ex) {
            setMeuCertificado(null);
//            ex.printStackTrace();
            System.out.printf("\nerro no certificado, deseja tentar novamente? ");
            int result = Integer.parseInt(new Scanner(System.in).nextLine().replaceAll("\\D", ""));
            if (result == 1)
                return true;
            else
                return false;
//
////            ServiceAlertMensagem alertMensagem = new ServiceAlertMensagem();
////            alertMensagem.setCabecalho("Certificado digital");
////            alertMensagem.setContentText("erro no certificado, deseja tentar novamente?");
//            if (alertMensagem.alertYesNo().get() == ButtonType.NO)
//                return false;
//            else
//                return true;
        }
        return false;
    }

    public LoadCertificadoA3 getMeuCertificado() {
        return meuCertificado.get();
    }

    public ObjectProperty<LoadCertificadoA3> meuCertificadoProperty() {
        return meuCertificado;
    }

    public void setMeuCertificado(LoadCertificadoA3 loadCertificadoA3) {
        this.meuCertificado.set(loadCertificadoA3);
    }

    public static void main(String[] args) throws FileNotFoundException {
        new ServiceVariaveisSistema().getVariaveisSistema();
        Testes testes = new Testes();
//        boolean errCertificado = true;
//        while (errCertificado) {
//            errCertificado = testes.errLoadCertificadoA3();
//        }
        while (testes.errLoadCertificadoA3())
            testes.errLoadCertificadoA3();

        if (testes.meuCertificadoProperty().getValue() == null) return;

        System.out.printf("certificado é meio válido \n");

        /**
         * *-**-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*---*--*-*-*-*-*-
         * *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
         */

//        try {
//            new ServiceVariaveisSistema().getVariaveisSistema();
//
//            System.out.printf("\nqual saida de produto vc que gerar NF? ");
//            Long nPed = Long.valueOf(new Scanner(System.in).nextLine().replaceAll("\\D", ""));
//
//            NewNotaFiscal notaFiscal = new NewNotaFiscal(new SaidaProdutoDAO().getById(SaidaProduto.class, nPed));
//
//            SaidaProdutoNfeDAO saidaProdutoNfeDAO = new SaidaProdutoNfeDAO();
//            SaidaProdutoNfe saidaProdutoNfe = notaFiscal.getNfe();
//
////            ServiceUtilJSon.printJsonFromObject(notaFiscal, String.format("Nota [%d]\n", nPed));
////
//            TEnviNFe tEnviNFe = new EnviNfe_v400(notaFiscal.getEnviNfeVO(), MY_ZONE_TIME).gettEnviNFe();
//
//            String xmlNFe = ServiceUtilXml.objectToXml(tEnviNFe);
//            System.out.printf("strEnviNFe:\n%s\n\n\n", xmlNFe);
//
//            MeuCertificado meuCertificado = new MeuCertificado();
//            meuCertificado.getCertificates().loadToken();
//            meuCertificado.getCertificates().loadSocketDinamico();
//
//            NFeAssinarXml assinarXml = new NFeAssinarXml(xmlNFe, meuCertificado.getCertificates());
//            String xmlNfe_Assinado = ServiceOutputXML.outputXML(assinarXml.getDocument());
//            System.out.printf("xmlNfe_Assinado:\n%s\n\n", xmlNfe_Assinado);
//
//            NFeAutorizacao NFeAutorizacao = new NFeAutorizacao(xmlNfe_Assinado, 2);
//            String xmlNfe_Autorizacao = NFeAutorizacao.getXmlAutorizacaoNFe();
//            System.out.printf("xmlNfe_Autorizacao:\n%s\n\n", xmlNfe_Autorizacao);
//
//            TConsReciNFe tConsReciNFe =
//                    new NFeRetConsReciNfe(ServiceUtilXml.xmlToObject(xmlNfe_Autorizacao, TRetEnviNFe.class))
//                            .gettConsReciNFe();
//            NFeRetAutorizacao NFeRetAutorizacao = new NFeRetAutorizacao(ServiceUtilXml.objectToXml(tConsReciNFe));
//            String xmlNfe_RetAutorizacao = NFeRetAutorizacao.getXmlRetAutorizacaoNFe();
//            System.out.printf("xmlNfe_RetAutorizacao:\n%s\n\n", xmlNfe_RetAutorizacao);
//
//            NFeProc nFeProc = new NFeProc(xmlNfe_Assinado, xmlNfe_RetAutorizacao);
//            nFeProc.setStrVersao(tConsReciNFe.getVersao());
//            nFeProc.setTnFe(ServiceUtilXml.xmlToObject(nFeProc.getStringTNFe(), TNFe.class));
//            nFeProc.settProtNFe(ServiceUtilXml.xmlToObject(nFeProc.getStringTProtNFe(), TProtNFe.class));
//            String xmlNFe_Proc = ServiceUtilXml.objectToXml(nFeProc.getResultNFeProc());
//            System.out.printf("xmlNfe_Proc:\n%s\n\n", xmlNFe_Proc);
//
//
//            try {
//                saidaProdutoNfeDAO.transactionBegin();
//                saidaProdutoNfe.setXmlAssinatura(
//                        new SerialBlob(String.format("%s%s",
//                                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
//                                xmlNfe_Assinado)
//                                .getBytes()
//                        )
//                );
//                saidaProdutoNfe.setXmlProtNfe(new SerialBlob(xmlNFe_Proc.getBytes()));
//                saidaProdutoNfe = saidaProdutoNfeDAO.setTransactionPersist(saidaProdutoNfe);
//                saidaProdutoNfeDAO.transactionCommit();
//            } catch (Exception e) {
//                saidaProdutoNfeDAO.transactionRollback();
//                e.printStackTrace();
//            }
//
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//

        /**
         * *-**-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*---*--*-*-*-*-*-
         * *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
         */
    }
}
