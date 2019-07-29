package br.com.tlmacedo.cafeperfeito.model.vo;


import br.com.tlmacedo.cafeperfeito.model.enums.ColaboradorSituacao;
import javafx.beans.property.*;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDateTime;

@Entity(name = "Colaborador")
@Table(name = "colaborador")
@Inheritance(strategy = InheritanceType.JOINED)
public class Colaborador implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty apelido = new SimpleStringProperty();
    private StringProperty ctps = new SimpleStringProperty();
    private ObjectProperty<LocalDateTime> dtAdmisao = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> salario = new SimpleObjectProperty<>();
    private IntegerProperty ativo = new SimpleIntegerProperty();

    private Blob imagem, imagemBack;


    public Colaborador() {
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

    @Column(length = 120, nullable = false, unique = true)
    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    @Column(length = 30, nullable = false, unique = true)
    public String getApelido() {
        return apelido.get();
    }

    public StringProperty apelidoProperty() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido.set(apelido);
    }

    @Column(length = 30, nullable = false, unique = true)
    public String getCtps() {
        return ctps.get();
    }

    public StringProperty ctpsProperty() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps.set(ctps);
    }

    @Column(nullable = false)
    public LocalDateTime getDtAdmisao() {
        return dtAdmisao.get();
    }

    public ObjectProperty<LocalDateTime> dtAdmisaoProperty() {
        return dtAdmisao;
    }

    public void setDtAdmisao(LocalDateTime dtAdmisao) {
        this.dtAdmisao.set(dtAdmisao);
    }

    @Column(length = 19, scale = 2, nullable = false)
    public BigDecimal getSalario() {
        return salario.get();
    }

    public ObjectProperty<BigDecimal> salarioProperty() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario.set(salario);
    }

    @Column(length = 2, nullable = false)
    public ColaboradorSituacao getAtivo() {
        return ColaboradorSituacao.toEnum(ativo.get());
    }

    public IntegerProperty ativoProperty() {
        return ativo;
    }

    public void setAtivo(ColaboradorSituacao ativo) {
        this.ativo.set(ativo.getCod());
    }

    @JsonIgnore
    @SuppressWarnings("JpaAttributeTypeInspection")
    public Blob getImagem() {
        return imagem;
    }

    public void setImagem(Blob imagem) {
        this.imagem = imagem;
    }

    @Transient
    public Blob getImagemBack() {
        return imagemBack;
    }

    public void setImagemBack(Blob imagemBack) {
        this.imagemBack = imagemBack;
    }

    @Override
    public String toString() {
        return "Colaborador{" +
                "id=" + id +
                ", nome=" + nome +
                ", apelido=" + apelido +
                ", ctps=" + ctps +
                ", dataAdmisao=" + dtAdmisao +
                ", salario=" + salario +
                ", ativo=" + ativo +
                '}';
    }
}
