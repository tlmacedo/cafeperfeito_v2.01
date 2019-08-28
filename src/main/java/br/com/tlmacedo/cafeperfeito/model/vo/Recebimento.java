package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoSituacao;
import javafx.beans.property.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "Recebimento")
@Table(name = "recebimento")
public class Recebimento implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private ContasAReceber aReceber = new ContasAReceber();
    private PagamentoSituacao pagamentoSituacao;
    private StringProperty documento = new SimpleStringProperty();
    private ObjectProperty<LocalDate> dtVencimento = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> valor = new SimpleObjectProperty<>();

    private ObjectProperty<BigDecimal> vlrPago = new SimpleObjectProperty<>();
    private Usuario usarioPagamento = new Usuario();
    private ObjectProperty<LocalDate> dtPagamento = new SimpleObjectProperty<>();

    private Empresa emissorRecibo;

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
        return aReceber;
    }

    public void setaReceber(ContasAReceber aReceber) {
        this.aReceber = aReceber;
    }

    @Enumerated(EnumType.ORDINAL)
    public PagamentoSituacao getPagamentoSituacao() {
        return pagamentoSituacao;
    }

    public void setPagamentoSituacao(PagamentoSituacao pagamentoSituacao) {
        this.pagamentoSituacao = pagamentoSituacao;
    }

    @Column(length = 15, nullable = false)
    public String getDocumento() {
        return documento.get();
    }

    public StringProperty documentoProperty() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento.set(documento);
    }

    @Column(nullable = false)
    public LocalDate getDtVencimento() {
        return dtVencimento.get();
    }

    public ObjectProperty<LocalDate> dtVencimentoProperty() {
        return dtVencimento;
    }

    public void setDtVencimento(LocalDate dtVencimento) {
        this.dtVencimento.set(dtVencimento);
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

    @Column(length = 19, scale = 4, nullable = false)
    public BigDecimal getVlrPago() {
        return vlrPago.get();
    }

    public ObjectProperty<BigDecimal> vlrPagoProperty() {
        return vlrPago;
    }

    public void setVlrPago(BigDecimal vlrPago) {
        this.vlrPago.set(vlrPago);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Usuario getUsarioPagamento() {
        return usarioPagamento;
    }

    public void setUsarioPagamento(Usuario usarioPagamento) {
        this.usarioPagamento = usarioPagamento;
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

    @ManyToOne(fetch = FetchType.LAZY)
    public Empresa getEmissorRecibo() {
        return emissorRecibo;
    }

    public void setEmissorRecibo(Empresa emissorRecibo) {
        this.emissorRecibo = emissorRecibo;
    }
}
