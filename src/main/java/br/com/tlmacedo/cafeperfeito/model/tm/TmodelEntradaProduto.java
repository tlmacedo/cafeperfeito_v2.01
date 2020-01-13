package br.com.tlmacedo.cafeperfeito.model.tm;

import br.com.tlmacedo.cafeperfeito.controller.ControllerPrincipal;
import br.com.tlmacedo.cafeperfeito.model.dao.FichaKardexDAO;
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
import java.time.LocalDate;
import java.util.stream.Collectors;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_DATA;

public class TmodelEntradaProduto {

    private TablePosition tp;
    private TableView<EntradaProdutoProduto> tvItensNfe;
    private ObservableList<EntradaProdutoProduto> entradaProdutoProdutoObservableList;
    private TextField txtPesquisaProduto;

    private TableColumn<EntradaProdutoProduto, String> colId;
    private TableColumn<EntradaProdutoProduto, String> colProdId;
    private TableColumn<EntradaProdutoProduto, String> colProdCod;
    private TableColumn<EntradaProdutoProduto, String> colProdDescricao;
    private TableColumn<EntradaProdutoProduto, TipoCodigoCFOP> colCFOP;
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
    private ObjectProperty<BigDecimal> totalFrete = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<BigDecimal> totalImpEntrada = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<BigDecimal> totalImposto = new SimpleObjectProperty<>(BigDecimal.ZERO);
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

        setColCFOP(new TableColumn<>("CFOP"));
        getColCFOP().setPrefWidth(100);
        getColCFOP().setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getCodigoCFOP()));
        getColCFOP().setCellFactory(param -> new SetCellFactoryTableCell_ComboBox<>(TipoCodigoCFOP.getList()));
        getColCFOP().setOnEditCommit(editEvent -> {
            editEvent.getRowValue().setCodigoCFOP(editEvent.getNewValue());
            getTvItensNfe().getSelectionModel().selectNext();
            totalizaLinha(editEvent.getRowValue());
        });

        setColProdLote(new TableColumn<>("lote"));
        getColProdLote().setPrefWidth(105);
        getColProdLote().setStyle("-fx-alignment: center;");
        getColProdLote().setCellValueFactory(param -> param.getValue().loteProperty());
        getColProdLote().setCellFactory(param -> new SetCellFactoryTableCell_EdtitingCell<EntradaProdutoProduto, String>(
                ServiceMascara.getTextoMask(15, "*")));
        getColProdLote().setOnEditCommit(editEvent -> {
            editEvent.getRowValue().loteProperty().setValue(editEvent.getNewValue());
            getTvItensNfe().getSelectionModel().selectNext();
        });

        setColProdValidade(new TableColumn<>("validade"));
        getColProdValidade().setPrefWidth(90);
        getColProdValidade().setStyle("-fx-alignment: center-right;");
        getColProdValidade().setCellValueFactory(param -> new SimpleStringProperty(param.getValue().dtValidadeProperty().get().format(DTF_DATA)));
        getColProdValidade().setCellFactory(param -> new SetCellFactoryTableCell_EdtitingCell<EntradaProdutoProduto, String>(
                "##/##/####"));
        getColProdValidade().setOnEditCommit(editEvent -> {
            editEvent.getRowValue().dtValidadeProperty().setValue(LocalDate.parse(editEvent.getNewValue(), DTF_DATA));
            getTvItensNfe().getSelectionModel().selectNext();
        });

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
            editEvent.getRowValue().setQtd(Integer.parseInt(editEvent.getNewValue()));
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
            getTvItensNfe().getSelectionModel().selectNext();
            totalizaLinha(editEvent.getRowValue());
        });

        setColVlrImposto(new TableColumn<>("imp R$"));
        getColVlrImposto().setPrefWidth(90);
        getColVlrImposto().setStyle("-fx-alignment: center-right;");
        getColVlrImposto().setCellValueFactory(param -> new SimpleStringProperty(
                ServiceMascara.getMoeda(param.getValue().vlrImpostoProperty().get(), 2)
        ));
        getColVlrImposto().setCellFactory(param -> new SetCellFactoryTableCell_EdtitingCell<EntradaProdutoProduto, String>(
                ServiceMascara.getNumeroMask(12, 2)
        ));
        getColVlrImposto().setOnEditCommit(editEvent -> {
            editEvent.getRowValue().setVlrImposto(ServiceMascara.getBigDecimalFromTextField(editEvent.getNewValue(), 2));
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
                getColProdCod(), getColProdDescricao(), getColCFOP(), getColProdLote(),
                getColProdValidade(), getColQtd(), getColVlrUnitario(), getColVlrBruto(), getColVlrDesconto(),
                getColVlrImposto(), getColVlrLiquido()
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
                if (getTp().getTableColumn() == getColCFOP()) {
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.UP) {
                        getTvItensNfe().edit(getTp().getRow(), getColCFOP());
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

            totalImpEntradaProperty().addListener(observable -> atualizaTotalImposto());

            totalFreteProperty().addListener(observable -> atualizaTotalImposto());

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


        entradaProdutoProduto.vlrLiquidoProperty().setValue((entradaProdutoProduto.vlrBrutoProperty().getValue()
                .add(entradaProdutoProduto.vlrImpostoProperty().getValue()))
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
        totalQtdVolumeProperty().setValue(qtdVol[0]);

        totalQtdItemProperty().setValue(getEntradaProdutoProdutoObservableList().stream()
                .collect(Collectors.groupingBy(EntradaProdutoProduto::getProduto)).size());
        totalQtdProdutoProperty().setValue(getEntradaProdutoProdutoObservableList().stream()
                .collect(Collectors.summingInt(EntradaProdutoProduto::getQtd)));
        totalBrutoProperty().setValue(getEntradaProdutoProdutoObservableList().stream()
                .map(EntradaProdutoProduto::getVlrBruto)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        totalDescontoProperty().setValue(getEntradaProdutoProdutoObservableList().stream()
                .map(EntradaProdutoProduto::getVlrDesconto)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        atualizaTotalImposto();
//        totalImpostoProperty().setValue(getEntradaProdutoProdutoObservableList().stream()
//                .map(EntradaProdutoProduto::getVlrImposto)
//                .reduce(BigDecimal.ZERO, BigDecimal::add));
//        totalLiquidoProperty().setValue(getEntradaProdutoProdutoObservableList().stream()
//                .map(EntradaProdutoProduto::getVlrLiquido)
//                .reduce(BigDecimal.ZERO, BigDecimal::add)
//                .add(totalFreteProperty().getValue()));
    }

    private void atualizaTotalImposto() {
        totalImpostoProperty().setValue(getEntradaProdutoProdutoObservableList().stream()
                .map(EntradaProdutoProduto::getVlrImposto)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(totalImpEntradaProperty().getValue()));
        atualizaTotal();
    }

    private void atualizaTotal() {
        totalLiquidoProperty().setValue(
                (totalBrutoProperty().getValue()
                        .add(totalImpostoProperty().getValue())
                        .add(totalFreteProperty().getValue()))
                        .subtract(totalDescontoProperty().getValue()));
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
    private boolean newFichaKardex(EntradaProdutoProduto entradaProdutoProduto) {
        FichaKardexDAO fichaKardexDAO = new FichaKardexDAO();
        try {
            fichaKardexDAO.transactionBegin();
            fichaKardexDAO.setTransactionPersist(new FichaKardex(entradaProdutoProduto));
            fichaKardexDAO.transactionCommit();
        } catch (Exception ex) {
            fichaKardexDAO.transactionRollback();
            ex.printStackTrace();
            return false;
        }
        return true;
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
    public boolean incluirEstoque() {
        try {
            getEntradaProdutoProdutoObservableList().stream()
                    .forEach(entradaProdutoProduto -> {
                        entradaProdutoProduto.produtoProperty().getValue()
                                .getProdutoEstoqueList().add(new ProdutoEstoque(entradaProdutoProduto));
                        newFichaKardex(entradaProdutoProduto);
                    });
//            getEntradaProdutoProdutoObservableList().stream()
//                    .forEach(entradaProdutoProduto -> {
//                        Produto produto = entradaProdutoProduto.produtoProperty().getValue();
//                        ProdutoEstoque newEstoque = new ProdutoEstoque();
//                        newEstoque.produtoProperty().setValue(entradaProdutoProduto.produtoProperty().getValue());
//                        newEstoque.qtdProperty().setValue(entradaProdutoProduto.qtdProperty().getValue());
//                        newEstoque.loteProperty().setValue(entradaProdutoProduto.loteProperty().getValue());
//                        newEstoque.dtValidadeProperty().setValue(entradaProdutoProduto.dtValidadeProperty().getValue());
//                        newEstoque.vlrUnitarioProperty().setValue(entradaProdutoProduto.vlrUnitarioProperty().getValue());
//                        newEstoque.vlrFreteBrutoProperty().setValue(freteBrutoKg
//                                .multiply(entradaProdutoProduto.produtoProperty().getValue().pesoProperty().getValue())
//                                .setScale(4, RoundingMode.HALF_UP));
//                        newEstoque.vlrImpostoNaEntradaProperty().setValue(entradaProdutoProduto.produtoProperty().getValue()
//                                .precoCompraProperty().getValue().multiply(impEntrada.divide(new BigDecimal("100."))));
//                        newEstoque.vlrImpostoFreteNaEntradaProperty().setValue(
//                                produto.pesoProperty().getValue().multiply(impFreteEntrada));
//                        newEstoque.vlrImpostoDentroFreteProperty().setValue(
//                                produto.pesoProperty().getValue().multiply(impFrete));
//                        newEstoque.vlrFreteTaxaProperty().setValue(
//                                produto.pesoProperty().getValue().multiply(taxaFrete));
//                        newEstoque.usuarioCadastroProperty().setValue(UsuarioLogado.getUsuario());
//                        newEstoque.docEntradaProperty().setValue(docEntrada);
//                        newEstoque.docEntradaChaveNFeProperty().setValue(chaveNFe);
//                        entradaProdutoProduto.vlrFreteProperty().setValue(
//                                newEstoque.vlrFreteBrutoProperty().getValue()
//                                        .add(newEstoque.vlrFreteTaxaProperty().getValue())
//                                        .add(newEstoque.vlrImpostoDentroFreteProperty().getValue())
//                        );
//                        entradaProdutoProduto.vlrImpostoProperty().setValue(
//                                newEstoque.vlrImpostoNaEntradaProperty().getValue()
//                                        .add(newEstoque.vlrImpostoFreteNaEntradaProperty().getValue())
//                        );
//                        produto.ultFreteProperty().setValue(entradaProdutoProduto.vlrFreteProperty().getValue());
//                        produto.setUltImpostoSefaz(entradaProdutoProduto.vlrImpostoProperty().getValue());
//
//                        produto.getProdutoEstoqueList().add(newEstoque);
//                        newFichaKardex(entradaProdutoProduto, newEstoque);
//                    });
        } catch (Exception ex) {
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

    public TextField getTxtPesquisaProduto() {
        return txtPesquisaProduto;
    }

    public void setTxtPesquisaProduto(TextField txtPesquisaProduto) {
        this.txtPesquisaProduto = txtPesquisaProduto;
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

    public TableColumn<EntradaProdutoProduto, TipoCodigoCFOP> getColCFOP() {
        return colCFOP;
    }

    public void setColCFOP(TableColumn<EntradaProdutoProduto, TipoCodigoCFOP> colCFOP) {
        this.colCFOP = colCFOP;
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

    public BigDecimal getTotalFrete() {
        return totalFrete.get();
    }

    public ObjectProperty<BigDecimal> totalFreteProperty() {
        return totalFrete;
    }

    public void setTotalFrete(BigDecimal totalFrete) {
        this.totalFrete.set(totalFrete);
    }

    public BigDecimal getTotalImposto() {
        return totalImposto.get();
    }

    public ObjectProperty<BigDecimal> totalImpostoProperty() {
        return totalImposto;
    }

    public void setTotalImposto(BigDecimal totalImposto) {
        this.totalImposto.set(totalImposto);
    }

    public BigDecimal getTotalImpEntrada() {
        return totalImpEntrada.get();
    }

    public ObjectProperty<BigDecimal> totalImpEntradaProperty() {
        return totalImpEntrada;
    }

    public void setTotalImpEntrada(BigDecimal totalImpEntrada) {
        this.totalImpEntrada.set(totalImpEntrada);
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
