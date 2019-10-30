package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.SituacaoCadastroEmpresa;
import br.com.tlmacedo.cafeperfeito.model.enums.TipoEndereco;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;

@Entity(name = "Empresa")
@Table(name = "empresa")
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongProperty id = new SimpleLongProperty();
    private BooleanProperty pessoaJuridica = new SimpleBooleanProperty();
    private SituacaoCadastroEmpresa situacao;
    private StringProperty cnpj = new SimpleStringProperty();
    private StringProperty ie = new SimpleStringProperty();
    private StringProperty razao = new SimpleStringProperty();
    private StringProperty fantasia = new SimpleStringProperty();
    private BooleanProperty cliente = new SimpleBooleanProperty();
    private BooleanProperty fornecedor = new SimpleBooleanProperty();
    private BooleanProperty transportadora = new SimpleBooleanProperty();

    private ObjectProperty<Usuario> usuarioCadastro = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> dtCadastro = new SimpleObjectProperty<>(null);
    private ObjectProperty<Usuario> usuarioAtualizacao = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> dtAtualizacao = new SimpleObjectProperty<>();
    private StringProperty observacoes = new SimpleStringProperty();
    private ObjectProperty<BigDecimal> limite = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private IntegerProperty prazo = new SimpleIntegerProperty(0);
    private BooleanProperty prazoDiaUtil = new SimpleBooleanProperty(false);
    private StringProperty iSuframa = new SimpleStringProperty();
    private StringProperty iMunicpipal = new SimpleStringProperty();

    private List<EmpresaCondicoes> empresaCondicoes = new ArrayList<>();

    private List<Endereco> enderecoList = new ArrayList<>();
    private List<Telefone> telefoneList = new ArrayList<>();
    private List<EmailHomePage> emailHomePageList = new ArrayList<>();

    private ObjectProperty<BigDecimal> limiteUtilizado = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<LocalDate> dtUltimoPedido = new SimpleObjectProperty<>(null);
    private ObjectProperty<BigDecimal> vlrUltimoPedido = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private IntegerProperty qtdPedidos = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> vlrTickeMedio = new SimpleObjectProperty<>(BigDecimal.ZERO);

    public Empresa() {
    }

    public Empresa(Empresa empresa) {
        this.idProperty().setValue(0);
        this.razaoProperty().setValue("");
        this.fantasiaProperty().setValue("");
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

    @Column(length = 1, nullable = false)
    public boolean isPessoaJuridica() {
        return pessoaJuridica.get();
    }

    public BooleanProperty pessoaJuridicaProperty() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(boolean pessoaJuridica) {
        this.pessoaJuridica.set(pessoaJuridica);
    }

    @Enumerated(EnumType.ORDINAL)
    public SituacaoCadastroEmpresa getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoCadastroEmpresa situacao) {
        this.situacao = situacao;
    }

    @Column(length = 14, nullable = false)
    public String getCnpj() {
        return cnpj.get();
    }

    public StringProperty cnpjProperty() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj.set(cnpj);
    }

    @Column(length = 14)
    public String getIe() {
        return ie.get();
    }

    public StringProperty ieProperty() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie.set(ie);
    }

    @Column(length = 80, nullable = false)
    public String getRazao() {
        return razao.get();
    }

    public StringProperty razaoProperty() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao.set(razao);
    }

    @Column(length = 80, nullable = false)
    public String getFantasia() {
        return fantasia.get();
    }

    public StringProperty fantasiaProperty() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia.set(fantasia);
    }

    @Column(length = 1, nullable = false)
    public boolean isCliente() {
        return cliente.get();
    }

    public BooleanProperty clienteProperty() {
        return cliente;
    }

    public void setCliente(boolean cliente) {
        this.cliente.set(cliente);
    }

    @Column(length = 1, nullable = false)
    public boolean isFornecedor() {
        return fornecedor.get();
    }

    public BooleanProperty fornecedorProperty() {
        return fornecedor;
    }

    public void setFornecedor(boolean fornecedor) {
        this.fornecedor.set(fornecedor);
    }

    @Column(length = 1, nullable = false)
    public boolean isTransportadora() {
        return transportadora.get();
    }

    public BooleanProperty transportadoraProperty() {
        return transportadora;
    }

    public void setTransportadora(boolean transportadora) {
        this.transportadora.set(transportadora);
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    public Usuario getUsuarioCadastro() {
        return usuarioCadastro.get();
    }

    public ObjectProperty<Usuario> usuarioCadastroProperty() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(Usuario usuarioCadastro) {
        this.usuarioCadastro.set(usuarioCadastro);
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    public Usuario getUsuarioAtualizacao() {
        return usuarioAtualizacao.get();
    }

    public ObjectProperty<Usuario> usuarioAtualizacaoProperty() {
        return usuarioAtualizacao;
    }

    public void setUsuarioAtualizacao(Usuario usuarioAtualizacao) {
        this.usuarioAtualizacao.set(usuarioAtualizacao);
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

    @Column(length = 1500)
    public String getObservacoes() {
        return observacoes.get();
    }

    public StringProperty observacoesProperty() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes.set(observacoes);
    }

    @Column(length = 19, scale = 4, nullable = false)
    public BigDecimal getLimite() {
        return limite.get();
    }

    public ObjectProperty<BigDecimal> limiteProperty() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite.set(limite);
    }

    @Column(length = 3, nullable = false)
    public int getPrazo() {
        return prazo.get();
    }

    public IntegerProperty prazoProperty() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo.set(prazo);
    }

    @Column(length = 1, nullable = false)
    public boolean isPrazoDiaUtil() {
        return prazoDiaUtil.get();
    }

    public BooleanProperty prazoDiaUtilProperty() {
        return prazoDiaUtil;
    }

    public void setPrazoDiaUtil(boolean prazoDiaUtil) {
        this.prazoDiaUtil.set(prazoDiaUtil);
    }

    @Column(length = 9)
    public String getiSuframa() {
        return iSuframa.get();
    }

    public StringProperty iSuframaProperty() {
        return iSuframa;
    }

    public void setiSuframa(String iSuframa) {
        this.iSuframa.set(iSuframa);
    }

    @Column(length = 15)
    public String getiMunicpipal() {
        return iMunicpipal.get();
    }

    public StringProperty iMunicpipalProperty() {
        return iMunicpipal;
    }

    public void setiMunicpipal(String iMunicpipal) {
        this.iMunicpipal.set(iMunicpipal);
    }

    @JsonIgnore
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<EmpresaCondicoes> getEmpresaCondicoes() {
        return empresaCondicoes;
    }

    public void setEmpresaCondicoes(List<EmpresaCondicoes> empresaCondicoes) {
        this.empresaCondicoes = empresaCondicoes;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Endereco> getEnderecoList() {
        return enderecoList;
    }

    public void setEnderecoList(List<Endereco> enderecoList) {
        this.enderecoList = enderecoList;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Telefone> getTelefoneList() {
        return telefoneList;
    }

    public void setTelefoneList(List<Telefone> telefoneList) {
        this.telefoneList = telefoneList;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<EmailHomePage> getEmailHomePageList() {
        return emailHomePageList;
    }

    public void setEmailHomePageList(List<EmailHomePage> emailHomePageList) {
        this.emailHomePageList = emailHomePageList;
    }

    @Transient
    public String getRazaoFantasia() {
        return String.format("%s (%s)",
                razaoProperty().getValue(),
                fantasiaProperty().getValue()
        );
    }

    @Transient
    public BigDecimal getLimiteUtilizado() {
        return limiteUtilizado.get();
    }

    public ObjectProperty<BigDecimal> limiteUtilizadoProperty() {
        return limiteUtilizado;
    }

    public void setLimiteUtilizado(BigDecimal limiteUtilizado) {
        this.limiteUtilizado.set(limiteUtilizado);
    }

    @Transient
    public LocalDate getDtUltimoPedido() {
        return dtUltimoPedido.get();
    }

    public ObjectProperty<LocalDate> dtUltimoPedidoProperty() {
        return dtUltimoPedido;
    }

    public void setDtUltimoPedido(LocalDate dtUltimoPedido) {
        this.dtUltimoPedido.set(dtUltimoPedido);
    }

    @Transient
    public BigDecimal getVlrUltimoPedido() {
        return vlrUltimoPedido.get();
    }

    public ObjectProperty<BigDecimal> vlrUltimoPedidoProperty() {
        return vlrUltimoPedido;
    }

    public void setVlrUltimoPedido(BigDecimal vlrUltimoPedido) {
        this.vlrUltimoPedido.set(vlrUltimoPedido);
    }

    @Transient
    public int getQtdPedidos() {
        return qtdPedidos.get();
    }

    public IntegerProperty qtdPedidosProperty() {
        return qtdPedidos;
    }

    public void setQtdPedidos(int qtdPedidos) {
        this.qtdPedidos.set(qtdPedidos);
    }

    @Transient
    public BigDecimal getVlrTickeMedio() {
        return vlrTickeMedio.get();
    }

    public ObjectProperty<BigDecimal> vlrTickeMedioProperty() {
        return vlrTickeMedio;
    }

    public void setVlrTickeMedio(BigDecimal vlrTickeMedio) {
        this.vlrTickeMedio.set(vlrTickeMedio);
    }

    @Transient
    @JsonIgnore
    public String getxNome(int tamanho) {
        String xNome = String.format("%s%s", getRazao(),
                !getFantasia().equals("")
                        ? String.format(" (%s)", getFantasia())
                        : "");
        if (xNome.length() > tamanho)
            xNome = xNome.substring(0, tamanho);
        return xNome;
    }

    @Transient
    @JsonIgnore
    public String getRazaoFantasia(int tamanho) {
        if (getRazaoFantasia().length() > tamanho)
            return getRazaoFantasia().substring(0, tamanho);
        return getRazaoFantasia();
    }

    @Transient
    @JsonIgnore
    public String getRazao(int tamanho) {
        if (getRazao().length() > tamanho)
            return getRazao().substring(0, tamanho);
        return getRazao();
    }

    @Transient
    @JsonIgnore
    public String getFantasia(int tamanho) {
        if (getFantasia().length() > tamanho)
            return getFantasia().substring(0, tamanho);
        return getFantasia();
    }

    @Transient
    @JsonIgnore
    public Endereco getEndereco(TipoEndereco tipoEndereco) {
        if (tipoEndereco == null)
            tipoEndereco = TipoEndereco.PRINCIPAL;
        TipoEndereco finalTipoEndereco = tipoEndereco;
        return getEnderecoList().stream()
                .filter(endereco -> endereco.getTipo() == finalTipoEndereco)
                .findFirst().orElse(null);
    }

    @Transient
    @JsonIgnore
    public String getEndereco(Endereco end) {
        if (end == null)
            return "";
        return String.format("%s, %s - %s",
                end.logradouroProperty().getValue(),
                end.numeroProperty().getValue(),
                end.bairroProperty().getValue());
    }

    @Transient
    @JsonIgnore
    public String getEnderecoPrincipal() {
        return getEndereco(getEndereco(TipoEndereco.PRINCIPAL));
    }

    @Transient
    @JsonIgnore
    public String getEnderecoEntrega() {
        return getEndereco(getEndereco(TipoEndereco.ENTREGA));
    }

    @Transient
    @JsonIgnore
    public String getMunicipio() {
        Endereco end;
        if ((end = getEndereco(TipoEndereco.PRINCIPAL)) == null) return TCONFIG.getInfLoja().getMunicipio();
        return end.getMunicipio().getDescricao();
    }

    @Transient
    @JsonIgnore
    public String getUf() {
        Endereco end;
        if ((end = getEndereco(TipoEndereco.PRINCIPAL)) == null) return TCONFIG.getInfLoja().getUf();
        return end.getMunicipio().getUf().getSigla();
    }

    @Transient
    @JsonIgnore
    public String getFonePrincipal() {
        Telefone tel = getTelefoneList().stream()
                .filter(telefone -> telefone.isPrincipal())
                .findFirst().orElse(getTelefoneList().stream().findFirst().orElse(null));
        return tel == null ? "" : tel.getDescricao();
    }

    @Transient
    @JsonIgnore
    public String getEmailPrincipal() {
        EmailHomePage mail = getEmailHomePageList().stream()
                .sorted(Comparator.comparing(EmailHomePage::getTipoEmailHomePage).reversed())
                .sorted(Comparator.comparing(EmailHomePage::isPrincipal).reversed())
                .sorted(Comparator.comparing(EmailHomePage::getId))
                .findFirst().orElse(null);
        if (mail == null)
            return "";
        return mail.descricaoProperty().getValue();
    }

    @Override
    public String toString() {
        if (razaoProperty().get() == null || razaoProperty().getValue().equals(""))
            return "";
        else
            return String.format(
                    "%s (%s)",
                    razaoProperty().get(),
                    fantasiaProperty().get()
            );
    }
}
