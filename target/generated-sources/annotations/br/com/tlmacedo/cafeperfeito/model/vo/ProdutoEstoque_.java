package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.vo.Produto;
import br.com.tlmacedo.cafeperfeito.model.vo.Usuario;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2020-01-09T10:59:45")
@StaticMetamodel(ProdutoEstoque.class)
public class ProdutoEstoque_ { 

    public static volatile SingularAttribute<ProdutoEstoque, Integer> qtd;
    public static volatile SingularAttribute<ProdutoEstoque, LocalDate> dtValidade;
    public static volatile SingularAttribute<ProdutoEstoque, BigDecimal> vlrFreteBruto;
    public static volatile SingularAttribute<ProdutoEstoque, LocalDateTime> dtCadastro;
    public static volatile SingularAttribute<ProdutoEstoque, String> lote;
    public static volatile SingularAttribute<ProdutoEstoque, BigDecimal> vlrFreteTaxa;
    public static volatile SingularAttribute<ProdutoEstoque, String> docEntradaChaveNFe;
    public static volatile SingularAttribute<ProdutoEstoque, BigDecimal> vlrImpostoFreteNaEntrada;
    public static volatile SingularAttribute<ProdutoEstoque, BigDecimal> vlrImpostoDentroFrete;
    public static volatile SingularAttribute<ProdutoEstoque, Produto> produto;
    public static volatile SingularAttribute<ProdutoEstoque, String> docEntrada;
    public static volatile SingularAttribute<ProdutoEstoque, BigDecimal> vlrImpostoNaEntrada;
    public static volatile SingularAttribute<ProdutoEstoque, BigDecimal> vlrUnitario;
    public static volatile SingularAttribute<ProdutoEstoque, Long> id;
    public static volatile SingularAttribute<ProdutoEstoque, Usuario> usuarioCadastro;

}