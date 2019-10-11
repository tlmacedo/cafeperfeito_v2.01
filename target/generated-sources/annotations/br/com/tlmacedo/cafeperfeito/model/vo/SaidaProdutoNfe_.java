package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.NfeDadosDestinoOperacao;
import br.com.tlmacedo.cafeperfeito.model.enums.NfeDadosIndicadorConsumidorFinal;
import br.com.tlmacedo.cafeperfeito.model.enums.NfeDadosIndicadorPresenca;
import br.com.tlmacedo.cafeperfeito.model.enums.NfeDadosModelo;
import br.com.tlmacedo.cafeperfeito.model.enums.NfeDadosNaturezaOperacao;
import br.com.tlmacedo.cafeperfeito.model.enums.NfeStatusSefaz;
import br.com.tlmacedo.cafeperfeito.model.enums.NfeTransporteModFrete;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20131113-rNA", date="2019-10-11T11:43:59")
@StaticMetamodel(SaidaProdutoNfe.class)
public class SaidaProdutoNfe_ { 

    public static volatile SingularAttribute<SaidaProdutoNfe, NfeTransporteModFrete> modFrete;
    public static volatile SingularAttribute<SaidaProdutoNfe, Integer> numero;
    public static volatile SingularAttribute<SaidaProdutoNfe, LocalDateTime> dtHoraEmissao;
    public static volatile SingularAttribute<SaidaProdutoNfe, String> xmlAssinatura;
    public static volatile SingularAttribute<SaidaProdutoNfe, String> chave;
    public static volatile SingularAttribute<SaidaProdutoNfe, SaidaProduto> saidaProduto;
    public static volatile SingularAttribute<SaidaProdutoNfe, NfeStatusSefaz> statusSefaz;
    public static volatile SingularAttribute<SaidaProdutoNfe, NfeDadosModelo> modelo;
    public static volatile SingularAttribute<SaidaProdutoNfe, Empresa> transportador;
    public static volatile SingularAttribute<SaidaProdutoNfe, String> xmlProtNfe;
    public static volatile SingularAttribute<SaidaProdutoNfe, String> cobrancaNumero;
    public static volatile SingularAttribute<SaidaProdutoNfe, NfeDadosNaturezaOperacao> naturezaOperacao;
    public static volatile SingularAttribute<SaidaProdutoNfe, NfeDadosIndicadorConsumidorFinal> consumidorFinal;
    public static volatile SingularAttribute<SaidaProdutoNfe, String> informacaoAdicional;
    public static volatile SingularAttribute<SaidaProdutoNfe, Integer> serie;
    public static volatile SingularAttribute<SaidaProdutoNfe, LocalDateTime> dtHoraSaida;
    public static volatile SingularAttribute<SaidaProdutoNfe, Long> id;
    public static volatile SingularAttribute<SaidaProdutoNfe, NfeDadosDestinoOperacao> destinoOperacao;
    public static volatile SingularAttribute<SaidaProdutoNfe, NfeDadosIndicadorPresenca> indicadorPresenca;

}