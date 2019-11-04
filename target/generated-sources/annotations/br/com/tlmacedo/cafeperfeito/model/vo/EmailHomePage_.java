package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.TipoEmailHomePage;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-11-04T14:57:09")
@StaticMetamodel(EmailHomePage.class)
public class EmailHomePage_ { 

    public static volatile SingularAttribute<EmailHomePage, Boolean> principal;
    public static volatile SingularAttribute<EmailHomePage, TipoEmailHomePage> tipoEmailHomePage;
    public static volatile SingularAttribute<EmailHomePage, Long> id;
    public static volatile SingularAttribute<EmailHomePage, String> descricao;

}