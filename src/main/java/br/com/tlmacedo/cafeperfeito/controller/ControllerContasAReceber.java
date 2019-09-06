package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.EnumsTasks;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoSituacao;
import br.com.tlmacedo.cafeperfeito.model.enums.StatusBarContasAReceber;
import br.com.tlmacedo.cafeperfeito.model.tm.TmodelContasAReceber;
import br.com.tlmacedo.cafeperfeito.model.vo.ContasAReceber;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.Recebimento;
import br.com.tlmacedo.cafeperfeito.service.ServiceAlertMensagem;
import br.com.tlmacedo.cafeperfeito.service.ServiceCampoPersonalizado;
import br.com.tlmacedo.cafeperfeito.service.ServiceSegundoPlano;
import br.com.tlmacedo.cafeperfeito.service.autoComplete.ServiceAutoCompleteComboBox;
import br.com.tlmacedo.cafeperfeito.service.format.FormatDataPicker;
import br.com.tlmacedo.cafeperfeito.view.ViewContasAReceber;
import br.com.tlmacedo.cafeperfeito.view.ViewRecebimento;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerContasAReceber implements Initializable, ModeloCafePerfeito {


    public AnchorPane painelViewContasAReceber;
    public TitledPane tpnContasAReceber;
    public DatePicker dtpData1;
    public DatePicker dtpData2;
    public CheckBox chkDtVenda;
    public ComboBox<Empresa> cboEmpresa;
    public TextField txtPesquisa;
    public ComboBox cboPagamentoSituacao;
    public Label lblRegistrosLocalizados;
    public TreeTableView<Object> ttvContasAReceber;
    public Label lblQtdClientes;
    public Label lblQtdContas;
    public Label lblQtdContasPagas;
    public Label lblQtdContasAbertas;
    public Label lblTotalContas;
    public Label lblTotalContasPagas;
    public Label lblTotalContasAbertas;


    private boolean tabCarregada = false;
    private List<EnumsTasks> enumsTasksList = new ArrayList<>();

    private String nomeTab = ViewContasAReceber.getTitulo();
    private String nomeController = "contasAReceber";
    private ObjectProperty<StatusBarContasAReceber> statusBar = new SimpleObjectProperty<>();
    private EventHandler eventHandlerContasAReceber;
    private ServiceAlertMensagem alertMensagem;

    private TmodelContasAReceber tmodelaReceber;
    private ObservableList<ContasAReceber> aReceberObservableList;
    private FilteredList<ContasAReceber> aReceberFilteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        criarObjetos();
        preencherObjetos();
        fatorarObjetos();
        if (!isTabCarregada()) {
            Platform.runLater(() -> fechar());
            return;
        }
        escutarTecla();
        fieldsFormat();
    }

    @Override
    public void fieldsFormat() {
        ServiceCampoPersonalizado.fieldTextFormat(getPainelViewContasAReceber());
    }

    @Override
    public void fechar() {
        ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getTabs().remove(
                ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getTabs().stream()
                        .filter(tab -> tab.textProperty().get().equals(getNomeTab()))
                        .findFirst().orElse(null)
        );
        if (isTabCarregada())
            ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal()
                    .removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerContasAReceber());
    }

    @Override
    public void criarObjetos() {
        getEnumsTasksList().add(EnumsTasks.TABELA_CRIAR);
    }

    @Override
    public void preencherObjetos() {
        getEnumsTasksList().add(EnumsTasks.TABELA_VINCULAR);

        getEnumsTasksList().add(EnumsTasks.TABELA_PREENCHER);

        getEnumsTasksList().add(EnumsTasks.COMBOS_PREENCHER);

        setTabCarregada(new ServiceSegundoPlano().executaListaTarefas(newTaskSaidaProduto(), String.format("Abrindo %s!", getNomeTab())));

    }

    @Override
    public void fatorarObjetos() {
        //getDtpData1().setDayCellFactory(param -> new FormatDataPicker(null));
        getDtpData2().setDayCellFactory(param -> new FormatDataPicker(getDtpData1().getValue()));

        getDtpData1().valueProperty().addListener((ov, o, n) -> {
            if (n == null || getDtpData1().getValue().compareTo(getDtpData2().getValue()) <= 0) return;
            getDtpData2().setValue(n);
            getDtpData2().setDayCellFactory(param -> new FormatDataPicker(n));
        });


    }

    @Override
    public void escutarTecla() {
        statusBarProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            showStatusBar();
        });

        if (statusBarProperty().get() == null)
            setStatusBar(StatusBarContasAReceber.DIGITACAO);

        setEventHandlerContasAReceber(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().getSelectedIndex() < 0)
                    return;
                if (!ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().getSelectedItem().getText().equals(getNomeTab()))
                    return;
                if (!ControllerPrincipal.getCtrlPrincipal().teclaDisponivel(event.getCode())) return;
                switch (event.getCode()) {
                    case F1:
                        //limpaCampos(getPainelViewSaidaProduto());
                        break;
                    case F2:
//                        ServiceCalculaTempo calcTmp = new ServiceCalculaTempo();
//                        getEnumsTasksList().clear();
//                        getEnumsTasksList().add(EnumsTasks.SALVAR_SAIDA);
//                        if (new ServiceSegundoPlano().executaListaTarefas(newTaskSaidaProduto(), String.format("Salvando %s!", getNomeTab()))) {
//                            new ViewRecebimento().openViewRecebimento(getTmodelSaidaProduto().getaReceber());
//                            limpaCampos(getPainelViewSaidaProduto());
//                        }
//                        calcTmp.fim();
                        break;
                    case F4:
                        Object obj;
                        if ((obj = getTtvContasAReceber().getSelectionModel().getSelectedItem().getValue()) == null)
                            return;
                        if (obj instanceof ContasAReceber) {
                            if (getTtvContasAReceber().getSelectionModel().getSelectedItem().getChildren().size() > 0) {
                                obj = (Recebimento) getTtvContasAReceber().getSelectionModel().getSelectedItem().getChildren().get(0).getValue();
                            }
                        }
                        new ViewRecebimento().openViewRecebimento((Recebimento) obj);
                        getTtvContasAReceber().refresh();
                        break;
                    case F6:
//                        getCboEmpresa().getEditor().setEditable(true);
//                        getCboEmpresa().requestFocus();
                        break;
                    case F7:
                        getTxtPesquisa().requestFocus();
                        break;
                    case F8:
//                        getTvItensPedido().requestFocus();
//                        getTvItensPedido().getSelectionModel().select(getSaidaProdutoProdutoObservableList().size() - 1,
//                                getTmodelSaidaProduto().getColQtd());
                        break;
                    case F9:
//                        getTpnNfe().setExpanded(!getTpnNfe().isExpanded());
//                        if (getTpnNfe().isExpanded())
//                            getCboNfeDadosNaturezaOperacao().requestFocus();
//                        else
//                            getTxtPesquisa().requestFocus();
                        break;
                    case F12:
                        fechar();
                        break;
                }
            }
        });

        ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            if (n.getText().equals(getNomeTab()))
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().addEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerContasAReceber());
            else
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerContasAReceber());
        });

        new ServiceAutoCompleteComboBox(Empresa.class, getCboEmpresa());

        getCboEmpresa().focusedProperty().addListener((ov, o, n) -> {
            if (!n) {
                getTmodelaReceber().setEmpresa(getCboEmpresa().getValue());
            }
            showStatusBar();
        });

        getLblQtdClientes().textProperty().bind(getTmodelaReceber().qtdClientesProperty().asString());

        getLblQtdContas().textProperty().bind(getTmodelaReceber().qtdContasProperty().asString());

        getLblQtdContasPagas().textProperty().bind(getTmodelaReceber().qtdContasPagasProperty().asString());

        getLblQtdContasAbertas().textProperty().bind(getTmodelaReceber().qtdContasAbertasProperty().asString());

    }

    /**
     * Begin Tasks
     */

    private Task newTaskSaidaProduto() {
        int qtdTasks = getEnumsTasksList().size();
        final int[] cont = {1};
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Loading...");
                for (EnumsTasks tasks : getEnumsTasksList()) {
                    updateProgress(cont[0]++, qtdTasks);
                    Thread.sleep(200);
                    updateMessage(String.format("%s%s", tasks.getDescricao(),
                            tasks.getDescricao().endsWith(" de ") ? getNomeController() : ""));
                    switch (tasks) {
                        case TABELA_CRIAR:
                            setTmodelaReceber(new TmodelContasAReceber());
                            getTmodelaReceber().criarTabela();
//
//                            setTmodelSaidaProduto(new TmodelSaidaProduto());
//                            getTmodelSaidaProduto().criaTabela();
                            break;

                        case TABELA_VINCULAR:
                            getTmodelaReceber().setDtpData1(getDtpData1());
                            getTmodelaReceber().setDtpData2(getDtpData2());
                            getTmodelaReceber().setChkDtVenda(getChkDtVenda());
                            getTmodelaReceber().setTxtPesquisa(getTxtPesquisa());
                            getTmodelaReceber().setCboPagamentoSituacao(getCboPagamentoSituacao());
                            getTmodelaReceber().setLblRegistrosLocalizados(getLblRegistrosLocalizados());
                            getTmodelaReceber().setTtvContasAReceber(getTtvContasAReceber());
                            setaReceberObservableList(getTmodelaReceber().getaReceberObservableList());
                            setaReceberFilteredList(getTmodelaReceber().getaReceberFilteredList());
                            getTmodelaReceber().escutaLista();
//
//                            getTmodelSaidaProduto().setTvSaidaProdutoProduto(getTvItensPedido());
//                            getTmodelSaidaProduto().setTxtPesquisa(getTxtPesquisa());
//                            getTmodelSaidaProduto().setDtpDtSaida(getDtpDtSaida());
//                            getTmodelSaidaProduto().setDtpDtVencimento(getDtpDtVencimento());
//                            setSaidaProdutoProdutoObservableList(getTmodelSaidaProduto().getSaidaProdutoProdutoObservableList());
//                            getTmodelSaidaProduto().escutaLista();

                            getDtpData1().setValue(LocalDate.of(LocalDate.now().getYear(), 1, 1));
                            getDtpData2().setValue(LocalDate.now().plusMonths(2).withDayOfMonth(1).minusDays(1));

                            break;

                        case COMBOS_PREENCHER:
                            getCboEmpresa().setItems(
                                    new EmpresaDAO().getAll(Empresa.class, null, null, null, "razao")
                                            .stream().filter(Empresa::isCliente)
                                            .collect(Collectors.toCollection(FXCollections::observableArrayList))
                            );

                            getCboEmpresa().getItems().add(0, new Empresa());

                            getCboPagamentoSituacao().setItems(Arrays.stream(PagamentoSituacao.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                            getCboPagamentoSituacao().getItems().add(0, "");
                            break;

                        case TABELA_PREENCHER:
                            getTmodelaReceber().preencherTabela();
//
//                            getTmodelSaidaProduto().preencheTabela();
                            break;

                        case SALVAR_SAIDA:
//                            if (getTmodelSaidaProduto().guardarSaidaProduto()) {
//                                if (getTmodelSaidaProduto().salvarSaidaProduto()) {
//                                    getProdutoObservableList().setAll(new ProdutoDAO().getAll(Produto.class, null, null, null, "descricao"));
//                                    getTtvProdutos().refresh();
//                                } else {
//                                    Thread.currentThread().interrupt();
//                                }
//                            } else {
//                                Thread.currentThread().interrupt();
//                            }
                            break;
                    }
                }
                updateMessage("tarefa concluída!!!");
                updateProgress(qtdTasks, qtdTasks);
                return null;
            }
        };
    }

    /**
     * END Tasks
     */

    /**
     * Begin voids
     */

    private void showStatusBar() {
        try {
//            if (getaReceberObservableList().size() <= 0 || getCboEmpresa().getValue().idProperty().getValue() == 0)
//                ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().atualizaStatusBar(statusBarProperty().getValue().getDescricao().replace("  [F2-Finalizar venda]", ""));
//            else
            ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().atualizaStatusBar(statusBarProperty().getValue().getDescricao());
        } catch (Exception ex) {
            ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().atualizaStatusBar(statusBarProperty().getValue().getDescricao());
        }
    }

    /**
     * END voids
     */

    /**
     * Begin Getters e Setters
     */

    public AnchorPane getPainelViewContasAReceber() {
        return painelViewContasAReceber;
    }

    public void setPainelViewContasAReceber(AnchorPane painelViewContasAReceber) {
        this.painelViewContasAReceber = painelViewContasAReceber;
    }

    public TitledPane getTpnContasAReceber() {
        return tpnContasAReceber;
    }

    public void setTpnContasAReceber(TitledPane tpnContasAReceber) {
        this.tpnContasAReceber = tpnContasAReceber;
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

    public ComboBox<Empresa> getCboEmpresa() {
        return cboEmpresa;
    }

    public void setCboEmpresa(ComboBox<Empresa> cboEmpresa) {
        this.cboEmpresa = cboEmpresa;
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

    public Label getLblQtdClientes() {
        return lblQtdClientes;
    }

    public void setLblQtdClientes(Label lblQtdClientes) {
        this.lblQtdClientes = lblQtdClientes;
    }

    public Label getLblQtdContas() {
        return lblQtdContas;
    }

    public void setLblQtdContas(Label lblQtdContas) {
        this.lblQtdContas = lblQtdContas;
    }

    public Label getLblQtdContasPagas() {
        return lblQtdContasPagas;
    }

    public void setLblQtdContasPagas(Label lblQtdContasPagas) {
        this.lblQtdContasPagas = lblQtdContasPagas;
    }

    public Label getLblQtdContasAbertas() {
        return lblQtdContasAbertas;
    }

    public void setLblQtdContasAbertas(Label lblQtdContasAbertas) {
        this.lblQtdContasAbertas = lblQtdContasAbertas;
    }

    public Label getLblTotalContas() {
        return lblTotalContas;
    }

    public void setLblTotalContas(Label lblTotalContas) {
        this.lblTotalContas = lblTotalContas;
    }

    public Label getLblTotalContasPagas() {
        return lblTotalContasPagas;
    }

    public void setLblTotalContasPagas(Label lblTotalContasPagas) {
        this.lblTotalContasPagas = lblTotalContasPagas;
    }

    public Label getLblTotalContasAbertas() {
        return lblTotalContasAbertas;
    }

    public void setLblTotalContasAbertas(Label lblTotalContasAbertas) {
        this.lblTotalContasAbertas = lblTotalContasAbertas;
    }

    public boolean isTabCarregada() {
        return tabCarregada;
    }

    public void setTabCarregada(boolean tabCarregada) {
        this.tabCarregada = tabCarregada;
    }

    public List<EnumsTasks> getEnumsTasksList() {
        return enumsTasksList;
    }

    public void setEnumsTasksList(List<EnumsTasks> enumsTasksList) {
        this.enumsTasksList = enumsTasksList;
    }

    public String getNomeTab() {
        return nomeTab;
    }

    public void setNomeTab(String nomeTab) {
        this.nomeTab = nomeTab;
    }

    public String getNomeController() {
        return nomeController;
    }

    public void setNomeController(String nomeController) {
        this.nomeController = nomeController;
    }

    public StatusBarContasAReceber getStatusBar() {
        return statusBar.get();
    }

    public ObjectProperty<StatusBarContasAReceber> statusBarProperty() {
        return statusBar;
    }

    public void setStatusBar(StatusBarContasAReceber statusBar) {
        this.statusBar.set(statusBar);
    }

    public EventHandler getEventHandlerContasAReceber() {
        return eventHandlerContasAReceber;
    }

    public void setEventHandlerContasAReceber(EventHandler eventHandlerContasAReceber) {
        this.eventHandlerContasAReceber = eventHandlerContasAReceber;
    }

    public ServiceAlertMensagem getAlertMensagem() {
        return alertMensagem;
    }

    public void setAlertMensagem(ServiceAlertMensagem alertMensagem) {
        this.alertMensagem = alertMensagem;
    }

    public TmodelContasAReceber getTmodelaReceber() {
        return tmodelaReceber;
    }

    public void setTmodelaReceber(TmodelContasAReceber tmodelaReceber) {
        this.tmodelaReceber = tmodelaReceber;
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

/**
 * END Getters e Setters
 */

}
