import br.com.tlmacedo.cafeperfeito.model.dao.SaidaProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProdutoProduto;
import br.com.tlmacedo.cafeperfeito.service.ServiceUtilJSon;
import br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema;
import br.com.tlmacedo.nfe.model.vo.DetVO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        getPedido().getSaidaProdutoProdutoList().stream()
                .forEach(saidaProdutoProduto -> {
                    System.out.printf("000%s\n", saidaProdutoProduto);
                });
        System.out.printf("\n\n\n\n");
        getPedido().getSaidaProdutoProdutoList().stream()
                .sorted(Comparator.comparing(SaidaProdutoProduto::getProdId)
                        .thenComparing(SaidaProdutoProduto::getLote))
                .forEach(saidaProdutoProduto -> {
                    System.out.printf("001%s\n", saidaProdutoProduto);
                });
        System.out.printf("\n\n\n\n");


        if (getPedido().getSaidaProdutoProdutoList().size() > 0) {
            List<DetVO> detVOList = new ArrayList<>();
            getPedido().getSaidaProdutoProdutoList().stream()
                    .sorted(Comparator.comparing(SaidaProdutoProduto::getProdId)
                            .thenComparing(SaidaProdutoProduto::getLote))
                    .collect(Collectors.groupingBy(SaidaProdutoProduto::getProdId))
                    .forEach((aLong, saidaProdutoProdutos) -> {
//                        if (isImprimirLote())
                        for (SaidaProdutoProduto saidProd : saidaProdutoProdutos) {
                            System.out.printf("Prod: %s\n", saidProd);
//                                setDetVO(new DetVO());
//                                new_ProdVO(saidProd);
                        }
//                            saidaProdutoProdutos.stream()
//                                    .sorted(Comparator.comparing(SaidaProdutoProduto::getLote))
//                                    .collect(Collectors.groupingBy(SaidaProdutoProduto::getLote))
//                                    .forEach((s, saidaProdutoProdutos1) -> {
//                                        DetVO detVO = new DetVO();
//                                        detVO.setnItem(String.valueOf(nItem[0]++));
//                                        detVO.setProd(new_ProdVO(saidaProdutoProdutos1.get(0)));
//                                        detVO.getProd().setxProd(
//                                                String.format("%s Lt[%s] val.:%s",
//                                                        detVO.getProd().getxProd().substring(0,
//                                                                (detVO.getProd().getxProd().length() - (20 + s.length()))),
//                                                        s,
//                                                        saidaProdutoProdutos1.get(0).dtValidadeProperty().getValue().format(DTF_DATA)
//                                                )
//                                        );
//                                        for (int i = 1; i < saidaProdutoProdutos1.size(); i++) {
//                                            if (detVO[0].getProd().getCFOP().substring(1)
//                                                    .equals(saidaProdutoProdutos1.get(i).codigoCFOPProperty().getValue().getCod())) {
//                                                add_ProdVO(detVO[0].getProd(), saidaProdutoProdutos1.get(i));
//                                            } else {
//                                                detVO[0] = new DetVO();
//                                                detVO[0].setnItem(String.valueOf(nItem[0]++));
//                                                detVO[0].setProd(new_ProdVO(saidaProdutoProdutos1.get(i)));
//                                                detVO[0].getProd().setxProd(
//                                                        String.format("%s Lt[%s] val.:%s",
//                                                                detVO[0].getProd().getxProd().substring(0,
//                                                                        (detVO[0].getProd().getxProd().length() - (20 + s.length()))),
//                                                                s,
//                                                                saidaProdutoProdutos1.get(i).dtValidadeProperty().getValue().format(DTF_DATA)
//                                                        )
//                                                );
//                                            }
//                                        }
//                                    });
                    });
//            totalVO.getIcmsTot().setvNF(null);
        }


//        getPedido().getSaidaProdutoProdutoList().stream()
//                .sorted(comparator)
//                .collect(Collectors.groupingBy(SaidaProdutoProduto::getProdId))
//                .forEach((aLong, saidaProdutoProdutos) -> {
//                    saidaProdutoProdutos.stream()
//                            .collect(Collectors.groupingBy(SaidaProdutoProduto::getLote))
//                            .forEach((s, saidaProdutoProdutos1) -> {
//                                for (SaidaProdutoProduto saidaProd : saidaProdutoProdutos1) {
//                                    if (isImprimeLote()) {
//                                        SaidaProdutoProduto myProd = new SaidaProdutoProduto();
//                                        myProd = saidaProd;
//                                        myProd.descricaoProperty().setValue(
//                                                String.format("%s  Lt[%s] val.:%s",
//                                                        myProd.descricaoProperty().getValue(),
//                                                        myProd.loteProperty().getValue(),
//                                                        myProd.dtValidadeProperty().getValue().format(DTF_DATA)
//                                                ));
//                                        System.out.printf("%s\n", myProd);
//                                    } else {
//                                        System.out.printf("ainda em manutenção!!!");
//                                    }
//
//                                }
//                            });
//                });

        System.out.printf("\n\n\n\n");
        getPedido().getSaidaProdutoProdutoList().stream()
                .forEach(saidaProdutoProduto -> {
                    System.out.printf("002%s\n", saidaProdutoProduto);
                });


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
