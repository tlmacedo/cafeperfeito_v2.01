package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.vo.FiscalTributosSefazAm;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2020-03-31T17:34:53")
@StaticMetamodel(EntradaFiscal.class)
public class EntradaFiscal_ { 

    public static volatile SingularAttribute<EntradaFiscal, BigDecimal> vlrJuros;
    public static volatile SingularAttribute<EntradaFiscal, BigDecimal> vlrTributo;
    public static volatile SingularAttribute<EntradaFiscal, FiscalTributosSefazAm> tributosSefazAm;
    public static volatile SingularAttribute<EntradaFiscal, String> origem;
    public static volatile SingularAttribute<EntradaFiscal, BigDecimal> vlrDocumento;
    public static volatile SingularAttribute<EntradaFiscal, BigDecimal> vlrTaxa;
    public static volatile SingularAttribute<EntradaFiscal, Long> id;
    public static volatile SingularAttribute<EntradaFiscal, BigDecimal> vlrMulta;
    public static volatile SingularAttribute<EntradaFiscal, String> controle;

}