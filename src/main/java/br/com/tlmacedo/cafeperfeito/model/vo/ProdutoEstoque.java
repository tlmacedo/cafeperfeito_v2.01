package br.com.tlmacedo.cafeperfeito.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "ProdutoEstoque")
@Table(name = "produto_estoque")
public class ProdutoEstoque implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private Produto produto = new Produto();
    private IntegerProperty qtd = new SimpleIntegerProperty();
    private StringProperty lote = new SimpleStringProperty();
    private ObjectProperty<LocalDate> validade = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> vlrBruto = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> vlrFreteBruto = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> vlrImpostoNaEntrada = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> vlrImpostoFreteNaEntrada = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> vlrImpostoDentroFrete = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> vlrFreteTaxa = new SimpleObjectProperty<>();

    private Usuario usuarioCadastro = new Usuario();
    private ObjectProperty<LocalDateTime> dtCadastro = new SimpleObjectProperty<>();

    private StringProperty docEntrada = new SimpleStringProperty();
    private StringProperty docEntradaChaveNFe = new SimpleStringProperty();

    public ProdutoEstoque() {
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
    @ManyToOne(fetch = FetchType.LAZY)
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Column(length = 5, nullable = false)
    public int getQtd() {
        return qtd.get();
    }

    public IntegerProperty qtdProperty() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd.set(qtd);
    }

    @Column(length = 15, nullable = false)
    public String getLote() {
        return lote.get();
    }

    public StringProperty loteProperty() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote.set(lote);
    }

    public LocalDate getValidade() {
        return validade.get();
    }

    public ObjectProperty<LocalDate> validadeProperty() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade.set(validade);
    }

    @Column(length = 19, scale = 4, nullable = false)
    public BigDecimal getVlrBruto() {
        return vlrBruto.get();
    }

    public ObjectProperty<BigDecimal> vlrBrutoProperty() {
        return vlrBruto;
    }

    public void setVlrBruto(BigDecimal vlrBruto) {
        this.vlrBruto.set(vlrBruto);
    }

    @Column(length = 19, scale = 4, nullable = false)
    public BigDecimal getVlrFreteBruto() {
        return vlrFreteBruto.get();
    }

    public ObjectProperty<BigDecimal> vlrFreteBrutoProperty() {
        return vlrFreteBruto;
    }

    public void setVlrFreteBruto(BigDecimal vlrFreteBruto) {
        this.vlrFreteBruto.set(vlrFreteBruto);
    }

    @Column(length = 19, scale = 4, nullable = false)
    public BigDecimal getVlrImpostoNaEntrada() {
        return vlrImpostoNaEntrada.get();
    }

    public ObjectProperty<BigDecimal> vlrImpostoNaEntradaProperty() {
        return vlrImpostoNaEntrada;
    }

    public void setVlrImpostoNaEntrada(BigDecimal vlrImpostoNaEntrada) {
        this.vlrImpostoNaEntrada.set(vlrImpostoNaEntrada);
    }

    @Column(length = 19, scale = 4, nullable = false)
    public BigDecimal getVlrImpostoFreteNaEntrada() {
        return vlrImpostoFreteNaEntrada.get();
    }

    public ObjectProperty<BigDecimal> vlrImpostoFreteNaEntradaProperty() {
        return vlrImpostoFreteNaEntrada;
    }

    public void setVlrImpostoFreteNaEntrada(BigDecimal vlrImpostoFreteNaEntrada) {
        this.vlrImpostoFreteNaEntrada.set(vlrImpostoFreteNaEntrada);
    }

    @Column(length = 19, scale = 4, nullable = false)
    public BigDecimal getVlrImpostoDentroFrete() {
        return vlrImpostoDentroFrete.get();
    }

    public ObjectProperty<BigDecimal> vlrImpostoDentroFreteProperty() {
        return vlrImpostoDentroFrete;
    }

    public void setVlrImpostoDentroFrete(BigDecimal vlrImpostoDentroFrete) {
        this.vlrImpostoDentroFrete.set(vlrImpostoDentroFrete);
    }

    @Column(length = 19, scale = 4, nullable = false)
    public BigDecimal getVlrFreteTaxa() {
        return vlrFreteTaxa.get();
    }

    public ObjectProperty<BigDecimal> vlrFreteTaxaProperty() {
        return vlrFreteTaxa;
    }

    public void setVlrFreteTaxa(BigDecimal vlrFreteTaxa) {
        this.vlrFreteTaxa.set(vlrFreteTaxa);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Usuario getUsuarioCadastro() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(Usuario usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
    }

    @CreationTimestamp
    @Column(nullable = false)
    public LocalDateTime getDtCadastro() {
        return dtCadastro.get();
    }

    public ObjectProperty<LocalDateTime> dtCadastroProperty() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDateTime dtCadastro) {
        this.dtCadastro.set(dtCadastro);
    }

    @Column(length = 15, nullable = false)
    public String getDocEntrada() {
        return docEntrada.get();
    }

    public StringProperty docEntradaProperty() {
        return docEntrada;
    }

    public void setDocEntrada(String docEntrada) {
        this.docEntrada.set(docEntrada);
    }

    @Column(length = 44, nullable = false)
    public String getDocEntradaChaveNFe() {
        return docEntradaChaveNFe.get();
    }

    public StringProperty docEntradaChaveNFeProperty() {
        return docEntradaChaveNFe;
    }

    public void setDocEntradaChaveNFe(String docEntradaChaveNFe) {
        this.docEntradaChaveNFe.set(docEntradaChaveNFe);
    }

    @Override
    public String toString() {
        return "ProdutoEstoque{" +
                "id=" + id +
                ", produto=" + produto +
                ", qtd=" + qtd +
                ", lote=" + lote +
                ", validade=" + validade +
                ", vlrBruto=" + vlrBruto +
                ", vlrFreteBruto=" + vlrFreteBruto +
                ", vlrImpostoNaEntrada=" + vlrImpostoNaEntrada +
                ", vlrImpostoFreteNaEntrada=" + vlrImpostoFreteNaEntrada +
                ", vlrImpostoDentroFrete=" + vlrImpostoDentroFrete +
                ", vlrFreteTaxa=" + vlrFreteTaxa +
                ", usuarioCadastro=" + usuarioCadastro +
                ", dtCadastro=" + dtCadastro +
                ", docEntrada=" + docEntrada +
                ", docEntradaChaveNFe=" + docEntradaChaveNFe +
                '}';
    }
}