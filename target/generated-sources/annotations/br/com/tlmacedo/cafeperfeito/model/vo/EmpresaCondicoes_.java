package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.Produto;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2019-08-28T12:44:15")
@StaticMetamodel(EmpresaCondicoes.class)
public class EmpresaCondicoes_ { 

    public static volatile SingularAttribute<EmpresaCondicoes, Integer> qtdMinima;
    public static volatile SingularAttribute<EmpresaCondicoes, Produto> produto;
    public static volatile SingularAttribute<EmpresaCondicoes, BigDecimal> desconto;
    public static volatile SingularAttribute<EmpresaCondicoes, BigDecimal> valor;
    public static volatile SingularAttribute<EmpresaCondicoes, Integer> bonificacao;
    public static volatile SingularAttribute<EmpresaCondicoes, Long> id;
    public static volatile SingularAttribute<EmpresaCondicoes, Integer> prazo;
    public static volatile SingularAttribute<EmpresaCondicoes, Empresa> empresa;
    public static volatile SingularAttribute<EmpresaCondicoes, Integer> retirada;
    public static volatile SingularAttribute<EmpresaCondicoes, LocalDate> validade;

}