package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;

@Entity(name = "SaidaProdutoNfe")
@Table(name = "saida_produto_nfe")
public class SaidaProdutoNfe implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private BooleanProperty cancelada = new SimpleBooleanProperty(false);
    private ObjectProperty<SaidaProduto> saidaProduto = new SimpleObjectProperty<>();
    private StringProperty chave = new SimpleStringProperty();
    private ObjectProperty<NfeStatusSefaz> statusSefaz = new SimpleObjectProperty<>();
    private ObjectProperty<NfeDadosNaturezaOperacao> naturezaOperacao = new SimpleObjectProperty<>();
    private ObjectProperty<NfeDadosModelo> modelo = new SimpleObjectProperty<>();
    private IntegerProperty serie = new SimpleIntegerProperty();
    private IntegerProperty numero = new SimpleIntegerProperty();
    private ObjectProperty<LocalDateTime> dtHoraEmissao = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> dtHoraSaida = new SimpleObjectProperty<>();
    private ObjectProperty<NfeDadosDestinoOperacao> destinoOperacao = new SimpleObjectProperty<>();
    private ObjectProperty<NfeImpressaoTpImp> impressaoTpImp = new SimpleObjectProperty<>();
    private ObjectProperty<NfeImpressaoTpEmis> impressaoTpEmis = new SimpleObjectProperty<>();
    private ObjectProperty<NfeImpressaoFinNFe> impressaoFinNFe = new SimpleObjectProperty<>();
    private BooleanProperty impressaoLtProduto = new SimpleBooleanProperty(false);
    private ObjectProperty<NfeDadosIndicadorConsumidorFinal> consumidorFinal = new SimpleObjectProperty<>();
    private ObjectProperty<NfeDadosIndicadorPresenca> indicadorPresenca = new SimpleObjectProperty<>();
    private ObjectProperty<NfeTransporteModFrete> modFrete = new SimpleObjectProperty<>();
    private ObjectProperty<Empresa> transportador;
    private StringProperty cobrancaNumero = new SimpleStringProperty();
    private ObjectProperty<NfeCobrancaDuplicataPagamentoIndicador> pagamentoIndicador = new SimpleObjectProperty<>();
    private ObjectProperty<NfeCobrancaDuplicataPagamentoMeio> pagamentoMeio = new SimpleObjectProperty<>();
    private StringProperty informacaoAdicional = new SimpleStringProperty();
    private StringProperty digVal = new SimpleStringProperty();
    private ObjectProperty<Blob> xmlAssinatura = new SimpleObjectProperty<>();
    private ObjectProperty<Blob> xmlProtNfe = new SimpleObjectProperty<>();

    public SaidaProdutoNfe() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }


    public boolean isCancelada() {
        return cancelada.get();
    }

    public BooleanProperty canceladaProperty() {
        return cancelada;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada.set(cancelada);
    }

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public SaidaProduto getSaidaProduto() {
        return saidaProduto.get();
    }

    public ObjectProperty<SaidaProduto> saidaProdutoProperty() {
        return saidaProduto;
    }

    public void setSaidaProduto(SaidaProduto saidaProduto) {
        this.saidaProduto.set(saidaProduto);
    }

    @Column(length = 47, nullable = false, unique = true)
    public String getChave() {
        return chave.get();
    }

    public StringProperty chaveProperty() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave.set(chave);
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeStatusSefaz getStatusSefaz() {
        return statusSefaz.get();
    }

    public ObjectProperty<NfeStatusSefaz> statusSefazProperty() {
        return statusSefaz;
    }

    public void setStatusSefaz(NfeStatusSefaz statusSefaz) {
        this.statusSefaz.set(statusSefaz);
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeDadosNaturezaOperacao getNaturezaOperacao() {
        return naturezaOperacao.get();
    }

    public ObjectProperty<NfeDadosNaturezaOperacao> naturezaOperacaoProperty() {
        return naturezaOperacao;
    }

    public void setNaturezaOperacao(NfeDadosNaturezaOperacao naturezaOperacao) {
        this.naturezaOperacao.set(naturezaOperacao);
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeDadosModelo getModelo() {
        return modelo.get();
    }

    public ObjectProperty<NfeDadosModelo> modeloProperty() {
        return modelo;
    }

    public void setModelo(NfeDadosModelo modelo) {
        this.modelo.set(modelo);
    }

    @Column(length = 3, nullable = false)
    public int getSerie() {
        return serie.get();
    }

    public IntegerProperty serieProperty() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie.set(serie);
    }

    @Column(length = 9, nullable = false)
    public int getNumero() {
        return numero.get();
    }

    public IntegerProperty numeroProperty() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero.set(numero);
    }

    @Column(nullable = false)
    public LocalDateTime getDtHoraEmissao() {
        return dtHoraEmissao.get();
    }

    public ObjectProperty<LocalDateTime> dtHoraEmissaoProperty() {
        return dtHoraEmissao;
    }

    public void setDtHoraEmissao(LocalDateTime dtHoraEmissao) {
        this.dtHoraEmissao.set(dtHoraEmissao);
    }

    @Column(nullable = false)
    public LocalDateTime getDtHoraSaida() {
        return dtHoraSaida.get();
    }

    public ObjectProperty<LocalDateTime> dtHoraSaidaProperty() {
        return dtHoraSaida;
    }

    public void setDtHoraSaida(LocalDateTime dtHoraSaida) {
        this.dtHoraSaida.set(dtHoraSaida);
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeDadosDestinoOperacao getDestinoOperacao() {
        return destinoOperacao.get();
    }

    public ObjectProperty<NfeDadosDestinoOperacao> destinoOperacaoProperty() {
        return destinoOperacao;
    }

    public void setDestinoOperacao(NfeDadosDestinoOperacao destinoOperacao) {
        this.destinoOperacao.set(destinoOperacao);
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeDadosIndicadorConsumidorFinal getConsumidorFinal() {
        return consumidorFinal.get();
    }

    public ObjectProperty<NfeDadosIndicadorConsumidorFinal> consumidorFinalProperty() {
        return consumidorFinal;
    }

    public void setConsumidorFinal(NfeDadosIndicadorConsumidorFinal consumidorFinal) {
        this.consumidorFinal.set(consumidorFinal);
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeDadosIndicadorPresenca getIndicadorPresenca() {
        return indicadorPresenca.get();
    }

    public ObjectProperty<NfeDadosIndicadorPresenca> indicadorPresencaProperty() {
        return indicadorPresenca;
    }

    public void setIndicadorPresenca(NfeDadosIndicadorPresenca indicadorPresenca) {
        this.indicadorPresenca.set(indicadorPresenca);
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeTransporteModFrete getModFrete() {
        return modFrete.get();
    }

    public ObjectProperty<NfeTransporteModFrete> modFreteProperty() {
        return modFrete;
    }

    public void setModFrete(NfeTransporteModFrete modFrete) {
        this.modFrete.set(modFrete);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Empresa getTransportador() {
        return transportadorProperty().get();
    }

    public ObjectProperty<Empresa> transportadorProperty() {
        if (transportador == null)
            transportador = new SimpleObjectProperty<>(new EmpresaDAO().getById(Empresa.class, 0L));
        return transportador;
    }

    public void setTransportador(Empresa transportador) {
        transportadorProperty().set(transportador);
    }

    @Column(length = 10, nullable = false)
    public String getCobrancaNumero() {
        return cobrancaNumero.get();
    }

    public StringProperty cobrancaNumeroProperty() {
        return cobrancaNumero;
    }

    public void setCobrancaNumero(String cobrancaNumero) {
        this.cobrancaNumero.set(cobrancaNumero);
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeCobrancaDuplicataPagamentoIndicador getPagamentoIndicador() {
        return pagamentoIndicador.get();
    }

    public ObjectProperty<NfeCobrancaDuplicataPagamentoIndicador> pagamentoIndicadorProperty() {
        return pagamentoIndicador;
    }

    public void setPagamentoIndicador(NfeCobrancaDuplicataPagamentoIndicador pagamentoIndicador) {
        this.pagamentoIndicador.set(pagamentoIndicador);
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeCobrancaDuplicataPagamentoMeio getPagamentoMeio() {
        return pagamentoMeio.get();
    }

    public ObjectProperty<NfeCobrancaDuplicataPagamentoMeio> pagamentoMeioProperty() {
        return pagamentoMeio;
    }

    public void setPagamentoMeio(NfeCobrancaDuplicataPagamentoMeio pagamentoMeio) {
        this.pagamentoMeio.set(pagamentoMeio);
    }

    @Column(length = 5000, nullable = false)
    public String getInformacaoAdicional() {
        return informacaoAdicional.get();
    }

    public StringProperty informacaoAdicionalProperty() {
        return informacaoAdicional;
    }

    public void setInformacaoAdicional(String informacaoAdicional) {
        this.informacaoAdicional.set(informacaoAdicional);
    }

    //    @JsonIgnore
//    @SuppressWarnings("JpaAttributeTypeInspection")
    @Column(length = 28, unique = true)
    public String getDigVal() {
        return digVal.get();
    }

    public StringProperty digValProperty() {
        return digVal;
    }

    public void setDigVal(String digVal) {
        this.digVal.set(digVal);
    }

    @JsonIgnore
    @SuppressWarnings("JpaAttributeTypeInspection")
    public Blob getXmlAssinatura() {
        return xmlAssinatura.get();
    }

    public ObjectProperty<Blob> xmlAssinaturaProperty() {
        return xmlAssinatura;
    }

    public void setXmlAssinatura(Blob xmlAssinatura) {
        this.xmlAssinatura.set(xmlAssinatura);
    }

    @JsonIgnore
    @SuppressWarnings("JpaAttributeTypeInspection")
    public Blob getXmlProtNfe() {
        return xmlProtNfe.get();
    }

    public ObjectProperty<Blob> xmlProtNfeProperty() {
        return xmlProtNfe;
    }

    public void setXmlProtNfe(Blob xmlProtNfe) {
        this.xmlProtNfe.set(xmlProtNfe);
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeImpressaoTpImp getImpressaoTpImp() {
        return impressaoTpImp.get();
    }

    public ObjectProperty<NfeImpressaoTpImp> impressaoTpImpProperty() {
        return impressaoTpImp;
    }

    public void setImpressaoTpImp(NfeImpressaoTpImp impressaoTpImp) {
        this.impressaoTpImp.set(impressaoTpImp);
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeImpressaoTpEmis getImpressaoTpEmis() {
        return impressaoTpEmis.get();
    }

    public ObjectProperty<NfeImpressaoTpEmis> impressaoTpEmisProperty() {
        return impressaoTpEmis;
    }

    public void setImpressaoTpEmis(NfeImpressaoTpEmis impressaoTpEmis) {
        this.impressaoTpEmis.set(impressaoTpEmis);
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeImpressaoFinNFe getImpressaoFinNFe() {
        return impressaoFinNFe.get();
    }

    public ObjectProperty<NfeImpressaoFinNFe> impressaoFinNFeProperty() {
        return impressaoFinNFe;
    }

    public void setImpressaoFinNFe(NfeImpressaoFinNFe impressaoFinNFe) {
        this.impressaoFinNFe.set(impressaoFinNFe);
    }

    @Column(length = 1, nullable = false)
    public boolean isImpressaoLtProduto() {
        return impressaoLtProduto.get();
    }

    public BooleanProperty impressaoLtProdutoProperty() {
        return impressaoLtProduto;
    }

    public void setImpressaoLtProduto(boolean impressaoLtProduto) {
        this.impressaoLtProduto.set(impressaoLtProduto);
    }

    @Override
    public String toString() {
        return "SaidaProdutoNfe{" +
                "id=" + id +
                ", saidaProduto=" + saidaProduto +
                ", chave=" + chave +
                ", statusSefaz=" + statusSefaz +
                ", naturezaOperacao=" + naturezaOperacao +
                ", modelo=" + modelo +
                ", serie=" + serie +
                ", numero=" + numero +
                ", dtHoraEmissao=" + dtHoraEmissao +
                ", dtHoraSaida=" + dtHoraSaida +
                ", destinoOperacao=" + destinoOperacao +
                ", impressaoTpImp=" + impressaoTpImp +
                ", impressaoTpEmis=" + impressaoTpEmis +
                ", impressaoFinNFe=" + impressaoFinNFe +
                ", impressaoLtProduto=" + impressaoLtProduto +
                ", consumidorFinal=" + consumidorFinal +
                ", indicadorPresenca=" + indicadorPresenca +
                ", modFrete=" + modFrete +
                ", transportador=" + transportador +
                ", cobrancaNumero=" + cobrancaNumero +
                ", pagamentoIndicador=" + pagamentoIndicador +
                ", pagamentoMeio=" + pagamentoMeio +
                ", informacaoAdicional=" + informacaoAdicional +
                ", xmlAssinatura=" + xmlAssinatura +
                ", xmlProtNfe=" + xmlProtNfe +
                '}';
    }
}
