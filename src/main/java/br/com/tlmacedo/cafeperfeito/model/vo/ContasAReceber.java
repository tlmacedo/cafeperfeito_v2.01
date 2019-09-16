package br.com.tlmacedo.cafeperfeito.model.vo;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ContasAReceber")
@Table(name = "contas_a_receber")
public class ContasAReceber implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private SaidaProduto saidaProduto = new SaidaProduto();
    private ObjectProperty<LocalDate> dtVencimento = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> valor = new SimpleObjectProperty<>();
    private Usuario usuarioCadastro = new Usuario();
    private ObjectProperty<LocalDateTime> dtCadastro = new SimpleObjectProperty<>();

    private List<Recebimento> recebimentoList = new ArrayList<>();

    private ObjectProperty<BigDecimal> vlrPedido = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> vlrDesc = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> valorPago = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> valorSaldo = new SimpleObjectProperty<>();

    public ContasAReceber() {
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
    public SaidaProduto getSaidaProduto() {
        return saidaProduto;
    }

    public void setSaidaProduto(SaidaProduto saidaProduto) {
        this.saidaProduto = saidaProduto;
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

    @ManyToOne(fetch = FetchType.LAZY)
    public Usuario getUsuarioCadastro() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(Usuario usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
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


    @OneToMany(mappedBy = "aReceber", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Recebimento> getRecebimentoList() {
        return recebimentoList;
    }

    public void setRecebimentoList(List<Recebimento> recebimentoList) {
        this.recebimentoList = recebimentoList;
    }

    @Transient
    public BigDecimal getVlrPedido() {
        return vlrPedido.get();
    }

    public ObjectProperty<BigDecimal> vlrPedidoProperty() {
        return vlrPedido;
    }

    public void setVlrPedido(BigDecimal vlrPedido) {
        this.vlrPedido.set(vlrPedido);
    }

    @Transient
    public BigDecimal getVlrDesc() {
        return vlrDesc.get();
    }

    public ObjectProperty<BigDecimal> vlrDescProperty() {
        return vlrDesc;
    }

    public void setVlrDesc(BigDecimal vlrDesc) {
        this.vlrDesc.set(vlrDesc);
    }

    @Transient
    public BigDecimal getValorPago() {
        return valorPago.get();
    }

    public ObjectProperty<BigDecimal> valorPagoProperty() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago.set(valorPago);
    }

    @Transient
    public BigDecimal getValorSaldo() {
        return valorSaldo.get();
    }

    public ObjectProperty<BigDecimal> valorSaldoProperty() {
        return valorSaldo;
    }

    public void setValorSaldo(BigDecimal valorSaldo) {
        this.valorSaldo.set(valorSaldo);
    }

    @Override
    public String toString() {
        try {
            return "ContasAReceber{" +
                    "id=" + id +
                    ", saidaProduto=" + saidaProduto +
                    ", dtVencimento=" + dtVencimento +
                    ", valor=" + valor +
                    ", usuarioCadastro=" + usuarioCadastro +
                    ", dtCadastro=" + dtCadastro +
                    ", recebimentoList=" + recebimentoList +
                    ", valorPago=" + valorPago +
                    ", valorSaldo=" + valorSaldo +
                    '}';

        } catch (Exception ex) {
            System.out.printf("Deu zebra bem aqui!!!!!!");
            ex.printStackTrace();
            return null;
        }
    }
}
