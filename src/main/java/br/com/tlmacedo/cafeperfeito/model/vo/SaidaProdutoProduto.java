package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.dao.ProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.TipoSaidaProduto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "SaidaProdutoProduto")
@Table(name = "saida_produto_produto")
public class SaidaProdutoProduto implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private SaidaProduto saidaProduto = new SaidaProduto();
    private LongProperty idProd = new SimpleLongProperty();
    private StringProperty codigo = new SimpleStringProperty();
    private StringProperty descricao = new SimpleStringProperty();
    private TipoSaidaProduto tipoSaidaProduto;
    private StringProperty lote = new SimpleStringProperty();
    private ObjectProperty<LocalDate> dtValidade = new SimpleObjectProperty<>();

    private IntegerProperty qtd = new SimpleIntegerProperty();
    private ObjectProperty<BigDecimal> vlrVenda = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> vlrBruto = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> vlrDesconto = new SimpleObjectProperty<>();

    private ObjectProperty<BigDecimal> vlrLiquido = new SimpleObjectProperty<>();
    private IntegerProperty estoque = new SimpleIntegerProperty();

    private Produto produto = new Produto();

    public SaidaProdutoProduto() {
    }

    public SaidaProdutoProduto(Integer idProd, Produto produto, TipoSaidaProduto tipSaida, Integer qtd) {
        this.produto = new ProdutoDAO().getById(Produto.class, idProd.longValue());
        this.idProd = getProduto().idProperty();
        this.codigo = getProduto().codigoProperty();
        this.descricao = getProduto().descricaoProperty();
        this.tipoSaidaProduto = tipSaida;
        this.qtd = new SimpleIntegerProperty(qtd == null ? 1 : qtd);

        this.estoque = produto.tblEstoqueProperty();
        this.lote = produto.tblLoteProperty();
        this.dtValidade = produto.tblValidadeProperty();
        this.vlrVenda = getProduto().precoVendaProperty();
        this.vlrDesconto = new SimpleObjectProperty<>(BigDecimal.ZERO.setScale(2));
        if (!tipSaida.equals(TipoSaidaProduto.VENDA)) {
            this.vlrDesconto = new SimpleObjectProperty<>(vlrVendaProperty().getValue().multiply(BigDecimal.valueOf(qtdProperty().getValue())));
        }
        this.vlrBruto = new SimpleObjectProperty<>(vlrVendaProperty().getValue().multiply(BigDecimal.valueOf(qtdProperty().getValue())));
        this.vlrLiquido = new SimpleObjectProperty<>(vlrBrutoProperty().getValue().subtract(vlrDescontoProperty().getValue()));
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
    public SaidaProduto getSaidaProduto() {
        return saidaProduto;
    }

    public void setSaidaProduto(SaidaProduto saidaProduto) {
        this.saidaProduto = saidaProduto;
    }

    @Column(length = 20, nullable = false)
    public long getIdProd() {
        return idProd.get();
    }

    public LongProperty idProdProperty() {
        return idProd;
    }

    public void setIdProd(long idProd) {
        this.idProd.set(idProd);
    }

    @Column(length = 15, nullable = false)
    public String getCodigo() {
        return codigo.get();
    }

    public StringProperty codigoProperty() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }

    @Column(length = 120, nullable = false)
    public String getDescricao() {
        return descricao.get();
    }

    public StringProperty descricaoProperty() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    @Enumerated(EnumType.ORDINAL)
    public TipoSaidaProduto getTipoSaidaProduto() {
        return tipoSaidaProduto;
    }

    public void setTipoSaidaProduto(TipoSaidaProduto tipoSaidaProduto) {
        this.tipoSaidaProduto = tipoSaidaProduto;
    }

    @Column(length = 15)
    public String getLote() {
        return lote.get();
    }

    public StringProperty loteProperty() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote.set(lote);
    }

    public LocalDate getDtValidade() {
        return dtValidade.get();
    }

    public ObjectProperty<LocalDate> dtValidadeProperty() {
        return dtValidade;
    }

    public void setDtValidade(LocalDate dtValidade) {
        this.dtValidade.set(dtValidade);
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
    public BigDecimal getVlrVenda() {
        return vlrVenda.get();
    }

    public ObjectProperty<BigDecimal> vlrVendaProperty() {
        return vlrVenda;
    }

    public void setVlrVenda(BigDecimal vlrVenda) {
        this.vlrVenda.set(vlrVenda);
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
    public BigDecimal getVlrDesconto() {
        return vlrDesconto.get();
    }

    public ObjectProperty<BigDecimal> vlrDescontoProperty() {
        return vlrDesconto;
    }

    public void setVlrDesconto(BigDecimal vlrDesconto) {
        this.vlrDesconto.set(vlrDesconto);
    }

    @Transient
    public int getEstoque() {
        return estoque.get();
    }

    public IntegerProperty estoqueProperty() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque.set(estoque);
    }

    @JsonIgnore
    @Transient
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Transient
    public BigDecimal getVlrLiquido() {
        return vlrLiquido.get();
    }

    @Transient
    public BigDecimal getValorLiquido() {
        return vlrBrutoProperty().getValue().subtract(vlrDescontoProperty().getValue());
    }

    public ObjectProperty<BigDecimal> vlrLiquidoProperty() {
        return vlrLiquido;
    }

    public void setVlrLiquido(BigDecimal vlrLiquido) {
        this.vlrLiquido.set(vlrLiquido);
    }

//    @Override
//    public String toString() {
//        return "SaidaProdutoProduto{" +
//                "produto=" + produto +
//                '}';
//    }

    @Override
    public String toString() {
        return "SaidaProdutoProduto{" +
                "id=" + id +
                ", saidaProduto=" + saidaProduto +
                ", idProd=" + idProd +
                ", codigo=" + codigo +
                ", descricao=" + descricao +
                ", tipoSaidaProduto=" + tipoSaidaProduto +
                ", lote=" + lote +
                ", dtValidade=" + dtValidade +
                ", qtd=" + qtd +
                ", vlrVenda=" + vlrVenda +
                ", vlrBruto=" + vlrBruto +
                ", vlrDesconto=" + vlrDesconto +
                ", vlrLiquido=" + vlrLiquido +
                ", estoque=" + estoque +
                ", produto=" + produto +
                '}';
    }
}
