package br.com.tlmacedo.cafeperfeito.model.vo;

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
    private Empresa cliente = new Empresa();
    private Usuario vendedor = new Usuario();
    private ObjectProperty<LocalDateTime> dtCadastro = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> dtSaida = new SimpleObjectProperty<>();

    private List<SaidaProdutoProduto> saidaProdutoProdutoList = new ArrayList<>();

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

    @ManyToOne
    public Empresa getCliente() {
        return cliente;
    }

    public void setCliente(Empresa cliente) {
        this.cliente = cliente;
    }

    @ManyToOne
    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
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

    @OneToMany(mappedBy = "saidaProduto", cascade = CascadeType.ALL)
    public List<SaidaProdutoProduto> getSaidaProdutoProdutoList() {
        return saidaProdutoProdutoList;
    }

    public void setSaidaProdutoProdutoList(List<SaidaProdutoProduto> saidaProdutoProdutoList) {
        this.saidaProdutoProdutoList = saidaProdutoProdutoList;
    }

//    public List<SaidaProdutoProduto> getSaidaProdutoProdutoList() {
//        return saidaProdutoProdutoList;
//    }
//
//    public void setSaidaProdutoProdutoList(List<SaidaProdutoProduto> saidaProdutoProdutoList) {
//        this.saidaProdutoProdutoList = saidaProdutoProdutoList;
//    }

}
