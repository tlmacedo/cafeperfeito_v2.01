package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.ContasAReceberDAO;
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
import br.com.tlmacedo.cafeperfeito.service.format.FormatDataPicker;
import br.com.tlmacedo.cafeperfeito.view.ViewContasAReceber;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
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

    private boolean tabCarregada = false;
    private List<EnumsTasks> enumsTasksList = new ArrayList<>();

    private String nomeTab = ViewContasAReceber.getTitulo();
    private String nomeController = "contasAReceber";
    private ObjectProperty<StatusBarContasAReceber> statusBar = new SimpleObjectProperty<>(StatusBarContasAReceber.DIGITACAO);
    private EventHandler eventHandlerContasAReceber;
    private ServiceAlertMensagem alertMensagem;

    private TmodelContasAReceber tmodelContasAReceber;
    private ObjectProperty<ContasAReceber> contasAReceber = new SimpleObjectProperty<>();
    private ContasAReceberDAO contasAReceberDAO;

    private FilteredList<ContasAReceber> contasAReceberFilteredList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            criarObjetos();
            preencherObjetos();
            if (!isTabCarregada()) {
                Platform.runLater(() -> fechar());
                return;
            }
            escutarTecla();
            fatorarObjetos();
            fieldsFormat();
            Platform.runLater(() -> limpaCampos(getPainelViewContasAReceber()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void fieldsFormat() throws Exception {
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
    public void criarObjetos() throws Exception {
        getEnumsTasksList().add(EnumsTasks.TABELA_CRIAR);
    }

    @Override
    public void preencherObjetos() throws Exception {
        getEnumsTasksList().add(EnumsTasks.TABELA_VINCULAR);

        getEnumsTasksList().add(EnumsTasks.TABELA_PREENCHER);

        getEnumsTasksList().add(EnumsTasks.COMBOS_PREENCHER);

        setTabCarregada(new ServiceSegundoPlano().executaListaTarefas(newTaskContasAReceber(), String.format("Abrindo %s!", getNomeTab())));
    }

    @Override
    public void fatorarObjetos() {

        getDtpData2().valueProperty().addListener((ov, o, n) -> {
            if (n == null && getDtpData1().getValue() == null)
                getDtpData1().setValue(LocalDate.now());
        });

        getDtpData1().valueProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            getDtpData2().setDayCellFactory(param -> new FormatDataPicker(getDtpData1().getValue()));

            if (getDtpData2().getValue() == null
                    || getDtpData1().getValue().compareTo(getDtpData2().getValue()) > 0)
                getDtpData2().setValue(n);
        });
    }

    @Override
    public void escutarTecla() {
        statusBarProperty().addListener((ov, o, n) -> {
            if (n == null)
                statusBarProperty().setValue(StatusBarContasAReceber.DIGITACAO);
            showStatusBar();
        });

        setEventHandlerContasAReceber(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try {
                    if (ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().getSelectedIndex() < 0)
                        return;
                    if (!ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().getSelectedItem().getText().equals(getNomeTab()))
                        return;
                    if (!ControllerPrincipal.getCtrlPrincipal().teclaDisponivel(event.getCode())) return;
                    switch (event.getCode()) {
                        case F1:
                            limpaCampos(getPainelViewContasAReceber());
                            break;
                        case F2:
                            break;
                        case F4:

                            break;
                        case F6:
                            getCboEmpresa().requestFocus();
                            getCboEmpresa().setValue(null);
                            break;
                        case F7:
                            getTxtPesquisa().requestFocus();
                            break;
                        case F8:
                            break;
                        case F9:
                            break;
                        case F12:
                            fechar();
                            break;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

//        ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
//            if (n == null) return;
//            if (n.getText().equals(getNomeTab())) {
//                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().addEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerSaidaProduto());
//                showStatusBar();
//            } else {
//                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerSaidaProduto());
//            }
//        });
//
//        getSaidaProdutoProdutoObservableList().addListener((ListChangeListener<? super SaidaProdutoProduto>) c -> {
//            showStatusBar();
//        });
//
//        new ServiceAutoCompleteComboBox(Empresa.class, getCboEmpresa());
//
//        new ServiceAutoCompleteComboBox(Empresa.class, getCboNfeTransporteTransportadora());
//
//        empresaProperty().bind(Bindings.createObjectBinding(() -> {
//            if (getCboEmpresa().getValue() == null)
//                return new Empresa();
//            return getCboEmpresa().getValue();
//        }, getCboEmpresa().valueProperty()));
//
//        empresaProperty().addListener((ov, o, n) -> {
////            if (n == null)
////                n = new Empresa();
//            exibirEmpresaDetalhe();
//            showStatusBar();
//        });
//
//        getCboEmpresa().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() == KeyCode.ENTER && getCboEmpresa().getValue() != null)
//                getTxtPesquisaProduto().requestFocus();
//        });
//
//        getCboEndereco().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
//            if (n == null) {
//                limpaEndereco();
//                return;
//            }
//            getLblLogradoruro().setText(n.logradouroProperty().getValue());
//            getLblNumero().setText(n.numeroProperty().getValue());
//            getLblBairro().setText(n.bairroProperty().getValue());
//            getLblComplemento().setText(n.complementoProperty().getValue());
//        });
//
//        prazoProperty().addListener((ov, o, n) -> {
//            if (n == null)
//                getDtpDtVencimento().setValue(getDtpDtSaida().getValue());
//            else
//                getDtpDtVencimento().setValue(getDtpDtSaida().getValue().plusDays(n.intValue()));
//            getLblPrazo().setText(n.toString());
//        });
//
//        getTxtPesquisaProduto().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() != KeyCode.ENTER) return;
//            getTtvProdutoEstoque().requestFocus();
//            getTtvProdutoEstoque().getSelectionModel().selectFirst();
//        });
//
//        getLblQtdItem().textProperty().bind(getTmodelSaidaProduto().totalQtdItemProperty().asString());
//        getLblQtdTotal().textProperty().bind(getTmodelSaidaProduto().totalQtdProdutoProperty().asString());
//        getLblQtdVolume().textProperty().bind(getTmodelSaidaProduto().totalQtdVolumeProperty().asString());
//        getLblTotalBruto().textProperty().bind(Bindings.createStringBinding(() ->
//                        ServiceMascara.getMoeda(getTmodelSaidaProduto().totalBrutoProperty().getValue(), 2),
//                getTmodelSaidaProduto().totalBrutoProperty()
//        ));
//        getLblTotalDesconto().textProperty().bind(Bindings.createStringBinding(() ->
//                        ServiceMascara.getMoeda(getTmodelSaidaProduto().totalDescontoProperty().getValue(), 2),
//                getTmodelSaidaProduto().totalDescontoProperty()
//        ));
//        getLblTotalLiquido().textProperty().bind(Bindings.createStringBinding(() ->
//                        ServiceMascara.getMoeda(getTmodelSaidaProduto().totalLiquidoProperty().getValue(), 2),
//                getTmodelSaidaProduto().totalLiquidoProperty()
//        ));
//
//        getTpnNfe().expandedProperty().addListener((ov, o, n) -> {
//            if (n) {
//                getTxtNfeDadosNumero().setText(String.valueOf(nfeLastNumberProperty().getValue() + 1));
//                getTxtNfeDadosSerie().setText(String.valueOf(TCONFIG.getNfe().getNFeSerie()));
//                getCboNfeDadosNaturezaOperacao().requestFocus();
//            } else {
//                limpaCampos(getTpnNfe());
//                getTxtPesquisaProduto().requestFocus();
//            }
//        });
//
//        getCboNfeTransporteTransportadora().disableProperty().bind(getCboNfeTransporteModFrete().valueProperty().isEqualTo(NfeTransporteModFrete.REMETENTE));
//
//        getCboNfeTransporteTransportadora().disableProperty().addListener((ov, o, n) -> {
//            if (n)
//                getCboNfeTransporteTransportadora().getSelectionModel().select(-1);
//        });
//
//        informacoesAdicionasNFeProperty().bind(
//                Bindings.createStringBinding(() -> refreshNFeInfAdicionas(),
//                        getLblTotalLiquido().textProperty(), getDtpDtVencimento().valueProperty()));
//
//        informacoesAdicionasNFeProperty().addListener((ov, o, n) -> {
//            if (n == null)
//                n = "";
//            getTxaNfeInformacoesAdicionais().setText(n);
//        });
//
//        getDtpDtSaida().focusedProperty().addListener((ov, o, n) -> {
//            ServiceFormatDataPicker.formatDataPicker(getDtpDtSaida(), n);
//        });
//
//        getDtpDtVencimento().focusedProperty().addListener((ov, o, n) -> {
//            ServiceFormatDataPicker.formatDataPicker(getDtpDtVencimento(), n);
//        });
//
//        getDtpNfeDadosDtSaida().focusedProperty().addListener((ov, o, n) -> {
//            ServiceFormatDataPicker.formatDataPicker(getDtpNfeDadosDtSaida(), n);
//        });
//
//        getDtpNfeDadosDtEmissao().focusedProperty().addListener((ov, o, n) -> {
//            ServiceFormatDataPicker.formatDataPicker(getDtpNfeDadosDtEmissao(), n);
//        });
//
//        //getCboNfeTransporteModFrete().disableProperty().bind(getCboNfeTransporteModFrete().selectionModelProperty().isEqualTo(NfeTransporteModFrete.REMETENTE));
    }

    /**
     * Begin Tasks
     */

    private Task newTaskContasAReceber() {
        try {
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
                                setTmodelContasAReceber(new TmodelContasAReceber());
                                getTmodelContasAReceber().criarTabela();
                                break;

                            case TABELA_VINCULAR:
                                getTmodelContasAReceber().setDtpData1(getDtpData1());
                                getTmodelContasAReceber().setDtpData2(getDtpData2());
                                getTmodelContasAReceber().setChkDtVenda(getChkDtVenda());
                                getTmodelContasAReceber().setTxtPesquisa(getTxtPesquisa());
                                getTmodelContasAReceber().setLblRegistrosLocalizados(getLblRegistrosLocalizados());
                                getTmodelContasAReceber().setTtvContasAReceber(getTtvContasAReceber());
                                setContasAReceberFilteredList(getTmodelContasAReceber().getContasAReceberFilteredList());
                                getTmodelContasAReceber().empresaProperty().bind(getCboEmpresa().valueProperty());
                                getTmodelContasAReceber().pagamentoSituacaoProperty().bind(getCboPagamentoSituacao().valueProperty());
                                getTmodelContasAReceber().escutaLista();
//                                getDtpData1().setValue(LocalDate.of(LocalDate.now().getYear(), 1, 1));
//                                getDtpData2().setValue(LocalDate.now().plusMonths(2).withDayOfMonth(1).minusDays(1));
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
                                getTmodelContasAReceber().preencherTabela();
                                break;

                            case SALVAR_ENT_SAIDA:
//                                if (gettmod().guardarSaidaProduto()) {
//                                    if (gettmod().salvarSaidaProduto()) {
//                                        getProdutoObservableList().setAll(new ProdutoDAO().getAll(Produto.class, null, "descricao"));
//                                        getTtvProdutos().refresh();
//                                    } else {
//                                        Thread.currentThread().interrupt();
//                                    }
//                                } else {
//                                    Thread.currentThread().interrupt();
//                                }
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
                    updateMessage("tarefa concluída!!!");
                    updateProgress(qtdTasks, qtdTasks);
                    getEnumsTasksList().clear();
                    return null;
                }
            };
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * END Tasks
     */

    /**
     * Begin voids
     */

    private void limpaCampos(AnchorPane anchorPane) {
        ServiceCampoPersonalizado.fieldClear(anchorPane);
    }


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
            if (contasAReceberProperty().getValue() == null) {
                stb = stb.replace("[Insert-Novo recebimento]  [Ctrl+P-Imprimir recibo]  [F4-Editar recebimento]  ", "");
            } else {
                if (contasAReceberProperty().getValue().getRecebimentoList().size() <= 0) {
                    stb = stb.replace("[Ctrl+P-Imprimir recibo]  [F4-Editar recebimento]  ", "");
                } else {
                    if (contasAReceberProperty().getValue().valorProperty().getValue()
                            .compareTo(contasAReceberProperty().getValue().getRecebimentoList().stream()
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

    public TmodelContasAReceber getTmodelContasAReceber() {
        return tmodelContasAReceber;
    }

    public void setTmodelContasAReceber(TmodelContasAReceber tmodelContasAReceber) {
        this.tmodelContasAReceber = tmodelContasAReceber;
    }

    public ContasAReceber getContasAReceber() {
        return contasAReceber.get();
    }

    public ObjectProperty<ContasAReceber> contasAReceberProperty() {
        return contasAReceber;
    }

    public void setContasAReceber(ContasAReceber contasAReceber) {
        this.contasAReceber.set(contasAReceber);
    }

    public ContasAReceberDAO getContasAReceberDAO() {
        return contasAReceberDAO;
    }

    public void setContasAReceberDAO(ContasAReceberDAO contasAReceberDAO) {
        this.contasAReceberDAO = contasAReceberDAO;
    }

    public FilteredList<ContasAReceber> getContasAReceberFilteredList() {
        return contasAReceberFilteredList;
    }

    public void setContasAReceberFilteredList(FilteredList<ContasAReceber> contasAReceberFilteredList) {
        this.contasAReceberFilteredList = contasAReceberFilteredList;
    }

    /**
     * END Getters e Setters
     */

}
