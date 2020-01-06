package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.model.enums.SituacaoEntrada;
import br.com.tlmacedo.cafeperfeito.service.serialize_deserialize.EntradaProdutoDeserializer;
import br.com.tlmacedo.cafeperfeito.service.serialize_deserialize.EntradaProdutoSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: thiagomacedo
 * Date: 2019-02-26
 * Time: 11:27
 */

@Entity(name = "EntradaProduto")
@Table(name = "entrada_produto")
@JsonSerialize(using = EntradaProdutoSerializer.class)
@JsonDeserialize(using = EntradaProdutoDeserializer.class)
public class EntradaProduto implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private SituacaoEntrada situacao;

    private ObjectProperty<Empresa> loja = new SimpleObjectProperty<>();
    private ObjectProperty<Empresa> fornecedor = new SimpleObjectProperty<>();

    private ObjectProperty<Usuario> usuarioCadastro = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> dtCadastro = new SimpleObjectProperty<>(LocalDateTime.now());

    private List<EntradaProdutoProduto> entradaProdutoProdutoList = new ArrayList<>();

    public EntradaProduto() {
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

    @Enumerated(EnumType.ORDINAL)
    public SituacaoEntrada getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoEntrada situacao) {
        this.situacao = situacao;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Empresa getLoja() {
        return loja.get();
    }

    public ObjectProperty<Empresa> lojaProperty() {
        return loja;
    }

    public void setLoja(Empresa loja) {
        this.loja.set(loja);
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Empresa getFornecedor() {
        return fornecedor.get();
    }

    public ObjectProperty<Empresa> fornecedorProperty() {
        return fornecedor;
    }

    public void setFornecedor(Empresa fornecedor) {
        this.fornecedor.set(fornecedor);
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    @OneToMany(mappedBy = "entradaProduto", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<EntradaProdutoProduto> getEntradaProdutoProdutoList() {
        return entradaProdutoProdutoList;
    }

    public void setEntradaProdutoProdutoList(List<EntradaProdutoProduto> entradaProdutoProdutoList) {
        this.entradaProdutoProdutoList = entradaProdutoProdutoList;
    }

    @Override
    public String toString() {
        return "EntradaProduto{" +
                "id=" + id +
                ", situacao=" + situacao +
                ", loja=" + loja +
                ", fornecedor=" + fornecedor +
                ", usuarioCadastro=" + usuarioCadastro +
                ", dtCadastro=" + dtCadastro +
                ", entradaProdutoProdutoList=" + entradaProdutoProdutoList +
                '}';
    }
}
