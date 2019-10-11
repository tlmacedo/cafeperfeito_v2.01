package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoModalidade;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoSituacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Recebimento")
@Table(name = "recebimento")
public class Recebimento implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private ObjectProperty<ContasAReceber> aReceber = new SimpleObjectProperty<>();
    private PagamentoSituacao pagamentoSituacao;
    private StringProperty documento = new SimpleStringProperty();
    private PagamentoModalidade pagamentoModalidade;
    private ObjectProperty<BigDecimal> valor = new SimpleObjectProperty<>();

    private ObjectProperty<Usuario> usuarioPagamento = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> dtPagamento = new SimpleObjectProperty<>();

    private ObjectProperty<Usuario> usuarioCadastro = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> dtCadastro = new SimpleObjectProperty<>();

    private ObjectProperty<Empresa> emissorRecibo = new SimpleObjectProperty<>();

    public Recebimento() {
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
    public ContasAReceber getaReceber() {
        return aReceber.get();
    }

    public ObjectProperty<ContasAReceber> aReceberProperty() {
        return aReceber;
    }

    public void setaReceber(ContasAReceber aReceber) {
        this.aReceber.set(aReceber);
    }

    @Enumerated(EnumType.ORDINAL)
    public PagamentoSituacao getPagamentoSituacao() {
        return pagamentoSituacao;
    }

    public void setPagamentoSituacao(PagamentoSituacao pagamentoSituacao) {
        this.pagamentoSituacao = pagamentoSituacao;
    }

    @Column(length = 18, nullable = false)
    public String getDocumento() {
        return documento.get();
    }

    public StringProperty documentoProperty() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento.set(documento);
    }

    @Enumerated(EnumType.ORDINAL)
    public PagamentoModalidade getPagamentoModalidade() {
        return pagamentoModalidade;
    }

    public void setPagamentoModalidade(PagamentoModalidade pagamentoModalidade) {
        this.pagamentoModalidade = pagamentoModalidade;
    }

    @Column(length = 19, scale = 4, nullable = false)
    public BigDecimal getValor() {
        return valor.get();
    }

    public ObjectProperty<BigDecimal> valorProperty() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor.set(valor);
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    public Usuario getUsuarioPagamento() {
        return usuarioPagamento.get();
    }

    public ObjectProperty<Usuario> usuarioPagamentoProperty() {
        return usuarioPagamento;
    }

    public void setUsuarioPagamento(Usuario usuarioPagamento) {
        this.usuarioPagamento.set(usuarioPagamento);
    }

    public LocalDate getDtPagamento() {
        return dtPagamento.get();
    }

    public ObjectProperty<LocalDate> dtPagamentoProperty() {
        return dtPagamento;
    }

    public void setDtPagamento(LocalDate dtPagamento) {
        this.dtPagamento.set(dtPagamento);
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
    public LocalDateTime getDtCadastro() {
        return dtCadastro.get();
    }

    public ObjectProperty<LocalDateTime> dtCadastroProperty() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDateTime dtCadastro) {
        this.dtCadastro.set(dtCadastro);
    }

    @Transient
    public Empresa getEmissorRecibo() {
        return emissorRecibo.get();
    }

    public ObjectProperty<Empresa> emissorReciboProperty() {
        return emissorRecibo;
    }

    public void setEmissorRecibo(Empresa emissorRecibo) {
        this.emissorRecibo.set(emissorRecibo);
    }

    @Override
    public String toString() {
        return "Recebimento{" +
                "id=" + id +
                ", aReceber=" + aReceber +
                ", pagamentoSituacao=" + pagamentoSituacao +
                ", documento=" + documento +
                ", pagamentoModalidade=" + pagamentoModalidade +
                ", valor=" + valor +
                ", usuarioPagamento=" + usuarioPagamento +
                ", dtPagamento=" + dtPagamento +
                ", usuarioCadastro=" + usuarioCadastro +
                ", dtCadastro=" + dtCadastro +
                ", emissorRecibo=" + emissorRecibo +
                '}';
    }
}
