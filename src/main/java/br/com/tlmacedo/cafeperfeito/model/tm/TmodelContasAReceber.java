package br.com.tlmacedo.cafeperfeito.model.tm;

import br.com.tlmacedo.cafeperfeito.model.dao.ContasAReceberDAO;
import br.com.tlmacedo.cafeperfeito.model.vo.ContasAReceber;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.Recebimento;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;

import java.math.BigDecimal;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_DATAHORA_HM;

public class TmodelContasAReceber {

    private TablePosition tp;
    private DatePicker dtpData1;
    private DatePicker dtpData2;
    private CheckBox chkDtVenda;
    private ObjectProperty<Empresa> empresa;
    private TextField txtPesquisa;
    private ComboBox cboPagamentoSituacao;
    private Label lblRegistrosLocalizados;
    private TreeTableView<ContasAReceber> ttvContasAReceber;
    private ContasAReceberDAO aReceberDAO = new ContasAReceberDAO();
    private ObservableList<ContasAReceber> aReceberObservableList = FXCollections.observableArrayList(getaReceberDAO().getAll(ContasAReceber.class, null, null, null, "dtVencimento"));
    private FilteredList<ContasAReceber> aReceberFilteredList = new FilteredList<>(aReceberObservableList);

    private TreeItem<ContasAReceber> aReceberTreeItem;
    private TreeTableColumn colId;
    private TreeTableColumn colCliente_Documento;
    private TreeTableColumn colDtVenda_Modalidade;
    private TreeTableColumn colSituacao;
    private TreeTableColumn colDtVencimento_DtPagamento;
    private TreeTableColumn colValor;
    private TreeTableColumn colValorPago;
    private TreeTableColumn colValorSaldo;
    private TreeTableColumn colUsuario;

    private IntegerProperty qtdClientes = new SimpleIntegerProperty(0);
    private IntegerProperty qtdContas = new SimpleIntegerProperty(0);
    private IntegerProperty qtdContasPagas = new SimpleIntegerProperty(0);
    private IntegerProperty qtdContasAbertas = new SimpleIntegerProperty(0);
    private ObjectProperty<BigDecimal> totalContas = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<BigDecimal> totalContasPagas = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<BigDecimal> totalContasAbertas = new SimpleObjectProperty<>(BigDecimal.ZERO);


    /**
     * Begin voids
     */

    public void criarTabela() {
        setColId(new TreeTableColumn("id"));
        getColId().setPrefWidth(48);
        getColId().setStyle("-fx-alignment: center-right;");
        getColId().setCellValueFactory((Object obj) -> {
            final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
            if (dataObj instanceof ContasAReceber) {
                return new ReadOnlyStringWrapper(((ContasAReceber) dataObj).idProperty().getValue().toString());
            } else if (dataObj instanceof Recebimento) {
                return "";
            }
            return null;
        });

        setColCliente_Documento(new TreeTableColumn("cliente / documento"));
        getColCliente_Documento().setPrefWidth(250);
        getColCliente_Documento().setCellValueFactory((Object obj) -> {
            final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
            if (dataObj instanceof ContasAReceber) {
                return new ReadOnlyStringWrapper(((ContasAReceber) dataObj).getSaidaProduto().getCliente().getRazaoFantasia());
            } else if (dataObj instanceof Recebimento) {
                return new ReadOnlyStringWrapper(((Recebimento) dataObj).documentoProperty().getValue());
            }
            return null;
        });

        setColDtVenda_Modalidade(new TreeTableColumn("dt. venda / mod pag"));
        getColDtVenda_Modalidade().setPrefWidth(100);
        getColDtVenda_Modalidade().setStyle("-fx-alignment: center-right");
        getColDtVenda_Modalidade().setCellValueFactory((Object obj) -> {
            final Object dataObj = ((TreeTableColumn.CellDataFeatures) obj).getValue().getValue();
            if (dataObj instanceof ContasAReceber) {
                return new ReadOnlyStringWrapper(((ContasAReceber) dataObj).getSaidaProduto().dtCadastroProperty().getValue().format(DTF_DATAHORA_HM));
            } else if (dataObj instanceof Recebimento) {
                return new ReadOnlyStringWrapper(((Recebimento) dataObj).getPagamentoModalidade().getDescricao());
            }
            return null;
        });
    }


    public void preencherTabela() {
        try {
            setaReceberTreeItem(new TreeItem<>());
            getaReceberFilteredList().forEach(aReceber1 -> {
                TreeItem<ContasAReceber> receberTreeItem = new TreeItem<>(aReceber1);
                getaReceberTreeItem().getChildren().add(receberTreeItem);
                aReceber1.getRecebimentoList().stream()
                        .forEach(recebimento -> {
                            receberTreeItem.getChildren().add(new TreeItem(recebimento));
                        });
            });

            getTtvContasAReceber().getColumns().setAll(getColId(), getColCliente_Documento(), getColDtVenda_Modalidade());
            getTtvContasAReceber().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            getTtvContasAReceber().setRoot(getaReceberTreeItem());
            getTtvContasAReceber().setShowRoot(false);
        } catch (Exception ex) {
            System.out.printf("\nDeuzebrabemaqui\n");
            ex.printStackTrace();
        }
    }

    public void escutaLista() {

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

    public TreeTableView<ContasAReceber> getTtvContasAReceber() {
        return ttvContasAReceber;
    }

    public void setTtvContasAReceber(TreeTableView<ContasAReceber> ttvContasAReceber) {
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

    public TreeItem<ContasAReceber> getaReceberTreeItem() {
        return aReceberTreeItem;
    }

    public void setaReceberTreeItem(TreeItem<ContasAReceber> aReceberTreeItem) {
        this.aReceberTreeItem = aReceberTreeItem;
    }

    public TreeTableColumn getColId() {
        return colId;
    }

    public void setColId(TreeTableColumn colId) {
        this.colId = colId;
    }

    public TreeTableColumn getColCliente_Documento() {
        return colCliente_Documento;
    }

    public void setColCliente_Documento(TreeTableColumn colCliente_Documento) {
        this.colCliente_Documento = colCliente_Documento;
    }

    public TreeTableColumn getColDtVenda_Modalidade() {
        return colDtVenda_Modalidade;
    }

    public void setColDtVenda_Modalidade(TreeTableColumn colDtVenda_Modalidade) {
        this.colDtVenda_Modalidade = colDtVenda_Modalidade;
    }

    public TreeTableColumn getColSituacao() {
        return colSituacao;
    }

    public void setColSituacao(TreeTableColumn colSituacao) {
        this.colSituacao = colSituacao;
    }

    public TreeTableColumn getColDtVencimento_DtPagamento() {
        return colDtVencimento_DtPagamento;
    }

    public void setColDtVencimento_DtPagamento(TreeTableColumn colDtVencimento_DtPagamento) {
        this.colDtVencimento_DtPagamento = colDtVencimento_DtPagamento;
    }

    public TreeTableColumn getColValor() {
        return colValor;
    }

    public void setColValor(TreeTableColumn colValor) {
        this.colValor = colValor;
    }

    public TreeTableColumn getColValorPago() {
        return colValorPago;
    }

    public void setColValorPago(TreeTableColumn colValorPago) {
        this.colValorPago = colValorPago;
    }

    public TreeTableColumn getColValorSaldo() {
        return colValorSaldo;
    }

    public void setColValorSaldo(TreeTableColumn colValorSaldo) {
        this.colValorSaldo = colValorSaldo;
    }

    public TreeTableColumn getColUsuario() {
        return colUsuario;
    }

    public void setColUsuario(TreeTableColumn colUsuario) {
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

/**
 * END Getters e Setters
 */
}
