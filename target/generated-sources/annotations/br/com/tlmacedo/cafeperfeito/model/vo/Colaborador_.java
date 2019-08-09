package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.ColaboradorSituacao;
import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2019-08-07T15:34:27")
@StaticMetamodel(Colaborador.class)
public class Colaborador_ { 

    public static volatile SingularAttribute<Colaborador, ColaboradorSituacao> ativo;
    public static volatile SingularAttribute<Colaborador, String> apelido;
    public static volatile SingularAttribute<Colaborador, BigDecimal> salario;
    public static volatile SingularAttribute<Colaborador, Blob> imagem;
    public static volatile SingularAttribute<Colaborador, String> nome;
    public static volatile SingularAttribute<Colaborador, String> ctps;
    public static volatile SingularAttribute<Colaborador, Long> id;
    public static volatile SingularAttribute<Colaborador, LocalDateTime> dtAdmisao;

}