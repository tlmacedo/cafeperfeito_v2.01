package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.vo.Produto;
import java.sql.Blob;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2019-08-27T21:37:17")
@StaticMetamodel(ProdutoCodigoBarra.class)
public class ProdutoCodigoBarra_ { 

    public static volatile SingularAttribute<ProdutoCodigoBarra, String> codigoBarra;
    public static volatile SingularAttribute<ProdutoCodigoBarra, Produto> produto;
    public static volatile SingularAttribute<ProdutoCodigoBarra, Blob> imgCodigoBarra;
    public static volatile SingularAttribute<ProdutoCodigoBarra, Long> id;

}