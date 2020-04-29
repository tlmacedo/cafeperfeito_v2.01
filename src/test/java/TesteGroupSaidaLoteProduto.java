import br.com.tlmacedo.cafeperfeito.model.dao.SaidaProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProdutoProduto;
import br.com.tlmacedo.cafeperfeito.service.ServiceUtilJSon;
import br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema;
import br.com.tlmacedo.nfe.model.vo.DetVO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.stream.Collectors;

public class TesteGroupSaidaLoteProduto {

    static Long nPed = 102L;
    static SaidaProduto pedido;
    static boolean imprimeLote = false;
    private static StringProperty xmlNFe = new SimpleStringProperty();


    public static void main(String[] args) throws Exception {
        new ServiceVariaveisSistema().getVariaveisSistema();
        setPedido(new SaidaProdutoDAO().getById(SaidaProduto.class, getnPed()));


        imprimePedido();
        setImprimeLote(false);

//        if (isImprimeLote()) {
//            getPedido().getSaidaProdutoProdutoList().stream()
//                    .forEach(saidaProdutoProduto -> {
//                        saidaProdutoProduto.descricaoProperty().setValue(
//                                String.format("%s  Lt[%s] val.:%s",
//                                        saidaProdutoProduto.descricaoProperty().getValue(),
//                                        saidaProdutoProduto.loteProperty().getValue(),
//                                        saidaProdutoProduto.dtValidadeProperty().getValue().format(DTF_DATA)
//                                ));
//                    });
//        }
        if (isImprimeLote())
            getPedido().getSaidaProdutoProdutoList().stream()
                    .collect(Collectors.groupingBy(SaidaProdutoProduto::getLote))
                    .forEach((s, saidaProdutoProdutos) -> {
                        DetVO detVO = new DetVO();

                        saidaProdutoProdutos.stream()
                                .forEach(saidaProdutoProduto -> {
                                    System.out.printf("\t%s\n", saidaProdutoProduto.toString());
                                });
                    });


//        getPedido().getSaidaProdutoProdutoList().stream()
//                .forEach(saidaProdutoProduto -> {
//                    saidaProdutoProduto.produtoProperty().getValue().descricaoProperty().setValue(
//                            String.format("%s  lt[%s] val.:%s",
//                                    saidaProdutoProduto.descricaoProperty().getValue(),
//                                    saidaProdutoProduto.loteProperty().getValue(),
//                                    saidaProdutoProduto.dtValidadeProperty().getValue().format(DTF_DATA))
//                    );
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

//        xmlNFeProperty().setValue(Nfe.getXml(getPedido()));
//        SaidaProdutoNfe nfeTemp = getPedido().getSaidaProdutoNfeList().stream().filter(saidaProdutoNfe -> !saidaProdutoNfe.isCancelada()).findFirst().orElse(null);
////        if (!saidaProdutoNfeProperty().getValue().equals(nfeTemp) && nfeTemp != null)
////            saidaProdutoNfeProperty().setValue(nfeTemp);
//        System.out.printf("xmlNFe:\n%s\n---------------------**********************\n\n", xmlNFeProperty().getValue());
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
