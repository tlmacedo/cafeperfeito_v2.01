import br.com.tlmacedo.cafeperfeito.model.vo.Recebimento;
import br.com.tlmacedo.cafeperfeito.service.ServiceValidarDado;

import java.util.Scanner;

public class Testes {


    public static void main(String[] args) {

        try {
            boolean continua = true;


            while (continua) {
                System.out.printf("\nQual número a ser gerado: ");
                String entrada = new Scanner(System.in).nextLine();

                if (entrada.equals("exit")) {
                    continua = false;
                    continue;
                }

//                new RecebimentoDAO().getAll(Recebimento.class, String.format("dtCadastro BETWEEN '%s' AND '%s'",
//                        LocalDate.now().atTime(0,0,0),
//                        LocalDate.now().atTime(23,59,59)), "dtCadastro DESC").stream()
//                        .forEach(System.out::println);
                String numeroGerado = "";
                if (entrada.equals(""))
                    numeroGerado = ServiceValidarDado.gerarCodigoCafePerfeito(Recebimento.class);
                else
                    numeroGerado = ServiceValidarDado.gerarCodigoCafePerfeito(entrada);

                System.out.printf("\nnúmero gerado foi: %s", numeroGerado);
//                String validacao = null;
//                boolean result = true;
//                while (result) {
//                    System.out.printf("\nnúmero a ser validado é: ");
//                    validacao = new Scanner(System.in).nextLine();
//                    result = !ServiceValidarDado.isCodigoCafePerfeito(validacao);
//                    System.out.printf("\no número é: [%s]", !result);
//                }
//                System.out.printf("\n\no número: [%s] foi validado com sucesso\n\n\n", validacao);

            }

//                System.out.printf("\nQuais saidas que vão ser atualizadas: ");
//                String saida = new Scanner(System.in).nextLine();
//                if (saida.equals("exit")) {
//                    continua = false;
//                    continue;
//                }
//                List<SaidaProduto> saidaProdutoList = new ArrayList<>();
//                for (String split : saida.split(",")) {
//                    System.out.printf("split: %s\n", split);
//                    saidaProdutoList.add(new SaidaProdutoDAO().getById(SaidaProduto.class, Long.valueOf(split)));
//                }
//                if (saidaProdutoList.size() == 0) continua = false;
//
////                System.out.printf("\nQual id do Estoque? ");
////                ProdutoEstoque estoque = new ProdutoEstoqueDAO().getById(ProdutoEstoque.class, Long.valueOf(new Scanner(System.in).nextLine()));
//
//
//                saidaProdutoList.stream()
//                        .forEach(saidaProduto ->
//                                ServiceUtilJSon.printJsonFromObject(saidaProduto, String.format("saidaProduto: [%d]", saidaProduto.idProperty().getValue()))
//                        );

//            SaidaProdutoProduto saidaProdutoProduto = new SaidaProdutoProdutoDAO().getById(SaidaProdutoProduto.class, Long.valueOf(new Scanner(System.in).nextLine()));
//            System.out.printf("\nQual id do Estoque? ");
//            ProdutoEstoque estoque = new ProdutoEstoqueDAO().getById(ProdutoEstoque.class, Long.valueOf(new Scanner(System.in).nextLine()));

//            }
//
//            ObservableList<ContasAReceber> contasAReceberObservableList = FXCollections.observableArrayList(new ContasAReceberDAO().getAll(ContasAReceber.class, null,  "dtVencimento"));
//            FilteredList<ContasAReceber> contasAReceberFilteredList = new FilteredList<>(contasAReceberObservableList);
//
//            TreeItem contasAReceberTreeItem = new TreeItem();
//
////            contasAReceberFilteredList.stream().forEach(aReceber -> {
////                final TreeItem receberTreeItem = new TreeItem(aReceber);
////                contasAReceberTreeItem.getChildren().add(receberTreeItem);
////                System.out.printf("item0: [%s]\n", receberTreeItem);
////                aReceber.getRecebimentoList().stream()
////                        .forEach(recebimento -> {
////                            System.out.printf("\t\titem1: [%s]\n", recebimento);
////                            receberTreeItem.getChildren().add(new TreeItem(recebimento));
////                        });
////            });
//
//            System.out.printf("qtdClientes: [%s]\n", contasAReceberFilteredList.stream()
//                    .map(ContasAReceber::getSaidaProduto)
//                    .map(SaidaProduto::getCliente)
//                    .collect(Collectors.groupingBy(Empresa::getId))
//                    .size());
//
//            System.out.printf("qtdContas: [%s]\n", (int) contasAReceberFilteredList.size());
//
//
//            System.out.printf("data1: [%s]\n\n", LocalDate.now().minusDays(39));
//            contasAReceberFilteredList.setPredicate(aReceber -> aReceber.dtCadastroProperty().getValue().toLocalDate().compareTo(LocalDate.now().minusDays(39)) >= 0);
//
//            System.out.printf("VlrContas0 R$: [%s]\n",
//                    contasAReceberFilteredList.stream()
//                            .filter(aReceber -> aReceber.getRecebimentoList().stream()
//                                    .filter(recebimento -> recebimento.getPagamentoModalidade().equals(PagamentoModalidade.RETIRADA)
//                                            || recebimento.getPagamentoModalidade().equals(PagamentoModalidade.AMOSTRA)
//                                            || recebimento.getPagamentoModalidade().equals(PagamentoModalidade.BONIFICACAO)).count() > 0)
//                            .map(ContasAReceber::getSaidaProduto)
//                            .map(SaidaProduto::getSaidaProdutoProdutoList)
//                            .map(saidaProdutoProdutos -> saidaProdutoProdutos.stream()
//                                    .filter(saidaProdutoProduto -> !saidaProdutoProduto.getTipoSaidaProduto().equals(TipoSaidaProduto.VENDA))
//                                    .map(SaidaProdutoProduto::getVlrDesconto).reduce(BigDecimal.ZERO, BigDecimal::add)
//                            )
//                            .reduce(BigDecimal.ZERO, BigDecimal::add)
////                    contasAReceberFilteredList.stream()
////                            .map(ContasAReceber::getSaidaProduto)
////                            .map(SaidaProduto::getSaidaProdutoProdutoList)
////                            .map(saidaProdutoProdutos -> saidaProdutoProdutos.stream()
////                                    .filter(saidaProdutoProduto -> !saidaProdutoProduto.getTipoSaidaProduto().equals(TipoSaidaProduto.VENDA))
////                                    .map(SaidaProdutoProduto::getVlrDesconto).reduce(BigDecimal.ZERO, BigDecimal::add)
////                            )
////                            .reduce(BigDecimal.ZERO, BigDecimal::add)
//            );
//            System.out.printf("VlrContas1 R$: [%s]\n",
////                    contasAReceberFilteredList.stream()
////                            .filter(aReceber -> {
////                                if (aReceber.getRecebimentoList().stream()
////                                        .filter(recebimento -> recebimento.getPagamentoModalidade().equals(PagamentoModalidade.RETIRADA))
////                                        .count() > 0)
////                                    return true;
////                                return false;
////                            }).map(ContasAReceber::getSaidaProduto)
////                            .map(SaidaProduto::getSaidaProdutoProdutoList)
////                            .map(value -> value.stream().map(SaidaProdutoProduto::getVlrDesconto).reduce(BigDecimal.ZERO, BigDecimal::add))
////                            .reduce(BigDecimal.ZERO, BigDecimal::add)
//                    contasAReceberFilteredList.stream()
//                            .filter(aReceber -> {
//                                return aReceber.getRecebimentoList().stream()
//                                        .filter(recebimento -> !recebimento.getPagamentoModalidade().equals(PagamentoModalidade.RETIRADA)
//                                                && !recebimento.getPagamentoModalidade().equals(PagamentoModalidade.AMOSTRA)
//                                                && !recebimento.getPagamentoModalidade().equals(PagamentoModalidade.BONIFICACAO)).count() > 0;
//                            })
//                            .map(ContasAReceber::getSaidaProduto)
//                            .map(SaidaProduto::getSaidaProdutoProdutoList)
//                            .map(saidaProdutoProdutos -> saidaProdutoProdutos.stream()
//                                    .filter(saidaProdutoProduto -> !saidaProdutoProduto.getTipoSaidaProduto().equals(TipoSaidaProduto.VENDA))
//                                    .map(SaidaProdutoProduto::getVlrDesconto).reduce(BigDecimal.ZERO, BigDecimal::add)
//                            )
//                            .reduce(BigDecimal.ZERO, BigDecimal::add)
//            );

        } catch (
                Exception ex) {
            ex.printStackTrace();
        }
    }
}
