package br.com.tlmacedo.cafeperfeito.model.tm;

import br.com.tlmacedo.cafeperfeito.model.enums.TModelTipo;
import br.com.tlmacedo.cafeperfeito.model.vo.Produto;
import br.com.tlmacedo.cafeperfeito.service.ServiceAlertMensagem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TmodelProduto {

    private final TModelTipo tModelTipo;
    private Label lblRegistrosLocalizados;
    private TextField txtPesquisa;
    private TreeTableView<Produto> ttvProduto;
    private ObservableList<Produto> produtoObservableList;
    private FilteredList<Produto> produtoFilteredList;
    private ServiceAlertMensagem alertMensagem;

    private TreeItem<Produto> produtoTreeItem;
    private TreeTableColumn<Produto, String> colId;
    private TreeTableColumn<Produto, String> colCodigo;
    private TreeTableColumn<Produto, String> colDescricao;
    private TreeTableColumn<Produto, String> colVarejo;
    private TreeTableColumn<Produto, String> colUndCom;
    private TreeTableColumn<Produto, BigDecimal> colPrecoCompra;
    private TreeTableColumn<Produto, BigDecimal> colPrecoVenda;
    private TreeTableColumn<Produto, Integer> colEstoque;
    private TreeTableColumn<Produto, String> colLote;
    private TreeTableColumn<Produto, LocalDate> colValidade;
    private TreeTableColumn<Produto, String> colNFeEntrada;


    public TmodelProduto(TModelTipo tModelTipo) {
        this.tModelTipo = tModelTipo;
    }

    /**
     * Begin voids
     */

    public void criaTabela() {
        try {
            setColId(new TreeTableColumn<>("id"));
            getColId().setPrefWidth(48);
            getColId().setStyle("-fx-alignment: center-right;");
            getColId().setCellValueFactory(param -> {
                if (param.getValue().getValue().idProperty().get() == 0)
                    return new SimpleStringProperty("");
                else
                    return param.getValue().getValue().idProperty().asString();
            });

            setColCodigo(new TreeTableColumn<>("código"));
            getColCodigo().setPrefWidth(60);
            getColCodigo().setStyle("-fx-alignment: center-right;");
            getColCodigo().setCellValueFactory(param -> param.getValue().getValue().codigoProperty());

            setColDescricao(new TreeTableColumn<>("descrição"));
            getColDescricao().setPrefWidth(350);
            getColDescricao().setCellValueFactory(param -> param.getValue().getValue().descricaoProperty());

            setColVarejo(new TreeTableColumn<>("varejo"));
            getColVarejo().setPrefWidth(50);
            getColVarejo().setStyle("-fx-alignment: center-right;");
            getColVarejo().setCellValueFactory(param -> {
                if (param.getValue().getValue().varejoProperty().get() == 0)
                    return new SimpleStringProperty("");
                else
                    return param.getValue().getValue().varejoProperty().asString();
            });

            setColUndCom(new TreeTableColumn<>("und com"));
            getColUndCom().setPrefWidth(70);
            getColUndCom().setCellValueFactory(param -> {
                if (param.getValue().getValue().getUnidadeComercial() == null)
                    return new SimpleStringProperty("");
                else
                    return new SimpleStringProperty(param.getValue().getValue().getUnidadeComercial().getDescricao());
            });

            setColPrecoCompra(new TreeTableColumn<>("preço compra"));
            getColPrecoCompra().setPrefWidth(90);
            getColPrecoCompra().setStyle("-fx-alignment: center-right;");
            getColPrecoCompra().setCellValueFactory(param -> param.getValue().getValue().precoCompraProperty());

            setColPrecoVenda(new TreeTableColumn<>("preço venda"));
            getColPrecoVenda().setPrefWidth(90);
            getColPrecoVenda().setStyle("-fx-alignment: center-right;");
            getColPrecoVenda().setCellValueFactory(param -> param.getValue().getValue().precoVendaProperty());

            setColEstoque(new TreeTableColumn<>("estoque"));
            getColEstoque().setPrefWidth(65);
            getColEstoque().setStyle("-fx-alignment: center-right;");
            getColEstoque().setCellValueFactory(param -> param.getValue().getValue().tblEstoqueProperty().asObject());

            setColLote(new TreeTableColumn<>("lote"));
            getColLote().setPrefWidth(105);
            getColLote().setStyle("-fx-alignment: center;");
            getColLote().setCellValueFactory(param -> param.getValue().getValue().tblLoteProperty());

            setColValidade(new TreeTableColumn<>("validade"));
            getColValidade().setPrefWidth(105);
            getColValidade().setStyle("-fx-alignment: center-right;");
            getColValidade().setCellValueFactory(param -> param.getValue().getValue().tblValidadeProperty());

            setColNFeEntrada(new TreeTableColumn<>("doc. ent."));
            getColNFeEntrada().setPrefWidth(100);
            getColNFeEntrada().setStyle("-fx-alignment: center-right;");
            getColNFeEntrada().setCellValueFactory(param -> param.getValue().getValue().tblDocEntradaProperty());


        } catch (
                Exception ex) {
            ex.printStackTrace();
        }

    }

    public void preencheTabela() {
        setProdutoTreeItem(new TreeItem<>());
        getProdutoFilteredList().forEach(
                produtoTreeItem -> {
                    final int[] estq = {0};
                    TreeItem<Produto> prodTree = new TreeItem<>(produtoTreeItem);
                    getProdutoTreeItem().getChildren().add(prodTree);
                    produtoTreeItem.getProdutoEstoqueList().stream()
                            .forEach(produtoEstoque -> {
                                estq[0] += produtoEstoque.getQtd();
                                if (produtoEstoque.getQtd() > 0)
                                    prodTree.getChildren().add(new TreeItem<>(new Produto(produtoEstoque)));
                            });
                    prodTree.getValue().setTblEstoque(estq[0]);
                }
        );


        getTtvProduto().getColumns().setAll(
                getColId(), getColCodigo(), getColDescricao(), getColVarejo(), getColUndCom(), getColPrecoCompra(),
                getColPrecoVenda(), getColEstoque(), getColLote(), getColValidade(), getColNFeEntrada()
        );

        if (gettModelTipo().equals(TModelTipo.PROD_VENDA)) {
            getTtvProduto().getColumns().remove(getColPrecoCompra());
        }

        getTtvProduto().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        getTtvProduto().setRoot(getProdutoTreeItem());
        getTtvProduto().setShowRoot(false);
    }

    public void escutaLista() {
        getTtvProduto().setRowFactory(produtoTreeTableView -> {
            TreeTableRow<Produto> row = new TreeTableRow<>() {
                @Override
                protected void updateItem(Produto item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        getStyleClass().remove("produto-estoque");
                    } else if (item.getDescricao() == null) {
                        if (!getStyleClass().contains("produto-estoque"))
                            getStyleClass().add("produto-estoque");
                    } else {
                        getStyleClass().remove("produto-estoque");
                    }
                }
            };
            return row;
        });
    }


    /**
     * END Voids
     */

    public TModelTipo gettModelTipo() {
        return tModelTipo;
    }

    /**
     * Begin Gets and Setters
     */

    public Label getLblRegistrosLocalizados() {
        return lblRegistrosLocalizados;
    }

    public void setLblRegistrosLocalizados(Label lblRegistrosLocalizados) {
        this.lblRegistrosLocalizados = lblRegistrosLocalizados;
    }

    public TextField getTxtPesquisa() {
        return txtPesquisa;
    }

    public void setTxtPesquisa(TextField txtPesquisa) {
        this.txtPesquisa = txtPesquisa;
    }

    public TreeTableView<Produto> getTtvProduto() {
        return ttvProduto;
    }

    public void setTtvProduto(TreeTableView<Produto> ttvProduto) {
        this.ttvProduto = ttvProduto;
    }

    public ObservableList<Produto> getProdutoObservableList() {
        return produtoObservableList;
    }

    public void setProdutoObservableList(ObservableList<Produto> produtoObservableList) {
        this.produtoObservableList = produtoObservableList;
    }

    public FilteredList<Produto> getProdutoFilteredList() {
        return produtoFilteredList;
    }

    public void setProdutoFilteredList(FilteredList<Produto> produtoFilteredList) {
        this.produtoFilteredList = produtoFilteredList;
    }

    public ServiceAlertMensagem getAlertMensagem() {
        return alertMensagem;
    }

    public void setAlertMensagem(ServiceAlertMensagem alertMensagem) {
        this.alertMensagem = alertMensagem;
    }

    public TreeItem<Produto> getProdutoTreeItem() {
        return produtoTreeItem;
    }

    public void setProdutoTreeItem(TreeItem<Produto> produtoTreeItem) {
        this.produtoTreeItem = produtoTreeItem;
    }

    public TreeTableColumn<Produto, String> getColId() {
        return colId;
    }

    public void setColId(TreeTableColumn<Produto, String> colId) {
        this.colId = colId;
    }

    public TreeTableColumn<Produto, String> getColCodigo() {
        return colCodigo;
    }

    public void setColCodigo(TreeTableColumn<Produto, String> colCodigo) {
        this.colCodigo = colCodigo;
    }

    public TreeTableColumn<Produto, String> getColDescricao() {
        return colDescricao;
    }

    public void setColDescricao(TreeTableColumn<Produto, String> colDescricao) {
        this.colDescricao = colDescricao;
    }

    public TreeTableColumn<Produto, String> getColVarejo() {
        return colVarejo;
    }

    public void setColVarejo(TreeTableColumn<Produto, String> colVarejo) {
        this.colVarejo = colVarejo;
    }

    public TreeTableColumn<Produto, String> getColUndCom() {
        return colUndCom;
    }

    public void setColUndCom(TreeTableColumn<Produto, String> colUndCom) {
        this.colUndCom = colUndCom;
    }

    public TreeTableColumn<Produto, BigDecimal> getColPrecoCompra() {
        return colPrecoCompra;
    }

    public void setColPrecoCompra(TreeTableColumn<Produto, BigDecimal> colPrecoCompra) {
        this.colPrecoCompra = colPrecoCompra;
    }

    public TreeTableColumn<Produto, BigDecimal> getColPrecoVenda() {
        return colPrecoVenda;
    }

    public void setColPrecoVenda(TreeTableColumn<Produto, BigDecimal> colPrecoVenda) {
        this.colPrecoVenda = colPrecoVenda;
    }

    public TreeTableColumn<Produto, Integer> getColEstoque() {
        return colEstoque;
    }

    public void setColEstoque(TreeTableColumn<Produto, Integer> colEstoque) {
        this.colEstoque = colEstoque;
    }

    public TreeTableColumn<Produto, String> getColLote() {
        return colLote;
    }

    public void setColLote(TreeTableColumn<Produto, String> colLote) {
        this.colLote = colLote;
    }

    public TreeTableColumn<Produto, LocalDate> getColValidade() {
        return colValidade;
    }

    public void setColValidade(TreeTableColumn<Produto, LocalDate> colValidade) {
        this.colValidade = colValidade;
    }

    public TreeTableColumn<Produto, String> getColNFeEntrada() {
        return colNFeEntrada;
    }

    public void setColNFeEntrada(TreeTableColumn<Produto, String> colNFeEntrada) {
        this.colNFeEntrada = colNFeEntrada;
    }

    /**
     * END Gets and Setters
     */
}
