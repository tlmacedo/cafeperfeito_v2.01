package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.CteTomadorServico;
import br.com.tlmacedo.cafeperfeito.model.enums.NfeCteModelo;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.EntradaFiscal;
import br.com.tlmacedo.cafeperfeito.model.vo.FiscalFreteSituacaoTributaria;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2019-12-30T17:07:23")
@StaticMetamodel(EntradaCte.class)
public class EntradaCte_ { 

    public static volatile SingularAttribute<EntradaCte, BigDecimal> vlrTaxas;
    public static volatile SingularAttribute<EntradaCte, String> numero;
    public static volatile SingularAttribute<EntradaCte, BigDecimal> vlrFreteBruto;
    public static volatile SingularAttribute<EntradaCte, Integer> qtdVolume;
    public static volatile SingularAttribute<EntradaCte, String> chave;
    public static volatile SingularAttribute<EntradaCte, FiscalFreteSituacaoTributaria> situacaoTributaria;
    public static volatile SingularAttribute<EntradaCte, LocalDate> dataEmissao;
    public static volatile SingularAttribute<EntradaCte, NfeCteModelo> modelo;
    public static volatile SingularAttribute<EntradaCte, BigDecimal> vlrImpostoFrete;
    public static volatile SingularAttribute<EntradaCte, Empresa> emissor;
    public static volatile SingularAttribute<EntradaCte, String> serie;
    public static volatile SingularAttribute<EntradaCte, EntradaFiscal> entradaFiscal;
    public static volatile SingularAttribute<EntradaCte, BigDecimal> vlrCte;
    public static volatile SingularAttribute<EntradaCte, BigDecimal> pesoBruto;
    public static volatile SingularAttribute<EntradaCte, Long> id;
    public static volatile SingularAttribute<EntradaCte, BigDecimal> vlrColeta;
    public static volatile SingularAttribute<EntradaCte, CteTomadorServico> tomadorServico;

}