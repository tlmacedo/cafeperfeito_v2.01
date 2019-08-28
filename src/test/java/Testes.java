import br.com.tlmacedo.cafeperfeito.model.dao.ProdutoEstoqueDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.ProdutoEstoque;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Testes {


    public static void main(String[] args) {

        try {
            List<ProdutoEstoque> produtoEstoqueList = new ProdutoEstoqueDAO().getAll(ProdutoEstoque.class, "produto_id", "=", "5", "validade");


            List<String> words = Arrays.asList("Oracle", "Java", "Magazine");
            List<Integer> wordLengths =
                    words.stream()
                            .map(String::length)
                            .collect(Collectors.toList());
            produtoEstoqueList.stream().filter(estoque -> estoque.qtdProperty().getValue() > 0).forEach(estoque ->
                    System.out.printf("produtoEstoqueList: [%s]\n", estoque));

            produtoEstoqueList.stream().filter(estoque -> estoque.qtdProperty().getValue() > 0).forEach(estoque ->
                    System.out.printf("produtoEstoqueList: \n\t[%s]*[%s]=[%s]\n\n",
                            estoque.qtdProperty().getValue(),
                            estoque.vlrBrutoProperty().getValue()
                                    .add(estoque.vlrFreteBrutoProperty().getValue())
                                    .add(estoque.vlrImpostoNaEntradaProperty().getValue())
                                    .add(estoque.vlrImpostoFreteNaEntradaProperty().getValue())
                                    .add(estoque.vlrImpostoDentroFreteProperty().getValue())
                                    .add(estoque.vlrFreteTaxaProperty().getValue()),
                            (estoque.vlrBrutoProperty().getValue()
                                    .add(estoque.vlrFreteBrutoProperty().getValue())
                                    .add(estoque.vlrImpostoNaEntradaProperty().getValue())
                                    .add(estoque.vlrImpostoFreteNaEntradaProperty().getValue())
                                    .add(estoque.vlrImpostoDentroFreteProperty().getValue())
                                    .add(estoque.vlrFreteTaxaProperty().getValue()))
                                    .multiply(BigDecimal.valueOf(estoque.qtdProperty().getValue())))
            );

            System.out.printf("qtd * vlrBruto = [%s]", produtoEstoqueList.stream().filter(estoque -> estoque.qtdProperty().getValue() > 0)
                    .map(estoque ->
                            (estoque.vlrBrutoProperty().getValue()
                                    .add(estoque.vlrFreteBrutoProperty().getValue())
                                    .add(estoque.vlrImpostoNaEntradaProperty().getValue())
                                    .add(estoque.vlrImpostoFreteNaEntradaProperty().getValue())
                                    .add(estoque.vlrImpostoDentroFreteProperty().getValue())
                                    .add(estoque.vlrFreteTaxaProperty().getValue()))
                                    .multiply(BigDecimal.valueOf(estoque.qtdProperty().getValue()))
                    )
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

        } catch (
                Exception ex) {
            ex.printStackTrace();
        }
    }
}
