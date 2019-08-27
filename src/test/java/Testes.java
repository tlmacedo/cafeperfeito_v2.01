import br.com.tlmacedo.cafeperfeito.model.dao.ProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.Produto;
import br.com.tlmacedo.cafeperfeito.model.vo.ProdutoEstoque;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TreeItem;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class Testes {


    public static void main(String[] args) {

        try {
            FilteredList<Produto> produtoFilteredList = new FilteredList<>(FXCollections.observableArrayList(new ProdutoDAO().getAll(Produto.class, null, null, null, "descricao")));
            produtoFilteredList.setPredicate(produto -> {
                if (produto.descricaoProperty().getValue().toLowerCase().contains("su"))
                    return true;
                return false;
            });
            TreeItem<Produto> produtoTreeItem = new TreeItem<>();
            produtoFilteredList.forEach(
                    produto -> {
                        final int[] estq = {0};
                        TreeItem<Produto> prodTree = new TreeItem<>(produto);
                        produtoTreeItem.getChildren().add(prodTree);
                        produto.getProdutoEstoqueList().stream()
                                .filter(produtoEstoque -> produtoEstoque.qtdProperty().getValue() > 0)
                                .sorted(Comparator.comparing(ProdutoEstoque::getValidade))
                                .collect(Collectors.groupingBy(ProdutoEstoque::getLote,
                                        LinkedHashMap::new,
                                        Collectors.toList()))
                                .forEach((s, produtoEstoques) -> {
                                    System.out.printf("lote: [%s]\n", s);
                                });
//                        produto.getProdutoEstoqueList().stream()
//                                .filter(produtoEstoque -> produtoEstoque.qtdProperty().getValue() > 0)
//                                .sorted(Comparator.comparing(ProdutoEstoque::getValidade))
//                                .collect(Collectors.groupingBy(ProdutoEstoque::getLote))
//                                .forEach((s, produtoEstoques) -> {
//                                    System.out.printf("s: [%s]\n", s);
//                                    ProdutoEstoque estoque = new ProdutoEstoque();
//                                    estoque.qtdProperty().setValue(produtoEstoques.stream().collect(Collectors.summingInt(ProdutoEstoque::getQtd)));
//                                    estoque.loteProperty().setValue(s);
//                                    estoque.validadeProperty().setValue(produtoEstoques.stream().findFirst().orElse(null).validadeProperty().getValue());
//                                    estq[0] += estoque.qtdProperty().getValue();
//                                    prodTree.getChildren().add(new TreeItem<>(new Produto(estoque)));
//                                });
                        prodTree.getValue().setTblEstoque(estq[0]);
                    }
            );

            produtoTreeItem.getChildren().stream()
                    .forEach(produtoTreeItem1 -> {
                        System.out.printf("%s\t%s\t%s\t%s\t%s\n",
                                produtoTreeItem1.getValue().codigoProperty().getValue(),
                                produtoTreeItem1.getValue().descricaoProperty().getValue(),
                                produtoTreeItem1.getValue().precoVendaProperty().getValue().toString(),
                                //produtoTreeItem1.getValue().getTblEstoque_id(),
                                produtoTreeItem1.getValue().tblLoteProperty().getValue(),
                                produtoTreeItem1.getValue().tblValidadeProperty().getValue()

                        );
                        produtoTreeItem1.getChildren().stream()
                                .forEach(produtoTreeItem2 -> {
                                    System.out.printf("\t\t\t\t%s\t%s\t%s\n",
                                            produtoTreeItem2.getValue().getTblEstoque(),
                                            produtoTreeItem2.getValue().tblLoteProperty().getValue(),
                                            produtoTreeItem2.getValue().tblValidadeProperty().getValue()

                                    );
                                });
                    });


//            UndComercialProduto.getList().stream().forEach(tipoEndereco -> System.out.printf("%d-%s\n", tipoEndereco.getCod(), tipoEndereco));
//
//            boolean continuar = true;
//            while (continuar) {
//                System.out.printf("\nQual código vc vai pesquisar agora? ");
//                Integer val = Integer.parseInt(new Scanner(System.in).nextLine().replaceAll("\\D", ""));
//                if (val == 100)
//                    continuar = true;
//                System.out.printf("\nUnd Comércial do código(%d): [%s]", val, UndComercialProduto.toEnum(val));
//            }


//            new ProdutoDAO().getAll(Produto.class, null, null, null, "descricao").stream()
//                    .forEach(produto -> System.out.printf("%s [%s]\n", produto.descricaoProperty().get(),
//                            produto.getUnidadeComercial()));

//            new EnderecoDAO().getAll(Endereco.class, null, null, null, null).stream()
//                    .forEach(endereco -> System.out.printf("%s\n", endereco.logradouroProperty().get()));

//        new EmpresaDAO().getAll(Empresa.class, null, null, null, "razao").stream()
//                .forEach(empresa -> System.out.printf("%s (%s)\n", empresa.razaoProperty().get(), empresa.getEnderecoList()));
        } catch (
                Exception ex) {
            ex.printStackTrace();
        }
    }
}
