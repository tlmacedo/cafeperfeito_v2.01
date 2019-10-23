package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "SaidaProdutoNfe")
@Table(name = "saida_produto_nfe")
public class SaidaProdutoNfe implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private ObjectProperty<SaidaProduto> saidaProduto = new SimpleObjectProperty<>();
    private StringProperty chave = new SimpleStringProperty();
    private NfeStatusSefaz statusSefaz;
    private NfeDadosNaturezaOperacao naturezaOperacao;
    private NfeDadosModelo modelo;
    private IntegerProperty serie = new SimpleIntegerProperty();
    private IntegerProperty numero = new SimpleIntegerProperty();
    private ObjectProperty<LocalDateTime> dtHoraEmissao = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> dtHoraSaida = new SimpleObjectProperty<>();
    private NfeDadosDestinoOperacao destinoOperacao;
    private NfeDadosIndicadorConsumidorFinal consumidorFinal;
    private NfeDadosIndicadorPresenca indicadorPresenca;
    private NfeTransporteModFrete modFrete;
    private ObjectProperty<Empresa> transportador;
    private StringProperty cobrancaNumero = new SimpleStringProperty();
    private NfeCobrancaDuplicataPagamentoIndicador pagamentoIndicador;
    private NfeCobrancaDuplicataPagamentoMeio pagamentoMeio;
    private StringProperty informacaoAdicional = new SimpleStringProperty();
    private StringProperty xmlAssinatura = new SimpleStringProperty();
    private StringProperty xmlProtNfe = new SimpleStringProperty();

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

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
        return statusSefaz;
    }

    public void setStatusSefaz(NfeStatusSefaz statusSefaz) {
        this.statusSefaz = statusSefaz;
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeDadosNaturezaOperacao getNaturezaOperacao() {
        return naturezaOperacao;
    }

    public void setNaturezaOperacao(NfeDadosNaturezaOperacao naturezaOperacao) {
        this.naturezaOperacao = naturezaOperacao;
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeDadosModelo getModelo() {
        return modelo;
    }

    public void setModelo(NfeDadosModelo modelo) {
        this.modelo = modelo;
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
        return destinoOperacao;
    }

    public void setDestinoOperacao(NfeDadosDestinoOperacao destinoOperacao) {
        this.destinoOperacao = destinoOperacao;
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeDadosIndicadorConsumidorFinal getConsumidorFinal() {
        return consumidorFinal;
    }

    public void setConsumidorFinal(NfeDadosIndicadorConsumidorFinal consumidorFinal) {
        this.consumidorFinal = consumidorFinal;
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeDadosIndicadorPresenca getIndicadorPresenca() {
        return indicadorPresenca;
    }

    public void setIndicadorPresenca(NfeDadosIndicadorPresenca indicadorPresenca) {
        this.indicadorPresenca = indicadorPresenca;
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeTransporteModFrete getModFrete() {
        return modFrete;
    }

    public void setModFrete(NfeTransporteModFrete modFrete) {
        this.modFrete = modFrete;
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

    @Column(length = 60, nullable = false)
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
        return pagamentoIndicador;
    }

    public void setPagamentoIndicador(NfeCobrancaDuplicataPagamentoIndicador pagamentoIndicador) {
        this.pagamentoIndicador = pagamentoIndicador;
    }

    @Enumerated(EnumType.ORDINAL)
    public NfeCobrancaDuplicataPagamentoMeio getPagamentoMeio() {
        return pagamentoMeio;
    }

    public void setPagamentoMeio(NfeCobrancaDuplicataPagamentoMeio pagamentoMeio) {
        this.pagamentoMeio = pagamentoMeio;
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

    @Column(length = 5000, nullable = false)
    public String getXmlAssinatura() {
        return xmlAssinatura.get();
    }

    public StringProperty xmlAssinaturaProperty() {
        return xmlAssinatura;
    }

    public void setXmlAssinatura(String xmlAssinatura) {
        this.xmlAssinatura.set(xmlAssinatura);
    }

    @Column(length = 5000, nullable = false)
    public String getXmlProtNfe() {
        return xmlProtNfe.get();
    }

    public StringProperty xmlProtNfeProperty() {
        return xmlProtNfe;
    }

    public void setXmlProtNfe(String xmlProtNfe) {
        this.xmlProtNfe.set(xmlProtNfe);
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
