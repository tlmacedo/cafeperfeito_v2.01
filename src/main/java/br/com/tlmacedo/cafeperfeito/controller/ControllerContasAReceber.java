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
import br.com.tlmacedo.cafeperfeito.service.*;
import br.com.tlmacedo.cafeperfeito.service.autoComplete.ServiceAutoCompleteComboBox;
import br.com.tlmacedo.cafeperfeito.service.format.FormatDataPicker;
import br.com.tlmacedo.cafeperfeito.view.ViewContasAReceber;
import br.com.tlmacedo.cafeperfeito.view.ViewRecebimento;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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

import java.math.BigDecimal;
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
    public ComboBox<PagamentoSituacao> cboPagamentoSituacao;
    public Label lblRegistrosLocalizados;
    public TreeTableView<Object> ttvContasAReceber;
    public Label lblTotQtdClientes;
    public Label lblTotalQtdClientes;
    public Label lblTotQtdContas;
    public Label lblTotalContas;
    public Label lblTotQtdRetiradas;
    public Label lblTotalRetiradas;
    public Label lblTotQtdDescontos;
    public Label lblTotalDescontos;
    public Label lblTotQtdLucroBruto;
    public Label lblTotalLucroBruto;
    public Label lblTotQtdContasAReceber;
    public Label lblTotalContasAReceber;
    public Label lblTotQtdContasVencidas;
    public Label lblTotalContasVencidas;
    public Label lblTotQtdContasPendentes;
    public Label lblTotalContasPendentes;
    public Label lblTotQtdContasPagas;
    public Label lblTotalContasPagas;
    public Label lblTotQtdContasSaldoClientes;
    public Label lblTotalContasSaldoClientes;
    public Label lblTotQtdLucroLiquido;
    public Label lblTotalLucroLiquido;

//    public Label lblQtdContas;
//    public Label lblQtdContasPagas;
//    public Label lblQtdContasAbertas;
//    public Label lblTotalContas;
//    public Label lblTotalContasPagas;
//    public Label lblTotalContasAbertas;


    private boolean tabCarregada = false;
    private List<EnumsTasks> enumsTasksList = new ArrayList<>();

    private String nomeTab = ViewContasAReceber.getTitulo();
    private String nomeController = "contasAReceber";
    private ObjectProperty<StatusBarContasAReceber> statusBar = new SimpleObjectProperty<>();
    private EventHandler eventHandlerContasAReceber;
    private ServiceAlertMensagem alertMensagem;

    private TmodelContasAReceber tmodelaReceber;
    private ObjectProperty<ContasAReceber> aReceber = new SimpleObjectProperty<>();
    private ObjectProperty<Recebimento> recebimento = new SimpleObjectProperty<>();
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
                        if (recebimentoProperty().getValue() == null
                                || !getTtvContasAReceber().isFocused()) return;
                        new ViewRecebimento().openViewRecebimento((Recebimento) recebimentoProperty().getValue());
                        getTmodelaReceber().preencherTabela();
                        break;
                    case F6:
                        getCboEmpresa().requestFocus();
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
                    case HELP:
                        new ViewRecebimento().openViewRecebimento(aReceberProperty().getValue(), null);
                        break;
                    case P:
                        if (event.isControlDown()) {
                            if (recebimentoProperty().getValue() == null
                                    || !getTtvContasAReceber().isFocused()) return;
                            try {
                                new ServiceRecibo().imprimeRecibo(recebimentoProperty().getValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }
        });

        getTtvContasAReceber().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            try {
                if (n.getValue() instanceof ContasAReceber) {
                    aReceberProperty().setValue((ContasAReceber) n.getValue());
                    if (((ContasAReceber) n.getValue()).getRecebimentoList().size() > 0)
                        recebimentoProperty().setValue((Recebimento) n.getChildren().get(0).getValue());
                    else
                        recebimentoProperty().setValue(null);
                } else if (n.getValue() instanceof Recebimento) {
                    aReceberProperty().setValue((ContasAReceber) n.getParent().getValue());
                    recebimentoProperty().setValue((Recebimento) n.getValue());
                }
            } catch (Exception ex) {
                aReceberProperty().setValue(null);
                recebimentoProperty().setValue(null);
            }

//            Object obj, objEnvi;
//            if ((obj = getTtvContasAReceber().getSelectionModel().getSelectedItem().getValue()) == null)
//                return;
//            if (obj instanceof ContasAReceber) {
//                if (getTtvContasAReceber().getSelectionModel().getSelectedItem().getChildren().size() > 0) {
//                    obj = (Recebimento) getTtvContasAReceber().getSelectionModel().getSelectedItem().getChildren().get(0).getValue();
//                }
//            }
        });

        ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            if (n.getText().equals(getNomeTab()))
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().addEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerContasAReceber());
            else
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerContasAReceber());
        });

        new ServiceAutoCompleteComboBox(Empresa.class, getCboEmpresa());

        getCboEmpresa().valueProperty().addListener((ov, o, n) -> {
//            if (n != null) {
//                getTmodelaReceber().setEmpresa(n);
//            }
            showStatusBar();
        });

//        getCboPagamentoSituacao().valueProperty().addListener((ov, o, n) -> {
//            if (n != null && !n.toString().equals("")) {
//                getTmodelaReceber().setPagamentoSituacao(PagamentoSituacao.valueOf(n.toString().toUpperCase()));
//            }
//        });

        getDtpData1().focusedProperty().addListener((ov, o, n) -> {
            if (!n) return;
            getDtpData1().getEditor().deselect();
        });
        getDtpData2().focusedProperty().addListener((ov, o, n) -> {
            if (!n) return;
            getDtpData2().getEditor().deselect();
        });

        aReceberProperty().addListener((ov, o, n) -> {
            showStatusBar();
        });

        getLblTotalQtdClientes().textProperty().bind(getTmodelaReceber().qtdClientesProperty().asString());

        getLblTotQtdContas().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Contas: [%d]", getTmodelaReceber().qtdContasProperty().getValue()),
                getTmodelaReceber().qtdContasProperty()));

        getLblTotalContas().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasProperty().getValue(), 2)),
                getTmodelaReceber().totalContasProperty()));

        getLblTotQtdRetiradas().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Bonif / Retiradas: [%d]", getTmodelaReceber().qtdContasRetiradasProperty().getValue()),
                getTmodelaReceber().qtdContasRetiradasProperty()));

        getLblTotalRetiradas().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasRetiradasProperty().getValue(), 2)),
                getTmodelaReceber().totalContasRetiradasProperty()));

        getLblTotQtdDescontos().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Descontos Vendas: [%d]", getTmodelaReceber().qtdContasDescontosProperty().getValue()),
                getTmodelaReceber().qtdContasDescontosProperty()));

        getLblTotalDescontos().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasDescontosProperty().getValue(), 2)),
                getTmodelaReceber().totalContasDescontosProperty()));

        getLblTotQtdLucroBruto().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Lucro Bruto: [%s%%]", ServiceMascara.getMoeda(getTmodelaReceber().percLucroBrutoProperty().getValue(), 2)),
                getTmodelaReceber().percLucroBrutoProperty()));

        getLblTotalLucroBruto().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalLucroBrutoProperty().getValue(), 2)),
                getTmodelaReceber().totalLucroBrutoProperty()));

        getLblTotQtdContasAReceber().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Contas a Receber: [%d]", getTmodelaReceber().qtdContasAReceberProperty().getValue()),
                getTmodelaReceber().qtdContasAReceberProperty()));

        getLblTotalContasAReceber().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasAReceberProperty().getValue(), 2)),
                getTmodelaReceber().totalContasAReceberProperty()));

        getLblTotQtdContasVencidas().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Contas Vencidas: [%d]", getTmodelaReceber().qtdContasVencidasProperty().getValue()),
                getTmodelaReceber().qtdContasVencidasProperty()));

        getLblTotalContasVencidas().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasVencidasProperty().getValue(), 2)),
                getTmodelaReceber().totalContasVencidasProperty()));

        getLblTotQtdContasPendentes().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Contas Pendentes: [%d]", getTmodelaReceber().qtdContasPendentesProperty().getValue()),
                getTmodelaReceber().qtdContasPendentesProperty()));

        getLblTotalContasPendentes().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasPendentesProperty().getValue(), 2)),
                getTmodelaReceber().totalContasPendentesProperty()));

        getLblTotQtdContasPagas().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Contas Pagas: [%d]", getTmodelaReceber().qtdContasPagasProperty().getValue()),
                getTmodelaReceber().qtdContasPagasProperty()));

        getLblTotalContasPagas().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasPagasProperty().getValue(), 2)),
                getTmodelaReceber().totalContasPagasProperty()));

        getLblTotQtdContasSaldoClientes().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Saldo de clientes: [%d]", getTmodelaReceber().qtdContasSaldoClientesProperty().getValue()),
                getTmodelaReceber().qtdContasSaldoClientesProperty()));

        getLblTotalContasSaldoClientes().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasSaldoClientesProperty().getValue(), 2)),
                getTmodelaReceber().totalContasSaldoClientesProperty()));

        getLblTotQtdLucroLiquido().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Lucro Líquido: [%s%%]", ServiceMascara.getMoeda(getTmodelaReceber().percLucroLiquidoProperty().getValue(), 2)),
                getTmodelaReceber().percLucroLiquidoProperty()));

        getLblTotalLucroLiquido().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalLucroLiquidoProperty().getValue(), 2)),
                getTmodelaReceber().totalLucroLiquidoProperty()));

//        getLblQtdContas().textProperty().bind(getTmodelaReceber().qtdContasProperty().asString());
//
//        getLblQtdContasPagas().textProperty().bind(getTmodelaReceber().qtdContasPagasProperty().asString());
//
//        getLblQtdContasAbertas().textProperty().bind(getTmodelaReceber().qtdContasAbertasProperty().asString());

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
                try {
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
                                getTmodelaReceber().setLblRegistrosLocalizados(getLblRegistrosLocalizados());
                                getTmodelaReceber().setTtvContasAReceber(getTtvContasAReceber());
                                setaReceberObservableList(getTmodelaReceber().getaReceberObservableList());
                                setaReceberFilteredList(getTmodelaReceber().getaReceberFilteredList());
                                getTmodelaReceber().empresaProperty().bind(getCboEmpresa().valueProperty());
                                getTmodelaReceber().pagamentoSituacaoProperty().bind(getCboPagamentoSituacao().valueProperty());
                                getTmodelaReceber().escutaLista();

                                getDtpData1().setValue(LocalDate.of(LocalDate.now().getYear(), 1, 1));
                                getDtpData2().setValue(LocalDate.now().plusMonths(2).withDayOfMonth(1).minusDays(1));

                                break;

                            case COMBOS_PREENCHER:
                                getCboEmpresa().setItems(
                                        new EmpresaDAO().getAll(Empresa.class, null, "razao")
                                                .stream().filter(Empresa::isCliente)
                                                .collect(Collectors.toCollection(FXCollections::observableArrayList))
                                );
                                getCboEmpresa().getItems().add(0, new Empresa(null));

                                getCboPagamentoSituacao().setItems(Arrays.stream(PagamentoSituacao.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                getCboPagamentoSituacao().getItems().add(0, null);
                                break;

                            case TABELA_PREENCHER:
                                //getTmodelaReceber().preencherTabela();
                                getTmodelaReceber().preencherTabela();
//
//                            getTmodelSaidaProduto().preencheTabela();
                                break;

                            case SALVAR_SAIDA:
//                            if (getTmodelSaidaProduto().guardarSaidaProduto()) {
//                                if (getTmodelSaidaProduto().salvarSaidaProduto()) {
//                                    getProdutoObservableList().setAll(new ProdutoDAO().getAll(Produto.class, null,  "descricao"));
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
                } catch (Exception ex) {
                    ex.printStackTrace();
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
//        try {
//            if (getTtvContasAReceber().get.size() <= 0 || getCboEmpresa().getValue().idProperty().getValue() == 0)
//                ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().atualizaStatusBar(statusBarProperty().getValue().getDescricao().replace("  [F2-Finalizar venda]", ""));
//            else
//                ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().atualizaStatusBar(statusBarProperty().getValue().getDescricao());
//        } catch (Exception ex) {
//            ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().atualizaStatusBar(statusBarProperty().getValue().getDescricao());
//        }

        String stb = "";
        try {
            stb = statusBarProperty().getValue().getDescricao();
            if (aReceberProperty().getValue() == null) {
                stb = stb.replace("[Insert-Novo recebimento]  [Ctrl+P-Imprimir recibo]  [F4-Editar recebimento]  ", "");
            } else {
                if (aReceberProperty().getValue().getRecebimentoList().size() <= 0) {
                    stb = stb.replace("[Ctrl+P-Imprimir recibo]  [F4-Editar recebimento]  ", "");
                } else {
                    if (aReceberProperty().getValue().valorProperty().getValue()
                            .compareTo(aReceberProperty().getValue().getRecebimentoList().stream()
                                    .map(Recebimento::getValor).reduce(BigDecimal.ZERO, BigDecimal::add)) <= 0)
                        stb = stb.replace("[Insert-Novo recebimento]  ", "");
                }
            }
        } catch (Exception ex) {
            //stb = statusBarProperty().getValue().getDescricao();
        }
        ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().atualizaStatusBar(stb);
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

    public ComboBox<PagamentoSituacao> getCboPagamentoSituacao() {
        return cboPagamentoSituacao;
    }

    public void setCboPagamentoSituacao(ComboBox<PagamentoSituacao> cboPagamentoSituacao) {
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

    public Label getLblTotQtdClientes() {
        return lblTotQtdClientes;
    }

    public void setLblTotQtdClientes(Label lblTotQtdClientes) {
        this.lblTotQtdClientes = lblTotQtdClientes;
    }

    public Label getLblTotalQtdClientes() {
        return lblTotalQtdClientes;
    }

    public void setLblTotalQtdClientes(Label lblTotalQtdClientes) {
        this.lblTotalQtdClientes = lblTotalQtdClientes;
    }

    public Label getLblTotQtdContas() {
        return lblTotQtdContas;
    }

    public void setLblTotQtdContas(Label lblTotQtdContas) {
        this.lblTotQtdContas = lblTotQtdContas;
    }

    public Label getLblTotalContas() {
        return lblTotalContas;
    }

    public void setLblTotalContas(Label lblTotalContas) {
        this.lblTotalContas = lblTotalContas;
    }

    public Label getLblTotQtdRetiradas() {
        return lblTotQtdRetiradas;
    }

    public void setLblTotQtdRetiradas(Label lblTotQtdRetiradas) {
        this.lblTotQtdRetiradas = lblTotQtdRetiradas;
    }

    public Label getLblTotalRetiradas() {
        return lblTotalRetiradas;
    }

    public void setLblTotalRetiradas(Label lblTotalRetiradas) {
        this.lblTotalRetiradas = lblTotalRetiradas;
    }

    public Label getLblTotQtdDescontos() {
        return lblTotQtdDescontos;
    }

    public void setLblTotQtdDescontos(Label lblTotQtdDescontos) {
        this.lblTotQtdDescontos = lblTotQtdDescontos;
    }

    public Label getLblTotalDescontos() {
        return lblTotalDescontos;
    }

    public void setLblTotalDescontos(Label lblTotalDescontos) {
        this.lblTotalDescontos = lblTotalDescontos;
    }

    public Label getLblTotQtdLucroBruto() {
        return lblTotQtdLucroBruto;
    }

    public void setLblTotQtdLucroBruto(Label lblTotQtdLucroBruto) {
        this.lblTotQtdLucroBruto = lblTotQtdLucroBruto;
    }

    public Label getLblTotalLucroBruto() {
        return lblTotalLucroBruto;
    }

    public void setLblTotalLucroBruto(Label lblTotalLucroBruto) {
        this.lblTotalLucroBruto = lblTotalLucroBruto;
    }

    public Label getLblTotQtdContasAReceber() {
        return lblTotQtdContasAReceber;
    }

    public void setLblTotQtdContasAReceber(Label lblTotQtdContasAReceber) {
        this.lblTotQtdContasAReceber = lblTotQtdContasAReceber;
    }

    public Label getLblTotalContasAReceber() {
        return lblTotalContasAReceber;
    }

    public void setLblTotalContasAReceber(Label lblTotalContasAReceber) {
        this.lblTotalContasAReceber = lblTotalContasAReceber;
    }

    public Label getLblTotQtdContasVencidas() {
        return lblTotQtdContasVencidas;
    }

    public void setLblTotQtdContasVencidas(Label lblTotQtdContasVencidas) {
        this.lblTotQtdContasVencidas = lblTotQtdContasVencidas;
    }

    public Label getLblTotalContasVencidas() {
        return lblTotalContasVencidas;
    }

    public void setLblTotalContasVencidas(Label lblTotalContasVencidas) {
        this.lblTotalContasVencidas = lblTotalContasVencidas;
    }

    public Label getLblTotQtdContasPendentes() {
        return lblTotQtdContasPendentes;
    }

    public void setLblTotQtdContasPendentes(Label lblTotQtdContasPendentes) {
        this.lblTotQtdContasPendentes = lblTotQtdContasPendentes;
    }

    public Label getLblTotalContasPendentes() {
        return lblTotalContasPendentes;
    }

    public void setLblTotalContasPendentes(Label lblTotalContasPendentes) {
        this.lblTotalContasPendentes = lblTotalContasPendentes;
    }

    public Label getLblTotQtdContasPagas() {
        return lblTotQtdContasPagas;
    }

    public void setLblTotQtdContasPagas(Label lblTotQtdContasPagas) {
        this.lblTotQtdContasPagas = lblTotQtdContasPagas;
    }

    public Label getLblTotalContasPagas() {
        return lblTotalContasPagas;
    }

    public void setLblTotalContasPagas(Label lblTotalContasPagas) {
        this.lblTotalContasPagas = lblTotalContasPagas;
    }

    public Label getLblTotQtdContasSaldoClientes() {
        return lblTotQtdContasSaldoClientes;
    }

    public void setLblTotQtdContasSaldoClientes(Label lblTotQtdContasSaldoClientes) {
        this.lblTotQtdContasSaldoClientes = lblTotQtdContasSaldoClientes;
    }

    public Label getLblTotalContasSaldoClientes() {
        return lblTotalContasSaldoClientes;
    }

    public void setLblTotalContasSaldoClientes(Label lblTotalContasSaldoClientes) {
        this.lblTotalContasSaldoClientes = lblTotalContasSaldoClientes;
    }

    public Label getLblTotQtdLucroLiquido() {
        return lblTotQtdLucroLiquido;
    }

    public void setLblTotQtdLucroLiquido(Label lblTotQtdLucroLiquido) {
        this.lblTotQtdLucroLiquido = lblTotQtdLucroLiquido;
    }

    public Label getLblTotalLucroLiquido() {
        return lblTotalLucroLiquido;
    }

    public void setLblTotalLucroLiquido(Label lblTotalLucroLiquido) {
        this.lblTotalLucroLiquido = lblTotalLucroLiquido;
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

    public ContasAReceber getaReceber() {
        return aReceber.get();
    }

    public ObjectProperty<ContasAReceber> aReceberProperty() {
        return aReceber;
    }

    public void setaReceber(ContasAReceber aReceber) {
        this.aReceber.set(aReceber);
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

    public Recebimento getRecebimento() {
        return recebimento.get();
    }

    public ObjectProperty<Recebimento> recebimentoProperty() {
        return recebimento;
    }

    public void setRecebimento(Recebimento recebimento) {
        this.recebimento.set(recebimento);
    }
/**
 * END Getters e Setters
 */

}
