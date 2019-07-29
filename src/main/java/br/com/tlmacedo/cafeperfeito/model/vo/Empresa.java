package br.com.tlmacedo.cafeperfeito.model.vo;

import javafx.beans.property.*;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "Empresa")
@Table(name = "empresa")
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongProperty id = new SimpleLongProperty();
    private BooleanProperty pessoaJuridica = new SimpleBooleanProperty();
    private IntegerProperty situacao = new SimpleIntegerProperty();
    private StringProperty cnpj = new SimpleStringProperty();
    private StringProperty ie = new SimpleStringProperty();
    private StringProperty razao = new SimpleStringProperty();
    private StringProperty fantasia = new SimpleStringProperty();
    private BooleanProperty cliente = new SimpleBooleanProperty();
    private BooleanProperty fornecedor = new SimpleBooleanProperty();
    private BooleanProperty transportadora = new SimpleBooleanProperty();

    private Usuario usuarioCadastro = new Usuario();
    private ObjectProperty<LocalDateTime> dtCadastro = new SimpleObjectProperty<>();
    private Usuario usuarioAtualizacao = new Usuario();
    private ObjectProperty<LocalDateTime> dtAtualizacao = new SimpleObjectProperty<>();

    public Empresa() {
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

    @Column(length = 2, nullable = false)
    public int getSituacao() {
        return situacao.get();
    }

    public IntegerProperty situacaoProperty() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao.set(situacao);
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
    @ManyToOne
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

    @JsonIgnore
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

    @Override
    public String toString() {
        return String.format(
                "%s (%s)",
                razaoProperty().get(),
                fantasiaProperty().get()
        );
    }
}
