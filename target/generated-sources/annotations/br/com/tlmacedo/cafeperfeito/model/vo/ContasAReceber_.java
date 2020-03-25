package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.vo.Recebimento;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.Usuario;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2020-03-24T20:23:22")
@StaticMetamodel(ContasAReceber.class)
public class ContasAReceber_ { 

    public static volatile SingularAttribute<ContasAReceber, LocalDate> dtVencimento;
    public static volatile SingularAttribute<ContasAReceber, LocalDateTime> dtCadastro;
    public static volatile SingularAttribute<ContasAReceber, BigDecimal> valor;
    public static volatile SingularAttribute<ContasAReceber, SaidaProduto> saidaProduto;
    public static volatile SingularAttribute<ContasAReceber, Long> id;
    public static volatile SingularAttribute<ContasAReceber, Usuario> usuarioCadastro;
    public static volatile ListAttribute<ContasAReceber, Recebimento> recebimentoList;

}