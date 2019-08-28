package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoSituacao;
import br.com.tlmacedo.cafeperfeito.model.vo.ContasAReceber;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.Usuario;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2019-08-28T17:03:12")
@StaticMetamodel(Recebimento.class)
public class Recebimento_ { 

    public static volatile SingularAttribute<Recebimento, PagamentoSituacao> pagamentoSituacao;
    public static volatile SingularAttribute<Recebimento, BigDecimal> vlrPago;
    public static volatile SingularAttribute<Recebimento, LocalDate> dtVencimento;
    public static volatile SingularAttribute<Recebimento, ContasAReceber> aReceber;
    public static volatile SingularAttribute<Recebimento, BigDecimal> valor;
    public static volatile SingularAttribute<Recebimento, Empresa> emissorRecibo;
    public static volatile SingularAttribute<Recebimento, String> documento;
    public static volatile SingularAttribute<Recebimento, Usuario> usarioPagamento;
    public static volatile SingularAttribute<Recebimento, Long> id;
    public static volatile SingularAttribute<Recebimento, LocalDate> dtPagamento;

}