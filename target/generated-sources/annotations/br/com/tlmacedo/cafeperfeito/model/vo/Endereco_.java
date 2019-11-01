package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.TipoEndereco;
import br.com.tlmacedo.cafeperfeito.model.vo.Municipio;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-11-01T14:47:43")
@StaticMetamodel(Endereco.class)
public class Endereco_ { 

    public static volatile SingularAttribute<Endereco, String> pontoReferencia;
    public static volatile SingularAttribute<Endereco, TipoEndereco> tipo;
    public static volatile SingularAttribute<Endereco, String> complemento;
    public static volatile SingularAttribute<Endereco, String> numero;
    public static volatile SingularAttribute<Endereco, String> bairro;
    public static volatile SingularAttribute<Endereco, Municipio> municipio;
    public static volatile SingularAttribute<Endereco, String> logradouro;
    public static volatile SingularAttribute<Endereco, Long> id;
    public static volatile SingularAttribute<Endereco, String> cep;

}