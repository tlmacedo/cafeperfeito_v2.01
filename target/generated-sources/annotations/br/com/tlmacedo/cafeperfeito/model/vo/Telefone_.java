package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.vo.TelefoneOperadora;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-10-17T14:03:08")
@StaticMetamodel(Telefone.class)
public class Telefone_ { 

    public static volatile SingularAttribute<Telefone, Boolean> principal;
    public static volatile SingularAttribute<Telefone, TelefoneOperadora> telefoneOperadora;
    public static volatile SingularAttribute<Telefone, Long> id;
    public static volatile SingularAttribute<Telefone, String> descricao;

}