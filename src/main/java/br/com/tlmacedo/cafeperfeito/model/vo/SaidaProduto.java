package br.com.tlmacedo.cafeperfeito.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "SaidaProduto")
@Table(name = "saida_produto")
public class SaidaProduto implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private ObjectProperty<Empresa> cliente = new SimpleObjectProperty<>();
    private ObjectProperty<Usuario> vendedor = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> dtCadastro = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> dtSaida = new SimpleObjectProperty<>();

    private List<SaidaProdutoProduto> saidaProdutoProdutoList = new ArrayList<>();

    private ObjectProperty<SaidaProdutoNfe> saidaProdutoNfe = new SimpleObjectProperty<>();

    public SaidaProduto() {
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
    public Empresa getCliente() {
        return cliente.get();
    }

    public ObjectProperty<Empresa> clienteProperty() {
        return cliente;
    }

    public void setCliente(Empresa cliente) {
        this.cliente.set(cliente);
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    public Usuario getVendedor() {
        return vendedor.get();
    }

    public ObjectProperty<Usuario> vendedorProperty() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor.set(vendedor);
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

    @Column(nullable = false)
    public LocalDate getDtSaida() {
        return dtSaida.get();
    }

    public ObjectProperty<LocalDate> dtSaidaProperty() {
        return dtSaida;
    }

    public void setDtSaida(LocalDate dtSaida) {
        this.dtSaida.set(dtSaida);
    }

    @OneToMany(mappedBy = "saidaProduto", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<SaidaProdutoProduto> getSaidaProdutoProdutoList() {
        return saidaProdutoProdutoList;
    }

    public void setSaidaProdutoProdutoList(List<SaidaProdutoProduto> saidaProdutoProdutoList) {
        this.saidaProdutoProdutoList = saidaProdutoProdutoList;
    }

    @OneToOne(mappedBy = "saidaProduto", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    public SaidaProdutoNfe getSaidaProdutoNfe() {
        return saidaProdutoNfe.get();
    }

    public ObjectProperty<SaidaProdutoNfe> saidaProdutoNfeProperty() {
        return saidaProdutoNfe;
    }

    public void setSaidaProdutoNfe(SaidaProdutoNfe saidaProdutoNfe) {
        this.saidaProdutoNfe.set(saidaProdutoNfe);
    }

    //    public List<SaidaProdutoProduto> getSaidaProdutoProdutoList() {
//        return saidaProdutoProdutoList;
//    }
//
//    public void setSaidaProdutoProdutoList(List<SaidaProdutoProduto> saidaProdutoProdutoList) {
//        this.saidaProdutoProdutoList = saidaProdutoProdutoList;
//    }


    @Override
    public String toString() {
        return "SaidaProduto{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", vendedor=" + vendedor +
                ", dtCadastro=" + dtCadastro +
                ", dtSaida=" + dtSaida +
                ", saidaProdutoProdutoList=" + saidaProdutoProdutoList +
                '}';
    }
}
