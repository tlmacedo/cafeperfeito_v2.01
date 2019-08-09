package br.com.tlmacedo.cafeperfeito.model.vo;

import br.com.tlmacedo.cafeperfeito.service.ServiceImageUtil;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

@Entity(name = "ProdutoCodigoBarra")
@Table(name = "produto_codigo_barra")
public class ProdutoCodigoBarra implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private StringProperty codigoBarra = new SimpleStringProperty();

    private Produto produto;

    private Blob imgCodigoBarra;

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

    @Column(length = 18, nullable = false, unique = true)
    public String getCodigoBarra() {
        return codigoBarra.get();
    }

    public StringProperty codigoBarraProperty() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra.set(codigoBarra);
    }

    @ManyToOne
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @JsonIgnore
    @SuppressWarnings("JpaAttributeTypeInspection")
    public Blob getImgCodigoBarra() {
        return imgCodigoBarra;
    }

    public void setImgCodigoBarra(Blob imgCodigoBarra) {
        this.imgCodigoBarra = imgCodigoBarra;
    }

    @JsonIgnore
    @Transient
    public Image getImagemCodigoBarra() {
        try {
            return ServiceImageUtil.getImageFromInputStream(getImgCodigoBarra().getBinaryStream());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transient
    public void setImagemCodigoBarra(Image imagemCodigoBarra) {
        setImgCodigoBarra((Blob) ServiceImageUtil.getInputStreamFromImage(imagemCodigoBarra));
    }

    @Override
    public String toString() {
        return "ProdutoCodigoBarra{" +
                "id=" + id +
                ", codigoBarra=" + codigoBarra +
                ", produto=" + produto +
                '}';
    }
}
