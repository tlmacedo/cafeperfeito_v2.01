package br.com.tlmacedo.cafeperfeito.model.tm;

import br.com.tlmacedo.cafeperfeito.model.dao.ContasAReceberDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoModalidade;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoSituacao;
import br.com.tlmacedo.cafeperfeito.model.vo.ContasAReceber;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.Recebimento;
import br.com.tlmacedo.cafeperfeito.service.ServiceMascara;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.Collectors;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_DATA;

public class TmodelContasAReceber {

    private TablePosition tp;
    private TextField txtPesquisa;
    private Label lblRegistrosLocalizados;
    private TreeTableView<Object> ttvContasAReceber;
    private FilteredList<ContasAReceber> contasAReceberFilteredList = new FilteredList<>(
            FXCollections.observableArrayList(new ContasAReceberDAO().getAll(ContasAReceber.class, null, null))
    );
    private ObjectProperty<DatePicker> dtpData1 = new SimpleObjectProperty<>();
    private ObjectProperty<DatePicker> dtpData2 = new SimpleObjectProperty<>();
    private ObjectProperty<CheckBox> chkDtVenda = new SimpleObjectProperty<>();
    private ObjectProperty<Empresa> empresa = new SimpleObjectProperty<>();
    private ObjectProperty<PagamentoSituacao> pagamentoSituacao = new SimpleObjectProperty<>();

    private TreeItem<Object> pedidoTreeItem;
    private TreeTableColumn<Object, Long> colId;
    private TreeTableColumn<Object, String> colCliente_Documento;
    private TreeTableColumn<Object, LocalDate> colDtVenda;
    private TreeTableColumn<Object, String> colModalidade;
    private TreeTableColumn<Object, String> colSituacao;
    private TreeTableColumn<Object, LocalDate> colDtVencimento_DtPagamento;
    private TreeTableColumn<Object, String> colVlrBruto;
    private TreeTableColumn<Object, String> colVlrCredDeb;
    private TreeTableColumn<Object, String> colVlrLiquido;
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

    public TmodelContasAReceber() {
    }

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
//            } else if (cellData.getValue().getValue() instanceof Recebimento) {
//                return ((Recebimento) cellData.getValue().getValue()).idProperty().asObject();
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
        getColDtVenda().setPrefWidth(75);
        getColDtVenda().setStyle("-fx-alignment: center-right;");
        getColDtVenda().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber)
                return new SimpleObjectProperty<>(((ContasAReceber) cellData.getValue().getValue()).dtCadastroProperty().getValue().toLocalDate());
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
        getColModalidade().setPrefWidth(70);
        getColModalidade().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof Recebimento)
                return new SimpleStringProperty(((Recebimento) cellData.getValue().getValue()).getPagamentoModalidade().getDescricao());
            return new SimpleStringProperty("");
        });

        setColSituacao(new TreeTableColumn("/ situação"));
        getColSituacao().setPrefWidth(75);
        getColSituacao().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof Recebimento)
                return new SimpleStringProperty(((Recebimento) cellData.getValue().getValue()).getPagamentoSituacao().getDescricao());
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

        setColVlrBruto(new TreeTableColumn<>("vlr. Bruto"));
        getColVlrBruto().setPrefWidth(80);
        getColVlrBruto().setStyle("-fx-alignment: center-right;");
        getColVlrBruto().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleStringProperty(
                        ServiceMascara.getMoeda(((ContasAReceber) cellData.getValue().getValue()).valorProperty().getValue(), 2));
            }
            return new SimpleStringProperty("");
        });

        setColVlrCredDeb(new TreeTableColumn<>("Cred/Déb"));
        getColVlrCredDeb().setPrefWidth(60);
        getColVlrCredDeb().setStyle("-fx-alignment: center-right;");
        getColVlrCredDeb().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleStringProperty(
//                        ServiceMascara.getMoeda(((ContasAReceber) cellData.getValue().getValue()).getRecebimentoList().stream()
//                                .filter(recebimento -> recebimento.getPagamentoModalidade().equals(PagamentoModalidade.CREDITO)
//                                        || recebimento.getPagamentoModalidade().equals(PagamentoModalidade.DEBITO))
//                                .map(Recebimento::getValor).reduce(BigDecimal.ZERO, BigDecimal::add)
//                                .setScale(2, RoundingMode.HALF_UP), 2));
                        ServiceMascara.getMoeda(((ContasAReceber) cellData.getValue().getValue()).getVlrCredDeb()
                                .setScale(2, RoundingMode.HALF_UP), 2)
                );
            } else if (cellData.getValue().getValue() instanceof Recebimento) {
                if (((Recebimento) cellData.getValue().getValue()).getPagamentoModalidade().getDescricao().toLowerCase().contains("credito")
                        || ((Recebimento) cellData.getValue().getValue()).getPagamentoModalidade().getDescricao().toLowerCase().contains("debito"))
                    return new SimpleStringProperty(
                            ServiceMascara.getMoeda(((Recebimento) cellData.getValue().getValue()).valorProperty().getValue(), 2));
            }
            return new SimpleStringProperty("");
        });

        setColVlrLiquido(new TreeTableColumn("vlr Liq"));
        getColVlrLiquido().setPrefWidth(80);
        getColVlrLiquido().setStyle("-fx-alignment: center-right;");
        getColVlrLiquido().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleStringProperty(
                        ServiceMascara.getMoeda(((ContasAReceber) cellData.getValue().getValue()).vlrLiquidoProperty().getValue(), 2));
//            } else if (cellData.getValue().getValue() instanceof Recebimento) {
//                if (!(((Recebimento) cellData.getValue().getValue()).getPagamentoModalidade().getDescricao().toLowerCase().contains("credito")
//                        || ((Recebimento) cellData.getValue().getValue()).getPagamentoModalidade().getDescricao().toLowerCase().contains("debito")))
//                    return new SimpleStringProperty(
//                            ServiceMascara.getMoeda(((Recebimento) cellData.getValue().getValue()).valorProperty().getValue(), 2));
            }
            return new SimpleStringProperty("");
        });

        setColValorPago(new TreeTableColumn("vlr pago R$"));
        getColValorPago().setPrefWidth(80);
        getColValorPago().setStyle("-fx-alignment: center-right;");
        getColValorPago().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleStringProperty(
                        ServiceMascara.getMoeda(((ContasAReceber) cellData.getValue().getValue()).vlrPagoProperty().getValue(), 2));
            } else if (cellData.getValue().getValue() instanceof Recebimento) {
                if ((!(((Recebimento) cellData.getValue().getValue()).getPagamentoModalidade().getDescricao().toLowerCase().contains("credito")
                        || ((Recebimento) cellData.getValue().getValue()).getPagamentoModalidade().getDescricao().toLowerCase().contains("debito"))))
                    return new SimpleStringProperty(
                            ServiceMascara.getMoeda(((Recebimento) cellData.getValue().getValue()).valorProperty().getValue(), 2));
            }
            return new SimpleStringProperty("0,00");
        });

        setColValorSaldo(new TreeTableColumn<>("saldo R$"));
        getColValorSaldo().setPrefWidth(80);
        getColValorSaldo().setStyle("-fx-alignment: center-right;");
        getColValorSaldo().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleStringProperty(
                        ServiceMascara.getMoeda(((ContasAReceber) cellData.getValue().getValue()).vlrSaldoProperty().getValue(), 2));
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
            setPedidoTreeItem(new TreeItem());
            getContasAReceberFilteredList().stream()
                    .forEach(contasAReceber -> {
                        final BigDecimal[] vlrCredDeb = {BigDecimal.ZERO};
                        final BigDecimal[] vlrPago = {BigDecimal.ZERO};

                        TreeItem<Object> paiItem = new TreeItem(contasAReceber);
                        getPedidoTreeItem().getChildren().add(paiItem);
                        contasAReceber.getRecebimentoList().stream()
                                .forEach(recebimento -> {
                                    TreeItem<Object> filhoItem = new TreeItem(recebimento);
                                    paiItem.getChildren().add(filhoItem);
                                    if (recebimento.getPagamentoModalidade().getDescricao().toLowerCase().contains("credito")
                                            || recebimento.getPagamentoModalidade().getDescricao().toLowerCase().contains("debito")) {
                                        vlrCredDeb[0] = vlrCredDeb[0].add(recebimento.valorProperty().getValue());
                                    } else {
                                        if (recebimento.pagamentoSituacaoProperty().getValue().equals(PagamentoSituacao.QUITADO))
                                            vlrPago[0] = vlrPago[0].add(recebimento.valorProperty().getValue());
                                    }
                                });

                        BigDecimal vlrLiquido = contasAReceber.valorProperty().getValue().subtract(vlrCredDeb[0]);

                        contasAReceber.vlrCredDebProperty().setValue(vlrCredDeb[0]);

                        contasAReceber.vlrLiquidoProperty().setValue(vlrLiquido);

                        contasAReceber.vlrPagoProperty().setValue(vlrPago[0]);

                        contasAReceber.vlrSaldoProperty().setValue(vlrLiquido.subtract(vlrPago[0]));

                    });


            getTtvContasAReceber().getColumns().setAll(getColId(), getColCliente_Documento(), getColDtVenda(),
                    getColModalidade(), getColSituacao(), getColDtVencimento_DtPagamento(), getColVlrBruto(),
                    getColVlrCredDeb(), getColVlrLiquido(), getColValorPago(), getColValorSaldo());

            getTtvContasAReceber().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            getTtvContasAReceber().setRoot(getPedidoTreeItem());
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

        getContasAReceberFilteredList().addListener((ListChangeListener<? super ContasAReceber>) change -> {
            preencherTabela();
        });

        getLblRegistrosLocalizados().textProperty().bind(Bindings.createIntegerBinding(() ->
                getContasAReceberFilteredList().size(), getContasAReceberFilteredList().predicateProperty()).asString());

        empresaProperty().addListener(observable -> aplicaFiltro());
        getTxtPesquisa().textProperty().addListener(observable -> aplicaFiltro());
        pagamentoSituacaoProperty().addListener(observable -> aplicaFiltro());
        getDtpData1().valueProperty().addListener(observable -> aplicaFiltro());
        getDtpData2().valueProperty().addListener(observable -> aplicaFiltro());
        getChkDtVenda().selectedProperty().addListener(observable -> aplicaFiltro());
    }

    private void aplicaFiltro() {
        try {
            getContasAReceberFilteredList().setPredicate(contasAReceber -> {
                if (getDtpData1().getValue() == null || getDtpData2().getValue() == null)
                    return true;
                if (chkDtVendaProperty().getValue().isSelected()) {
                    if (contasAReceber.dtCadastroProperty().getValue().toLocalDate().isBefore(getDtpData1().getValue())
                            || contasAReceber.dtCadastroProperty().getValue().toLocalDate().isAfter(getDtpData2().getValue()))
                        return false;
                } else {
                    if (contasAReceber.dtVencimentoProperty().getValue().isBefore(getDtpData1().getValue())
                            || contasAReceber.dtVencimentoProperty().getValue().isAfter(getDtpData2().getValue()))
                        return false;
                }
                if (empresaProperty().getValue() != null) {
                    if (empresaProperty().getValue().idProperty().getValue() != 0)
                        if (contasAReceber.getSaidaProduto().getCliente().idProperty().getValue() != empresaProperty().getValue().idProperty().getValue()) {
                            return false;
                        }
                }

                if (pagamentoSituacaoProperty().getValue() != null) {
                    switch (pagamentoSituacaoProperty().getValue()) {
                        case PENDENTE:
                            if (contasAReceber.valorProperty().getValue()
                                    .compareTo(contasAReceber.getRecebimentoList().stream()
                                            .filter(recebimento -> recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
                                            .map(Recebimento::getValor).reduce(BigDecimal.ZERO, BigDecimal::add)) <= 0)
                                return false;
                            break;
                        case CANCELADO:
                            if (contasAReceber.getRecebimentoList().stream()
                                    .filter(recebimento -> recebimento.getPagamentoSituacao()
                                            .equals(PagamentoSituacao.CANCELADO)).count() == 0)
                                return false;
                            break;
                        case QUITADO:
                            if (contasAReceber.valorProperty().getValue()
                                    .compareTo(contasAReceber.getRecebimentoList().stream()
                                            .filter(recebimento -> recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
                                            .map(Recebimento::getValor).reduce(BigDecimal.ZERO, BigDecimal::add)) > 0)
                                return false;
                            break;
                    }
                }

                if (contasAReceber.getRecebimentoList().stream()
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
//        setQtdClientes(
//                getContasAReceberFilteredList().stream()
//                        .map(ContasAReceber::getSaidaProduto)
//                        .map(SaidaProduto::getCliente)
//                        .collect(Collectors.groupingBy(Empresa::getId))
//                        .size()
//        );
//
//        setQtdContas(getContasAReceberFilteredList().size());
//
//        setTotalContas(
//                getContasAReceberFilteredList().stream()
//                        .map(ContasAReceber::getValor)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add)
//                        .subtract(getContasAReceberFilteredList().stream()
//                                .map(ContasAReceber::getVlrCredDeb)
//                                .reduce(BigDecimal.ZERO, BigDecimal::add))
//        );
//
//        setQtdContasRetiradas(
//                (int) getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) == 0)
//                        .count()
//        );
//
//        setTotalContasRetiradas(
//                getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) == 0)
//                        .map(ContasAReceber::getValor)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add)
//        );
//
//        setQtdContasDescontos(
//                (int) getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) > 0
//                                && aReceber.saidaProdutoProperty().getValue().getSaidaProdutoProdutoList().stream()
//                                .map(SaidaProdutoProduto::getVlrDesconto).reduce(BigDecimal.ZERO, BigDecimal::add)
//                                .compareTo(BigDecimal.ZERO) > 0)
//                        .count()
//        );
//
//        setTotalContasDescontos(
//                getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) > 0
//                                && aReceber.saidaProdutoProperty().getValue().getSaidaProdutoProdutoList().stream()
//                                .map(SaidaProdutoProduto::getVlrDesconto).reduce(BigDecimal.ZERO, BigDecimal::add)
//                                .compareTo(BigDecimal.ZERO) > 0)
//                        .map(ContasAReceber::getSaidaProduto).map(SaidaProduto::getSaidaProdutoProdutoList)
//                        .map(saidaProdutoProdutos -> saidaProdutoProdutos.stream().map(SaidaProdutoProduto::getVlrDesconto)
//                                .reduce(BigDecimal.ZERO, BigDecimal::add))
//                        .reduce(BigDecimal.ZERO, BigDecimal::add)
//        );
//
//        setTotalLucroBruto(getContasAReceberFilteredList().stream()
//                .map(ContasAReceber::getSaidaProduto)
//                .map(SaidaProduto::getSaidaProdutoProdutoList)
//                .map(saidaProdutoProdutos -> saidaProdutoProdutos.stream()
//                        .map(saidaProdutoProduto -> saidaProdutoProduto.vlrBrutoProperty().getValue()
//                                .subtract(saidaProdutoProduto.vlrEntradaBrutoProperty().getValue()))
//                        .reduce(BigDecimal.ZERO, BigDecimal::add))
//                .reduce(BigDecimal.ZERO, BigDecimal::add));
//
//        if (getTotalLucroBruto().compareTo(BigDecimal.ZERO) > 0)
//            setPercLucroBruto(getTotalLucroBruto().multiply(new BigDecimal("100."))
//                    .divide(getTotalContas(), 4, RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP));
//        else
//            setPercLucroBruto(BigDecimal.ZERO);
//
//        setTotalLucroLiquido(getTotalLucroBruto().subtract(getTotalContasDescontos().add(getTotalContasRetiradas())));
//
//        System.out.printf("getTotalLucroLiquido: %s\n", getTotalLucroLiquido().toString());
//        System.out.printf("getTotalContas: %s\n", getTotalContas().toString());
//        if (getTotalLucroLiquido().compareTo(BigDecimal.ZERO) > 0)
//            setPercLucroLiquido((getTotalLucroLiquido().scaleByPowerOfTen(2).divide(getTotalContas())).setScale(2, RoundingMode.HALF_UP));
//        else
//            setPercLucroLiquido(BigDecimal.ZERO);
//
//        setQtdContasAReceber(
//                (int) getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) > 0)
//                        .count()
//        );
//
//        setTotalContasAReceber(
//                getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) > 0)
//                        .map(ContasAReceber::getValor)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add)
//        );
//
//        setQtdContasVencidas(
//                (int) getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.vlrSaldoProperty().getValue().compareTo(BigDecimal.ZERO) != 0
//                                && aReceber.dtVencimentoProperty().getValue().compareTo(LocalDate.now()) < 0)
//                        .count()
//        );
//
//        setTotalContasVencidas(
//                getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.vlrSaldoProperty().getValue().compareTo(BigDecimal.ZERO) != 0
//                                && aReceber.dtVencimentoProperty().getValue().compareTo(LocalDate.now()) < 0)
//                        .map(ContasAReceber::getVlrSaldo)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add)
//        );
//
//        setQtdContasPendentes(
//                (int) getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.vlrSaldoProperty().getValue().compareTo(BigDecimal.ZERO) > 0
//                                && aReceber.dtVencimentoProperty().getValue().compareTo(LocalDate.now()) >= 0)
//                        .count()
//        );
//
//        setTotalContasPendentes(
//                getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.vlrSaldoProperty().getValue().compareTo(BigDecimal.ZERO) > 0
//                                && aReceber.dtVencimentoProperty().getValue().compareTo(LocalDate.now()) >= 0)
//                        .map(ContasAReceber::getVlrSaldo)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add)
//        );
//
//        setQtdContasPagas(
//                (int) getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) > 0
//                                && aReceber.vlrSaldoProperty().getValue().compareTo(BigDecimal.ZERO) <= 0)
//                        .count()
//        );
//
//        setTotalContasPagas(
//                getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.valorProperty().getValue().compareTo(BigDecimal.ZERO) > 0
//                                && aReceber.vlrSaldoProperty().getValue().compareTo(BigDecimal.ZERO) <= 0)
//                        .map(ContasAReceber::getVlrPago)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add)
//        );
//
//        setQtdContasSaldoClientes(
//                (int) getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.vlrSaldoProperty().getValue().compareTo(BigDecimal.ZERO) < 0)
//                        .count()
//        );
//
//        setTotalContasSaldoClientes(
//                getContasAReceberFilteredList().stream()
//                        .filter(aReceber -> aReceber.vlrSaldoProperty().getValue().compareTo(BigDecimal.ZERO) < 0)
//                        .map(ContasAReceber::getVlrSaldo)
//                        .reduce(BigDecimal.ZERO, BigDecimal::add)
//        );
//
////        setPercLucroLiquido(
////                BigDecimal.ONE.setScale(4)
////        );
//
////        setTotalLucroLiquido(
////                BigDecimal.ONE.setScale(4)
////        );
//
////        setPercLucroLiquido(
////                getQtdContasVencidas() + getQtdContasPendentes() + getQtdContasPagas()
////        );
//
////        setTotalContasConfirmacao(
////                getTotalContasVencidas().add(getTotalContasPendentes()).add(getTotalContasPagas()).add(getTotalContasSaldoClientes())
////        );
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

    public TextField getTxtPesquisa() {
        return txtPesquisa;
    }

    public void setTxtPesquisa(TextField txtPesquisa) {
        this.txtPesquisa = txtPesquisa;
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

    public FilteredList<ContasAReceber> getContasAReceberFilteredList() {
        return contasAReceberFilteredList;
    }

    public void setContasAReceberFilteredList(FilteredList<ContasAReceber> contasAReceberFilteredList) {
        this.contasAReceberFilteredList = contasAReceberFilteredList;
    }

    public DatePicker getDtpData1() {
        return dtpData1.get();
    }

    public ObjectProperty<DatePicker> dtpData1Property() {
        return dtpData1;
    }

    public void setDtpData1(DatePicker dtpData1) {
        this.dtpData1.set(dtpData1);
    }

    public DatePicker getDtpData2() {
        return dtpData2.get();
    }

    public ObjectProperty<DatePicker> dtpData2Property() {
        return dtpData2;
    }

    public void setDtpData2(DatePicker dtpData2) {
        this.dtpData2.set(dtpData2);
    }

    public CheckBox getChkDtVenda() {
        return chkDtVenda.get();
    }

    public ObjectProperty<CheckBox> chkDtVendaProperty() {
        return chkDtVenda;
    }

    public void setChkDtVenda(CheckBox chkDtVenda) {
        this.chkDtVenda.set(chkDtVenda);
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

    public PagamentoSituacao getPagamentoSituacao() {
        return pagamentoSituacao.get();
    }

    public ObjectProperty<PagamentoSituacao> pagamentoSituacaoProperty() {
        return pagamentoSituacao;
    }

    public void setPagamentoSituacao(PagamentoSituacao pagamentoSituacao) {
        this.pagamentoSituacao.set(pagamentoSituacao);
    }

    public TreeItem<Object> getPedidoTreeItem() {
        return pedidoTreeItem;
    }

    public void setPedidoTreeItem(TreeItem<Object> pedidoTreeItem) {
        this.pedidoTreeItem = pedidoTreeItem;
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

    public TreeTableColumn<Object, String> getColVlrBruto() {
        return colVlrBruto;
    }

    public void setColVlrBruto(TreeTableColumn<Object, String> colVlrBruto) {
        this.colVlrBruto = colVlrBruto;
    }

    public TreeTableColumn<Object, String> getColVlrCredDeb() {
        return colVlrCredDeb;
    }

    public void setColVlrCredDeb(TreeTableColumn<Object, String> colVlrCredDeb) {
        this.colVlrCredDeb = colVlrCredDeb;
    }

    public TreeTableColumn<Object, String> getColVlrLiquido() {
        return colVlrLiquido;
    }

    public void setColVlrLiquido(TreeTableColumn<Object, String> colVlrLiquido) {
        this.colVlrLiquido = colVlrLiquido;
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
