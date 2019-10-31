package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.TipoSaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.Produto;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-10-31T14:48:27")
@StaticMetamodel(SaidaProdutoProduto.class)
public class SaidaProdutoProduto_ { 

    public static volatile SingularAttribute<SaidaProdutoProduto, Integer> qtd;
    public static volatile SingularAttribute<SaidaProdutoProduto, BigDecimal> vlrEntrada;
    public static volatile SingularAttribute<SaidaProdutoProduto, String> codigo;
    public static volatile SingularAttribute<SaidaProdutoProduto, LocalDate> dtValidade;
    public static volatile SingularAttribute<SaidaProdutoProduto, String> lote;
    public static volatile SingularAttribute<SaidaProdutoProduto, SaidaProduto> saidaProduto;
    public static volatile SingularAttribute<SaidaProdutoProduto, BigDecimal> vlrDesconto;
    public static volatile SingularAttribute<SaidaProdutoProduto, BigDecimal> vlrBruto;
    public static volatile SingularAttribute<SaidaProdutoProduto, String> descricao;
    public static volatile SingularAttribute<SaidaProdutoProduto, Produto> produto;
    public static volatile SingularAttribute<SaidaProdutoProduto, BigDecimal> vlrEntradaBruto;
    public static volatile SingularAttribute<SaidaProdutoProduto, TipoSaidaProduto> tipoSaidaProduto;
    public static volatile SingularAttribute<SaidaProdutoProduto, Long> id;
    public static volatile SingularAttribute<SaidaProdutoProduto, BigDecimal> vlrVenda;

}