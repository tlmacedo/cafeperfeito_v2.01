package br.com.tlmacedo.cafeperfeito.model.tm;

import br.com.tlmacedo.cafeperfeito.model.dao.ContasAReceberDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoModalidade;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoSituacao;
import br.com.tlmacedo.cafeperfeito.model.vo.*;
import br.com.tlmacedo.cafeperfeito.service.ServiceMascara;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.Collectors;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_DATA;

public class TmodelContasAReceber {

    private TablePosition tp;
    private DatePicker dtpData1;
    private DatePicker dtpData2;
    private CheckBox chkDtVenda;
    private ObjectProperty<Empresa> empresa = new SimpleObjectProperty<>();
    private TextField txtPesquisa;
    private ObjectProperty<PagamentoSituacao> pagamentoSituacao = new SimpleObjectProperty<>();
    //    private ComboBox cboPagamentoSituacao;
    private Label lblRegistrosLocalizados;
    private TreeTableView<Object> ttvContasAReceber;
    private ContasAReceberDAO aReceberDAO = new ContasAReceberDAO();
    private ObservableList<ContasAReceber> aReceberObservableList = FXCollections.observableArrayList(getaReceberDAO().getAll(ContasAReceber.class, null, "dtCadastro DESC"));
    private FilteredList<ContasAReceber> aReceberFilteredList = new FilteredList<ContasAReceber>(getaReceberObservableList());

    private TreeItem<Object> aReceberTreeItem;
    private TreeTableColumn<Object, Long> colId;
    private TreeTableColumn<Object, String> colCliente_Documento;
    private TreeTableColumn<Object, LocalDate> colDtVenda;
    private TreeTableColumn<Object, String> colModalidade;
    private TreeTableColumn<Object, String> colSituacao;
    private TreeTableColumn<Object, LocalDate> colDtVencimento_DtPagamento;
    private TreeTableColumn<Object, String> colVlrPedido;
    private TreeTableColumn<Object, String> colVlrDesc;
    private TreeTableColumn<Object, String> colValor;
    private TreeTableColumn<Object, String> colValorPago;
    private TreeTableColumn<Object, String> colValorSaldo;
    private TreeTableColumn<Object, String> colUsuario;

    private IntegerProperty qtdClientes = new SimpleIntegerProperty(0);

    private IntegerProperty qtdContas = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> totalContas = new SimpleObjectProperty<>(BigDecimal.ZERO);

    private IntegerProperty qtdContasRetiradas = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> totalContasRetiradas = new SimpleObjectProperty<>(BigDecimal.ZERO);

    private IntegerProperty qtdContasDescontos = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> totalContasDescontos = new SimpleObjectProperty<>(BigDecimal.ZERO);

    private IntegerProperty qtdContasAReceber = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> totalContasAReceber = new SimpleObjectProperty<>(BigDecimal.ZERO);

    private IntegerProperty qtdContasVencidas = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> totalContasVencidas = new SimpleObjectProperty<>(BigDecimal.ZERO);

    private IntegerProperty qtdContasPendentes = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> totalContasPendentes = new SimpleObjectProperty<>(BigDecimal.ZERO);

    private IntegerProperty qtdContasPagas = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> totalContasPagas = new SimpleObjectProperty<>(BigDecimal.ZERO);

    private IntegerProperty qtdContasSaldoClientes = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> totalContasSaldoClientes = new SimpleObjectProperty<>(BigDecimal.ZERO);

    private ObjectProperty<BigDecimal> percLucroBruto = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<BigDecimal> totalLucroBruto = new SimpleObjectProperty<>(BigDecimal.ZERO);

    private ObjectProperty<BigDecimal> percLucroLiquido = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<BigDecimal> totalLucroLiquido = new SimpleObjectProperty<>(BigDecimal.ZERO);

    /**
     * Begin voids
     */

    public void criarTabela() {
        setColId(new TreeTableColumn("id"));
        getColId().setPrefWidth(50);
        getColId().setStyle("-fx-alignment: center-right;");
        getColId().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return ((ContasAReceber) cellData.getValue().getValue()).idProperty().asObject();
            } else if (cellData.getValue().getValue() instanceof Recebimento) {
                return ((Recebimento) cellData.getValue().getValue()).idProperty().asObject();
            }
            return new SimpleObjectProperty<>(null);
        });

        setColCliente_Documento(new TreeTableColumn("cliente / documento pag"));
        getColCliente_Documento().setPrefWidth(280);
        getColCliente_Documento().setCellFactory(cellFactory -> new TreeTableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getItem());
                if (getTreeTableRow().getItem() instanceof ContasAReceber)
                    setAlignment(Pos.CENTER_LEFT);
                else
                    setAlignment(Pos.CENTER_RIGHT);
            }
        });
        getColCliente_Documento().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleStringProperty(((ContasAReceber) cellData.getValue().getValue()).getSaidaProduto().getCliente().getRazaoFantasia());
            } else if (cellData.getValue().getValue() instanceof Recebimento) {
                return ((Recebimento) cellData.getValue().getValue()).documentoProperty();
            }
            return new SimpleStringProperty("");
        });

        setColDtVenda(new TreeTableColumn("dt. venda"));
        getColDtVenda().setPrefWidth(90);
        getColDtVenda().setStyle("-fx-alignment: center-right;");
        getColDtVenda().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleObjectProperty<>(((ContasAReceber) cellData.getValue().getValue()).dtCadastroProperty().getValue().toLocalDate());
            }
            return new SimpleObjectProperty<>();
        });
        getColDtVenda().setCellFactory(cellFactory -> new TreeTableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                try {
                    setText(empty ? null : getItem().format(DTF_DATA));
                } catch (Exception ex) {
                    if (ex instanceof NullPointerException)
                        setText("");
                }
            }
        });

        setColModalidade(new TreeTableColumn("/ mod pag"));
        getColModalidade().setPrefWidth(100);
        getColModalidade().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof Recebimento) {
                return new SimpleStringProperty(((Recebimento) cellData.getValue().getValue()).getPagamentoModalidade().getDescricao());
            }
            return new SimpleStringProperty("");
        });

        setColSituacao(new TreeTableColumn("/ situação"));
        getColSituacao().setPrefWidth(80);
        getColSituacao().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof Recebimento) {
                return new SimpleStringProperty(((Recebimento) cellData.getValue().getValue()).getPagamentoSituacao().getDescricao());
            }
            return new SimpleStringProperty("");
        });

        setColDtVencimento_DtPagamento(new TreeTableColumn("dt. venc / dt. pag"));
        getColDtVencimento_DtPagamento().setPrefWidth(90);
        getColDtVencimento_DtPagamento().setStyle("-fx-alignment: center-right;");
        getColDtVencimento_DtPagamento().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleObjectProperty<>(((ContasAReceber) cellData.getValue().getValue()).dtVencimentoProperty().getValue());
            } else if (cellData.getValue().getValue() instanceof Recebimento) {
                if (((Recebimento) cellData.getValue().getValue()).dtPagamentoProperty().getValue() != null)
                    return new SimpleObjectProperty<>(((Recebimento) cellData.getValue().getValue()).dtPagamentoProperty().getValue());
            }
            return new SimpleObjectProperty<>();
        });
        getColDtVencimento_DtPagamento().setCellFactory(cellFactory -> new TreeTableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                try {
                    setText(empty ? null : getItem().format(DTF_DATA));
                } catch (Exception ex) {
                    if (ex instanceof NullPointerException)
                        setText("");
                }
            }
        });

        setColVlrPedido(new TreeTableColumn<>("vlr. Ped."));
        getColVlrPedido().setPrefWidth(90);
        getColVlrPedido().setStyle("-fx-alignment: center-right;");
        getColVlrPedido().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleStringProperty(
                        ServiceMascara.getMoeda(((ContasAReceber) cellData.getValue().getValue()).vlrPedidoProperty().getValue(), 2));
            }
            return new SimpleStringProperty("");
        });

        setColVlrDesc(new TreeTableColumn<>("vlr. Desc."));
        getColVlrDesc().setPrefWidth(90);
        getColVlrDesc().setStyle("-fx-alignment: center-right;");
        getColVlrDesc().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber)
                return new SimpleStringProperty(
                        ServiceMascara.getMoeda(((ContasAReceber) cellData.getValue().getValue()).vlrDescProperty().getValue(), 2));
            return new SimpleStringProperty("");
        });

        setColValor(new TreeTableColumn("valor R$"));
        getColValor().setPrefWidth(90);
        getColValor().setStyle("-fx-alignment: center-right;");
        getColValor().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleStringProperty(
                        ServiceMascara.getMoeda(((ContasAReceber) cellData.getValue().getValue()).valorProperty().getValue(), 2));
            } else if (cellData.getValue().getValue() instanceof Recebimento) {
                if (!((Recebimento) cellData.getValue().getValue()).getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
                    return new SimpleStringProperty(
                            ServiceMascara.getMoeda(((Recebimento) cellData.getValue().getValue()).valorProperty().getValue(), 2));
            }
            return new SimpleStringProperty("");
        });

        setColValorPago(new TreeTableColumn("vlr pago R$"));
        getColValorPago().setPrefWidth(90);
        getColValorPago().setStyle("-fx-alignment: center-right;");
        getColValorPago().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleStringProperty(
                        ServiceMascara.getMoeda(((ContasAReceber) cellData.getValue().getValue()).valorPagoProperty().getValue(), 2));
            } else if (cellData.getValue().getValue() instanceof Recebimento) {
                if (((Recebimento) cellData.getValue().getValue()).getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
                    return new SimpleStringProperty(
                            ServiceMascara.getMoeda(((Recebimento) cellData.getValue().getValue()).valorProperty().getValue(), 2));
            }
            return new SimpleStringProperty("0,00");
        });

        setColValorSaldo(new TreeTableColumn<>("saldo R$"));
        getColValorSaldo().setPrefWidth(90);
        getColValorSaldo().setStyle("-fx-alignment: center-right;");
        getColValorSaldo().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleStringProperty(
                        ServiceMascara.getMoeda(((ContasAReceber) cellData.getValue().getValue()).valorSaldoProperty().getValue(), 2));
//            } else if (cellData.getValue().getValue() instanceof Recebimento) {
//                if (!((Recebimento) cellData.getValue().getValue()).getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
//                    return new SimpleStringProperty(
//                            ServiceMascara.getMoeda(((Recebimento) cellData.getValue().getValue()).valorProperty().getValue(), 2));
            }
            return new SimpleStringProperty("");
        });


    }


    public void preencherTabela() {
        try {
            setaReceberTreeItem(new TreeItem());
            getaReceberFilteredList().stream()
//                    .filter(aReceber -> aReceber.getSaidaProduto().getCliente().idProperty().getValue() == 64)
                    .forEach(aReceber -> {
                        final BigDecimal[] vlrPago = {BigDecimal.ZERO};
                        final BigDecimal[] vlrSaldo = {aReceber.valorProperty().getValue()};
                        TreeItem<Object> paiItem = new TreeItem(aReceber);
                        getaReceberTreeItem().getChildren().add(paiItem);
                        aReceber.getRecebimentoList().stream()
                                .forEach(recebimento -> {
                                    if (recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO)) {
                                        vlrPago[0] = vlrPago[0].add(recebimento.valorProperty().getValue());
                                        vlrSaldo[0] = vlrSaldo[0].subtract(recebimento.valorProperty().getValue());
                                    }
                                    TreeItem<Object> filhoItem = new TreeItem(recebimento);
                                    paiItem.getChildren().add(filhoItem);
                                });
                        ((ContasAReceber) paiItem.getValue()).valorPagoProperty().setValue(vlrPago[0]);
                        ((ContasAReceber) paiItem.getValue()).valorSaldoProperty().setValue(vlrSaldo[0]);
                        ((ContasAReceber) paiItem.getValue()).vlrPedidoProperty().setValue(aReceber.getSaidaProduto().getSaidaProdutoProdutoList().stream()
                                .map(SaidaProdutoProduto::getVlrBruto).reduce(BigDecimal.ZERO, BigDecimal::add));
                        ((ContasAReceber) paiItem.getValue()).vlrDescProperty().setValue(aReceber.getSaidaProduto().getSaidaProdutoProdutoList().stream()
                                .map(SaidaProdutoProduto::getVlrDesconto).reduce(BigDecimal.ZERO, BigDecimal::add));
                    });


            getTtvContasAReceber().getColumns().setAll(getColId(), getColCliente_Documento(), getColDtVenda(),
                    getColModalidade(), getColSituacao(), getColDtVencimento_DtPagamento(), getColVlrPedido(),
                    getColVlrDesc(), getColValor(), getColValorPago(), getColValorSaldo());

            getTtvContasAReceber().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            getTtvContasAReceber().setRoot(getaReceberTreeItem());
            getTtvContasAReceber().setShowRoot(false);

            totalizaTabela();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void escutaLista() {
        getTtvContasAReceber().setRowFactory(objectTreeTableView -> new TreeTableRow<>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                getStyleClass().removeAll(getStyleClass().stream().filter(s -> s.contains("recebimento-")).collect(Collectors.toList()));
                if (!empty) {
                    String stilo = "";
                    if (item instanceof ContasAReceber) {
                        PagamentoModalidade modalidade = null;
                        BigDecimal vlrPg = BigDecimal.ZERO;
                        try {
                            if (LocalDate.now().compareTo(((ContasAReceber) item).dtVencimentoProperty().getValue()) <= 0) {
                                stilo = "recebimento-pendente";
                            } else {
                                stilo = "recebimento-vencido";
                            }
                            modalidade = ((ContasAReceber) item).getRecebimentoList().stream().sorted(Comparator.comparing(Recebimento::getValor).reversed())
                                    .findFirst().orElse(null).getPagamentoModalidade();
                            vlrPg = ((ContasAReceber) item).getRecebimentoList().stream()
                                    .filter(recebimento -> recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
                                    .map(Recebimento::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
                            if (vlrPg.compareTo(((ContasAReceber) item).valorProperty().getValue()) >= 0) {
                                stilo = "recebimento-pago";
                            }
                        } catch (Exception ex) {

                        }
                        if (modalidade != null && (modalidade.equals(PagamentoModalidade.RETIRADA)
                                || modalidade.equals(PagamentoModalidade.BONIFICACAO)
                                || modalidade.equals(PagamentoModalidade.AMOSTRA))) {
                            stilo = "recebimento-retirada";
                        } else {
                        }
                    } else if (item instanceof Recebimento) {
                        stilo = "recebimento-recebimento";
                    }
                    if (!stilo.equals(""))
                        getStyleClass().add(stilo);
                }
            }
        });

        getaReceberFilteredList().addListener((ListChangeListener<? super ContasAReceber>) change -> {
            preencherTabela();
        });

        getLblRegistrosLocalizados().textProperty().bind(Bindings.createIntegerBinding(() ->
                getaReceberFilteredList().size(), getaReceberFilteredList().predicateProperty()).asString());

        empresaProperty().addListener(observable -> aplicaFiltro());
        getTxtPesquisa().textProperty().addListener(observable -> aplicaFiltro());
        pagamentoSituacaoProperty().addListener(observable -> aplicaFiltro());
        getDtpData1().valueProperty().addListener(observable -> aplicaFiltro());
        getDtpData2().valueProperty().addListener(observable -> aplicaFiltro());
        getChkDtVenda().selectedProperty().addListener(observable -> aplicaFiltro());

    }

    private void aplicaFiltro() {
        try {
            getaReceberFilteredList().setPredicate(aReceber -> {
                if (getDtpData1().getValue() == null || getDtpData2().getValue() == null)
                    return true;
                if (getChkDtVenda().isSelected()) {
                    if (aReceber.dtCadastroProperty().getValue().toLocalDate().isBefore(getDtpData1().getValue())
                            || aReceber.dtCadastroProperty().getValue().toLocalDate().isAfter(getDtpData2().getValue()))
                        return false;
                } else {
                    if (aReceber.dtVencimentoProperty().getValue().isBefore(getDtpData1().getValue())
                            || aReceber.dtVencimentoProperty().getValue().isAfter(getDtpData2().getValue())) {
                        return false;
                    }
                }
                if (empresaProperty().getValue() != null) {
                    if (empresaProperty().getValue().idProperty().getValue() != 0)
                        if (aReceber.getSaidaProduto().getCliente().idProperty().getValue() != empresaProperty().getValue().idProperty().getValue()) {
                            return false;
                        }
                }

                if (pagamentoSituacaoProperty().getValue() != null) {
                    switch (pagamentoSituacaoProperty().getValue()) {
                        case PENDENTE:
                            if (aReceber.valorProperty().getValue()
                                    .compareTo(aReceber.getRecebimentoList().stream()
                                            .filter(recebimento -> recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
                                            .map(Recebimento::getValor).reduce(BigDecimal.ZERO, BigDecimal::add)) <= 0)
                                return false;
                            break;
                        case CANCELADO:
                            if (aReceber.getRecebimentoList().stream()
                                    .filter(recebimento -> recebimento.getPagamentoSituacao()
                                            .equals(PagamentoSituacao.CANCELADO)).count() == 0)
                                return false;
                            break;
                        case QUITADO:
                            if (aReceber.valorProperty().getValue()
                                    .compareTo(aReceber.getRecebimentoList().stream()
                                            .filter(recebimento -> recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
                                            .map(Recebimento::getValor).reduce(BigDecimal.ZERO, BigDecimal::add)) > 0)
                                return false;
                            break;
                    }
                }

                if (aReceber.getRecebimentoList().stream()
                        .filter(recebimento -> recebimento.documentoProperty().getValue().toLowerCase()
                                .contains(getTxtPesquisa().getText().toLowerCase())).count() == 0)
                    return false;

                return true;
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void totalizaTabela() {
        setQtdClientes(
                getaReceberFilteredList().stream()
                        .map(ContasAReceber::getSaidaProduto)
                        .map(SaidaProduto::getCliente)
                        .collect(Collectors.groupingBy(Empresa::getId))
                        .size()
        );

        setQtdContas((int) getaReceberFilteredList().size());

        setTotalContas(
                getaReceberFilteredList().stream()
                        .map(ContasAReceber::getVlrPedido)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        setQtdContasRetiradas(
                (int) getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) == 0)
                        .count()
        );

        setTotalContasRetiradas(
                getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) == 0)
                        .map(ContasAReceber::getVlrPedido)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        setQtdContasDescontos(
                (int) getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) > 0
                                && aReceber.vlrDescProperty().getValue().compareTo(BigDecimal.ZERO) > 0)
                        .count()
        );

        setTotalContasDescontos(
                getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) > 0
                                && aReceber.vlrDescProperty().getValue().compareTo(BigDecimal.ZERO) > 0)
                        .map(ContasAReceber::getVlrDesc)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        setPercLucroBruto(
                BigDecimal.TEN.setScale(4)
        );

        BigDecimal valBruto = getaReceberFilteredList().stream()
                .map(ContasAReceber::getSaidaProduto)
                .map(SaidaProduto::getSaidaProdutoProdutoList)
                .map(saidaProdutoProdutos -> saidaProdutoProdutos.stream()
                        .map(SaidaProdutoProduto::getVlrBruto)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("valBruto:\t\t\t\t\t[%s]\n", valBruto);
        BigDecimal valEntradaBruto = getaReceberFilteredList().stream()
                .map(ContasAReceber::getSaidaProduto)
                .map(SaidaProduto::getSaidaProdutoProdutoList)
                .map(saidaProdutoProdutos -> saidaProdutoProdutos.stream()
                        .map(SaidaProdutoProduto::getVlrEntradaBruto)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("valEntradaBruto:\t\t\t[%s]\n", valEntradaBruto);
        BigDecimal valDesconto = getaReceberFilteredList().stream()
                .map(ContasAReceber::getSaidaProduto)
                .map(SaidaProduto::getSaidaProdutoProdutoList)
                .map(saidaProdutoProdutos -> saidaProdutoProdutos.stream()
                        .map(SaidaProdutoProduto::getVlrDesconto)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("valDesconto:\t\t\t\t[%s]\n", valDesconto);
        BigDecimal valLiq = valBruto.subtract(valEntradaBruto.add(valDesconto));
        System.out.printf("valLiq:\t\t\t\t\t\t[%s]\n\n", valLiq);

        BigDecimal val = getaReceberFilteredList().stream()
                .map(ContasAReceber::getSaidaProduto)
                .map(SaidaProduto::getSaidaProdutoProdutoList)
                .map(saidaProdutoProdutos -> saidaProdutoProdutos.stream()
                        .map(saidaProdutoProduto ->
                                saidaProdutoProduto.vlrBrutoProperty().getValue()
                                        .subtract(saidaProdutoProduto.vlrEntradaBrutoProperty().getValue()
                                                .add(saidaProdutoProduto.vlrDescontoProperty().getValue())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("val:\t\t\t\t\t\t[%s]\n\n\n\n", val);

        setTotalLucroBruto(
                val.setScale(4)
//                BigDecimal.TEN.setScale(4)
        );

        setQtdContasAReceber(
                (int) getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) > 0)
                        .count()
        );

        setTotalContasAReceber(
                getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) > 0)
                        .map(ContasAReceber::getValor)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        setQtdContasVencidas(
                (int) getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorSaldoProperty().getValue().compareTo(BigDecimal.ZERO) > 0
                                && aReceber.dtVencimentoProperty().getValue().compareTo(LocalDate.now()) < 0)
                        .count()
        );

        setTotalContasVencidas(
                getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorSaldoProperty().getValue().compareTo(BigDecimal.ZERO) > 0
                                && aReceber.dtVencimentoProperty().getValue().compareTo(LocalDate.now()) < 0)
                        .map(ContasAReceber::getValorSaldo)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        setQtdContasPendentes(
                (int) getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorSaldoProperty().getValue().compareTo(BigDecimal.ZERO) > 0
                                && aReceber.dtVencimentoProperty().getValue().compareTo(LocalDate.now()) >= 0)
                        .count()
        );

        setTotalContasPendentes(
                getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorSaldoProperty().getValue().compareTo(BigDecimal.ZERO) > 0
                                && aReceber.dtVencimentoProperty().getValue().compareTo(LocalDate.now()) >= 0)
                        .map(ContasAReceber::getValorSaldo)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        setQtdContasPagas(
                (int) getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) > 0
                                && aReceber.valorSaldoProperty().getValue().compareTo(BigDecimal.ZERO) <= 0)
                        .count()
        );

        setTotalContasPagas(
                getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) > 0
                                && aReceber.valorSaldoProperty().getValue().compareTo(BigDecimal.ZERO) <= 0)
                        .map(ContasAReceber::getValorPago)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        setQtdContasSaldoClientes(
                (int) getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorSaldoProperty().getValue().compareTo(BigDecimal.ZERO) < 0)
                        .count()
        );

        setTotalContasSaldoClientes(
                getaReceberFilteredList().stream()
                        .filter(aReceber -> aReceber.valorSaldoProperty().getValue().compareTo(BigDecimal.ZERO) < 0)
                        .map(ContasAReceber::getValorSaldo)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        setPercLucroLiquido(
                BigDecimal.ONE.setScale(4)
        );

        setTotalLucroLiquido(
                BigDecimal.ONE.setScale(4)
        );

//        setPercLucroLiquido(
//                getQtdContasVencidas() + getQtdContasPendentes() + getQtdContasPagas()
//        );

//        setTotalContasConfirmacao(
//                getTotalContasVencidas().add(getTotalContasPendentes()).add(getTotalContasPagas()).add(getTotalContasSaldoClientes())
//        );
    }

    /**
     * END voids
     */


    /**
     * Begin Getters e Setters
     */
    public TablePosition getTp() {
        return tp;
    }

    public void setTp(TablePosition tp) {
        this.tp = tp;
    }

    public DatePicker getDtpData1() {
        return dtpData1;
    }

    public void setDtpData1(DatePicker dtpData1) {
        this.dtpData1 = dtpData1;
    }

    public DatePicker getDtpData2() {
        return dtpData2;
    }

    public void setDtpData2(DatePicker dtpData2) {
        this.dtpData2 = dtpData2;
    }

    public CheckBox getChkDtVenda() {
        return chkDtVenda;
    }

    public void setChkDtVenda(CheckBox chkDtVenda) {
        this.chkDtVenda = chkDtVenda;
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

    public TextField getTxtPesquisa() {
        return txtPesquisa;
    }

    public void setTxtPesquisa(TextField txtPesquisa) {
        this.txtPesquisa = txtPesquisa;
    }

    public PagamentoSituacao getPagamentoSituacao() {
        return pagamentoSituacao.get();
    }

    public ObjectProperty<PagamentoSituacao> pagamentoSituacaoProperty() {
        return pagamentoSituacao;
    }

    public void setPagamentoSituacao(PagamentoSituacao pagamentoSituacao) {
        this.pagamentoSituacao.set(pagamentoSituacao);
    }

    public Label getLblRegistrosLocalizados() {
        return lblRegistrosLocalizados;
    }

    public void setLblRegistrosLocalizados(Label lblRegistrosLocalizados) {
        this.lblRegistrosLocalizados = lblRegistrosLocalizados;
    }

    public TreeTableView<Object> getTtvContasAReceber() {
        return ttvContasAReceber;
    }

    public void setTtvContasAReceber(TreeTableView<Object> ttvContasAReceber) {
        this.ttvContasAReceber = ttvContasAReceber;
    }

    public ContasAReceberDAO getaReceberDAO() {
        return aReceberDAO;
    }

    public void setaReceberDAO(ContasAReceberDAO aReceberDAO) {
        this.aReceberDAO = aReceberDAO;
    }

    public ObservableList<ContasAReceber> getaReceberObservableList() {
        return aReceberObservableList;
    }

    public void setaReceberObservableList(ObservableList<ContasAReceber> aReceberObservableList) {
        this.aReceberObservableList = aReceberObservableList;
    }

    public FilteredList<ContasAReceber> getaReceberFilteredList() {
        return aReceberFilteredList;
    }

    public void setaReceberFilteredList(FilteredList<ContasAReceber> aReceberFilteredList) {
        this.aReceberFilteredList = aReceberFilteredList;
    }

    public TreeItem<Object> getaReceberTreeItem() {
        return aReceberTreeItem;
    }

    public void setaReceberTreeItem(TreeItem<Object> aReceberTreeItem) {
        this.aReceberTreeItem = aReceberTreeItem;
    }

    public TreeTableColumn<Object, Long> getColId() {
        return colId;
    }

    public void setColId(TreeTableColumn<Object, Long> colId) {
        this.colId = colId;
    }

    public TreeTableColumn<Object, String> getColCliente_Documento() {
        return colCliente_Documento;
    }

    public void setColCliente_Documento(TreeTableColumn<Object, String> colCliente_Documento) {
        this.colCliente_Documento = colCliente_Documento;
    }

    public TreeTableColumn<Object, LocalDate> getColDtVenda() {
        return colDtVenda;
    }

    public void setColDtVenda(TreeTableColumn<Object, LocalDate> colDtVenda) {
        this.colDtVenda = colDtVenda;
    }

    public TreeTableColumn<Object, String> getColModalidade() {
        return colModalidade;
    }

    public void setColModalidade(TreeTableColumn<Object, String> colModalidade) {
        this.colModalidade = colModalidade;
    }

    public TreeTableColumn<Object, String> getColSituacao() {
        return colSituacao;
    }

    public void setColSituacao(TreeTableColumn<Object, String> colSituacao) {
        this.colSituacao = colSituacao;
    }

    public TreeTableColumn<Object, LocalDate> getColDtVencimento_DtPagamento() {
        return colDtVencimento_DtPagamento;
    }

    public void setColDtVencimento_DtPagamento(TreeTableColumn<Object, LocalDate> colDtVencimento_DtPagamento) {
        this.colDtVencimento_DtPagamento = colDtVencimento_DtPagamento;
    }

    public TreeTableColumn<Object, String> getColVlrPedido() {
        return colVlrPedido;
    }

    public void setColVlrPedido(TreeTableColumn<Object, String> colVlrPedido) {
        this.colVlrPedido = colVlrPedido;
    }

    public TreeTableColumn<Object, String> getColVlrDesc() {
        return colVlrDesc;
    }

    public void setColVlrDesc(TreeTableColumn<Object, String> colVlrDesc) {
        this.colVlrDesc = colVlrDesc;
    }

    public TreeTableColumn<Object, String> getColValor() {
        return colValor;
    }

    public void setColValor(TreeTableColumn<Object, String> colValor) {
        this.colValor = colValor;
    }

    public TreeTableColumn<Object, String> getColValorPago() {
        return colValorPago;
    }

    public void setColValorPago(TreeTableColumn<Object, String> colValorPago) {
        this.colValorPago = colValorPago;
    }

    public TreeTableColumn<Object, String> getColValorSaldo() {
        return colValorSaldo;
    }

    public void setColValorSaldo(TreeTableColumn<Object, String> colValorSaldo) {
        this.colValorSaldo = colValorSaldo;
    }

    public TreeTableColumn<Object, String> getColUsuario() {
        return colUsuario;
    }

    public void setColUsuario(TreeTableColumn<Object, String> colUsuario) {
        this.colUsuario = colUsuario;
    }

    public int getQtdClientes() {
        return qtdClientes.get();
    }

    public IntegerProperty qtdClientesProperty() {
        return qtdClientes;
    }

    public void setQtdClientes(int qtdClientes) {
        this.qtdClientes.set(qtdClientes);
    }

    public int getQtdContas() {
        return qtdContas.get();
    }

    public IntegerProperty qtdContasProperty() {
        return qtdContas;
    }

    public void setQtdContas(int qtdContas) {
        this.qtdContas.set(qtdContas);
    }

    public BigDecimal getTotalContas() {
        return totalContas.get();
    }

    public ObjectProperty<BigDecimal> totalContasProperty() {
        return totalContas;
    }

    public void setTotalContas(BigDecimal totalContas) {
        this.totalContas.set(totalContas);
    }

    public int getQtdContasRetiradas() {
        return qtdContasRetiradas.get();
    }

    public IntegerProperty qtdContasRetiradasProperty() {
        return qtdContasRetiradas;
    }

    public void setQtdContasRetiradas(int qtdContasRetiradas) {
        this.qtdContasRetiradas.set(qtdContasRetiradas);
    }

    public BigDecimal getTotalContasRetiradas() {
        return totalContasRetiradas.get();
    }

    public ObjectProperty<BigDecimal> totalContasRetiradasProperty() {
        return totalContasRetiradas;
    }

    public void setTotalContasRetiradas(BigDecimal totalContasRetiradas) {
        this.totalContasRetiradas.set(totalContasRetiradas);
    }

    public int getQtdContasDescontos() {
        return qtdContasDescontos.get();
    }

    public IntegerProperty qtdContasDescontosProperty() {
        return qtdContasDescontos;
    }

    public void setQtdContasDescontos(int qtdContasDescontos) {
        this.qtdContasDescontos.set(qtdContasDescontos);
    }

    public BigDecimal getTotalContasDescontos() {
        return totalContasDescontos.get();
    }

    public ObjectProperty<BigDecimal> totalContasDescontosProperty() {
        return totalContasDescontos;
    }

    public void setTotalContasDescontos(BigDecimal totalContasDescontos) {
        this.totalContasDescontos.set(totalContasDescontos);
    }

    public int getQtdContasAReceber() {
        return qtdContasAReceber.get();
    }

    public IntegerProperty qtdContasAReceberProperty() {
        return qtdContasAReceber;
    }

    public void setQtdContasAReceber(int qtdContasAReceber) {
        this.qtdContasAReceber.set(qtdContasAReceber);
    }

    public BigDecimal getTotalContasAReceber() {
        return totalContasAReceber.get();
    }

    public ObjectProperty<BigDecimal> totalContasAReceberProperty() {
        return totalContasAReceber;
    }

    public void setTotalContasAReceber(BigDecimal totalContasAReceber) {
        this.totalContasAReceber.set(totalContasAReceber);
    }

    public int getQtdContasVencidas() {
        return qtdContasVencidas.get();
    }

    public IntegerProperty qtdContasVencidasProperty() {
        return qtdContasVencidas;
    }

    public void setQtdContasVencidas(int qtdContasVencidas) {
        this.qtdContasVencidas.set(qtdContasVencidas);
    }

    public BigDecimal getTotalContasVencidas() {
        return totalContasVencidas.get();
    }

    public ObjectProperty<BigDecimal> totalContasVencidasProperty() {
        return totalContasVencidas;
    }

    public void setTotalContasVencidas(BigDecimal totalContasVencidas) {
        this.totalContasVencidas.set(totalContasVencidas);
    }

    public int getQtdContasPendentes() {
        return qtdContasPendentes.get();
    }

    public IntegerProperty qtdContasPendentesProperty() {
        return qtdContasPendentes;
    }

    public void setQtdContasPendentes(int qtdContasPendentes) {
        this.qtdContasPendentes.set(qtdContasPendentes);
    }

    public BigDecimal getTotalContasPendentes() {
        return totalContasPendentes.get();
    }

    public ObjectProperty<BigDecimal> totalContasPendentesProperty() {
        return totalContasPendentes;
    }

    public void setTotalContasPendentes(BigDecimal totalContasPendentes) {
        this.totalContasPendentes.set(totalContasPendentes);
    }

    public int getQtdContasPagas() {
        return qtdContasPagas.get();
    }

    public IntegerProperty qtdContasPagasProperty() {
        return qtdContasPagas;
    }

    public void setQtdContasPagas(int qtdContasPagas) {
        this.qtdContasPagas.set(qtdContasPagas);
    }

    public BigDecimal getTotalContasPagas() {
        return totalContasPagas.get();
    }

    public ObjectProperty<BigDecimal> totalContasPagasProperty() {
        return totalContasPagas;
    }

    public void setTotalContasPagas(BigDecimal totalContasPagas) {
        this.totalContasPagas.set(totalContasPagas);
    }

    public int getQtdContasSaldoClientes() {
        return qtdContasSaldoClientes.get();
    }

    public IntegerProperty qtdContasSaldoClientesProperty() {
        return qtdContasSaldoClientes;
    }

    public void setQtdContasSaldoClientes(int qtdContasSaldoClientes) {
        this.qtdContasSaldoClientes.set(qtdContasSaldoClientes);
    }

    public BigDecimal getTotalContasSaldoClientes() {
        return totalContasSaldoClientes.get();
    }

    public ObjectProperty<BigDecimal> totalContasSaldoClientesProperty() {
        return totalContasSaldoClientes;
    }

    public void setTotalContasSaldoClientes(BigDecimal totalContasSaldoClientes) {
        this.totalContasSaldoClientes.set(totalContasSaldoClientes);
    }

    public BigDecimal getPercLucroBruto() {
        return percLucroBruto.get();
    }

    public ObjectProperty<BigDecimal> percLucroBrutoProperty() {
        return percLucroBruto;
    }

    public void setPercLucroBruto(BigDecimal percLucroBruto) {
        this.percLucroBruto.set(percLucroBruto);
    }

    public BigDecimal getTotalLucroBruto() {
        return totalLucroBruto.get();
    }

    public ObjectProperty<BigDecimal> totalLucroBrutoProperty() {
        return totalLucroBruto;
    }

    public void setTotalLucroBruto(BigDecimal totalLucroBruto) {
        this.totalLucroBruto.set(totalLucroBruto);
    }

    public BigDecimal getPercLucroLiquido() {
        return percLucroLiquido.get();
    }

    public ObjectProperty<BigDecimal> percLucroLiquidoProperty() {
        return percLucroLiquido;
    }

    public void setPercLucroLiquido(BigDecimal percLucroLiquido) {
        this.percLucroLiquido.set(percLucroLiquido);
    }

    public BigDecimal getTotalLucroLiquido() {
        return totalLucroLiquido.get();
    }

    public ObjectProperty<BigDecimal> totalLucroLiquidoProperty() {
        return totalLucroLiquido;
    }

    public void setTotalLucroLiquido(BigDecimal totalLucroLiquido) {
        this.totalLucroLiquido.set(totalLucroLiquido);
    }

/**
 * END Getters e Setters
 */
}
