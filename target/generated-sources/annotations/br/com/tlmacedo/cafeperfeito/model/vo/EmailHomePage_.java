package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.TipoEmailHomePage;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2019-10-11T14:43:38")
@StaticMetamodel(EmailHomePage.class)
public class EmailHomePage_ { 

    public static volatile SingularAttribute<EmailHomePage, Boolean> principal;
    public static volatile SingularAttribute<EmailHomePage, TipoEmailHomePage> tipoEmailHomePage;
    public static volatile SingularAttribute<EmailHomePage, Long> id;
    public static volatile SingularAttribute<EmailHomePage, String> descricao;

}