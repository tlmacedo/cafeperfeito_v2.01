package br.com.tlmacedo.cafeperfeito.model.tm;

import br.com.tlmacedo.cafeperfeito.controller.ControllerPrincipal;
import br.com.tlmacedo.cafeperfeito.model.dao.FichaKardexDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.ProdutoEstoqueDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.TipoCodigoCFOP;
import br.com.tlmacedo.cafeperfeito.model.vo.EntradaProdutoProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.FichaKardex;
import br.com.tlmacedo.cafeperfeito.model.vo.ProdutoEstoque;
import br.com.tlmacedo.cafeperfeito.service.ServiceMascara;
import br.com.tlmacedo.cafeperfeito.service.format.cell.SetCellFactoryTableCell_ComboBox;
import br.com.tlmacedo.cafeperfeito.service.format.cell.SetCellFactoryTableCell_EdtitingCell;
import javafx.beans.property.*;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_DATA;

public class TmodelEntradaProduto {

    private TablePosition tp;
    private TextField txtPesquisaProduto;
    private TableView<EntradaProdutoProduto> tvItensNfe;
    private ObservableList<EntradaProdutoProduto> entradaProdutoProdutoObservableList;
    private EntradaProdutoProduto entradaProdutoProduto;

    private TableColumn<EntradaProdutoProduto, String> colId;
    private TableColumn<EntradaProdutoProduto, String> colProdId;
    private TableColumn<EntradaProdutoProduto, String> colProdCod;
    private TableColumn<EntradaProdutoProduto, String> colProdDescricao;
    private TableColumn<EntradaProdutoProduto, TipoCodigoCFOP> colTipoSaidaProduto;
    private TableColumn<EntradaProdutoProduto, String> colProdLote;
    private TableColumn<EntradaProdutoProduto, String> colProdValidade;
    private TableColumn<EntradaProdutoProduto, String> colQtd;
    private TableColumn<EntradaProdutoProduto, String> colVlrUnitario;
    private TableColumn<EntradaProdutoProduto, String> colVlrBruto;
    private TableColumn<EntradaProdutoProduto, String> colVlrDesconto;
    private TableColumn<EntradaProdutoProduto, String> colVlrImposto;
    private TableColumn<EntradaProdutoProduto, String> colVlrLiquido;

    private TableColumn<EntradaProdutoProduto, Integer> colEstoque;
    private TableColumn<EntradaProdutoProduto, Integer> colVarejo;
    private TableColumn<EntradaProdutoProduto, Integer> colVolume;

    private IntegerProperty totalQtdItem = new SimpleIntegerProperty(0);
    private IntegerProperty totalQtdProduto = new SimpleIntegerProperty(0);
    private IntegerProperty totalQtdVolume = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> totalBruto = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<BigDecimal> totalDesconto = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<BigDecimal> totalLiquido = new SimpleObjectProperty<>(BigDecimal.ZERO);


    public TmodelEntradaProduto() {
    }

    /**
     * Begin voids
     */

    public void criaTabela() {
        setColId(new TableColumn<>("id"));
        getColId().setPrefWidth(48);
        getColId().setStyle("-fx-alignment: center-right;");
        getColId().setCellValueFactory(param -> param.getValue().idProperty().asString());

        setColProdId(new TableColumn<>("idP"));
        getColProdId().setPrefWidth(48);
        getColProdId().setStyle("-fx-alignment: center-right;");
        getColProdId().setCellValueFactory(param -> param.getValue().produtoProperty().getValue().idProperty().asString());

        setColProdCod(new TableColumn<>("cód"));
        getColProdCod().setPrefWidth(60);
        getColProdCod().setStyle("-fx-alignment: center-right;");
        getColProdCod().setCellValueFactory(param -> param.getValue().codigoProperty());

        setColProdDescricao(new TableColumn<>("descrição"));
        getColProdDescricao().setPrefWidth(450);
        getColProdDescricao().setCellValueFactory(param -> param.getValue().descricaoProperty());

        setColTipoSaidaProduto(new TableColumn<>("tipo saida"));
        getColTipoSaidaProduto().setPrefWidth(100);
        getColTipoSaidaProduto().setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getCodigoCFOP()));
        getColTipoSaidaProduto().setCellFactory(param -> new SetCellFactoryTableCell_ComboBox<>(TipoCodigoCFOP.getList()));
        getColTipoSaidaProduto().setOnEditCommit(editEvent -> {
            editEvent.getRowValue().setCodigoCFOP(editEvent.getNewValue());
            getTvItensNfe().getSelectionModel().selectNext();
            totalizaLinha(editEvent.getRowValue());
        });

        setColProdLote(new TableColumn<>("lote"));
        getColProdLote().setPrefWidth(105);
        getColProdLote().setStyle("-fx-alignment: center;");
        getColProdLote().setCellValueFactory(param -> param.getValue().loteProperty());

        setColProdValidade(new TableColumn<>("validade"));
        getColProdValidade().setPrefWidth(100);
        getColProdValidade().setStyle("-fx-alignment: center-right;");
        getColProdValidade().setCellValueFactory(param -> new SimpleStringProperty(param.getValue().dtValidadeProperty().get().format(DTF_DATA)));

        setColQtd(new TableColumn<>("qtd"));
        getColQtd().setPrefWidth(70);
        getColQtd().setStyle("-fx-alignment: center-right;");
        getColQtd().setCellValueFactory(param -> new SimpleStringProperty(
                ServiceMascara.getMoeda(param.getValue().qtdProperty().toString(), 0)
        ));
        getColQtd().setCellFactory(param -> new SetCellFactoryTableCell_EdtitingCell<EntradaProdutoProduto, String>(
                ServiceMascara.getNumeroMask(12, 0)
        ));
        getColQtd().setOnEditCommit(editEvent -> {
            Integer nVal = Integer.parseInt(editEvent.getNewValue());
            Integer oVal = Integer.parseInt(editEvent.getOldValue());
//            if (nVal.compareTo(editEvent.getRowValue().estoqueProperty().getValue()) > 0) {
//                editEvent.getRowValue().setQtd(editEvent.getRowValue().estoqueProperty().getValue());
//                getTvSaidaProdutoProduto().refresh();
//            } else
            editEvent.getRowValue().setQtd(validEstoque(nVal, oVal));
//            calculaDescontoCliente();
            getTvItensNfe().getSelectionModel().selectNext();
            totalizaLinha(editEvent.getRowValue());
        });

        setColVlrUnitario(new TableColumn<>("vlr. unit"));
        getColVlrUnitario().setPrefWidth(90);
        getColVlrUnitario().setStyle("-fx-alignment: center-right;");
        getColVlrUnitario().setCellValueFactory(param -> new SimpleStringProperty(
                ServiceMascara.getMoeda(param.getValue().vlrUnitarioProperty().get(), 2)
        ));

        setColVlrBruto(new TableColumn<>("vlr bruto"));
        getColVlrBruto().setPrefWidth(90);
        getColVlrBruto().setStyle("-fx-alignment: center-right;");
        getColVlrBruto().setCellValueFactory(param ->
                new SimpleStringProperty(
                        ServiceMascara.getMoeda(param.getValue().vlrBrutoProperty().get(), 2)
                ));

        setColVlrDesconto(new TableColumn<>("desc R$"));
        getColVlrDesconto().setPrefWidth(90);
        getColVlrDesconto().setStyle("-fx-alignment: center-right;");
        getColVlrDesconto().setCellValueFactory(param -> new SimpleStringProperty(
                ServiceMascara.getMoeda(param.getValue().vlrDescontoProperty().get(), 2)
        ));
        getColVlrDesconto().setCellFactory(param -> new SetCellFactoryTableCell_EdtitingCell<EntradaProdutoProduto, String>(
                ServiceMascara.getNumeroMask(12, 2)
        ));
        getColVlrDesconto().setOnEditCommit(editEvent -> {
            editEvent.getRowValue().setVlrDesconto(ServiceMascara.getBigDecimalFromTextField(editEvent.getNewValue(), 2));
            getTxtPesquisaProduto().requestFocus();
            totalizaLinha(editEvent.getRowValue());
        });

        setColVlrLiquido(new TableColumn<>("total"));
        getColVlrLiquido().setPrefWidth(90);
        getColVlrLiquido().setStyle("-fx-alignment: center-right;");
        getColVlrLiquido().setCellValueFactory(param -> new SimpleStringProperty(
                ServiceMascara.getMoeda(param.getValue().vlrLiquidoProperty().get(), 2)
        ));

        setColEstoque(new TableColumn<>("estoque"));
        getColEstoque().setPrefWidth(70);
        getColEstoque().setStyle("-fx-alignment: center-right;");
        getColEstoque().setCellValueFactory(param -> param.getValue().estoqueProperty().asObject());

    }

    public void preencheTabela() {
        getTvItensNfe().getColumns().setAll(
                //getColId(), getColProdId(),
                getColProdCod(), getColProdDescricao(), getColTipoSaidaProduto(), getColProdLote(),
                getColProdValidade(), getColQtd(), getColVlrUnitario(), getColVlrBruto(), getColVlrDesconto(),
                getColVlrLiquido()
                //, getColEstoque()
        );


        getTvItensNfe().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        getTvItensNfe().getSelectionModel().setCellSelectionEnabled(true);
        getTvItensNfe().setEditable(true);
        getTvItensNfe().setItems(getEntradaProdutoProdutoObservableList());
    }

    public void escutaLista() {
        try {
            getTvItensNfe().getFocusModel().focusedCellProperty().addListener((ov, o, n) -> setTp(n));
            getTvItensNfe().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                if (getTvItensNfe().getEditingCell() == null && keyEvent.getCode() == KeyCode.ENTER) {
                    getTvItensNfe().getSelectionModel().selectNext();
                    //setTp(getTvSaidaProdutoProduto().getFocusModel().getFocusedCell());
                    keyEvent.consume();
                }
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    if (getTp().getTableColumn() == getColVlrLiquido())
                        getTxtPesquisaProduto().requestFocus();
                    if (getTvItensNfe().getEditingCell() != null)
                        getTvItensNfe().getSelectionModel().selectNext();
                }


                if (keyEvent.getCode() == KeyCode.DELETE)
                    if (getTvItensNfe().getEditingCell() == null)
                        getEntradaProdutoProdutoObservableList().remove(getTvItensNfe().getSelectionModel().getSelectedItem());
            });

            getTvItensNfe().setOnKeyPressed(keyEvent -> {
                if (getTvItensNfe().getSelectionModel().getSelectedItem() == null) return;
                if (getTp().getTableColumn() == getColTipoSaidaProduto()) {
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.UP) {
                        getTvItensNfe().edit(getTp().getRow(), getColTipoSaidaProduto());
                    }
                }
                if (keyEvent.getCode().isLetterKey()
                        || keyEvent.getCode().isDigitKey()) {
                    ControllerPrincipal.setLastKey(keyEvent.getText());
                    getTvItensNfe().edit(getTp().getRow(), getTp().getTableColumn());
                }
            });

            getEntradaProdutoProdutoObservableList().addListener((ListChangeListener<? super EntradaProdutoProduto>) change -> {
                totalizaTabela();
            });


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void totalizaLinha(EntradaProdutoProduto entradaProdutoProduto) {
        switch (entradaProdutoProduto.getCodigoCFOP()) {
            case AMOSTRA:
            case CORTESIA:
            case TESTE:
                entradaProdutoProduto.qtdProperty().setValue(1);
            case BONIFICACAO:
            case CONSUMO:
                entradaProdutoProduto.vlrBrutoProperty().setValue(entradaProdutoProduto.vlrUnitarioProperty().getValue()
                        .multiply(BigDecimal.valueOf(entradaProdutoProduto.qtdProperty().getValue())).setScale(2));
                entradaProdutoProduto.vlrDescontoProperty().setValue(entradaProdutoProduto.vlrBrutoProperty().getValue());
                break;
            case COMERCIALIZACAO:
                entradaProdutoProduto.vlrBrutoProperty().setValue(entradaProdutoProduto.vlrUnitarioProperty().getValue()
                        .multiply(BigDecimal.valueOf(entradaProdutoProduto.qtdProperty().getValue())).setScale(2));
                if (entradaProdutoProduto.vlrDescontoProperty().getValue()
                        .compareTo(entradaProdutoProduto.vlrBrutoProperty().getValue().multiply(new BigDecimal("0.15"))) > 0) {
                    entradaProdutoProduto.vlrDescontoProperty()
                            .setValue(entradaProdutoProduto.vlrBrutoProperty().getValue().multiply(new BigDecimal("0.15")));
                }
                break;
        }


        entradaProdutoProduto.vlrLiquidoProperty().setValue(entradaProdutoProduto.vlrBrutoProperty().getValue()
                .subtract(entradaProdutoProduto.vlrDescontoProperty().getValue()).setScale(2, RoundingMode.HALF_UP));

        getTvItensNfe().refresh();

        totalizaTabela();
    }

    private void totalizaTabela() {
        final Integer[] qtdVol = {0};
        getEntradaProdutoProdutoObservableList().stream()
                .forEach(entradaProdutoProduto -> {
                    Double div = entradaProdutoProduto.qtdProperty().getValue().doubleValue() / entradaProdutoProduto.getProduto().varejoProperty().getValue().doubleValue();
                    if ((div - div.intValue()) > 0)
                        qtdVol[0] += (div.intValue() + 1);
                    else
                        qtdVol[0] += div.intValue();
                });
        setTotalQtdVolume(qtdVol[0]);
        setTotalQtdItem(getEntradaProdutoProdutoObservableList().stream()
                .collect(Collectors.groupingBy(EntradaProdutoProduto::getProduto)).size());
        setTotalQtdProduto(getEntradaProdutoProdutoObservableList().stream()
                .collect(Collectors.summingInt(EntradaProdutoProduto::getQtd)));
        setTotalBruto(getEntradaProdutoProdutoObservableList().stream()
                .map(EntradaProdutoProduto::getVlrBruto)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        setTotalDesconto(getEntradaProdutoProdutoObservableList().stream()
                .map(EntradaProdutoProduto::getVlrDesconto)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        setTotalLiquido(getEntradaProdutoProdutoObservableList().stream()
                .map(EntradaProdutoProduto::getVlrLiquido)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

//    public void calculaDescontoCliente() {
//        if (fornecedorProperty().getValue() == null) return;
//        getEntradaProdutoProdutoObservableList().stream()
//                .collect(Collectors.groupingBy(SaidaProdutoProduto::getIdProd))
//                .forEach(
//                        (aLong, saidaProdutos) -> {
//                            EmpresaCondicoes condicoes;
//                            if ((condicoes = fornecedorProperty().get().getEmpresaCondicoes().stream()
//                                    .filter(empresaCondicoes -> empresaCondicoes.getProduto().idProperty().getValue() == aLong
//                                            && empresaCondicoes.validadeProperty().get().compareTo(LocalDate.now()) >= 0)
//                                    .sorted(Comparator.comparing(EmpresaCondicoes::getValidade))
//                                    .findFirst().orElse(null)) == null) {
//                                prazoProperty().setValue(fornecedorProperty().getValue().prazoProperty().getValue());
//                                saidaProdutos.stream()
//                                        .filter(saidaProdutoProduto -> saidaProdutoProduto.getTipoSaidaProduto().equals(TipoCodigoCFOP.BONIFICACAO)
//                                                || saidaProdutoProduto.getTipoSaidaProduto().equals(TipoCodigoCFOP.CONSUMO))
//                                        .forEach(saidaProdutoProduto -> {
//                                            saidaProdutoProduto.setVlrDesconto(BigDecimal.ZERO);
//                                            saidaProdutoProduto.setTipoSaidaProduto(TipoCodigoCFOP.COMERCIALIZACAO);
//                                        });
//                            } else {
//                                Integer fator = saidaProdutos.stream()
//                                        .filter(saidaProdutoProduto -> saidaProdutoProduto.getTipoSaidaProduto().equals(TipoCodigoCFOP.COMERCIALIZACAO))
//                                        .collect(Collectors.summingInt(SaidaProdutoProduto::getQtd)) / condicoes.qtdMinimaProperty().getValue();
//
//                                if (fator > 0 && condicoes.prazoProperty().getValue() > 0)
//                                    prazoProperty().setValue(condicoes.prazoProperty().getValue());
//                                else
//                                    prazoProperty().setValue(fornecedorProperty().getValue().prazoProperty().getValue());
//
//                                if (condicoes.valorProperty().get().compareTo(BigDecimal.ZERO) > 0) {
//                                    saidaProdutos.stream()
//                                            .forEach(saidaProdutoProduto -> {
//                                                saidaProdutoProduto.setVlrVenda(condicoes.valorProperty().get());
//                                                if (fator == 0)
//                                                    saidaProdutoProduto.setVlrDesconto(BigDecimal.ZERO);
//                                                else
//                                                    saidaProdutoProduto.setVlrDesconto(condicoes.descontoProperty().getValue()
//                                                            .multiply(BigDecimal.valueOf(saidaProdutoProduto.qtdProperty().getValue())));
//                                            });
//                                }
//
//
//                                if (fator > 0 && condicoes.qtdMinimaProperty().getValue() > 0) {
////                                    if (condicoes.descontoProperty().getValue().compareTo(BigDecimal.ZERO) > 0) {
////
////                                    } else
//                                    if (condicoes.bonificacaoProperty().getValue().compareTo(0) > 0
//                                            || condicoes.retiradaProperty().getValue().compareTo(0) > 0) {
//                                        TipoCodigoCFOP tSaidaProduto = null;
//                                        if (condicoes.bonificacaoProperty().getValue().compareTo(0) > 0) {
//                                            tSaidaProduto = TipoCodigoCFOP.BONIFICACAO;
//                                        } else if (condicoes.retiradaProperty().getValue().compareTo(0) > 0) {
//                                            tSaidaProduto = TipoCodigoCFOP.CONSUMO;
//                                        }
//                                        TipoCodigoCFOP finalTSaidaProduto = tSaidaProduto;
//                                        if (condicoes.bonificacaoProperty().getValue() == condicoes.qtdMinimaProperty().getValue()) {
//                                            saidaProdutos.stream().forEach(saidaProdutoProduto -> saidaProdutoProduto.setTipoSaidaProduto(TipoCodigoCFOP.BONIFICACAO));
//                                        } else if (condicoes.retiradaProperty().getValue() == condicoes.qtdMinimaProperty().getValue()) {
//                                            saidaProdutos.stream().forEach(saidaProdutoProduto -> saidaProdutoProduto.setTipoSaidaProduto(TipoCodigoCFOP.CONSUMO));
//                                        } else {
//                                            switch (tSaidaProduto) {
//                                                case BONIFICACAO:
//                                                    saidaProdutos.stream()
//                                                            .filter(saidaProdutoProduto -> saidaProdutoProduto.getTipoSaidaProduto().equals(TipoCodigoCFOP.CONSUMO))
//                                                            .forEach(saidaProdutoProduto -> getEntradaProdutoProdutoObservableList().remove(saidaProdutoProduto));
//                                                    break;
//                                                case CONSUMO:
//                                                    saidaProdutos.stream()
//                                                            .filter(saidaProdutoProduto -> saidaProdutoProduto.getTipoSaidaProduto().equals(TipoCodigoCFOP.BONIFICACAO))
//                                                            .forEach(saidaProdutoProduto -> getEntradaProdutoProdutoObservableList().remove(saidaProdutoProduto));
//                                                    break;
//                                            }
//                                            Integer qtdSaiuBonf = saidaProdutos.stream()
//                                                    .filter(saidaProdutoProduto -> saidaProdutoProduto.getTipoSaidaProduto().equals(TipoCodigoCFOP.BONIFICACAO)
//                                                            || saidaProdutoProduto.getTipoSaidaProduto().equals(TipoCodigoCFOP.CONSUMO))
//                                                    .collect(Collectors.summingInt(SaidaProdutoProduto::getQtd));
//                                            final Integer[] restoBonif = {fator - qtdSaiuBonf};
//                                            if (restoBonif[0] <= 0) {
//                                                saidaProdutos.stream()
//                                                        .filter(saidaProdutoProduto -> saidaProdutoProduto.getTipoSaidaProduto().equals(finalTSaidaProduto))
//                                                        .sorted(Comparator.comparing(SaidaProdutoProduto::getDtValidade).reversed())
//                                                        .forEach(saidaProdutoProduto -> {
//                                                            if (restoBonif[0] < 0) {
//                                                                saidaProdutoProduto.qtdProperty().setValue(saidaProdutoProduto.qtdProperty().getValue() + restoBonif[0]);
//                                                                if (saidaProdutoProduto.qtdProperty().getValue() <= 0) {
//                                                                    restoBonif[0] = saidaProdutoProduto.qtdProperty().getValue();
//                                                                    getEntradaProdutoProdutoObservableList().remove(saidaProdutoProduto);
//                                                                } else {
//                                                                    restoBonif[0] = 0;
//                                                                }
//                                                            }
//                                                        });
//                                            } else {
//                                                new ProdutoDAO().getById(Produto.class, aLong).getProdutoEstoqueList().stream()
//                                                        .filter(produtoEstoque -> produtoEstoque.qtdProperty().getValue().compareTo(0) > 0)
//                                                        .sorted(Comparator.comparing(ProdutoEstoque::getValidade)).sorted(Comparator.comparing(ProdutoEstoque::getId))
//                                                        .collect(Collectors.groupingBy(ProdutoEstoque::getLote,
//                                                                LinkedHashMap::new,
//                                                                Collectors.toList()))
//                                                        .forEach((s, produtoEstoques) -> {
//                                                            if (restoBonif[0] > 0) {
//                                                                Integer qtdSaiu = saidaProdutos.stream()
//                                                                        .filter(saidaProdutoProduto -> saidaProdutoProduto.getIdProd() == produtoEstoques.get(0).getProduto().idProperty().getValue()
//                                                                                && saidaProdutoProduto.loteProperty().getValue().equals(s))
//                                                                        .collect(Collectors.summingInt(SaidaProdutoProduto::getQtd));
//                                                                Integer qtdEstoque = produtoEstoques.stream().collect(Collectors.summingInt(ProdutoEstoque::getQtd));
//                                                                Integer qtdDisponivel = qtdEstoque - qtdSaiu;
//                                                                if (qtdDisponivel > 0) {
//                                                                    Integer qtdAdd = 0;
//                                                                    if (restoBonif[0] - qtdDisponivel >= 0)
//                                                                        qtdAdd = qtdDisponivel;
//                                                                    else
//                                                                        qtdAdd = restoBonif[0];
//                                                                    restoBonif[0] -= qtdAdd;
//                                                                    SaidaProdutoProduto sProd;
//                                                                    if ((sProd = saidaProdutos.stream()
//                                                                            .filter(saidaProdutoProduto -> saidaProdutoProduto.produtoProperty().getValue().idProperty().getValue() == produtoEstoques.get(0).getProduto().idProperty().getValue()
//                                                                                    && saidaProdutoProduto.loteProperty().getValue().equals(s) && saidaProdutoProduto.getTipoSaidaProduto().equals(finalTSaidaProduto))
//                                                                            .findFirst().orElse(null)) == null) {
//                                                                        Produto prod = null;
//                                                                        try {
//                                                                            prod = produtoEstoques.get(0).getProduto().clone();
//                                                                        } catch (CloneNotSupportedException e) {
//                                                                            e.printStackTrace();
//                                                                        }
//                                                                        prod.idProperty().setValue(produtoEstoques.get(0).getProduto().idProperty().getValue());
//                                                                        prod.tblEstoqueProperty().setValue(qtdEstoque);
//                                                                        prod.tblLoteProperty().setValue(s);
//                                                                        prod.tblValidadeProperty().setValue(produtoEstoques.get(0).validadeProperty().getValue());
//                                                                        getEntradaProdutoProdutoObservableList().add(new SaidaProdutoProduto(prod, finalTSaidaProduto, qtdAdd));
//                                                                    } else {
//                                                                        sProd.qtdProperty().setValue(sProd.qtdProperty().getValue() + qtdAdd);
//                                                                    }
//                                                                }
//                                                            }
//                                                        });
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                            saidaProdutos.stream().forEach(saidaProdutoProduto -> totalizaLinha(saidaProdutoProduto));
//                        }
//                );
//    }


//    public void limpaCampos() {
//        setProdutoEstoqueDAO(new ProdutoEstoqueDAO());
//        setFichaKardexDAO(new FichaKardexDAO());
//        getEntradaProdutoProdutoObservableList().clear();
//    }

    /**
     * END Voids
     */

    /**
     * Begin Returns
     */

//    public boolean updateSaidaProduto() {
//        try {
//            getSaidaProdutoDAO().transactionBegin();
//            setSaidaProduto(getSaidaProdutoDAO().setTransactionPersist(getSaidaProduto()));
//            getSaidaProdutoDAO().transactionCommit();
//        } catch (Exception ex) {
//            getSaidaProdutoDAO().transactionRollback();
//            ex.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    public boolean updateContasAReceber() {
//        try {
//            getContasAReceberDAO().transactionBegin();
//            setaReceber(getContasAReceberDAO().setTransactionPersist(getaReceber()));
//            getContasAReceberDAO().transactionCommit();
//        } catch (Exception ex) {
//            getContasAReceberDAO().transactionRollback();
//            ex.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    public ProdutoEstoque updateEstoque(ProdutoEstoque estoque) {
//        try {
//            getProdutoEstoqueDAO().transactionBegin();
//            estoque = getProdutoEstoqueDAO().setTransactionPersist(estoque);
//            getProdutoEstoqueDAO().transactionCommit();
//        } catch (Exception ex) {
//            getProdutoEstoqueDAO().transactionRollback();
//            ex.printStackTrace();
//            return null;
//        }
//        return estoque;
//    }
//
//    public FichaKardex updateFichaKardex(FichaKardex kardex) {
//        try {
//            getFichaKardexDAO().transactionBegin();
//            kardex = getFichaKardexDAO().setTransactionPersist(kardex);
//            getFichaKardexDAO().transactionCommit();
//        } catch (Exception ex) {
//            getFichaKardexDAO().transactionRollback();
//            ex.printStackTrace();
//            return null;
//        }
//        return kardex;
//    }

    public Integer validEstoque(Integer newQtd, Integer oldQtd) {
        if (getEntradaProdutoProdutoObservableList().stream()
                .filter(entradaProdutoProduto -> entradaProdutoProduto.produtoProperty().getValue().idProperty().getValue() == getTvItensNfe().getItems().get(getTp().getRow()).produtoProperty().getValue().idProperty().getValue()
                        && entradaProdutoProduto.loteProperty().getValue().equals(getTvItensNfe().getItems().get(getTp().getRow()).loteProperty().getValue())).count() == 1) {
            if (newQtd.compareTo(getTvItensNfe().getItems().get(getTp().getRow()).estoqueProperty().getValue()) > 0)
                return getTvItensNfe().getItems().get(getTp().getRow()).estoqueProperty().getValue();
            else
                return newQtd;
        } else {
            Integer qtdSaiu = getEntradaProdutoProdutoObservableList().stream()
                    .filter(entradaProdutoProduto -> entradaProdutoProduto.produtoProperty().getValue().idProperty().getValue() == getTvItensNfe().getItems().get(getTp().getRow()).produtoProperty().getValue().idProperty().getValue()
                            && entradaProdutoProduto.loteProperty().getValue().equals(getTvItensNfe().getItems().get(getTp().getRow()).loteProperty().getValue()))
                    .collect(Collectors.summingInt(EntradaProdutoProduto::getQtd)) - oldQtd;
            Integer qtdSaida = qtdSaiu + newQtd;
            if (qtdSaida.compareTo(getTvItensNfe().getItems().get(getTp().getRow()).estoqueProperty().getValue()) > 0)
                return getTvItensNfe().getItems().get(getTp().getRow()).estoqueProperty().getValue() - (qtdSaiu);
            else
                return newQtd;
        }
    }

    private FichaKardex newFichaKardex(Integer qtdSaida, ProdutoEstoque estoque, List<ProdutoEstoque> produtoEstoqueList) {
        FichaKardex fichaKardex = new FichaKardex();
        try {
            fichaKardex.setProduto(estoque.getProduto());
            fichaKardex.documentoProperty().setValue(getEntradaProdutoProduto().getEntradaProduto().idProperty().getValue().toString());
            fichaKardex.detalheProperty().setValue(estoque.loteProperty().getValue());
            fichaKardex.qtdProperty().setValue(qtdSaida);
            fichaKardex.vlrUnitarioProperty().setValue(estoque.vlrBrutoProperty().getValue()
                    .add(estoque.vlrFreteBrutoProperty().getValue())
                    .add(estoque.vlrImpostoNaEntradaProperty().getValue())
                    .add(estoque.vlrImpostoFreteNaEntradaProperty().getValue())
                    .add(estoque.vlrImpostoDentroFreteProperty().getValue())
                    .add(estoque.vlrFreteTaxaProperty().getValue())
            );

            fichaKardex.qtdEntradaProperty().setValue(0);
            fichaKardex.vlrEntradaProperty().setValue(BigDecimal.ZERO);

            fichaKardex.qtdSaidaProperty().setValue(qtdSaida);
            fichaKardex.vlrSaidaProperty().setValue(fichaKardex.vlrUnitarioProperty().getValue().multiply(BigDecimal.valueOf(qtdSaida)));

//            setLucroQtdSaida(getLucroQtdSaida() + fichaKardex.qtdSaidaProperty().getValue());
//            setLucroVlrSaida(getLucroVlrSaida().add(fichaKardex.vlrSaidaProperty().getValue()));

            fichaKardex.saldoProperty().setValue(produtoEstoqueList.stream().collect(Collectors.summingInt(ProdutoEstoque::getQtd)));

            fichaKardex.vlrSaldoProperty().setValue(produtoEstoqueList.stream().filter(stq -> stq.qtdProperty().getValue() > 0)
                    .map(stq ->
                            (stq.vlrBrutoProperty().getValue()
                                    .add(stq.vlrFreteBrutoProperty().getValue())
                                    .add(stq.vlrImpostoNaEntradaProperty().getValue())
                                    .add(stq.vlrImpostoFreteNaEntradaProperty().getValue())
                                    .add(stq.vlrImpostoDentroFreteProperty().getValue())
                                    .add(stq.vlrFreteTaxaProperty().getValue()))
                                    .multiply(BigDecimal.valueOf(stq.qtdProperty().getValue()))
                    )
                    .reduce(BigDecimal.ZERO, BigDecimal::add));

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return fichaKardex;
    }

    /**
     * END Returns
     */

    /**
     * Begin booleans
     */

//    public boolean guardarSaidaProduto() {
//        try {
//            getSaidaProduto().setCliente(empresaProperty().getValue());
//            getSaidaProduto().setVendedor(UsuarioLogado.getUsuario());
//            getSaidaProduto().setDtSaida(getDtpDtSaida().getValue());
//
//            getSaidaProdutoProdutoObservableList().stream().forEach(saidaProdutoProduto -> {
//                saidaProdutoProduto.setSaidaProduto(getSaidaProduto());
//                saidaProdutoProduto.setVlrEntrada(BigDecimal.ZERO);
//                saidaProdutoProduto.setVlrEntradaBruto(BigDecimal.ZERO);
//            });
//
//            getSaidaProduto().setSaidaProdutoProdutoList(getSaidaProdutoProdutoObservableList());
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    public boolean salvarSaidaProduto() {
//        try {
//            getProdutoEstoqueDAO().transactionBegin();
//            getFichaKardexDAO().transactionBegin();
//            if (baixarEstoque()) {
//                getSaidaProdutoDAO().transactionBegin();
//                ContasAReceber receber = new ContasAReceber();
//                getSaidaProduto().setContasAReceber(receber);
//                receber.dtVencimentoProperty().setValue(getDtpDtVencimento().getValue());
//                receber.valorProperty().setValue(getSaidaProdutoProdutoObservableList().stream()
//                        .map(SaidaProdutoProduto::getVlrLiquido)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add));
//                receber.setUsuarioCadastro(UsuarioLogado.getUsuario());
//                receber.setSaidaProduto(getSaidaProduto());
//
//                setSaidaProduto(getSaidaProdutoDAO().setTransactionPersist(getSaidaProduto()));
//
//                getFichaKardexDAO().transactionCommit();
//                getProdutoEstoqueDAO().transactionCommit();
//                getSaidaProdutoDAO().transactionCommit();
//            } else {
//                getFichaKardexDAO().transactionRollback();
//                getProdutoEstoqueDAO().transactionRollback();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            getFichaKardexDAO().transactionRollback();
//            getProdutoEstoqueDAO().transactionRollback();
//            getSaidaProdutoDAO().transactionRollback();
//            return false;
//        }
//        return true;
//    }
    public boolean baixarEstoque() {
        ProdutoEstoqueDAO produtoEstoqueDAO = new ProdutoEstoqueDAO();
        FichaKardexDAO fichaKardexDAO = new FichaKardexDAO();
        try {
            getEntradaProdutoProdutoObservableList().stream()
                    .sorted(Comparator.comparing(EntradaProdutoProduto::getIdProd)).sorted(Comparator.comparing(EntradaProdutoProduto::getDtValidade))
                    .collect(Collectors.groupingBy(EntradaProdutoProduto::getIdProd, LinkedHashMap::new, Collectors.toList()))
                    .forEach((aLong, entradaProdutoProdutos) -> {
                        entradaProdutoProdutos.stream()
                                .collect(Collectors.groupingBy(EntradaProdutoProduto::getLote, LinkedHashMap::new, Collectors.toList()))
                                .forEach((s, entradaProdutoProdutos1) -> {
                                    final Integer[] saldoSaida = {entradaProdutoProdutos1.stream().collect(Collectors.summingInt(EntradaProdutoProduto::getQtd)), 0};
                                    List<ProdutoEstoque> produtoEstoqueList = produtoEstoqueDAO.getAll(ProdutoEstoque.class, String.format("produto_id=%s", aLong.toString()), "validade");
                                    produtoEstoqueList.stream().filter(estoque -> estoque.qtdProperty().getValue() > 0
                                            && estoque.loteProperty().getValue().equals(s))
                                            .forEach(estoque -> {
                                                if (saldoSaida[0] > 0) {
                                                    try {
                                                        estoque.qtdProperty().setValue(estoque.qtdProperty().getValue() - saldoSaida[0]);
                                                        if (estoque.qtdProperty().getValue() < 0) {
                                                            fichaKardexDAO.setTransactionPersist(newFichaKardex(saldoSaida[0] + estoque.qtdProperty().getValue(), estoque, produtoEstoqueList));
                                                            saldoSaida[0] = estoque.qtdProperty().getValue() * (-1);
                                                            estoque.qtdProperty().setValue(0);
                                                        } else {
                                                            fichaKardexDAO.setTransactionPersist(newFichaKardex(saldoSaida[0], estoque, produtoEstoqueList));
                                                            saldoSaida[0] = 0;
                                                        }
                                                        produtoEstoqueDAO.setTransactionPersist(estoque);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
//                                                entradaProdutoProdutos.stream()
//                                                        .filter(entradaProdutoProduto -> entradaProdutoProduto.loteProperty().getValue()
//                                                                .equals(estoque.loteProperty().getValue()))
//                                                        .forEach(entradaProdutoProduto -> {
//                                                            entradaProdutoProduto.setVlrEntradaBruto(getLucroVlrSaida());
//                                                            entradaProdutoProduto.setVlrEntrada(getLucroVlrSaida().divide(BigDecimal.valueOf(getLucroQtdSaida()), 4, RoundingMode.HALF_UP));
//                                                        });
                                            });
                                });
                    });
            fichaKardexDAO.transactionCommit();
            produtoEstoqueDAO.transactionCommit();
        } catch (Exception ex) {
            fichaKardexDAO.transactionRollback();
            produtoEstoqueDAO.transactionRollback();
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * END booleans
     */


    /**
     * Begin Gets and Setters
     */

    public TablePosition getTp() {
        return tp;
    }

    public void setTp(TablePosition tp) {
        this.tp = tp;
    }

    public TextField getTxtPesquisaProduto() {
        return txtPesquisaProduto;
    }

    public void setTxtPesquisaProduto(TextField txtPesquisaProduto) {
        this.txtPesquisaProduto = txtPesquisaProduto;
    }

    public TableView<EntradaProdutoProduto> getTvItensNfe() {
        return tvItensNfe;
    }

    public void setTvItensNfe(TableView<EntradaProdutoProduto> tvItensNfe) {
        this.tvItensNfe = tvItensNfe;
    }

    public ObservableList<EntradaProdutoProduto> getEntradaProdutoProdutoObservableList() {
        return entradaProdutoProdutoObservableList;
    }

    public void setEntradaProdutoProdutoObservableList(ObservableList<EntradaProdutoProduto> entradaProdutoProdutoObservableList) {
        this.entradaProdutoProdutoObservableList = entradaProdutoProdutoObservableList;
    }

    public EntradaProdutoProduto getEntradaProdutoProduto() {
        return entradaProdutoProduto;
    }

    public void setEntradaProdutoProduto(EntradaProdutoProduto entradaProdutoProduto) {
        this.entradaProdutoProduto = entradaProdutoProduto;
    }

    public TableColumn<EntradaProdutoProduto, String> getColId() {
        return colId;
    }

    public void setColId(TableColumn<EntradaProdutoProduto, String> colId) {
        this.colId = colId;
    }

    public TableColumn<EntradaProdutoProduto, String> getColProdId() {
        return colProdId;
    }

    public void setColProdId(TableColumn<EntradaProdutoProduto, String> colProdId) {
        this.colProdId = colProdId;
    }

    public TableColumn<EntradaProdutoProduto, String> getColProdCod() {
        return colProdCod;
    }

    public void setColProdCod(TableColumn<EntradaProdutoProduto, String> colProdCod) {
        this.colProdCod = colProdCod;
    }

    public TableColumn<EntradaProdutoProduto, String> getColProdDescricao() {
        return colProdDescricao;
    }

    public void setColProdDescricao(TableColumn<EntradaProdutoProduto, String> colProdDescricao) {
        this.colProdDescricao = colProdDescricao;
    }

    public TableColumn<EntradaProdutoProduto, TipoCodigoCFOP> getColTipoSaidaProduto() {
        return colTipoSaidaProduto;
    }

    public void setColTipoSaidaProduto(TableColumn<EntradaProdutoProduto, TipoCodigoCFOP> colTipoSaidaProduto) {
        this.colTipoSaidaProduto = colTipoSaidaProduto;
    }

    public TableColumn<EntradaProdutoProduto, String> getColProdLote() {
        return colProdLote;
    }

    public void setColProdLote(TableColumn<EntradaProdutoProduto, String> colProdLote) {
        this.colProdLote = colProdLote;
    }

    public TableColumn<EntradaProdutoProduto, String> getColProdValidade() {
        return colProdValidade;
    }

    public void setColProdValidade(TableColumn<EntradaProdutoProduto, String> colProdValidade) {
        this.colProdValidade = colProdValidade;
    }

    public TableColumn<EntradaProdutoProduto, String> getColQtd() {
        return colQtd;
    }

    public void setColQtd(TableColumn<EntradaProdutoProduto, String> colQtd) {
        this.colQtd = colQtd;
    }

    public TableColumn<EntradaProdutoProduto, String> getColVlrUnitario() {
        return colVlrUnitario;
    }

    public void setColVlrUnitario(TableColumn<EntradaProdutoProduto, String> colVlrUnitario) {
        this.colVlrUnitario = colVlrUnitario;
    }

    public TableColumn<EntradaProdutoProduto, String> getColVlrBruto() {
        return colVlrBruto;
    }

    public void setColVlrBruto(TableColumn<EntradaProdutoProduto, String> colVlrBruto) {
        this.colVlrBruto = colVlrBruto;
    }

    public TableColumn<EntradaProdutoProduto, String> getColVlrDesconto() {
        return colVlrDesconto;
    }

    public void setColVlrDesconto(TableColumn<EntradaProdutoProduto, String> colVlrDesconto) {
        this.colVlrDesconto = colVlrDesconto;
    }

    public TableColumn<EntradaProdutoProduto, String> getColVlrImposto() {
        return colVlrImposto;
    }

    public void setColVlrImposto(TableColumn<EntradaProdutoProduto, String> colVlrImposto) {
        this.colVlrImposto = colVlrImposto;
    }

    public TableColumn<EntradaProdutoProduto, String> getColVlrLiquido() {
        return colVlrLiquido;
    }

    public void setColVlrLiquido(TableColumn<EntradaProdutoProduto, String> colVlrLiquido) {
        this.colVlrLiquido = colVlrLiquido;
    }

    public TableColumn<EntradaProdutoProduto, Integer> getColEstoque() {
        return colEstoque;
    }

    public void setColEstoque(TableColumn<EntradaProdutoProduto, Integer> colEstoque) {
        this.colEstoque = colEstoque;
    }

    public TableColumn<EntradaProdutoProduto, Integer> getColVarejo() {
        return colVarejo;
    }

    public void setColVarejo(TableColumn<EntradaProdutoProduto, Integer> colVarejo) {
        this.colVarejo = colVarejo;
    }

    public TableColumn<EntradaProdutoProduto, Integer> getColVolume() {
        return colVolume;
    }

    public void setColVolume(TableColumn<EntradaProdutoProduto, Integer> colVolume) {
        this.colVolume = colVolume;
    }

    public int getTotalQtdItem() {
        return totalQtdItem.get();
    }

    public IntegerProperty totalQtdItemProperty() {
        return totalQtdItem;
    }

    public void setTotalQtdItem(int totalQtdItem) {
        this.totalQtdItem.set(totalQtdItem);
    }

    public int getTotalQtdProduto() {
        return totalQtdProduto.get();
    }

    public IntegerProperty totalQtdProdutoProperty() {
        return totalQtdProduto;
    }

    public void setTotalQtdProduto(int totalQtdProduto) {
        this.totalQtdProduto.set(totalQtdProduto);
    }

    public int getTotalQtdVolume() {
        return totalQtdVolume.get();
    }

    public IntegerProperty totalQtdVolumeProperty() {
        return totalQtdVolume;
    }

    public void setTotalQtdVolume(int totalQtdVolume) {
        this.totalQtdVolume.set(totalQtdVolume);
    }

    public BigDecimal getTotalBruto() {
        return totalBruto.get();
    }

    public ObjectProperty<BigDecimal> totalBrutoProperty() {
        return totalBruto;
    }

    public void setTotalBruto(BigDecimal totalBruto) {
        this.totalBruto.set(totalBruto);
    }

    public BigDecimal getTotalDesconto() {
        return totalDesconto.get();
    }

    public ObjectProperty<BigDecimal> totalDescontoProperty() {
        return totalDesconto;
    }

    public void setTotalDesconto(BigDecimal totalDesconto) {
        this.totalDesconto.set(totalDesconto);
    }

    public BigDecimal getTotalLiquido() {
        return totalLiquido.get();
    }

    public ObjectProperty<BigDecimal> totalLiquidoProperty() {
        return totalLiquido;
    }

    public void setTotalLiquido(BigDecimal totalLiquido) {
        this.totalLiquido.set(totalLiquido);
    }

    /**
     * END Gets and Setters
     */
}
