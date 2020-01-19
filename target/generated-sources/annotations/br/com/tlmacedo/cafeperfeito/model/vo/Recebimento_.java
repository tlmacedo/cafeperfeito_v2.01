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

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2020-01-18T16:26:20")
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