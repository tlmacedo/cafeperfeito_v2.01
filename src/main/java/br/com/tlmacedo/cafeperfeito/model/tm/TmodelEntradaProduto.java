package br.com.tlmacedo.cafeperfeito.model.tm;

import br.com.tlmacedo.cafeperfeito.controller.ControllerPrincipal;
import br.com.tlmacedo.cafeperfeito.model.dao.FichaKardexDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.ProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.ProdutoEstoqueDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.TipoSaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.*;
import br.com.tlmacedo.cafeperfeito.service.ServiceMascara;
import br.com.tlmacedo.cafeperfeito.service.format.cell.SetCellFactoryTableCell_ComboBox;
import br.com.tlmacedo.cafeperfeito.service.format.cell.SetCellFactoryTableCell_EdtitingCell;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_DATA;

public class TmodelEntradaProduto {

    private TablePosition tp;
    private TextField txtPesquisa;
    private TableView<SaidaProdutoProduto> tvSaidaProdutoProduto;
    private ObservableList<SaidaProdutoProduto> saidaProdutoProdutoObservableList;
    private ObservableList<TipoSaidaProduto> tipoSaidaObservableList = FXCollections.observableArrayList();
    private ObjectProperty<Empresa> empresa = new SimpleObjectProperty<>();
    private SaidaProduto saidaProduto;
    //private ObjectProperty<DatePicker> dtpDtSaida, dtpDtVencimento;
    private ObjectProperty<DatePicker> dtpDtSaida = new SimpleObjectProperty<>(), dtpDtVencimento = new SimpleObjectProperty<>();
    private ProdutoEstoqueDAO produtoEstoqueDAO;
    private FichaKardexDAO fichaKardexDAO;

    private TableColumn<SaidaProdutoProduto, String> colId;
    private TableColumn<SaidaProdutoProduto, String> colProdId;
    private TableColumn<SaidaProdutoProduto, String> colProdCod;
    private TableColumn<SaidaProdutoProduto, String> colProdDescricao;
    private TableColumn<SaidaProdutoProduto, TipoSaidaProduto> colTipoSaidaProduto;
    private TableColumn<SaidaProdutoProduto, String> colProdLote;
    private TableColumn<SaidaProdutoProduto, String> colProdValidade;
    private TableColumn<SaidaProdutoProduto, String> colQtd;
    private TableColumn<SaidaProdutoProduto, String> colVlrVenda;
    private TableColumn<SaidaProdutoProduto, String> colVlrBruto;
    private TableColumn<SaidaProdutoProduto, String> colVlrDesconto;
    private TableColumn<SaidaProdutoProduto, String> colVlrLiquido;

    private TableColumn<SaidaProdutoProduto, Integer> colEstoque;
    private TableColumn<SaidaProdutoProduto, Integer> colVarejo;


    private IntegerProperty prazo = new SimpleIntegerProperty(0);
    private IntegerProperty totalQtdItem = new SimpleIntegerProperty(0);
    private IntegerProperty totalQtdProduto = new SimpleIntegerProperty(0);
    private IntegerProperty totalQtdVolume = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> totalBruto = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<BigDecimal> totalDesconto = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<BigDecimal> totalLiquido = new SimpleObjectProperty<>(BigDecimal.ZERO);

    private ObjectProperty<BigDecimal> lucroVlrSaida = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private IntegerProperty lucroQtdSaida = new SimpleIntegerProperty(0);


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
                new SimpleObjectProperty<>(param.getValue().getTipoSaidaProduto()));
        getColTipoSaidaProduto().setCellFactory(param -> new SetCellFactoryTableCell_ComboBox<>(TipoSaidaProduto.getList()));
        getColTipoSaidaProduto().setOnEditCommit(editEvent -> {
            editEvent.getRowValue().setTipoSaidaProduto(editEvent.getNewValue());
            getTvSaidaProdutoProduto().getSelectionModel().selectNext();
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
        getColQtd().setCellFactory(param -> new SetCellFactoryTableCell_EdtitingCell<SaidaProdutoProduto, String>(
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
            calculaDescontoCliente();
            getTvSaidaProdutoProduto().getSelectionModel().selectNext();
            totalizaLinha(editEvent.getRowValue());
        });

        setColVlrVenda(new TableColumn<>("vlr. venda"));
        getColVlrVenda().setPrefWidth(90);
        getColVlrVenda().setStyle("-fx-alignment: center-right;");
        getColVlrVenda().setCellValueFactory(param -> new SimpleStringProperty(
                ServiceMascara.getMoeda(param.getValue().vlrVendaProperty().get(), 2)
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
        getColVlrDesconto().setCellFactory(param -> new SetCellFactoryTableCell_EdtitingCell<SaidaProdutoProduto, String>(
                ServiceMascara.getNumeroMask(12, 2)
        ));
        getColVlrDesconto().setOnEditCommit(editEvent -> {
            editEvent.getRowValue().setVlrDesconto(ServiceMascara.getBigDecimalFromTextField(editEvent.getNewValue(), 2));
            getTxtPesquisa().requestFocus();
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
        getTvSaidaProdutoProduto().getColumns().setAll(
                //getColId(), getColProdId(),
                getColProdCod(), getColProdDescricao(), getColTipoSaidaProduto(), getColProdLote(),
                getColProdValidade(), getColQtd(), getColVlrVenda(), getColVlrBruto(), getColVlrDesconto(),
                getColVlrLiquido()
                //, getColEstoque()
        );


        getTvSaidaProdutoProduto().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        getTvSaidaProdutoProduto().getSelectionModel().setCellSelectionEnabled(true);
        getTvSaidaProdutoProduto().setEditable(true);
        getTvSaidaProdutoProduto().setItems(getSaidaProdutoProdutoObservableList());
    }

    public void escutaLista() {
        try {
            getTvSaidaProdutoProduto().getFocusModel().focusedCellProperty().addListener((ov, o, n) -> setTp(n));
            getTvSaidaProdutoProduto().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                if (getTvSaidaProdutoProduto().getEditingCell() == null && keyEvent.getCode() == KeyCode.ENTER) {
                    getTvSaidaProdutoProduto().getSelectionModel().selectNext();
                    //setTp(getTvSaidaProdutoProduto().getFocusModel().getFocusedCell());
                    keyEvent.consume();
                }
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    if (getTp().getTableColumn() == getColVlrLiquido())
                        getTxtPesquisa().requestFocus();
                    if (getTvSaidaProdutoProduto().getEditingCell() != null)
                        getTvSaidaProdutoProduto().getSelectionModel().selectNext();
                }


                if (keyEvent.getCode() == KeyCode.DELETE)
                    if (getTvSaidaProdutoProduto().getEditingCell() == null)
                        getSaidaProdutoProdutoObservableList().remove(getTvSaidaProdutoProduto().getSelectionModel().getSelectedItem());
            });

            getTvSaidaProdutoProduto().setOnKeyPressed(keyEvent -> {
                if (getTvSaidaProdutoProduto().getSelectionModel().getSelectedItem() == null) return;
                if (getTp().getTableColumn() == getColTipoSaidaProduto()) {
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.UP) {
                        getTvSaidaProdutoProduto().edit(getTp().getRow(), getColTipoSaidaProduto());
                    }
                }
                if (keyEvent.getCode().isLetterKey()
                        || keyEvent.getCode().isDigitKey()) {
                    ControllerPrincipal.setLastKey(keyEvent.getText());
                    getTvSaidaProdutoProduto().edit(getTp().getRow(), getTp().getTableColumn());
                }
            });

            getSaidaProdutoProdutoObservableList().addListener((ListChangeListener<? super SaidaProdutoProduto>) change -> {
                totalizaTabela();
            });

            empresaProperty().addListener(observable -> {
                calculaDescontoCliente();
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void totalizaLinha(SaidaProdutoProduto saidaProdutoProduto) {
        switch (saidaProdutoProduto.getTipoSaidaProduto()) {
            case AMOSTRA:
            case CORTESIA:
            case TESTE:
                saidaProdutoProduto.qtdProperty().setValue(1);
            case BONIFICACAO:
            case RETIRADA:
                saidaProdutoProduto.vlrBrutoProperty().setValue(saidaProdutoProduto.vlrVendaProperty().getValue()
                        .multiply(BigDecimal.valueOf(saidaProdutoProduto.qtdProperty().getValue())).setScale(2));
                saidaProdutoProduto.vlrDescontoProperty().setValue(saidaProdutoProduto.vlrBrutoProperty().getValue());
                break;
            case VENDA:
                saidaProdutoProduto.vlrBrutoProperty().setValue(saidaProdutoProduto.vlrVendaProperty().getValue()
                        .multiply(BigDecimal.valueOf(saidaProdutoProduto.qtdProperty().getValue())).setScale(2));
                if (saidaProdutoProduto.vlrDescontoProperty().getValue()
                        .compareTo(saidaProdutoProduto.vlrBrutoProperty().getValue().multiply(new BigDecimal("0.15"))) > 0) {
                    saidaProdutoProduto.vlrDescontoProperty()
                            .setValue(saidaProdutoProduto.vlrBrutoProperty().getValue().multiply(new BigDecimal("0.15")));
                }
                break;
        }


        saidaProdutoProduto.vlrLiquidoProperty().setValue(saidaProdutoProduto.vlrBrutoProperty().getValue()
                .subtract(saidaProdutoProduto.vlrDescontoProperty().getValue()).setScale(2, RoundingMode.HALF_UP));

        getTvSaidaProdutoProduto().refresh();

        totalizaTabela();
    }

    private void totalizaTabela() {
        final Integer[] qtdVol = {0};
        getSaidaProdutoProdutoObservableList().stream()
                .forEach(saidaProdutoProduto -> {
                    Double div = saidaProdutoProduto.qtdProperty().getValue().doubleValue() / saidaProdutoProduto.getProduto().varejoProperty().getValue().doubleValue();
                    if ((div - div.intValue()) > 0)
                        qtdVol[0] += (div.intValue() + 1);
                    else
                        qtdVol[0] += div.intValue();
                });
        setTotalQtdVolume(qtdVol[0]);
        setTotalQtdItem(getSaidaProdutoProdutoObservableList().stream()
                .collect(Collectors.groupingBy(SaidaProdutoProduto::getProduto)).size());
        setTotalQtdProduto(getSaidaProdutoProdutoObservableList().stream()
                .collect(Collectors.summingInt(SaidaProdutoProduto::getQtd)));
        setTotalBruto(getSaidaProdutoProdutoObservableList().stream()
                .map(SaidaProdutoProduto::getVlrBruto)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        setTotalDesconto(getSaidaProdutoProdutoObservableList().stream()
                .map(SaidaProdutoProduto::getVlrDesconto)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        setTotalLiquido(getSaidaProdutoProdutoObservableList().stream()
                .map(SaidaProdutoProduto::getVlrLiquido)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public void calculaDescontoCliente() {
        if (empresaProperty().getValue() == null) return;
        getSaidaProdutoProdutoObservableList().stream()
                .collect(Collectors.groupingBy(SaidaProdutoProduto::getIdProd))
                .forEach(
                        (aLong, saidaProdutos) -> {
                            EmpresaCondicoes condicoes;
                            if ((condicoes = empresaProperty().get().getEmpresaCondicoes().stream()
                                    .filter(empresaCondicoes -> empresaCondicoes.getProduto().idProperty().getValue() == aLong
                                            && empresaCondicoes.validadeProperty().get().compareTo(LocalDate.now()) >= 0)
                                    .sorted(Comparator.comparing(EmpresaCondicoes::getValidade))
                                    .findFirst().orElse(null)) == null) {
                                prazoProperty().setValue(empresaProperty().getValue().prazoProperty().getValue());
                                saidaProdutos.stream()
                                        .filter(saidaProdutoProduto -> saidaProdutoProduto.getTipoSaidaProduto().equals(TipoSaidaProduto.BONIFICACAO)
                                                || saidaProdutoProduto.getTipoSaidaProduto().equals(TipoSaidaProduto.RETIRADA))
                                        .forEach(saidaProdutoProduto -> {
                                            saidaProdutoProduto.setVlrDesconto(BigDecimal.ZERO);
                                            saidaProdutoProduto.setTipoSaidaProduto(TipoSaidaProduto.VENDA);
                                        });
                            } else {
                                Integer fator = saidaProdutos.stream()
                                        .filter(saidaProdutoProduto -> saidaProdutoProduto.getTipoSaidaProduto().equals(TipoSaidaProduto.VENDA))
                                        .collect(Collectors.summingInt(SaidaProdutoProduto::getQtd)) / condicoes.qtdMinimaProperty().getValue();

                                if (fator > 0 && condicoes.prazoProperty().getValue() > 0)
                                    prazoProperty().setValue(condicoes.prazoProperty().getValue());
                                else
                                    prazoProperty().setValue(empresaProperty().getValue().prazoProperty().getValue());

                                if (condicoes.valorProperty().get().compareTo(BigDecimal.ZERO) > 0) {
                                    saidaProdutos.stream()
                                            .forEach(saidaProdutoProduto -> {
                                                saidaProdutoProduto.setVlrVenda(condicoes.valorProperty().get());
                                                if (fator == 0)
                                                    saidaProdutoProduto.setVlrDesconto(BigDecimal.ZERO);
                                                else
                                                    saidaProdutoProduto.setVlrDesconto(condicoes.descontoProperty().getValue()
                                                            .multiply(BigDecimal.valueOf(saidaProdutoProduto.qtdProperty().getValue())));
                                            });
                                }


                                if (fator > 0 && condicoes.qtdMinimaProperty().getValue() > 0) {
//                                    if (condicoes.descontoProperty().getValue().compareTo(BigDecimal.ZERO) > 0) {
//
//                                    } else
                                    if (condicoes.bonificacaoProperty().getValue().compareTo(0) > 0
                                            || condicoes.retiradaProperty().getValue().compareTo(0) > 0) {
                                        TipoSaidaProduto tSaidaProduto = null;
                                        if (condicoes.bonificacaoProperty().getValue().compareTo(0) > 0) {
                                            tSaidaProduto = TipoSaidaProduto.BONIFICACAO;
                                        } else if (condicoes.retiradaProperty().getValue().compareTo(0) > 0) {
                                            tSaidaProduto = TipoSaidaProduto.RETIRADA;
                                        }
                                        TipoSaidaProduto finalTSaidaProduto = tSaidaProduto;
                                        if (condicoes.bonificacaoProperty().getValue() == condicoes.qtdMinimaProperty().getValue()) {
                                            saidaProdutos.stream().forEach(saidaProdutoProduto -> saidaProdutoProduto.setTipoSaidaProduto(TipoSaidaProduto.BONIFICACAO));
                                        } else if (condicoes.retiradaProperty().getValue() == condicoes.qtdMinimaProperty().getValue()) {
                                            saidaProdutos.stream().forEach(saidaProdutoProduto -> saidaProdutoProduto.setTipoSaidaProduto(TipoSaidaProduto.RETIRADA));
                                        } else {
                                            switch (tSaidaProduto) {
                                                case BONIFICACAO:
                                                    saidaProdutos.stream()
                                                            .filter(saidaProdutoProduto -> saidaProdutoProduto.getTipoSaidaProduto().equals(TipoSaidaProduto.RETIRADA))
                                                            .forEach(saidaProdutoProduto -> getSaidaProdutoProdutoObservableList().remove(saidaProdutoProduto));
                                                    break;
                                                case RETIRADA:
                                                    saidaProdutos.stream()
                                                            .filter(saidaProdutoProduto -> saidaProdutoProduto.getTipoSaidaProduto().equals(TipoSaidaProduto.BONIFICACAO))
                                                            .forEach(saidaProdutoProduto -> getSaidaProdutoProdutoObservableList().remove(saidaProdutoProduto));
                                                    break;
                                            }
                                            Integer qtdSaiuBonf = saidaProdutos.stream()
                                                    .filter(saidaProdutoProduto -> saidaProdutoProduto.getTipoSaidaProduto().equals(TipoSaidaProduto.BONIFICACAO)
                                                            || saidaProdutoProduto.getTipoSaidaProduto().equals(TipoSaidaProduto.RETIRADA))
                                                    .collect(Collectors.summingInt(SaidaProdutoProduto::getQtd));
                                            final Integer[] restoBonif = {fator - qtdSaiuBonf};
                                            if (restoBonif[0] <= 0) {
                                                saidaProdutos.stream()
                                                        .filter(saidaProdutoProduto -> saidaProdutoProduto.getTipoSaidaProduto().equals(finalTSaidaProduto))
                                                        .sorted(Comparator.comparing(SaidaProdutoProduto::getDtValidade).reversed())
                                                        .forEach(saidaProdutoProduto -> {
                                                            if (restoBonif[0] < 0) {
                                                                saidaProdutoProduto.qtdProperty().setValue(saidaProdutoProduto.qtdProperty().getValue() + restoBonif[0]);
                                                                if (saidaProdutoProduto.qtdProperty().getValue() <= 0) {
                                                                    restoBonif[0] = saidaProdutoProduto.qtdProperty().getValue();
                                                                    getSaidaProdutoProdutoObservableList().remove(saidaProdutoProduto);
                                                                } else {
                                                                    restoBonif[0] = 0;
                                                                }
                                                            }
                                                        });
                                            } else {
                                                new ProdutoDAO().getById(Produto.class, aLong).getProdutoEstoqueList().stream()
                                                        .filter(produtoEstoque -> produtoEstoque.qtdProperty().getValue().compareTo(0) > 0)
                                                        .sorted(Comparator.comparing(ProdutoEstoque::getValidade)).sorted(Comparator.comparing(ProdutoEstoque::getId))
                                                        .collect(Collectors.groupingBy(ProdutoEstoque::getLote,
                                                                LinkedHashMap::new,
                                                                Collectors.toList()))
                                                        .forEach((s, produtoEstoques) -> {
                                                            if (restoBonif[0] > 0) {
                                                                Integer qtdSaiu = saidaProdutos.stream()
                                                                        .filter(saidaProdutoProduto -> saidaProdutoProduto.getIdProd() == produtoEstoques.get(0).getProduto().idProperty().getValue()
                                                                                && saidaProdutoProduto.loteProperty().getValue().equals(s))
                                                                        .collect(Collectors.summingInt(SaidaProdutoProduto::getQtd));
                                                                Integer qtdEstoque = produtoEstoques.stream().collect(Collectors.summingInt(ProdutoEstoque::getQtd));
                                                                Integer qtdDisponivel = qtdEstoque - qtdSaiu;
                                                                if (qtdDisponivel > 0) {
                                                                    Integer qtdAdd = 0;
                                                                    if (restoBonif[0] - qtdDisponivel >= 0)
                                                                        qtdAdd = qtdDisponivel;
                                                                    else
                                                                        qtdAdd = restoBonif[0];
                                                                    restoBonif[0] -= qtdAdd;
                                                                    SaidaProdutoProduto sProd;
                                                                    if ((sProd = saidaProdutos.stream()
                                                                            .filter(saidaProdutoProduto -> saidaProdutoProduto.produtoProperty().getValue().idProperty().getValue() == produtoEstoques.get(0).getProduto().idProperty().getValue()
                                                                                    && saidaProdutoProduto.loteProperty().getValue().equals(s) && saidaProdutoProduto.getTipoSaidaProduto().equals(finalTSaidaProduto))
                                                                            .findFirst().orElse(null)) == null) {
                                                                        Produto prod = null;
                                                                        try {
                                                                            prod = produtoEstoques.get(0).getProduto().clone();
                                                                        } catch (CloneNotSupportedException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                        prod.idProperty().setValue(produtoEstoques.get(0).getProduto().idProperty().getValue());
                                                                        prod.tblEstoqueProperty().setValue(qtdEstoque);
                                                                        prod.tblLoteProperty().setValue(s);
                                                                        prod.tblValidadeProperty().setValue(produtoEstoques.get(0).validadeProperty().getValue());
                                                                        getSaidaProdutoProdutoObservableList().add(new SaidaProdutoProduto(prod, finalTSaidaProduto, qtdAdd));
                                                                    } else {
                                                                        sProd.qtdProperty().setValue(sProd.qtdProperty().getValue() + qtdAdd);
                                                                    }
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            }
                            saidaProdutos.stream().forEach(saidaProdutoProduto -> totalizaLinha(saidaProdutoProduto));
                        }
                );
    }


    public void limpaCampos() {
        setProdutoEstoqueDAO(new ProdutoEstoqueDAO());
        setFichaKardexDAO(new FichaKardexDAO());
        getSaidaProdutoProdutoObservableList().clear();
    }
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
        if (getSaidaProdutoProdutoObservableList().stream()
                .filter(saidaProdutoProduto -> saidaProdutoProduto.produtoProperty().getValue().idProperty().getValue() == getTvSaidaProdutoProduto().getItems().get(getTp().getRow()).produtoProperty().getValue().idProperty().getValue()
                        && saidaProdutoProduto.loteProperty().getValue().equals(getTvSaidaProdutoProduto().getItems().get(getTp().getRow()).loteProperty().getValue())).count() == 1) {
            if (newQtd.compareTo(getTvSaidaProdutoProduto().getItems().get(getTp().getRow()).estoqueProperty().getValue()) > 0)
                return getTvSaidaProdutoProduto().getItems().get(getTp().getRow()).estoqueProperty().getValue();
            else
                return newQtd;
        } else {
            Integer qtdSaiu = getSaidaProdutoProdutoObservableList().stream()
                    .filter(saidaProdutoProduto -> saidaProdutoProduto.produtoProperty().getValue().idProperty().getValue() == getTvSaidaProdutoProduto().getItems().get(getTp().getRow()).produtoProperty().getValue().idProperty().getValue()
                            && saidaProdutoProduto.loteProperty().getValue().equals(getTvSaidaProdutoProduto().getItems().get(getTp().getRow()).loteProperty().getValue()))
                    .collect(Collectors.summingInt(SaidaProdutoProduto::getQtd)) - oldQtd;
            Integer qtdSaida = qtdSaiu + newQtd;
            if (qtdSaida.compareTo(getTvSaidaProdutoProduto().getItems().get(getTp().getRow()).estoqueProperty().getValue()) > 0)
                return getTvSaidaProdutoProduto().getItems().get(getTp().getRow()).estoqueProperty().getValue() - (qtdSaiu);
            else
                return newQtd;
        }
    }

    private FichaKardex newFichaKardex(Integer qtdSaida, ProdutoEstoque estoque, List<ProdutoEstoque> produtoEstoqueList) {
        FichaKardex fichaKardex = new FichaKardex();
        try {
            fichaKardex.setProduto(estoque.getProduto());
            fichaKardex.documentoProperty().setValue(getSaidaProduto().idProperty().getValue().toString());
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

            setLucroQtdSaida(getLucroQtdSaida() + fichaKardex.qtdSaidaProperty().getValue());
            setLucroVlrSaida(getLucroVlrSaida().add(fichaKardex.vlrSaidaProperty().getValue()));

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
        getProdutoEstoqueDAO().transactionBegin();
        getFichaKardexDAO().transactionBegin();
        try {
            getSaidaProduto().getSaidaProdutoProdutoList().stream()
                    .sorted(Comparator.comparing(SaidaProdutoProduto::getIdProd)).sorted(Comparator.comparing(SaidaProdutoProduto::getDtValidade))
                    .collect(Collectors.groupingBy(SaidaProdutoProduto::getIdProd, LinkedHashMap::new, Collectors.toList()))
                    .forEach((aLong, saidaProdutoProdutos) -> {
                        saidaProdutoProdutos.stream()
                                .collect(Collectors.groupingBy(SaidaProdutoProduto::getLote, LinkedHashMap::new, Collectors.toList()))
                                .forEach((s, saidaProdutoProdutos1) -> {
                                    final Integer[] saldoSaida = {saidaProdutoProdutos1.stream().collect(Collectors.summingInt(SaidaProdutoProduto::getQtd)), 0};
                                    List<ProdutoEstoque> produtoEstoqueList = getProdutoEstoqueDAO().getAll(ProdutoEstoque.class, String.format("produto_id=%s", aLong.toString()), "validade");
                                    produtoEstoqueList.stream().filter(estoque -> estoque.qtdProperty().getValue() > 0
                                            && estoque.loteProperty().getValue().equals(s))
                                            .forEach(estoque -> {
                                                setLucroQtdSaida(0);
                                                setLucroVlrSaida(BigDecimal.ZERO);
                                                if (saldoSaida[0] > 0) {
                                                    try {
                                                        estoque.qtdProperty().setValue(estoque.qtdProperty().getValue() - saldoSaida[0]);
                                                        if (estoque.qtdProperty().getValue() < 0) {
                                                            getFichaKardexDAO().setTransactionPersist(newFichaKardex(saldoSaida[0] + estoque.qtdProperty().getValue(), estoque, produtoEstoqueList));
                                                            saldoSaida[0] = estoque.qtdProperty().getValue() * (-1);
                                                            estoque.qtdProperty().setValue(0);
                                                        } else {
                                                            getFichaKardexDAO().setTransactionPersist(newFichaKardex(saldoSaida[0], estoque, produtoEstoqueList));
                                                            saldoSaida[0] = 0;
                                                        }
                                                        getProdutoEstoqueDAO().setTransactionPersist(estoque);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                saidaProdutoProdutos.stream()
                                                        .filter(saidaProdutoProduto -> saidaProdutoProduto.loteProperty().getValue()
                                                                .equals(estoque.loteProperty().getValue()))
                                                        .forEach(saidaProdutoProduto -> {
                                                            saidaProdutoProduto.setVlrEntradaBruto(getLucroVlrSaida());
                                                            saidaProdutoProduto.setVlrEntrada(getLucroVlrSaida().divide(BigDecimal.valueOf(getLucroQtdSaida()), 4, RoundingMode.HALF_UP));
                                                        });
                                            });
                                });
                    });
            getFichaKardexDAO().transactionCommit();
            getProdutoEstoqueDAO().transactionCommit();
        } catch (Exception ex) {
            getFichaKardexDAO().transactionRollback();
            getProdutoEstoqueDAO().transactionRollback();
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

    public ObservableList<TipoSaidaProduto> getTipoSaidaObservableList() {
        return tipoSaidaObservableList;
    }

    public void setTipoSaidaObservableList(ObservableList<TipoSaidaProduto> tipoSaidaObservableList) {
        this.tipoSaidaObservableList = tipoSaidaObservableList;
    }

    @JsonIgnore
    public TablePosition getTp() {
        return tp;
    }

    public void setTp(TablePosition tp) {
        this.tp = tp;
    }

    public TableView<SaidaProdutoProduto> getTvSaidaProdutoProduto() {
        return tvSaidaProdutoProduto;
    }

    public void setTvSaidaProdutoProduto(TableView<SaidaProdutoProduto> tvSaidaProdutoProduto) {
        this.tvSaidaProdutoProduto = tvSaidaProdutoProduto;
    }

    public ObservableList<SaidaProdutoProduto> getSaidaProdutoProdutoObservableList() {
        return saidaProdutoProdutoObservableList;
    }

    public void setSaidaProdutoProdutoObservableList(ObservableList<SaidaProdutoProduto> saidaProdutoProdutoObservableList) {
        this.saidaProdutoProdutoObservableList = saidaProdutoProdutoObservableList;
    }

    public TableColumn<SaidaProdutoProduto, String> getColId() {
        return colId;
    }

    public void setColId(TableColumn<SaidaProdutoProduto, String> colId) {
        this.colId = colId;
    }

    public TableColumn<SaidaProdutoProduto, String> getColProdId() {
        return colProdId;
    }

    public void setColProdId(TableColumn<SaidaProdutoProduto, String> colProdId) {
        this.colProdId = colProdId;
    }

    public TableColumn<SaidaProdutoProduto, String> getColProdCod() {
        return colProdCod;
    }

    public void setColProdCod(TableColumn<SaidaProdutoProduto, String> colProdCod) {
        this.colProdCod = colProdCod;
    }

    public TableColumn<SaidaProdutoProduto, String> getColProdDescricao() {
        return colProdDescricao;
    }

    public void setColProdDescricao(TableColumn<SaidaProdutoProduto, String> colProdDescricao) {
        this.colProdDescricao = colProdDescricao;
    }

    public TableColumn<SaidaProdutoProduto, TipoSaidaProduto> getColTipoSaidaProduto() {
        return colTipoSaidaProduto;
    }

    public void setColTipoSaidaProduto(TableColumn<SaidaProdutoProduto, TipoSaidaProduto> colTipoSaidaProduto) {
        this.colTipoSaidaProduto = colTipoSaidaProduto;
    }

    public TableColumn<SaidaProdutoProduto, String> getColProdLote() {
        return colProdLote;
    }

    public void setColProdLote(TableColumn<SaidaProdutoProduto, String> colProdLote) {
        this.colProdLote = colProdLote;
    }

    public TableColumn<SaidaProdutoProduto, String> getColProdValidade() {
        return colProdValidade;
    }

    public void setColProdValidade(TableColumn<SaidaProdutoProduto, String> colProdValidade) {
        this.colProdValidade = colProdValidade;
    }

    public TableColumn<SaidaProdutoProduto, String> getColQtd() {
        return colQtd;
    }

    public void setColQtd(TableColumn<SaidaProdutoProduto, String> colQtd) {
        this.colQtd = colQtd;
    }

    public TableColumn<SaidaProdutoProduto, String> getColVlrVenda() {
        return colVlrVenda;
    }

    public void setColVlrVenda(TableColumn<SaidaProdutoProduto, String> colVlrVenda) {
        this.colVlrVenda = colVlrVenda;
    }

    public TableColumn<SaidaProdutoProduto, String> getColVlrBruto() {
        return colVlrBruto;
    }

    public void setColVlrBruto(TableColumn<SaidaProdutoProduto, String> colVlrBruto) {
        this.colVlrBruto = colVlrBruto;
    }

    public TableColumn<SaidaProdutoProduto, String> getColVlrDesconto() {
        return colVlrDesconto;
    }

    public void setColVlrDesconto(TableColumn<SaidaProdutoProduto, String> colVlrDesconto) {
        this.colVlrDesconto = colVlrDesconto;
    }

    public TableColumn<SaidaProdutoProduto, String> getColVlrLiquido() {
        return colVlrLiquido;
    }

    public void setColVlrLiquido(TableColumn<SaidaProdutoProduto, String> colVlrLiquido) {
        this.colVlrLiquido = colVlrLiquido;
    }

    public TableColumn<SaidaProdutoProduto, Integer> getColEstoque() {
        return colEstoque;
    }

    public void setColEstoque(TableColumn<SaidaProdutoProduto, Integer> colEstoque) {
        this.colEstoque = colEstoque;
    }

    public TableColumn<SaidaProdutoProduto, Integer> getColVarejo() {
        return colVarejo;
    }

    public void setColVarejo(TableColumn<SaidaProdutoProduto, Integer> colVarejo) {
        this.colVarejo = colVarejo;
    }

    public TextField getTxtPesquisa() {
        return txtPesquisa;
    }

    public void setTxtPesquisa(TextField txtPesquisa) {
        this.txtPesquisa = txtPesquisa;
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

    public Empresa getEmpresa() {
        return empresa.get();
    }

    public ObjectProperty<Empresa> empresaProperty() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa.set(empresa);
    }

    public int getPrazo() {
        return prazo.get();
    }

    public IntegerProperty prazoProperty() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo.set(prazo);
    }

    public SaidaProduto getSaidaProduto() {
        return saidaProduto;
    }

    public void setSaidaProduto(SaidaProduto saidaProduto) {
        this.saidaProduto = saidaProduto;
    }

//    public SaidaProdutoDAO getSaidaProdutoDAO() {
//        return saidaProdutoDAO;
//    }
//
//    public void setSaidaProdutoDAO(SaidaProdutoDAO saidaProdutoDAO) {
//        this.saidaProdutoDAO = saidaProdutoDAO;
//    }

    public DatePicker getDtpDtSaida() {
        return dtpDtSaida.get();
    }

    public ObjectProperty<DatePicker> dtpDtSaidaProperty() {
        return dtpDtSaida;
    }

    public void setDtpDtSaida(DatePicker dtpDtSaida) {
        this.dtpDtSaida.set(dtpDtSaida);
    }

    public DatePicker getDtpDtVencimento() {
        return dtpDtVencimento.get();
    }

    public ObjectProperty<DatePicker> dtpDtVencimentoProperty() {
        return dtpDtVencimento;
    }

    public void setDtpDtVencimento(DatePicker dtpDtVencimento) {
        this.dtpDtVencimento.set(dtpDtVencimento);
    }

    public ProdutoEstoqueDAO getProdutoEstoqueDAO() {
        return produtoEstoqueDAO;
    }

    public void setProdutoEstoqueDAO(ProdutoEstoqueDAO produtoEstoqueDAO) {
        this.produtoEstoqueDAO = produtoEstoqueDAO;
    }

    public FichaKardexDAO getFichaKardexDAO() {
        return fichaKardexDAO;
    }

    public void setFichaKardexDAO(FichaKardexDAO fichaKardexDAO) {
        this.fichaKardexDAO = fichaKardexDAO;
    }

//    public RecebimentoDAO getRecebimentoDAO() {
//        return recebimentoDAO;
//    }
//
//    public void setRecebimentoDAO(RecebimentoDAO recebimentoDAO) {
//        this.recebimentoDAO = recebimentoDAO;
//    }

    public BigDecimal getLucroVlrSaida() {
        return lucroVlrSaida.get();
    }

    public ObjectProperty<BigDecimal> lucroVlrSaidaProperty() {
        return lucroVlrSaida;
    }

    public void setLucroVlrSaida(BigDecimal lucroVlrSaida) {
        this.lucroVlrSaida.set(lucroVlrSaida);
    }

    public int getLucroQtdSaida() {
        return lucroQtdSaida.get();
    }

    public IntegerProperty lucroQtdSaidaProperty() {
        return lucroQtdSaida;
    }

    public void setLucroQtdSaida(int lucroQtdSaida) {
        this.lucroQtdSaida.set(lucroQtdSaida);
    }

    /**
     * END Gets and Setters
     */
}
