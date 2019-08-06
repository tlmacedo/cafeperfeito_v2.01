package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.SituacaoProduto;
import br.com.tlmacedo.cafeperfeito.model.enums.UndComercialProduto;
import javafx.beans.property.*;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDateTime;

@Entity(name = "Produto")
@Table(name = "produto")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private StringProperty codigo = new SimpleStringProperty();
    private StringProperty descricao = new SimpleStringProperty();
    private ObjectProperty<BigDecimal> peso = new SimpleObjectProperty<>();
    private IntegerProperty unidadeComercial = new SimpleIntegerProperty();
    private IntegerProperty situacao = new SimpleIntegerProperty();
    private ObjectProperty<BigDecimal> precoFabrica = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> precoConsumidor = new SimpleObjectProperty<>();
    private IntegerProperty varejo = new SimpleIntegerProperty();
    private ObjectProperty<BigDecimal> ultImpostoSefaz = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> ultFrete = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> comissao = new SimpleObjectProperty<>();

    private Usuario usuarioCadastro = new Usuario();
    private ObjectProperty<LocalDateTime> dtCadastro = new SimpleObjectProperty<>();
    private Usuario usuarioAtualizacao = new Usuario();
    private ObjectProperty<LocalDateTime> dtAtualizacao = new SimpleObjectProperty<>();


    private StringProperty nfeGenero = new SimpleStringProperty();
    private StringProperty ncm = new SimpleStringProperty();
    private StringProperty cest = new SimpleStringProperty();
    private FiscalCstOrigem fiscalCstOrigem = new FiscalCstOrigem();
    private FiscalIcms fiscalIcms = new FiscalIcms();
    private FiscalPisCofins fiscalPis = new FiscalPisCofins();
    private FiscalPisCofins fiscalCofins = new FiscalPisCofins();

    private Blob imgProduto, imgProdutoBack;

//    private LongProperty estoque_id = new SimpleLongProperty();
//    private IntegerProperty estoque = new SimpleIntegerProperty();
//    private StringProperty lote = new SimpleStringProperty();
//    private ObjectProperty<LocalDate> validade = new SimpleObjectProperty<>();
//    private StringProperty notaEntrada = new SimpleStringProperty();

    public Produto() {
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

    @Column(length = 15, nullable = false, unique = true)
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

    @Column(length = 19, scale = 3, nullable = false)
    public BigDecimal getPeso() {
        return peso.get();
    }

    public ObjectProperty<BigDecimal> pesoProperty() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso.set(peso);
    }

    @Column(length = 2, nullable = false)
    public UndComercialProduto getUnidadeComercial() {
        return UndComercialProduto.toEnum(unidadeComercial.get());
    }

    public IntegerProperty unidadeComercialProperty() {
        return unidadeComercial;
    }

    public void setUnidadeComercial(UndComercialProduto unidadeComercial) {
        this.unidadeComercial.set(unidadeComercial.getCod());
    }

    @Column(length = 2, nullable = false)
    public SituacaoProduto getSituacao() {
        return SituacaoProduto.toEnum(situacao.get());
    }

    public IntegerProperty situacaoProperty() {
        return situacao;
    }

    public void setSituacao(SituacaoProduto situacao) {
        this.situacao.set(situacao.getCod());
    }

    @Column(length = 19, scale = 2, nullable = false)
    public BigDecimal getPrecoFabrica() {
        return precoFabrica.get();
    }

    public ObjectProperty<BigDecimal> precoFabricaProperty() {
        return precoFabrica;
    }

    public void setPrecoFabrica(BigDecimal precoFabrica) {
        this.precoFabrica.set(precoFabrica);
    }

    @Column(length = 19, scale = 2, nullable = false)
    public BigDecimal getPrecoConsumidor() {
        return precoConsumidor.get();
    }

    public ObjectProperty<BigDecimal> precoConsumidorProperty() {
        return precoConsumidor;
    }

    public void setPrecoConsumidor(BigDecimal precoConsumidor) {
        this.precoConsumidor.set(precoConsumidor);
    }

    @Column(length = 2, nullable = false)
    public int getVarejo() {
        return varejo.get();
    }

    public IntegerProperty varejoProperty() {
        return varejo;
    }

    public void setVarejo(int varejo) {
        this.varejo.set(varejo);
    }

    @Column(length = 19, scale = 2, nullable = false)
    public BigDecimal getUltImpostoSefaz() {
        return ultImpostoSefaz.get();
    }

    public ObjectProperty<BigDecimal> ultImpostoSefazProperty() {
        return ultImpostoSefaz;
    }

    public void setUltImpostoSefaz(BigDecimal ultImpostoSefaz) {
        this.ultImpostoSefaz.set(ultImpostoSefaz);
    }

    @Column(length = 19, scale = 2, nullable = false)
    public BigDecimal getUltFrete() {
        return ultFrete.get();
    }

    public ObjectProperty<BigDecimal> ultFreteProperty() {
        return ultFrete;
    }

    public void setUltFrete(BigDecimal ultFrete) {
        this.ultFrete.set(ultFrete);
    }

    @Column(length = 19, scale = 2, nullable = false)
    public BigDecimal getComissao() {
        return comissao.get();
    }

    public ObjectProperty<BigDecimal> comissaoProperty() {
        return comissao;
    }

    public void setComissao(BigDecimal comissao) {
        this.comissao.set(comissao);
    }

    @ManyToOne
    @Column(nullable = false)
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

    @ManyToOne
    public Usuario getUsuarioAtualizacao() {
        return usuarioAtualizacao;
    }

    public void setUsuarioAtualizacao(Usuario usuarioAtualizacao) {
        this.usuarioAtualizacao = usuarioAtualizacao;
    }

    @UpdateTimestamp
    public LocalDateTime getDtAtualizacao() {
        return dtAtualizacao.get();
    }

    public ObjectProperty<LocalDateTime> dtAtualizacaoProperty() {
        return dtAtualizacao;
    }

    public void setDtAtualizacao(LocalDateTime dtAtualizacao) {
        this.dtAtualizacao.set(dtAtualizacao);
    }

    @Column(length = 2, nullable = false)
    public String getNfeGenero() {
        return nfeGenero.get();
    }

    public StringProperty nfeGeneroProperty() {
        return nfeGenero;
    }

    public void setNfeGenero(String nfeGenero) {
        this.nfeGenero.set(nfeGenero);
    }

    @Column(length = 8)
    public String getNcm() {
        return ncm.get();
    }

    public StringProperty ncmProperty() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm.set(ncm);
    }

    @Column(length = 7)
    public String getCest() {
        return cest.get();
    }

    public StringProperty cestProperty() {
        return cest;
    }

    public void setCest(String cest) {
        this.cest.set(cest);
    }

    @ManyToOne
    public FiscalCstOrigem getFiscalCstOrigem() {
        return fiscalCstOrigem;
    }

    public void setFiscalCstOrigem(FiscalCstOrigem fiscalCstOrigem) {
        this.fiscalCstOrigem = fiscalCstOrigem;
    }

    @ManyToOne
    public FiscalIcms getFiscalIcms() {
        return fiscalIcms;
    }

    public void setFiscalIcms(FiscalIcms fiscalIcms) {
        this.fiscalIcms = fiscalIcms;
    }

    @ManyToOne
    public FiscalPisCofins getFiscalPis() {
        return fiscalPis;
    }

    public void setFiscalPis(FiscalPisCofins fiscalPis) {
        this.fiscalPis = fiscalPis;
    }

    @ManyToOne
    public FiscalPisCofins getFiscalCofins() {
        return fiscalCofins;
    }

    public void setFiscalCofins(FiscalPisCofins fiscalCofins) {
        this.fiscalCofins = fiscalCofins;
    }

    @JsonIgnore
    @SuppressWarnings("JpaAttributeTypeInspection")
    public Blob getImgProduto() {
        return imgProduto;
    }

    public void setImgProduto(Blob imgProduto) {
        this.imgProduto = imgProduto;
    }

    @JsonIgnore
    @SuppressWarnings("JpaAttributeTypeInspection")
    public Blob getImgProdutoBack() {
        return imgProdutoBack;
    }

    public void setImgProdutoBack(Blob imgProdutoBack) {
        this.imgProdutoBack = imgProdutoBack;
    }
}
