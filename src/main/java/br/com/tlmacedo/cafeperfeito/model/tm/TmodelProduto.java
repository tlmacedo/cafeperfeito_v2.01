package br.com.tlmacedo.cafeperfeito.model.tm;

import br.com.tlmacedo.cafeperfeito.model.dao.ProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.TModelTipo;
import br.com.tlmacedo.cafeperfeito.model.vo.Produto;
import br.com.tlmacedo.cafeperfeito.model.vo.ProdutoEstoque;
import br.com.tlmacedo.cafeperfeito.service.ServiceMascara;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_DATA;

public class TmodelProduto {

    private final TModelTipo tModelTipo;
    private Label lblRegistrosLocalizados;
    private TextField txtPesquisa;
    private TreeTableView<Produto> ttvProduto;
    private ObservableList<Produto> produtoObservableList = FXCollections.observableArrayList(new ProdutoDAO().getAll(Produto.class, null, "descricao"));
    private FilteredList<Produto> produtoFilteredList = new FilteredList<>(getProdutoObservableList());

    private TreeItem<Produto> produtoTreeItem;
    private TreeTableColumn<Produto, String> colId;
    private TreeTableColumn<Produto, String> colCodigo;
    private TreeTableColumn<Produto, String> colDescricao;
    private TreeTableColumn<Produto, String> colVarejo;
    private TreeTableColumn<Produto, String> colUndCom;
    private TreeTableColumn<Produto, String> colPrecoCompra;
    private TreeTableColumn<Produto, String> colPrecoVenda;
    private TreeTableColumn<Produto, Integer> colEstoque;
    private TreeTableColumn<Produto, String> colLote;
    private TreeTableColumn<Produto, String> colValidade;
//    private TreeTableColumn<Produto, String> colNFeEntrada;

//    private TreeTableColumn<Produto, String> colEstoqueId;


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
            getColPrecoCompra().setCellValueFactory(param -> {
                if (param.getValue().getValue().precoCompraProperty().getValue() == null)
                    return new SimpleStringProperty("");
                else
                    return new SimpleStringProperty(ServiceMascara.getMoeda(param.getValue().getValue().precoCompraProperty().getValue(), 2));
            });

            setColPrecoVenda(new TreeTableColumn<>("preço venda"));
            getColPrecoVenda().setPrefWidth(90);
            getColPrecoVenda().setStyle("-fx-alignment: center-right;");
            getColPrecoVenda().setCellValueFactory(param -> {
                if (param.getValue().getValue().precoVendaProperty().getValue() == null)
                    return new SimpleStringProperty("");
                else
                    return new SimpleStringProperty(ServiceMascara.getMoeda(param.getValue().getValue().precoVendaProperty().getValue(), 2));
            });

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
            getColValidade().setCellValueFactory(param -> {
                if (param.getValue().getValue().tblValidadeProperty().getValue() == null)
                    return new SimpleStringProperty("");
                else
                    return new SimpleStringProperty(param.getValue().getValue().tblValidadeProperty().get().format(DTF_DATA));
            });

//            setColNFeEntrada(new TreeTableColumn<>("doc. ent."));
//            getColNFeEntrada().setPrefWidth(100);
//            getColNFeEntrada().setStyle("-fx-alignment: center-right;");
//            getColNFeEntrada().setCellValueFactory(param -> param.getValue().getValue().tblDocEntradaProperty());

//            setColEstoqueId(new TreeTableColumn<>("ID"));
//            getColEstoqueId().setPrefWidth(60);
//            getColEstoqueId().setStyle("-fx-alignment: center-right;");
//            getColEstoqueId().setCellValueFactory(param -> param.getValue().getValue().tblEstoque_idProperty().asString());

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
                            .filter(produtoEstoque -> produtoEstoque.qtdProperty().getValue().compareTo(0) > 0)
                            .sorted(Comparator.comparing(ProdutoEstoque::getValidade))
                            .collect(Collectors.groupingBy(ProdutoEstoque::getLote,
                                    LinkedHashMap::new,
                                    Collectors.toList()))
                            .forEach((s, produtoEstoques) -> {
                                ProdutoEstoque estoque = new ProdutoEstoque();
                                estoque.qtdProperty().setValue(produtoEstoques.stream().collect(Collectors.summingInt(ProdutoEstoque::getQtd)));
                                estoque.loteProperty().setValue(s);
                                estoque.validadeProperty().setValue(produtoEstoques.stream().findFirst().orElse(null).validadeProperty().getValue());
                                estq[0] += estoque.qtdProperty().getValue();
                                prodTree.getChildren().add(new TreeItem<>(new Produto(estoque)));
                            });
                    prodTree.getValue().setTblEstoque(estq[0]);
                }
        );


        getTtvProduto().getColumns().setAll(
                getColId(), getColCodigo(), getColDescricao(), getColVarejo(), getColUndCom(), getColPrecoCompra(),
                getColPrecoVenda(), getColEstoque(), getColLote(), getColValidade()//, getColNFeEntrada()
                //, getColEstoqueId()
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

        getTxtPesquisa().textProperty().addListener((ov, o, n) -> {
            String strFind = n.toLowerCase().trim();
            getProdutoFilteredList().setPredicate(produto -> {
                if (produto.idProperty().toString().contains(strFind))
                    return true;
                if (produto.codigoProperty().get().toLowerCase().contains(strFind))
                    return true;
                if (produto.descricaoProperty().get().toLowerCase().contains(strFind))
                    return true;
                if (produto.ncmProperty().get().contains(strFind))
                    return true;
                if (produto.cestProperty().get().contains(strFind))
                    return true;
                if (produto.getProdutoCodigoBarraList().stream()
                        .filter(codBarra -> codBarra.getCodigoBarra().contains(strFind))
                        .findFirst().orElse(null) != null)
                    return true;
                return false;
            });
        });

        getProdutoFilteredList().addListener((ListChangeListener<? super Produto>) change -> {
            Platform.runLater(() -> {
                preencheTabela();
                getTtvProduto().refresh();
            });
        });

        getLblRegistrosLocalizados().textProperty().bind(Bindings.createStringBinding(() ->
                String.format("%5d", getProdutoFilteredList().size()), getProdutoFilteredList()
        ));
    }

    public void atualizarProdutos() {
        getProdutoObservableList().setAll(new ProdutoDAO().getAll(Produto.class, null, "descricao"));
        getTtvProduto().refresh();
    }

    /**
     * END Voids
     */

    /**
     * Begin Gets and Setters
     */

    public TModelTipo gettModelTipo() {
        return tModelTipo;
    }

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

    public TreeTableColumn<Produto, String> getColPrecoCompra() {
        return colPrecoCompra;
    }

    public void setColPrecoCompra(TreeTableColumn<Produto, String> colPrecoCompra) {
        this.colPrecoCompra = colPrecoCompra;
    }

    public TreeTableColumn<Produto, String> getColPrecoVenda() {
        return colPrecoVenda;
    }

    public void setColPrecoVenda(TreeTableColumn<Produto, String> colPrecoVenda) {
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

    public TreeTableColumn<Produto, String> getColValidade() {
        return colValidade;
    }

    public void setColValidade(TreeTableColumn<Produto, String> colValidade) {
        this.colValidade = colValidade;
    }

    /**
     * END Gets and Setters
     */
}
