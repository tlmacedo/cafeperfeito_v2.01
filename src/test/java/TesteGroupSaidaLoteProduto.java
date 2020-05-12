import br.com.tlmacedo.cafeperfeito.model.dao.SaidaProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProdutoNfe;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProdutoProduto;
import br.com.tlmacedo.cafeperfeito.nfe.Nfe;
import br.com.tlmacedo.cafeperfeito.service.ServiceUtilJSon;
import br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Comparator;

public class TesteGroupSaidaLoteProduto {

    static Long nPed = 85L;
    //    static Long nPed = 102L;
    static SaidaProduto pedido;
    static boolean imprimeLote = false;
    private static StringProperty xmlNFe = new SimpleStringProperty();


    public static void main(String[] args) throws Exception {
        new ServiceVariaveisSistema().getVariaveisSistema();
        setPedido(new SaidaProdutoDAO().getById(SaidaProduto.class, getnPed()));


        imprimePedido();
        setImprimeLote(true);


        Comparator<SaidaProdutoProduto> comparator = Comparator.comparing(saidaProdutoProduto -> saidaProdutoProduto.getProduto().getId());
        comparator = comparator.thenComparing(Comparator.comparing(saidaProdutoProduto -> saidaProdutoProduto.getLote()));

//        getPedido().getSaidaProdutoProdutoList().stream()
//                .forEach(saidaProdutoProduto -> {
//                    System.out.printf("000%s\n", saidaProdutoProduto);
//                });
//        System.out.printf("\n\n\n\n");
//        getPedido().getSaidaProdutoProdutoList().stream()
//                .sorted(Comparator.comparing(SaidaProdutoProduto::getProdId)
//                        .thenComparing(SaidaProdutoProduto::getLote))
//                .forEach(saidaProdutoProduto -> {
//                    System.out.printf("001%s\n", saidaProdutoProduto);
//                });
//        System.out.printf("\n\n\n\n");
//
//
//        System.out.printf("\n\n\n\n");
//        getPedido().getSaidaProdutoProdutoList().stream()
//                .forEach(saidaProdutoProduto -> {
//                    System.out.printf("002%s\n", saidaProdutoProduto);
//                });


        xmlNFe_gerar();
    }

    private static void imprimePedido() {
        System.out.printf("\niniciando ped[%d]", getnPed().intValue());
        System.out.printf("\ncarregando...");
        ServiceUtilJSon.printJsonFromObject(getPedido(), String.format("nPed: [%d]", getnPed().intValue()));
        System.out.printf("\nfinalizando!!!");
        System.out.printf("\n\n");
    }


    private static void xmlNFe_gerar() throws Exception {

        xmlNFeProperty().setValue(Nfe.gerarNotaFiscal(getPedido()));
        SaidaProdutoNfe nfeTemp = getPedido().getSaidaProdutoNfeList().stream().filter(saidaProdutoNfe -> !saidaProdutoNfe.isCancelada()).findFirst().orElse(null);
//        if (!saidaProdutoNfeProperty().getValue().equals(nfeTemp) && nfeTemp != null)
//            saidaProdutoNfeProperty().setValue(nfeTemp);
        System.out.printf("xmlNFe:\n%s\n---------------------**********************\n\n", xmlNFeProperty().getValue());
    }


    public static Long getnPed() {
        return nPed;
    }

    public static void setnPed(Long nPed) {
        TesteGroupSaidaLoteProduto.nPed = nPed;
    }

    public static SaidaProduto getPedido() {
        return pedido;
    }

    public static void setPedido(SaidaProduto pedido) {
        TesteGroupSaidaLoteProduto.pedido = pedido;
    }

    public static boolean isImprimeLote() {
        return imprimeLote;
    }

    public static void setImprimeLote(boolean imprimeLote) {
        TesteGroupSaidaLoteProduto.imprimeLote = imprimeLote;
    }

    public static String getXmlNFe() {
        return xmlNFe.get();
    }

    public static StringProperty xmlNFeProperty() {
        return xmlNFe;
    }

    public static void setXmlNFe(String xmlNFe) {
        TesteGroupSaidaLoteProduto.xmlNFe.set(xmlNFe);
    }
}
