package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.vo.Produto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-10-30T13:44:16")
@StaticMetamodel(FichaKardex.class)
public class FichaKardex_ { 

    public static volatile SingularAttribute<FichaKardex, Integer> qtd;
    public static volatile SingularAttribute<FichaKardex, BigDecimal> vlrEntrada;
    public static volatile SingularAttribute<FichaKardex, String> detalhe;
    public static volatile SingularAttribute<FichaKardex, String> documento;
    public static volatile SingularAttribute<FichaKardex, Integer> qtdEntrada;
    public static volatile SingularAttribute<FichaKardex, Integer> saldo;
    public static volatile SingularAttribute<FichaKardex, Integer> qtdSaida;
    public static volatile SingularAttribute<FichaKardex, BigDecimal> vlrSaida;
    public static volatile SingularAttribute<FichaKardex, Produto> produto;
    public static volatile SingularAttribute<FichaKardex, BigDecimal> vlrUnitario;
    public static volatile SingularAttribute<FichaKardex, Long> id;
    public static volatile SingularAttribute<FichaKardex, LocalDateTime> dtMovimento;
    public static volatile SingularAttribute<FichaKardex, BigDecimal> vlrSaldo;

}