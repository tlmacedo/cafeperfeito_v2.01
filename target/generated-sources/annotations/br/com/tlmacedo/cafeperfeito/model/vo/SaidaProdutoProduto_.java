package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.TipoSaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2019-09-03T09:54:12")
@StaticMetamodel(SaidaProdutoProduto.class)
public class SaidaProdutoProduto_ { 

    public static volatile SingularAttribute<SaidaProdutoProduto, Integer> qtd;
    public static volatile SingularAttribute<SaidaProdutoProduto, String> codigo;
    public static volatile SingularAttribute<SaidaProdutoProduto, LocalDate> dtValidade;
    public static volatile SingularAttribute<SaidaProdutoProduto, Long> idProd;
    public static volatile SingularAttribute<SaidaProdutoProduto, String> lote;
    public static volatile SingularAttribute<SaidaProdutoProduto, TipoSaidaProduto> tipoSaidaProduto;
    public static volatile SingularAttribute<SaidaProdutoProduto, SaidaProduto> saidaProduto;
    public static volatile SingularAttribute<SaidaProdutoProduto, Long> id;
    public static volatile SingularAttribute<SaidaProdutoProduto, BigDecimal> vlrDesconto;
    public static volatile SingularAttribute<SaidaProdutoProduto, String> descricao;
    public static volatile SingularAttribute<SaidaProdutoProduto, BigDecimal> vlrVenda;
    public static volatile SingularAttribute<SaidaProdutoProduto, BigDecimal> vlrBruto;

}