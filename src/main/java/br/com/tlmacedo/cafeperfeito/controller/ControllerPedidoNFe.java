package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.EnumsTasks;
import br.com.tlmacedo.cafeperfeito.model.enums.PagamentoSituacao;
import br.com.tlmacedo.cafeperfeito.model.enums.StatusBarPedidoNFe;
import br.com.tlmacedo.cafeperfeito.model.enums.TModelTipo;
import br.com.tlmacedo.cafeperfeito.model.tm.TmodelPedido_Recibo_NFe;
import br.com.tlmacedo.cafeperfeito.model.vo.Empresa;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.SaidaProdutoNfe;
import br.com.tlmacedo.cafeperfeito.service.ServiceAlertMensagem;
import br.com.tlmacedo.cafeperfeito.service.ServiceCampoPersonalizado;
import br.com.tlmacedo.cafeperfeito.service.ServiceMascara;
import br.com.tlmacedo.cafeperfeito.service.ServiceSegundoPlano;
import br.com.tlmacedo.cafeperfeito.service.autoComplete.ServiceAutoCompleteComboBox;
import br.com.tlmacedo.cafeperfeito.service.format.FormatDataPicker;
import br.com.tlmacedo.cafeperfeito.view.ViewPedidoNFe;
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

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerPedidoNFe implements Initializable, ModeloCafePerfeito {


    public AnchorPane painelViewPedidoNFe;
    public TitledPane tpnPedidoNFe;
    public DatePicker dtpData1;
    public DatePicker dtpData2;
    public CheckBox chkDtEmissao;
    public ComboBox<Empresa> cboEmpresa;
    public TextField txtPesquisa;
    public ComboBox<PagamentoSituacao> cboPagamentoSituacao;
    public Label lblRegistrosLocalizados;
    public TreeTableView<Object> ttvPedidoNFe;
    public Label lblTotQtdClientes;
    public Label lblTotalQtdClientes;
    public Label lblTotQtdPedidos;
    public Label lblTotalPedidos;
//    public Label lblTotQtdRetiradas;
//    public Label lblTotalRetiradas;
//    public Label lblTotQtdDescontos;
//    public Label lblTotalDescontos;
//    public Label lblTotQtdLucroBruto;
//    public Label lblTotalLucroBruto;
//    public Label lblTotQtdContasAReceber;
//    public Label lblTotalContasAReceber;
//    public Label lblTotQtdContasVencidas;
//    public Label lblTotalContasVencidas;
//    public Label lblTotQtdContasPendentes;
//    public Label lblTotalContasPendentes;
//    public Label lblTotQtdContasPagas;
//    public Label lblTotalContasPagas;
//    public Label lblTotQtdContasSaldoClientes;
//    public Label lblTotalContasSaldoClientes;
//    public Label lblTotQtdLucroLiquido;
//    public Label lblTotalLucroLiquido;

    private boolean tabCarregada = false;
    private List<EnumsTasks> enumsTasksList = new ArrayList<>();

    private String nomeTab = ViewPedidoNFe.getTitulo();
    private String nomeController = "pedidoNFe";
    private ObjectProperty<StatusBarPedidoNFe> statusBar = new SimpleObjectProperty<>();
    private EventHandler eventHandlerPedidoNFe;
    private ServiceAlertMensagem alertMensagem;

    private TmodelPedido_Recibo_NFe tmodelPedidoNFe;
    private ObjectProperty<SaidaProduto> saidaProduto = new SimpleObjectProperty<>();
    private ObjectProperty<SaidaProdutoNfe> saidaProdutoNfe = new SimpleObjectProperty<>();
    private ObservableList<SaidaProduto> saidaProdutoObservableList;
    private FilteredList<SaidaProduto> saidaProdutoFilteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            criarObjetos();
            preencherObjetos();
            fatorarObjetos();
            if (!isTabCarregada()) {
                Platform.runLater(() -> fechar());
                return;
            }
            escutarTecla();
            fieldsFormat();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void fieldsFormat() throws Exception {
        ServiceCampoPersonalizado.fieldTextFormat(getPainelViewPedidoNFe());
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
                    .removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerPedidoNFe());
    }

    @Override
    public void criarObjetos() throws Exception {
        getEnumsTasksList().add(EnumsTasks.TABELA_CRIAR);
    }

    @Override
    public void preencherObjetos() throws Exception {
        getEnumsTasksList().add(EnumsTasks.TABELA_VINCULAR);

        getEnumsTasksList().add(EnumsTasks.TABELA_PREENCHER);

        getEnumsTasksList().add(EnumsTasks.COMBOS_PREENCHER);

        setTabCarregada(new ServiceSegundoPlano().executaListaTarefas(newTaskPedidoNFe(), String.format("Abrindo %s!", getNomeTab())));

    }

    @Override
    public void fatorarObjetos() throws Exception {
        //getDtpData1().setDayCellFactory(param -> new FormatDataPicker(null));
        getDtpData2().setDayCellFactory(param -> new FormatDataPicker(getDtpData1().getValue()));

        getDtpData1().valueProperty().addListener((ov, o, n) -> {
            if (n == null) return;// || getDtpData1().getValue().compareTo(getDtpData2().getValue()) <= 0) return;
            if (getDtpData1().getValue().compareTo(getDtpData2().getValue()) > 0)
                getDtpData2().setValue(n);
            getDtpData2().setDayCellFactory(param -> new FormatDataPicker(n));
        });


    }

    @Override
    public void escutarTecla() throws Exception {
        statusBarProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            showStatusBar();
        });

        if (statusBarProperty().get() == null)
            setStatusBar(StatusBarPedidoNFe.DIGITACAO);

        setEventHandlerPedidoNFe(new EventHandler<KeyEvent>() {
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
//                        if (recebimentoProperty().getValue() == null
//                                || !getTtvContasAReceber().isFocused()) return;
//                        new ViewRecebimento().openViewRecebimento((Recebimento) recebimentoProperty().getValue());
//                        getTmodelaReceber().preencherTabela();
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
//                        new ViewRecebimento().openViewRecebimento(aReceberProperty().getValue());
                        break;
                    case P:
                        if (event.isControlDown()) {
//                            if (recebimentoProperty().getValue() == null
//                                    || !getTtvContasAReceber().isFocused()) return;
//                            try {
//                                ControllerPrincipal.getCtrlPrincipal().getPrincipalStage().getScene().setCursor(Cursor.CROSSHAIR);
//                                new ServiceRecibo().imprimeRecibo(recebimentoProperty().getValue());
//                                ControllerPrincipal.getCtrlPrincipal().getPrincipalStage().getScene().setCursor(Cursor.DEFAULT);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                        }
                        break;
                }
            }
        });

        getTtvPedidoNFe().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            try {
                if (n.getValue() instanceof SaidaProduto) {
                    saidaProdutoProperty().setValue((SaidaProduto) n.getValue());
                    if (((SaidaProduto) n.getValue()).getSaidaProdutoNfeList().size() > 0)
                        saidaProdutoNfeProperty().setValue((SaidaProdutoNfe) n.getChildren().get(0).getValue());
                    else
                        saidaProdutoNfeProperty().setValue(null);
                } else if (n.getValue() instanceof SaidaProdutoNfe) {
                    saidaProdutoProperty().setValue((SaidaProduto) n.getParent().getValue());
                    saidaProdutoNfeProperty().setValue((SaidaProdutoNfe) n.getValue());
                }
            } catch (Exception ex) {
                saidaProdutoProperty().setValue(null);
                saidaProdutoNfeProperty().setValue(null);
            }
        });

        ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            if (n.getText().equals(getNomeTab())) {
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().addEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerPedidoNFe());
                showStatusBar();
            } else {
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerPedidoNFe());
            }
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

        saidaProdutoProperty().addListener((ov, o, n) -> {
            showStatusBar();
        });

        getLblTotalQtdClientes().textProperty().bind(getTmodelPedidoNFe().qtdClientesProperty().asString());

        getLblTotQtdPedidos().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Contas: [%d]", getTmodelPedidoNFe().qtdContasProperty().getValue()),
                getTmodelPedidoNFe().qtdContasProperty()));

        getLblTotalPedidos().textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelPedidoNFe().totalContasProperty().getValue(), 2)),
                getTmodelPedidoNFe().totalContasProperty()));

//        getLblTotQtdRetiradas().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("Bonif / Retiradas: [%d]", getTmodelaReceber().qtdContasRetiradasProperty().getValue()),
//                getTmodelaReceber().qtdContasRetiradasProperty()));
//
//        getLblTotalRetiradas().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasRetiradasProperty().getValue(), 2)),
//                getTmodelaReceber().totalContasRetiradasProperty()));
//
//        getLblTotQtdDescontos().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("Descontos Vendas: [%d]", getTmodelaReceber().qtdContasDescontosProperty().getValue()),
//                getTmodelaReceber().qtdContasDescontosProperty()));
//
//        getLblTotalDescontos().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasDescontosProperty().getValue(), 2)),
//                getTmodelaReceber().totalContasDescontosProperty()));
//
//        getLblTotQtdLucroBruto().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("Lucro Bruto: [%s%%]", ServiceMascara.getMoeda(getTmodelaReceber().percLucroBrutoProperty().getValue(), 4)),
//                getTmodelaReceber().percLucroBrutoProperty()));
//
//        getLblTotalLucroBruto().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalLucroBrutoProperty().getValue(), 2)),
//                getTmodelaReceber().totalLucroBrutoProperty()));
//
//        getLblTotQtdContasAReceber().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("Contas a Receber: [%d]", getTmodelaReceber().qtdContasAReceberProperty().getValue()),
//                getTmodelaReceber().qtdContasAReceberProperty()));
//
//        getLblTotalContasAReceber().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasAReceberProperty().getValue(), 2)),
//                getTmodelaReceber().totalContasAReceberProperty()));
//
//        getLblTotQtdContasVencidas().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("Contas Vencidas: [%d]", getTmodelaReceber().qtdContasVencidasProperty().getValue()),
//                getTmodelaReceber().qtdContasVencidasProperty()));
//
//        getLblTotalContasVencidas().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasVencidasProperty().getValue(), 2)),
//                getTmodelaReceber().totalContasVencidasProperty()));
//
//        getLblTotQtdContasPendentes().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("Contas Pendentes: [%d]", getTmodelaReceber().qtdContasPendentesProperty().getValue()),
//                getTmodelaReceber().qtdContasPendentesProperty()));
//
//        getLblTotalContasPendentes().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasPendentesProperty().getValue(), 2)),
//                getTmodelaReceber().totalContasPendentesProperty()));
//
//        getLblTotQtdContasPagas().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("Contas Pagas: [%d]", getTmodelaReceber().qtdContasPagasProperty().getValue()),
//                getTmodelaReceber().qtdContasPagasProperty()));
//
//        getLblTotalContasPagas().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasPagasProperty().getValue(), 2)),
//                getTmodelaReceber().totalContasPagasProperty()));
//
//        getLblTotQtdContasSaldoClientes().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("Saldo de clientes: [%d]", getTmodelaReceber().qtdContasSaldoClientesProperty().getValue()),
//                getTmodelaReceber().qtdContasSaldoClientesProperty()));
//
//        getLblTotalContasSaldoClientes().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalContasSaldoClientesProperty().getValue(), 2)),
//                getTmodelaReceber().totalContasSaldoClientesProperty()));
//
//        getLblTotQtdLucroLiquido().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("Lucro Líquido: [%s%%]", ServiceMascara.getMoeda(getTmodelaReceber().percLucroLiquidoProperty().getValue(), 4)),
//                getTmodelaReceber().percLucroLiquidoProperty()));
//
//        getLblTotalLucroLiquido().textProperty().bind(Bindings.createStringBinding(() ->
//                        String.format("R$ %s", ServiceMascara.getMoeda(getTmodelaReceber().totalLucroLiquidoProperty().getValue(), 2)),
//                getTmodelaReceber().totalLucroLiquidoProperty()));

    }

    /**
     * Begin Tasks
     */

    private Task newTaskPedidoNFe() {
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
                                setTmodelPedidoNFe(new TmodelPedido_Recibo_NFe(TModelTipo.PEDIDO_NFE));
                                getTmodelPedidoNFe().criarTabela();
//
//                            setTmodelSaidaProduto(new TmodelSaidaProduto());
//                            getTmodelSaidaProduto().criaTabela();
                                break;

                            case TABELA_VINCULAR:
                                getTmodelPedidoNFe().setDtpData1(getDtpData1());
                                getTmodelPedidoNFe().setDtpData2(getDtpData2());
                                getTmodelPedidoNFe().setChkDtVenda(getChkDtEmissao());
                                getTmodelPedidoNFe().setTxtPesquisa(getTxtPesquisa());
                                getTmodelPedidoNFe().setLblRegistrosLocalizados(getLblRegistrosLocalizados());
                                getTmodelPedidoNFe().setTtvPedido(getTtvPedidoNFe());
                                setSaidaProdutoObservableList(getTmodelPedidoNFe().getSaidaProdutoObservableList());
                                setSaidaProdutoFilteredList(getTmodelPedidoNFe().getSaidaProdutoFilteredList());
                                getTmodelPedidoNFe().empresaProperty().bind(getCboEmpresa().valueProperty());
                                getTmodelPedidoNFe().pagamentoSituacaoProperty().bind(getCboPagamentoSituacao().valueProperty());
                                getTmodelPedidoNFe().escutaLista();

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
                                getTmodelPedidoNFe().preencherTabela();
//
//                            getTmodelSaidaProduto().preencheTabela();
                                break;

                            case SALVAR_ENT_SAIDA:
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
//                            case RELATORIO_IMPRIME_RECIBO:
//                                Platform.runLater(() -> {
//                                    try {
//                                        new ServiceRecibo().imprimeRecibo(recebimentoProperty().getValue());
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                });
//                                break;
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                updateMessage("tarefa concluída!!!");
                updateProgress(qtdTasks, qtdTasks);
                getEnumsTasksList().clear();
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
            if (saidaProdutoProperty().getValue() == null) {
                stb = stb.replace("[Insert-Nova NFe]  [Ctrl+P-Imprimir recibo]  [Ctrl+N-Imprimir NFe]  [F4-Editar NFe]  ", "");
            } else {
                if (saidaProdutoProperty().getValue().getSaidaProdutoNfeList().size() <= 0) {
                    stb = stb.replace("[Insert-Nova NFe]  [Ctrl+P-Imprimir recibo]  [Ctrl+N-Imprimir NFe]  [F4-Editar NFe]  ", "");
                } else {
                    if (!saidaProdutoNfeProperty().getValue().getDigVal().equals(null)
                            && !saidaProdutoNfeProperty().getValue().isCancelada())
                        stb = stb.replace("[Insert-Nova NFe]  ", "");
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
    public AnchorPane getPainelViewPedidoNFe() {
        return painelViewPedidoNFe;
    }

    public void setPainelViewPedidoNFe(AnchorPane painelViewPedidoNFe) {
        this.painelViewPedidoNFe = painelViewPedidoNFe;
    }

    public TitledPane getTpnPedidoNFe() {
        return tpnPedidoNFe;
    }

    public void setTpnPedidoNFe(TitledPane tpnPedidoNFe) {
        this.tpnPedidoNFe = tpnPedidoNFe;
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

    public CheckBox getChkDtEmissao() {
        return chkDtEmissao;
    }

    public void setChkDtEmissao(CheckBox chkDtEmissao) {
        this.chkDtEmissao = chkDtEmissao;
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

    public TreeTableView<Object> getTtvPedidoNFe() {
        return ttvPedidoNFe;
    }

    public void setTtvPedidoNFe(TreeTableView<Object> ttvPedidoNFe) {
        this.ttvPedidoNFe = ttvPedidoNFe;
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

    public Label getLblTotQtdPedidos() {
        return lblTotQtdPedidos;
    }

    public void setLblTotQtdPedidos(Label lblTotQtdPedidos) {
        this.lblTotQtdPedidos = lblTotQtdPedidos;
    }

    public Label getLblTotalPedidos() {
        return lblTotalPedidos;
    }

    public void setLblTotalPedidos(Label lblTotalPedidos) {
        this.lblTotalPedidos = lblTotalPedidos;
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

    public StatusBarPedidoNFe getStatusBar() {
        return statusBar.get();
    }

    public ObjectProperty<StatusBarPedidoNFe> statusBarProperty() {
        return statusBar;
    }

    public void setStatusBar(StatusBarPedidoNFe statusBar) {
        this.statusBar.set(statusBar);
    }

    public EventHandler getEventHandlerPedidoNFe() {
        return eventHandlerPedidoNFe;
    }

    public void setEventHandlerPedidoNFe(EventHandler eventHandlerPedidoNFe) {
        this.eventHandlerPedidoNFe = eventHandlerPedidoNFe;
    }

    public ServiceAlertMensagem getAlertMensagem() {
        return alertMensagem;
    }

    public void setAlertMensagem(ServiceAlertMensagem alertMensagem) {
        this.alertMensagem = alertMensagem;
    }

    public TmodelPedido_Recibo_NFe getTmodelPedidoNFe() {
        return tmodelPedidoNFe;
    }

    public void setTmodelPedidoNFe(TmodelPedido_Recibo_NFe tmodelPedidoNFe) {
        this.tmodelPedidoNFe = tmodelPedidoNFe;
    }

    public SaidaProduto getSaidaProduto() {
        return saidaProduto.get();
    }

    public ObjectProperty<SaidaProduto> saidaProdutoProperty() {
        return saidaProduto;
    }

    public void setSaidaProduto(SaidaProduto saidaProduto) {
        this.saidaProduto.set(saidaProduto);
    }

    public SaidaProdutoNfe getSaidaProdutoNfe() {
        return saidaProdutoNfe.get();
    }

    public ObjectProperty<SaidaProdutoNfe> saidaProdutoNfeProperty() {
        return saidaProdutoNfe;
    }

    public void setSaidaProdutoNfe(SaidaProdutoNfe saidaProdutoNfe) {
        this.saidaProdutoNfe.set(saidaProdutoNfe);
    }

    public ObservableList<SaidaProduto> getSaidaProdutoObservableList() {
        return saidaProdutoObservableList;
    }

    public void setSaidaProdutoObservableList(ObservableList<SaidaProduto> saidaProdutoObservableList) {
        this.saidaProdutoObservableList = saidaProdutoObservableList;
    }

    public FilteredList<SaidaProduto> getSaidaProdutoFilteredList() {
        return saidaProdutoFilteredList;
    }

    public void setSaidaProdutoFilteredList(FilteredList<SaidaProduto> saidaProdutoFilteredList) {
        this.saidaProdutoFilteredList = saidaProdutoFilteredList;
    }

/**
 * END Getters e Setters
 */

}
