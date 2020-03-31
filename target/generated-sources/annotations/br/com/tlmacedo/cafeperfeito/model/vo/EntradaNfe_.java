package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.NfeCteModelo;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.EntradaFiscal;
import br.com.tlmacedo.cafeperfeito.model.vo.EntradaProduto;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.0.v20170811-rNA", date="2020-03-31T17:34:53")
@StaticMetamodel(EntradaNfe.class)
public class EntradaNfe_ { 

    public static volatile SingularAttribute<EntradaNfe, LocalDate> dtEmissao;
    public static volatile SingularAttribute<EntradaNfe, String> numero;
    public static volatile SingularAttribute<EntradaNfe, String> chave;
    public static volatile SingularAttribute<EntradaNfe, String> serie;
    public static volatile SingularAttribute<EntradaNfe, EntradaFiscal> entradaFiscal;
    public static volatile SingularAttribute<EntradaNfe, EntradaProduto> entradaProduto;
    public static volatile SingularAttribute<EntradaNfe, LocalDate> dtEntrada;
    public static volatile SingularAttribute<EntradaNfe, Empresa> fornecedor;
    public static volatile SingularAttribute<EntradaNfe, Long> id;
    public static volatile SingularAttribute<EntradaNfe, NfeCteModelo> modelo;

}