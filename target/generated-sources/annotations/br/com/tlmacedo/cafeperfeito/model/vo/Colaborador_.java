package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.SituacaoColaborador;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2020-01-06T17:01:20")
@StaticMetamodel(Colaborador.class)
public class Colaborador_ { 

    public static volatile SingularAttribute<Colaborador, SituacaoColaborador> situacao;
    public static volatile SingularAttribute<Colaborador, String> apelido;
    public static volatile SingularAttribute<Colaborador, Empresa> lojaAtivo;
    public static volatile SingularAttribute<Colaborador, BigDecimal> salario;
    public static volatile SingularAttribute<Colaborador, Blob> imagem;
    public static volatile SingularAttribute<Colaborador, String> nome;
    public static volatile SingularAttribute<Colaborador, String> ctps;
    public static volatile SingularAttribute<Colaborador, Long> id;
    public static volatile SingularAttribute<Colaborador, LocalDateTime> dtAdmisao;

}