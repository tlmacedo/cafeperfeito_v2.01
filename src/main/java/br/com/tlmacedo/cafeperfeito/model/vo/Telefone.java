package br.com.tlmacedo.cafeperfeito.model.vo;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Telefone")
@Table(name = "telefone")
public class Telefone implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private StringProperty descricao = new SimpleStringProperty();

    private TelefoneOperadora telefoneOperadora = new TelefoneOperadora();

    public Telefone() {
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

    @Column(length = 11, nullable = false)
    public String getDescricao() {
        return descricao.get();
    }

    public StringProperty descricaoProperty() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    @ManyToOne
    public TelefoneOperadora getTelefoneOperadora() {
        return telefoneOperadora;
    }

    public void setTelefoneOperadora(TelefoneOperadora telefoneOperadora) {
        this.telefoneOperadora = telefoneOperadora;
    }

    @Override
    public String toString() {
        return "Telefone{" +
                "id=" + id +
                ", descricao=" + descricao +
                ", telefoneOperadora=" + telefoneOperadora +
                '}';
    }
}
