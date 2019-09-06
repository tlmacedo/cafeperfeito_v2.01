package br.com.tlmacedo.cafeperfeito.model.tm;

import br.com.tlmacedo.cafeperfeito.model.dao.ContasAReceberDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoModalidade;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoSituacao;
import br.com.tlmacedo.cafeperfeito.model.vo.ContasAReceber;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.Recebimento;
import br.com.tlmacedo.cafeperfeito.service.ServiceMascara;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.*;

public class TmodelContasAReceber {

    private TablePosition tp;
    private DatePicker dtpData1;
    private DatePicker dtpData2;
    private CheckBox chkDtVenda;
    private ObjectProperty<Empresa> empresa;// = new SimpleObjectProperty<>();
    private TextField txtPesquisa;
    private ComboBox cboPagamentoSituacao;
    private Label lblRegistrosLocalizados;
    private TreeTableView<Object> ttvContasAReceber;
    private ContasAReceberDAO aReceberDAO = new ContasAReceberDAO();
    private ObservableList<ContasAReceber> aReceberObservableList = FXCollections.observableArrayList(getaReceberDAO().getAll(ContasAReceber.class, null, null, null, "dtCadastro DESC"));
    private FilteredList<ContasAReceber> aReceberFilteredList = new FilteredList<ContasAReceber>(getaReceberObservableList());

    private TreeItem<Object> aReceberTreeItem;
    private TreeTableColumn<Object, Long> colId;
    private TreeTableColumn<Object, String> colCliente_Documento;
    private TreeTableColumn<Object, String> colDtVenda_Modalidade;
    private TreeTableColumn<Object, String> colSituacao;
    private TreeTableColumn<Object, String> colDtVencimento_DtPagamento;
    private TreeTableColumn<Object, String> colValor;
    private TreeTableColumn<Object, String> colValorPago;
    private TreeTableColumn<Object, String> colValorSaldo;
    private TreeTableColumn<Object, String> colUsuario;

    private IntegerProperty qtdClientes = new SimpleIntegerProperty(0);
    private IntegerProperty qtdContas = new SimpleIntegerProperty(0);
    private IntegerProperty qtdContasPagas = new SimpleIntegerProperty(0);
    private IntegerProperty qtdContasAbertas = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> totalContas = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<BigDecimal> totalContasPagas = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<BigDecimal> totalContasAbertas = new SimpleObjectProperty<>(BigDecimal.ZERO);

    private List<String> styleList = new ArrayList<>();

    /**
     * Begin voids
     */

    private void carregaListaStyle() {

        getStyleList().add("pendente");
        getStyleList().add("vencido");
        getStyleList().add("pago");
        getStyleList().add("cancelado");
        getStyleList().add("retirada");
    }

    public void criarTabela() {
        carregaListaStyle();


        setColId(new TreeTableColumn("id"));
        getColId().setPrefWidth(58);
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
        getColCliente_Documento().setPrefWidth(250);
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

        setColDtVenda_Modalidade(new TreeTableColumn("dt. venda / mod pag"));
        getColDtVenda_Modalidade().setPrefWidth(120);
        getColDtVenda_Modalidade().setCellFactory(cellFactory -> new TreeTableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getItem());
                if (getTreeTableRow().getItem() instanceof ContasAReceber)
                    setAlignment(Pos.CENTER_RIGHT);
                else
                    setAlignment(Pos.CENTER_LEFT);
            }
        });
        getColDtVenda_Modalidade().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleStringProperty(((ContasAReceber) cellData.getValue().getValue()).dtCadastroProperty().getValue().format(DTF_MYSQL_DATAHORA_HM));
            } else if (cellData.getValue().getValue() instanceof Recebimento) {
                return new SimpleStringProperty(((Recebimento) cellData.getValue().getValue()).getPagamentoModalidade().getDescricao());
            }
            return new SimpleStringProperty("");
        });

        setColSituacao(new TreeTableColumn(" / situação"));
        getColSituacao().setPrefWidth(90);
        getColSituacao().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof Recebimento) {
                return new SimpleStringProperty(((Recebimento) cellData.getValue().getValue()).getPagamentoSituacao().getDescricao());
            }
            return new SimpleStringProperty("");
        });

        setColDtVencimento_DtPagamento(new TreeTableColumn("dt. venc / dt. pag"));
        getColDtVencimento_DtPagamento().setPrefWidth(100);
        getColDtVencimento_DtPagamento().setStyle("-fx-alignment: center-right;");
        getColDtVencimento_DtPagamento().setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue() instanceof ContasAReceber) {
                return new SimpleStringProperty(((ContasAReceber) cellData.getValue().getValue()).dtVencimentoProperty().getValue().format(DTF_MYSQL_DATA));
            } else if (cellData.getValue().getValue() instanceof Recebimento) {
                if (((Recebimento) cellData.getValue().getValue()).dtPagamentoProperty().getValue() == null)
                    return new SimpleStringProperty("");
                return new SimpleStringProperty(((Recebimento) cellData.getValue().getValue()).dtPagamentoProperty().getValue().format(DTF_MYSQL_DATA));
            }
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
                return new SimpleStringProperty(
                        ServiceMascara.getMoeda(((Recebimento) cellData.getValue().getValue()).valorProperty().getValue(), 2));
            }
            return new SimpleStringProperty("0,00");
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
            } else if (cellData.getValue().getValue() instanceof Recebimento) {
                if (!((Recebimento) cellData.getValue().getValue()).getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
                    return new SimpleStringProperty(
                            ServiceMascara.getMoeda(((Recebimento) cellData.getValue().getValue()).valorProperty().getValue(), 2));
            }
            return new SimpleStringProperty("0,00");
        });


    }


    public void preencherTabela() {
        try {
            setaReceberTreeItem(new TreeItem(null));
            getaReceberFilteredList().stream()
                    //.filter(aReceber -> aReceber.getSaidaProduto().getCliente().idProperty().getValue() == 87)
                    .forEach(aReceber1 -> {
                        final BigDecimal[] vlrPago = {BigDecimal.ZERO};
                        final BigDecimal[] vlrSaldo = {aReceber1.valorProperty().getValue()};
                        TreeItem<Object> paiItem = new TreeItem(aReceber1);
                        getaReceberTreeItem().getChildren().add(paiItem);
                        aReceber1.getRecebimentoList().stream()
                                .forEach(recebimento -> {
                                    if (recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO)) {
                                        vlrPago[0] = vlrPago[0].add(recebimento.valorProperty().getValue());
                                        vlrSaldo[0] = vlrSaldo[0].subtract(recebimento.valorProperty().getValue());
                                    }
                                    TreeItem<Object> filhoItem = new TreeItem<>(recebimento);
                                    paiItem.getChildren().add(filhoItem);
                                });
                        ((ContasAReceber) paiItem.getValue()).valorPagoProperty().setValue(vlrPago[0]);
                        ((ContasAReceber) paiItem.getValue()).valorSaldoProperty().setValue(vlrSaldo[0]);
                    });


            getTtvContasAReceber().getColumns().setAll(getColId(), getColCliente_Documento(), getColDtVenda_Modalidade(),
                    getColSituacao(), getColDtVencimento_DtPagamento(), getColValor(), getColValorPago(), getColValorSaldo());

            getTtvContasAReceber().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            getTtvContasAReceber().setRoot(getaReceberTreeItem());
            getTtvContasAReceber().setShowRoot(false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void escutaLista() {
        try {
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
                totalizaTabela();
            });

            getDtpData1().valueProperty().addListener(observable -> aplicaFiltro());
            getDtpData2().valueProperty().addListener(observable -> aplicaFiltro());
            getChkDtVenda().selectedProperty().addListener(observable -> aplicaFiltro());
//            empresaProperty().addListener((ov, o, n) -> {
//                if (n != null)
//                    aplicaFiltro();
//            });

            totalizaTabela();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void aplicaFiltro() {
        try {
            getaReceberFilteredList().setPredicate(aReceber -> {
                if (getDtpData1().getValue() == null || getDtpData2().getValue() == null)
                    return true;
                if (getChkDtVenda().isSelected()) {
//                    System.out.printf("dtpData1:[%s]\tdtCadastro:[%s]\tdtpData2:[%s]\t\tresult:[%s]\n",
//                            getDtpData1().getValue(),
//                            aReceber.dtCadastroProperty().getValue().toLocalDate().format(DTF_DATA),
//                            getDtpData2().getValue(),
//                            (!(aReceber.dtCadastroProperty().getValue().toLocalDate().isBefore(getDtpData1().getValue())
//                                    || aReceber.dtCadastroProperty().getValue().toLocalDate().isAfter(getDtpData2().getValue()))));
//                    if (aReceber.dtCadastroProperty().getValue().toLocalDate().isBefore(getDtpData1().getValue())
//                            || aReceber.dtCadastroProperty().getValue().toLocalDate().isAfter(getDtpData2().getValue()))
//                        return false;
                } else {
                    if (aReceber.dtVencimentoProperty().getValue().isBefore(getDtpData1().getValue())
                            || aReceber.dtVencimentoProperty().getValue().isAfter(getDtpData2().getValue())) {
                        System.out.printf("dtpData1:[%s]\tdtVencimento:[%s]\tdtpData2:[%s]\t\tresult:[%s]\n",
                                getDtpData1().getValue(),
                                aReceber.dtCadastroProperty().getValue().toLocalDate().format(DTF_DATA),
                                getDtpData2().getValue(),
                                (!(aReceber.dtVencimentoProperty().getValue().isBefore(getDtpData1().getValue())
                                        || aReceber.dtVencimentoProperty().getValue().isAfter(getDtpData2().getValue()))));
                        return false;
                    }
                }
//                if (empresaProperty().getValue() != null) {
//                    if (!aReceber.getSaidaProduto().getCliente().equals(empresaProperty().get()))
//                        return false;
//                }


                return true;
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void totalizaTabela() {
        getTtvContasAReceber().refresh();
//        setQtdClientes((int) getaReceberFilteredList().stream().map(ContasAReceber::getSaidaProduto).map(SaidaProduto::getCliente).count());
//
//        setQtdContas(getaReceberFilteredList().size());
//        setQtdContasPagas((int) getaReceberFilteredList().stream().filter(aReceber -> aReceber.valorProperty().getValue().compareTo(aReceber.getRecebimentoList().stream()
//                .filter(recebimento -> recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
//                .map(Recebimento::getValor).reduce(BigDecimal.ZERO, BigDecimal::add)) <= 0).count());
//        setQtdContasAbertas(getQtdContas() - getQtdContasPagas());
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

    public ComboBox getCboPagamentoSituacao() {
        return cboPagamentoSituacao;
    }

    public void setCboPagamentoSituacao(ComboBox cboPagamentoSituacao) {
        this.cboPagamentoSituacao = cboPagamentoSituacao;
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

    public TreeTableColumn<Object, String> getColDtVenda_Modalidade() {
        return colDtVenda_Modalidade;
    }

    public void setColDtVenda_Modalidade(TreeTableColumn<Object, String> colDtVenda_Modalidade) {
        this.colDtVenda_Modalidade = colDtVenda_Modalidade;
    }

    public TreeTableColumn<Object, String> getColSituacao() {
        return colSituacao;
    }

    public void setColSituacao(TreeTableColumn<Object, String> colSituacao) {
        this.colSituacao = colSituacao;
    }

    public TreeTableColumn<Object, String> getColDtVencimento_DtPagamento() {
        return colDtVencimento_DtPagamento;
    }

    public void setColDtVencimento_DtPagamento(TreeTableColumn<Object, String> colDtVencimento_DtPagamento) {
        this.colDtVencimento_DtPagamento = colDtVencimento_DtPagamento;
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

    public int getQtdContasPagas() {
        return qtdContasPagas.get();
    }

    public IntegerProperty qtdContasPagasProperty() {
        return qtdContasPagas;
    }

    public void setQtdContasPagas(int qtdContasPagas) {
        this.qtdContasPagas.set(qtdContasPagas);
    }

    public int getQtdContasAbertas() {
        return qtdContasAbertas.get();
    }

    public IntegerProperty qtdContasAbertasProperty() {
        return qtdContasAbertas;
    }

    public void setQtdContasAbertas(int qtdContasAbertas) {
        this.qtdContasAbertas.set(qtdContasAbertas);
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

    public BigDecimal getTotalContasPagas() {
        return totalContasPagas.get();
    }

    public ObjectProperty<BigDecimal> totalContasPagasProperty() {
        return totalContasPagas;
    }

    public void setTotalContasPagas(BigDecimal totalContasPagas) {
        this.totalContasPagas.set(totalContasPagas);
    }

    public BigDecimal getTotalContasAbertas() {
        return totalContasAbertas.get();
    }

    public ObjectProperty<BigDecimal> totalContasAbertasProperty() {
        return totalContasAbertas;
    }

    public void setTotalContasAbertas(BigDecimal totalContasAbertas) {
        this.totalContasAbertas.set(totalContasAbertas);
    }

    public List<String> getStyleList() {
        return styleList;
    }

    public void setStyleList(List<String> styleList) {
        this.styleList = styleList;
    }

    /**
     * END Getters e Setters
     */
}
