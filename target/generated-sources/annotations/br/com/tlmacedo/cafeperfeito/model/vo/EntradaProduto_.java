package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.SituacaoEntrada;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.EntradaCte;
import br.com.tlmacedo.cafeperfeito.model.vo.EntradaNfe;
import br.com.tlmacedo.cafeperfeito.model.vo.EntradaProdutoProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.Usuario;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2020-01-28T15:44:05")
@StaticMetamodel(EntradaProduto.class)
public class EntradaProduto_ { 

    public static volatile SingularAttribute<EntradaProduto, SituacaoEntrada> situacao;
    public static volatile SingularAttribute<EntradaProduto, EntradaNfe> entradaNfe;
    public static volatile SingularAttribute<EntradaProduto, LocalDateTime> dtCadastro;
    public static volatile SingularAttribute<EntradaProduto, Empresa> loja;
    public static volatile SingularAttribute<EntradaProduto, EntradaCte> entradaCte;
    public static volatile SingularAttribute<EntradaProduto, Long> id;
    public static volatile ListAttribute<EntradaProduto, EntradaProdutoProduto> entradaProdutoProdutoList;
    public static volatile SingularAttribute<EntradaProduto, Usuario> usuarioCadastro;

}