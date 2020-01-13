package br.com.tlmacedo.cafeperfeito.model.vo;

import javafx.beans.property.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Entity(name = "FichaKardex")
@Table(name = "ficha_kardex")
public class FichaKardex implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private ObjectProperty<Produto> produto = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> dtMovimento = new SimpleObjectProperty<>();
    private StringProperty documento = new SimpleStringProperty();
    private StringProperty detalhe = new SimpleStringProperty();
    private IntegerProperty qtd = new SimpleIntegerProperty();
    private ObjectProperty<BigDecimal> vlrUnitario = new SimpleObjectProperty<>();
    private IntegerProperty qtdEntrada = new SimpleIntegerProperty();
    private ObjectProperty<BigDecimal> vlrEntrada = new SimpleObjectProperty<>();
    private IntegerProperty qtdSaida = new SimpleIntegerProperty();
    private ObjectProperty<BigDecimal> vlrSaida = new SimpleObjectProperty<>();
    private IntegerProperty saldo = new SimpleIntegerProperty();
    private ObjectProperty<BigDecimal> vlrSaldo = new SimpleObjectProperty<>();

    public FichaKardex() {
    }

    public FichaKardex(EntradaProdutoProduto entradaProdutoProduto) {
        this.produto = entradaProdutoProduto.produtoProperty();
        this.documento = entradaProdutoProduto.entradaProdutoProperty().getValue()
                .entradaNfeProperty().getValue().numeroProperty();
        this.detalhe = entradaProdutoProduto.loteProperty();
        this.qtd = entradaProdutoProduto.qtdProperty();
        this.vlrUnitario = new SimpleObjectProperty<>(((entradaProdutoProduto.vlrBrutoProperty().getValue()
                .add(entradaProdutoProduto.vlrImpostoProperty().getValue())
                .add(entradaProdutoProduto.vlrFreteProperty().getValue()))
                .subtract(entradaProdutoProduto.vlrDescontoProperty().getValue()))
                .divide(new BigDecimal(qtdProperty().getValue())));
        this.qtdEntrada = entradaProdutoProduto.qtdProperty();
        this.vlrEntrada = new SimpleObjectProperty<>(vlrUnitarioProperty().getValue()
                .multiply(new BigDecimal(qtdProperty().getValue())));
        this.qtdSaida = new SimpleIntegerProperty(0);
        this.vlrSaida = new SimpleObjectProperty<>(BigDecimal.ZERO);
        this.saldo = new SimpleIntegerProperty(entradaProdutoProduto.produtoProperty().getValue()
                .getProdutoEstoqueList().stream().collect(Collectors.summingInt(ProdutoEstoque::getQtd)));
        this.vlrSaldo = new SimpleObjectProperty<>(
                entradaProdutoProduto.produtoProperty().getValue()
                        .getProdutoEstoqueList().stream().filter(stq -> stq.qtdProperty().getValue() > 0)
                        .map(stq ->
                                ((stq.vlrUnitarioProperty().getValue()
                                        .add(stq.vlrFreteProperty().getValue())
                                        .add(stq.vlrImpostoProperty().getValue())
                                        .subtract(stq.vlrDescontoProperty().getValue())))
                                        .multiply(BigDecimal.valueOf(stq.qtdProperty().getValue())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
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

    @ManyToOne(fetch = FetchType.LAZY)
    public Produto getProduto() {
        return produto.get();
    }

    public ObjectProperty<Produto> produtoProperty() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto.set(produto);
    }

    @CreationTimestamp
    public LocalDateTime getDtMovimento() {
        return dtMovimento.get();
    }

    public ObjectProperty<LocalDateTime> dtMovimentoProperty() {
        return dtMovimento;
    }

    public void setDtMovimento(LocalDateTime dtMovimento) {
        this.dtMovimento.set(dtMovimento);
    }

    @Column(length = 44, nullable = false)
    public String getDocumento() {
        return documento.get();
    }

    public StringProperty documentoProperty() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento.set(documento);
    }

    @Column(length = 20, nullable = false)
    public String getDetalhe() {
        return detalhe.get();
    }

    public StringProperty detalheProperty() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe.set(detalhe);
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

    @Column(length = 19, scale = 4, nullable = false)
    public BigDecimal getVlrUnitario() {
        return vlrUnitario.get();
    }

    public ObjectProperty<BigDecimal> vlrUnitarioProperty() {
        return vlrUnitario;
    }

    public void setVlrUnitario(BigDecimal vlrUnitario) {
        this.vlrUnitario.set(vlrUnitario);
    }

    @Column(length = 5, nullable = false)
    public int getQtdEntrada() {
        return qtdEntrada.get();
    }

    public IntegerProperty qtdEntradaProperty() {
        return qtdEntrada;
    }

    public void setQtdEntrada(int qtdEntrada) {
        this.qtdEntrada.set(qtdEntrada);
    }

    @Column(length = 19, scale = 4, nullable = false)
    public BigDecimal getVlrEntrada() {
        return vlrEntrada.get();
    }

    public ObjectProperty<BigDecimal> vlrEntradaProperty() {
        return vlrEntrada;
    }

    public void setVlrEntrada(BigDecimal vlrEntrada) {
        this.vlrEntrada.set(vlrEntrada);
    }

    @Column(length = 5, nullable = false)
    public int getQtdSaida() {
        return qtdSaida.get();
    }

    public IntegerProperty qtdSaidaProperty() {
        return qtdSaida;
    }

    public void setQtdSaida(int qtdSaida) {
        this.qtdSaida.set(qtdSaida);
    }

    @Column(length = 19, scale = 4, nullable = false)
    public BigDecimal getVlrSaida() {
        return vlrSaida.get();
    }

    public ObjectProperty<BigDecimal> vlrSaidaProperty() {
        return vlrSaida;
    }

    public void setVlrSaida(BigDecimal vlrSaida) {
        this.vlrSaida.set(vlrSaida);
    }

    @Column(length = 5, nullable = false)
    public int getSaldo() {
        return saldo.get();
    }

    public IntegerProperty saldoProperty() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo.set(saldo);
    }

    @Column(length = 19, scale = 4, nullable = false)
    public BigDecimal getVlrSaldo() {
        return vlrSaldo.get();
    }

    public ObjectProperty<BigDecimal> vlrSaldoProperty() {
        return vlrSaldo;
    }

    public void setVlrSaldo(BigDecimal vlrSaldo) {
        this.vlrSaldo.set(vlrSaldo);
    }

    @Override
    public String toString() {
        return "FichaKardex{" +
                "id=" + id +
                ", produto=" + produto +
                ", dtMovimento=" + dtMovimento +
                ", documento=" + documento +
                ", detalhe=" + detalhe +
                ", qtd=" + qtd +
                ", vlrUnitario=" + vlrUnitario +
                ", qtdEntrada=" + qtdEntrada +
                ", vlrEntrada=" + vlrEntrada +
                ", qtdSaida=" + qtdSaida +
                ", vlrSaida=" + vlrSaida +
                ", saldo=" + saldo +
                ", vlrSaldo=" + vlrSaldo +
                '}';
    }
}
