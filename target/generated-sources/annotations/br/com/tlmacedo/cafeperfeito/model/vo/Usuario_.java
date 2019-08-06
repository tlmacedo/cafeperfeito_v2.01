package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.AccessGuest;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2019-08-05T20:15:23")
@StaticMetamodel(Usuario.class)
public class Usuario_ extends Colaborador_ {

    public static volatile SingularAttribute<Usuario, AccessGuest> accessGuest;
    public static volatile SingularAttribute<Usuario, String> senha;
    public static volatile SingularAttribute<Usuario, String> email;

}