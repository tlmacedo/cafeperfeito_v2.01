package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.AccessGuest;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-12-09T15:45:17")
@StaticMetamodel(Usuario.class)
public class Usuario_ extends Colaborador_ {

    public static volatile SingularAttribute<Usuario, AccessGuest> accessGuest;
    public static volatile SingularAttribute<Usuario, String> senha;
    public static volatile SingularAttribute<Usuario, String> email;

}