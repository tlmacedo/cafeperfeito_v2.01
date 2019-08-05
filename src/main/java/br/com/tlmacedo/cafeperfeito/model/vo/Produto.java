package br.com.tlmacedo.cafeperfeito.model.vo;

import javafx.beans.property.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "Produto")
@Table(name = "produto")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongProperty id = new SimpleLongProperty();
    private StringProperty codigo = new SimpleStringProperty();
    private StringProperty descricao = new SimpleStringProperty();
    private ObjectProperty<BigDecimal> peso = new SimpleObjectProperty<>();
    private IntegerProperty unidadeComercial = new SimpleIntegerProperty();
    private IntegerProperty situacao = new SimpleIntegerProperty();
    private ObjectProperty<BigDecimal> precoFabrica = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> precoConsumidor = new SimpleObjectProperty<>();
    private IntegerProperty varejo = new SimpleIntegerProperty();
    private ObjectProperty<BigDecimal> ultImpostoSefaz = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> ultFrete = new SimpleObjectProperty<>();
    private ObjectProperty<BigDecimal> comissao = new SimpleObjectProperty<>();

    private Usuario usuarioCadastro = new Usuario();
    private ObjectProperty<LocalDateTime> dtCadastro = new SimpleObjectProperty<>();
    private Usuario usuarioAtualizacao = new Usuario();
    private ObjectProperty<LocalDateTime> dtAtualizacao = new SimpleObjectProperty<>();


    private StringProperty nfeGenero = new SimpleStringProperty();
    private StringProperty ncm = new SimpleStringProperty();
    private StringProperty cest = new SimpleStringProperty();
//    private FiscalCstOrigem fiscalCstOrigem = new FiscalCstOrigem();
//    private FiscalIcms fiscalIcms = new FiscalIcms();
//    private FiscalPisCofins fiscalPis = new FiscalPisCofins();
//    private FiscalPisCofins fiscalCofins = new FiscalPisCofins();


    private Blob imgProduto, imgProdutoBack;

    private LongProperty estoque_id = new SimpleLongProperty();
    private IntegerProperty estoque = new SimpleIntegerProperty();
    private StringProperty lote = new SimpleStringProperty();
    private ObjectProperty<LocalDate> validade = new SimpleObjectProperty<>();
    private StringProperty notaEntrada = new SimpleStringProperty();

    public Produto() {
    }
}
