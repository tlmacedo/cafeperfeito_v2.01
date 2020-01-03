package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.vo.Produto;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-12-30T17:07:23")
@StaticMetamodel(EntradaProdutoProduto.class)
public class EntradaProdutoProduto_ { 

    public static volatile SingularAttribute<EntradaProdutoProduto, Integer> qtd;
    public static volatile SingularAttribute<EntradaProdutoProduto, Integer> estoque;
    public static volatile SingularAttribute<EntradaProdutoProduto, String> codigo;
    public static volatile SingularAttribute<EntradaProdutoProduto, BigDecimal> vlrFabrica;
    public static volatile SingularAttribute<EntradaProdutoProduto, Produto> produto;
    public static volatile SingularAttribute<EntradaProdutoProduto, String> lote;
    public static volatile SingularAttribute<EntradaProdutoProduto, BigDecimal> vlrImposto;
    public static volatile SingularAttribute<EntradaProdutoProduto, Long> id;
    public static volatile SingularAttribute<EntradaProdutoProduto, Integer> varejo;
    public static volatile SingularAttribute<EntradaProdutoProduto, BigDecimal> vlrDesconto;
    public static volatile SingularAttribute<EntradaProdutoProduto, String> descricao;
    public static volatile SingularAttribute<EntradaProdutoProduto, LocalDate> validade;

}