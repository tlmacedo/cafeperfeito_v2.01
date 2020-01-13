package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.TipoCodigoCFOP;
import br.com.tlmacedo.cafeperfeito.model.vo.EntradaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.Produto;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2020-01-13T12:32:34")
@StaticMetamodel(EntradaProdutoProduto.class)
public class EntradaProdutoProduto_ { 

    public static volatile SingularAttribute<EntradaProdutoProduto, Integer> qtd;
    public static volatile SingularAttribute<EntradaProdutoProduto, String> codigo;
    public static volatile SingularAttribute<EntradaProdutoProduto, LocalDate> dtValidade;
    public static volatile SingularAttribute<EntradaProdutoProduto, String> lote;
    public static volatile SingularAttribute<EntradaProdutoProduto, TipoCodigoCFOP> codigoCFOP;
    public static volatile SingularAttribute<EntradaProdutoProduto, EntradaProduto> entradaProduto;
    public static volatile SingularAttribute<EntradaProdutoProduto, BigDecimal> vlrImposto;
    public static volatile SingularAttribute<EntradaProdutoProduto, BigDecimal> vlrFrete;
    public static volatile SingularAttribute<EntradaProdutoProduto, BigDecimal> vlrDesconto;
    public static volatile SingularAttribute<EntradaProdutoProduto, BigDecimal> vlrBruto;
    public static volatile SingularAttribute<EntradaProdutoProduto, String> descricao;
    public static volatile SingularAttribute<EntradaProdutoProduto, Produto> produto;
    public static volatile SingularAttribute<EntradaProdutoProduto, BigDecimal> vlrUnitario;
    public static volatile SingularAttribute<EntradaProdutoProduto, Long> id;

}