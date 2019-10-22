package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.SituacaoProduto;
import br.com.tlmacedo.cafeperfeito.model.enums.UndComercialProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.FiscalCstOrigem;
import br.com.tlmacedo.cafeperfeito.model.vo.FiscalIcms;
import br.com.tlmacedo.cafeperfeito.model.vo.FiscalPisCofins;
import br.com.tlmacedo.cafeperfeito.model.vo.ProdutoCodigoBarra;
import br.com.tlmacedo.cafeperfeito.model.vo.ProdutoEstoque;
import br.com.tlmacedo.cafeperfeito.model.vo.Usuario;
import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-10-22T14:28:09")
@StaticMetamodel(Produto.class)
public class Produto_ { 

    public static volatile SingularAttribute<Produto, String> nfeGenero;
    public static volatile SingularAttribute<Produto, BigDecimal> ultFrete;
    public static volatile SingularAttribute<Produto, FiscalIcms> fiscalIcms;
    public static volatile SingularAttribute<Produto, SituacaoProduto> situacao;
    public static volatile SingularAttribute<Produto, BigDecimal> precoVenda;
    public static volatile SingularAttribute<Produto, Integer> varejo;
    public static volatile ListAttribute<Produto, ProdutoEstoque> produtoEstoqueList;
    public static volatile SingularAttribute<Produto, BigDecimal> ultImpostoSefaz;
    public static volatile SingularAttribute<Produto, FiscalPisCofins> fiscalCofins;
    public static volatile SingularAttribute<Produto, LocalDateTime> dtAtualizacao;
    public static volatile ListAttribute<Produto, ProdutoCodigoBarra> produtoCodigoBarraList;
    public static volatile SingularAttribute<Produto, Blob> imgProduto;
    public static volatile SingularAttribute<Produto, String> ncm;
    public static volatile SingularAttribute<Produto, Long> id;
    public static volatile SingularAttribute<Produto, Usuario> usuarioCadastro;
    public static volatile SingularAttribute<Produto, String> codigo;
    public static volatile SingularAttribute<Produto, BigDecimal> precoCompra;
    public static volatile SingularAttribute<Produto, BigDecimal> peso;
    public static volatile SingularAttribute<Produto, LocalDateTime> dtCadastro;
    public static volatile SingularAttribute<Produto, UndComercialProduto> unidadeComercial;
    public static volatile SingularAttribute<Produto, String> cest;
    public static volatile SingularAttribute<Produto, String> descricao;
    public static volatile SingularAttribute<Produto, FiscalCstOrigem> fiscalCstOrigem;
    public static volatile SingularAttribute<Produto, BigDecimal> comissao;
    public static volatile SingularAttribute<Produto, Usuario> usuarioAtualizacao;
    public static volatile SingularAttribute<Produto, FiscalPisCofins> fiscalPis;

}