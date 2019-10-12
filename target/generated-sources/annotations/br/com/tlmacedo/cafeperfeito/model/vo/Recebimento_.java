package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoModalidade;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoSituacao;
import br.com.tlmacedo.cafeperfeito.model.vo.ContasAReceber;
import br.com.tlmacedo.cafeperfeito.model.vo.Usuario;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2019-10-11T14:43:38")
@StaticMetamodel(Recebimento.class)
public class Recebimento_ { 

    public static volatile SingularAttribute<Recebimento, PagamentoSituacao> pagamentoSituacao;
    public static volatile SingularAttribute<Recebimento, Usuario> usuarioPagamento;
    public static volatile SingularAttribute<Recebimento, ContasAReceber> aReceber;
    public static volatile SingularAttribute<Recebimento, LocalDateTime> dtCadastro;
    public static volatile SingularAttribute<Recebimento, PagamentoModalidade> pagamentoModalidade;
    public static volatile SingularAttribute<Recebimento, BigDecimal> valor;
    public static volatile SingularAttribute<Recebimento, String> documento;
    public static volatile SingularAttribute<Recebimento, Long> id;
    public static volatile SingularAttribute<Recebimento, Usuario> usuarioCadastro;
    public static volatile SingularAttribute<Recebimento, LocalDate> dtPagamento;

}