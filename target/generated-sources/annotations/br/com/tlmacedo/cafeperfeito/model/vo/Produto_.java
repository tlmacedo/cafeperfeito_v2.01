package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.vo.FiscalCstOrigem;
import br.com.tlmacedo.cafeperfeito.model.vo.FiscalIcms;
import br.com.tlmacedo.cafeperfeito.model.vo.FiscalPisCofins;
import br.com.tlmacedo.cafeperfeito.model.vo.Usuario;
import java.sql.Blob;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2019-08-05T20:15:23")
@StaticMetamodel(Produto.class)
public class Produto_ { 

    public static volatile SingularAttribute<Produto, StringProperty> nfeGenero;
    public static volatile SingularAttribute<Produto, ObjectProperty> ultFrete;
    public static volatile SingularAttribute<Produto, FiscalIcms> fiscalIcms;
    public static volatile SingularAttribute<Produto, IntegerProperty> situacao;
    public static volatile SingularAttribute<Produto, IntegerProperty> varejo;
    public static volatile SingularAttribute<Produto, ObjectProperty> precoConsumidor;
    public static volatile SingularAttribute<Produto, ObjectProperty> ultImpostoSefaz;
    public static volatile SingularAttribute<Produto, ObjectProperty> dtAtualizacao;
    public static volatile SingularAttribute<Produto, FiscalPisCofins> fiscalCofins;
    public static volatile SingularAttribute<Produto, Blob> imgProdutoBack;
    public static volatile SingularAttribute<Produto, Blob> imgProduto;
    public static volatile SingularAttribute<Produto, StringProperty> ncm;
    public static volatile SingularAttribute<Produto, LongProperty> id;
    public static volatile SingularAttribute<Produto, Usuario> usuarioCadastro;
    public static volatile SingularAttribute<Produto, StringProperty> codigo;
    public static volatile SingularAttribute<Produto, ObjectProperty> peso;
    public static volatile SingularAttribute<Produto, ObjectProperty> dtCadastro;
    public static volatile SingularAttribute<Produto, IntegerProperty> unidadeComercial;
    public static volatile SingularAttribute<Produto, StringProperty> cest;
    public static volatile SingularAttribute<Produto, StringProperty> descricao;
    public static volatile SingularAttribute<Produto, FiscalCstOrigem> fiscalCstOrigem;
    public static volatile SingularAttribute<Produto, ObjectProperty> comissao;
    public static volatile SingularAttribute<Produto, Usuario> usuarioAtualizacao;
    public static volatile SingularAttribute<Produto, FiscalPisCofins> fiscalPis;
    public static volatile SingularAttribute<Produto, ObjectProperty> precoFabrica;

}