package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.ProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.*;
import br.com.tlmacedo.cafeperfeito.model.tm.TmodelProduto;
import br.com.tlmacedo.cafeperfeito.model.tm.TmodelSaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.*;
import br.com.tlmacedo.cafeperfeito.service.*;
import br.com.tlmacedo.cafeperfeito.service.autoComplete.ServiceAutoCompleteComboBox;
import br.com.tlmacedo.cafeperfeito.service.format.FormatDataPicker;
import br.com.tlmacedo.cafeperfeito.view.ViewSaidaProduto;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerSaidaProduto implements Initializable, ModeloCafePerfeito {


    public AnchorPane painelViewSaidaProduto;

    public TitledPane tpnCliente;
    public DatePicker dtpDtSaida;
    public DatePicker dtpDtVencimento;
    public ComboBox<Empresa> cboEmpresa;
    public Label lblLimite;
    public Label lblLimiteUtilizado;
    public Label lblLimiteDisponivel;
    public Label lblPrazo;
    public Label lblUltimoPedidoDt;
    public Label lblUltimoPedidoDias;
    public Label lblUltimoPedidoVlr;
    public Label lblQtdPedidos;
    public Label lblTicketMedioVlr;
    public ComboBox<Endereco> cboEndereco;
    public Label lblLogradoruro;
    public Label lblNumero;
    public Label lblBairro;
    public Label lblComplemento;
    public ComboBox<Telefone> cboTelefone;

    public TitledPane tpnNfe;
    public Tab tabNfeDados;
    public ComboBox<NfeDadosNaturezaOperacao> cboNfeDadosNaturezaOperacao;
    public TextField txtNfeDadosNumero;
    public TextField txtNfeDadosSerie;
    public ComboBox<NfeDadosModelo> cboNfeDadosModelo;
    public DatePicker dtpNfeDadosDtEmissao;
    public TextField txtNfeDadosHoraEmissao;
    public DatePicker dtpNfeDadosDtSaida;
    public TextField txtNfeDadosHoraSaida;
    public ComboBox<NfeDadosDestinoOperacao> cboNfeDadosDestinoOperacao;
    public ComboBox<NfeDadosIndicadorConsumidorFinal> cboNfeDadosIndicadorConsumidorFinal;
    public ComboBox<NfeDadosIndicadorPresenca> cboNfeDadosIndicadorPresenca;

    public Tab tabNfeImpressao;
    public ComboBox<NfeImpressaoTpImp> cboNfeImpressaoTpImp;
    public ComboBox<NfeImpressaoTpEmis> cboNfeImpressaoTpEmis;
    public ComboBox<NfeImpressaoFinNFe> cboNfeImpressaoFinNFe;

    public Tab tabNfeTransporta;
    public ComboBox<NfeTransporteModFrete> cboNfeTransporteModFrete;
    public ComboBox<Empresa> cboNfeTransporteTransportadora;

    public Tab tabNfeCobranca;
    public ComboBox<NfeCobrancaDuplicataNumero> cboNfeCobrancaDuplicataNumeros;
    public TextField txtNfeCobrancaDuplicataValor;
    public ComboBox<NfeCobrancaDuplicataPagamentoIndicador> cboNfeCobrancaPagamentoIndicador;
    public ComboBox<NfeCobrancaDuplicataPagamentoMeio> cboNfeCobrancaPagamentoMeio;
    public TextField txtNfeCobrancaPagamentoValor;

    public Tab tabNfeInformacoes;
    public TextArea txaNfeInformacoesAdicionais;

    public TitledPane tpnProdutos;
    public TextField txtPesquisa;
    public Label lblRegistrosLocalizados;
    public TreeTableView<Produto> ttvProdutos;

    public TitledPane tpnItensPedido;
    public TableView<SaidaProdutoProduto> tvItensPedido;
    public VBox vBoxTotalQtdItem;
    public Label lblTotalQtdItem;
    public VBox vBoxTotalQtdProduto;
    public Label lblTotalQtdProduto;
    public VBox vBoxTotalQtdVolume;
    public Label lblTotalQtdVolume;
    public VBox vBoxTotalBruto;
    public Label lblTotalBruto;
    public VBox vBoxTotalDesconto;
    public Label lblTotalDesconto;
    public VBox vBoxTotalLiquido;
    public Label lblTotalLiquido;


    private boolean tabCarregada = false;
    private List<EnumsTasks> enumsTasksList = new ArrayList<>();

    private String nomeTab = ViewSaidaProduto.getTitulo();
    private String nomeController = "saidaProduto";
    private ObjectProperty<StatusBarSaidaProduto> statusBar = new SimpleObjectProperty<>();
    private Task<Void> taskSaidaProduto = newTaskSaidaProduto();
    private EventHandler eventHandlerSaidaProduto;
    private ServiceAlertMensagem alertMensagem;

    private TmodelProduto tmodelProduto;
    private ObservableList<Produto> produtoObservableList;
    private FilteredList<Produto> produtoFilteredList;
    private TmodelSaidaProduto tmodelSaidaProduto;
    private ObservableList<SaidaProdutoProduto> saidaProdutoProdutoObservableList;

    private Endereco endereco = new Endereco();

//    private SaidaProduto saidaProduto;
//    private SaidaProdutoDAO saidaProdutoDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarObjetos();
        preencherObjetos();
        fatorarObjetos();
        escutarTecla();
        ServiceCampoPersonalizado.fieldTextFormat(getPainelViewSaidaProduto());
        Platform.runLater(() -> limpaCampos(getPainelViewSaidaProduto()));
    }

    @Override
    public void fechar() {
        ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getTabs().remove(
                ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getTabs().stream()
                        .filter(tab -> tab.textProperty().get().equals(getNomeTab()))
                        .findFirst().orElse(null)
        );
        ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal()
                .removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerSaidaProduto());
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

        setTabCarregada(new ServiceSegundoPlano().abrindoCadastro(newTaskSaidaProduto(), String.format("Abrindo %s!", getNomeTab())));

    }

    @Override
    public void fatorarObjetos() {

        getDtpDtSaida().setDayCellFactory(param -> new FormatDataPicker(null));
        getDtpDtVencimento().setDayCellFactory(param -> new FormatDataPicker(null));
        getDtpNfeDadosDtEmissao().setDayCellFactory(param -> new FormatDataPicker(null));
        getDtpNfeDadosDtSaida().setDayCellFactory(param -> new FormatDataPicker(null));

        getDtpDtSaida().valueProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            getDtpDtVencimento().setValue(n);
            getDtpDtVencimento().setDayCellFactory(param -> new FormatDataPicker(n));
        });

        getDtpNfeDadosDtEmissao().valueProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            getDtpNfeDadosDtSaida().setValue(n);
            getDtpNfeDadosDtSaida().setDayCellFactory(param -> new FormatDataPicker(n));
        });


    }

    @Override
    public void escutarTecla() {

        statusBarProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            showStatusBar();
        });

        if (statusBarProperty().get() == null)
            setStatusBar(StatusBarSaidaProduto.DIGITACAO);

        getTtvProdutos().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() != KeyCode.ENTER
                    || getTtvProdutos().getSelectionModel().getSelectedItem() == null
                    || getTtvProdutos().getSelectionModel().getSelectedItem().getValue().tblEstoqueProperty().get() <= 0)
                return;
            Produto produtoEscolhido;
            Integer idProd;
            if ((idProd = getTtvProdutos().getSelectionModel().getSelectedItem().getValue().idProperty().getValue().intValue()) == 0) {
                produtoEscolhido = getTtvProdutos().getSelectionModel().getSelectedItem().getValue();
                idProd = getTtvProdutos().getSelectionModel().getSelectedItem().getParent().getValue().idProperty().getValue().intValue();
            } else {
                produtoEscolhido = getTtvProdutos().getSelectionModel().getSelectedItem().getChildren().get(0).getValue();
            }

            if (getSaidaProdutoProdutoObservableList().stream()
                    .filter(saidaProdutoProduto -> saidaProdutoProduto.loteProperty().getValue().equals(produtoEscolhido.tblLoteProperty().getValue()))
                    .findFirst().orElse(null) == null) {
                getSaidaProdutoProdutoObservableList().add(new SaidaProdutoProduto(idProd, produtoEscolhido, TipoSaidaProduto.VENDA, 1));
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F8));
                if (getTmodelSaidaProduto().empresaProperty().getValue() != null)
                    getTmodelSaidaProduto().calculaDescontoCliente();
            } else {
                final int[] row = {0};
                for (SaidaProdutoProduto saida : getSaidaProdutoProdutoObservableList()) {
                    if (saida.loteProperty().getValue().equals(produtoEscolhido.tblLoteProperty().getValue()))
                        break;
                    row[0]++;
                }
                getTvItensPedido().requestFocus();
                getTvItensPedido().getSelectionModel().select(row[0], getTmodelSaidaProduto().getColQtd());
            }
        });

        getTvItensPedido().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() != KeyCode.HELP) return;
            Produto produtoEscolhido = new Produto();
            produtoEscolhido.idProperty().setValue(getTvItensPedido().getSelectionModel().getSelectedItem().idProdProperty().getValue());
            produtoEscolhido.tblEstoqueProperty().setValue(getTvItensPedido().getSelectionModel().getSelectedItem().estoqueProperty().getValue());
            produtoEscolhido.tblLoteProperty().setValue(getTvItensPedido().getSelectionModel().getSelectedItem().loteProperty().getValue());
            produtoEscolhido.tblValidadeProperty().setValue(getTvItensPedido().getSelectionModel().getSelectedItem().dtValidadeProperty().getValue());
            getSaidaProdutoProdutoObservableList().add(new SaidaProdutoProduto(produtoEscolhido.idProperty().getValue().intValue(), produtoEscolhido, TipoSaidaProduto.AMOSTRA, 1));
        });

        setEventHandlerSaidaProduto(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().getSelectedIndex() < 0)
                    return;
                if (!ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().getSelectedItem().getText().equals(getNomeTab()))
                    return;
                if (!teclaDisponivel(event.getCode())) return;
                switch (event.getCode()) {
                    case F1:
                        limpaCampos(getPainelViewSaidaProduto());
                        break;
                    case F2:
                        getEnumsTasksList().clear();
                        getEnumsTasksList().add(EnumsTasks.SALVAR_SAIDA);
                        if (new ServiceSegundoPlano().abrindoCadastro(newTaskSaidaProduto(), String.format("Salvando %s!", getNomeTab()))) {
                            limpaCampos(getPainelViewSaidaProduto());
                        }
                        break;
                    case F6:
                        getCboEmpresa().getEditor().setEditable(true);
                        getCboEmpresa().requestFocus();
                        break;
                    case F7:
                        getTxtPesquisa().requestFocus();
                        break;
                    case F8:
                        getTvItensPedido().requestFocus();
                        getTvItensPedido().getSelectionModel().select(getSaidaProdutoProdutoObservableList().size() - 1,
                                getTmodelSaidaProduto().getColQtd());
                        break;
                    case F9:
                        getTpnNfe().setExpanded(!getTpnNfe().isExpanded());
                        if (getTpnNfe().isExpanded())
                            getCboNfeDadosNaturezaOperacao().requestFocus();
                        else
                            getTxtPesquisa().requestFocus();
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
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().addEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerSaidaProduto());
            else
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerSaidaProduto());
        });

        new ServiceAutoCompleteComboBox(Empresa.class, getCboEmpresa());

        getCboEmpresa().focusedProperty().addListener((ov, o, n) -> {
            if (!n) {
                if (getCboEmpresa().getValue() == null || getCboEmpresa().getValue().idProperty().getValue() == 0) {
                    getCboEndereco().getItems().clear();
                    getCboTelefone().getItems().clear();
                } else {
                    getCboEndereco().setItems(getCboEmpresa().getValue().getEnderecoList().stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
                    getCboTelefone().setItems(getCboEmpresa().getValue().getTelefoneList().stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));

                }
                getTmodelSaidaProduto().setEmpresa(getCboEmpresa().getValue());
                getCboEndereco().getSelectionModel().select(0);
                getCboTelefone().getSelectionModel().select(0);
                getLblLimite().setText(getCboEmpresa().getValue() == null || getCboEmpresa().getValue().idProperty().getValue() == 0
                        ? "0,00"
                        : ServiceMascara.getMoeda(getCboEmpresa().getValue().limiteProperty().getValue(), 2));
                getTmodelSaidaProduto().prazoProperty().setValue(getCboEmpresa().getValue() == null ? 0 : getCboEmpresa().getValue().getPrazo());
            }
            showStatusBar();
        });

        getCboEmpresa().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (getCboEmpresa().getSelectionModel().getSelectedItem() != null)
                getTxtPesquisa().requestFocus();
        });

        getCboEndereco().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (getCboEndereco().getSelectionModel().getSelectedItem() != null)
                getTxtPesquisa().requestFocus();
        });

        getLblLimiteDisponivel().textProperty().bind(Bindings.createStringBinding(() -> {
            BigDecimal vLimite = ServiceMascara.getBigDecimalFromTextField(getLblLimite().getText(), 2);
            BigDecimal vUtilizado = ServiceMascara.getBigDecimalFromTextField(getLblLimiteUtilizado().getText(), 2);
            return ServiceMascara.getMoeda(vLimite.subtract(vUtilizado).setScale(2, RoundingMode.HALF_UP), 2);
        }, getLblLimite().textProperty(), getLblLimiteUtilizado().textProperty()));

        getTmodelSaidaProduto().prazoProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            getLblPrazo().setText(n.toString());
            getDtpDtVencimento().setValue(getDtpDtSaida().getValue().plusDays(n.longValue()));
        });

        getDtpDtSaida().valueProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            getDtpDtVencimento().setValue(getDtpDtSaida().getValue().plusDays(Integer.parseInt(getLblPrazo().getText())));
        });

        getCboEndereco().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            if (n == null) {
                limpaEndereco();
                return;
            }
            getLblLogradoruro().setText(n.getLogradouro());
            getLblNumero().setText(n.getNumero());
            getLblBairro().setText(n.getBairro());
            getLblComplemento().setText(n.getComplemento());
        });

        getTabNfeInformacoes().selectedProperty().addListener((ov, o, n) -> {
            int diff = 21;
            if (!n) diff = (diff * (-1));
            organizaPosicaoCampos(diff);
        });

        getTpnNfe().expandedProperty().addListener((ov, o, n) -> {
            int diff = 85;
            if (!n) diff = (diff * (-1));
            organizaPosicaoCampos(diff);
        });

        getTpnNfe().setExpanded(false);

        getTxtPesquisa().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() != KeyCode.ENTER) return;
            getTtvProdutos().requestFocus();
            getTtvProdutos().getSelectionModel().selectFirst();
        });

        getLblTotalQtdItem().textProperty().bind(Bindings.createStringBinding(() ->
                        getTmodelSaidaProduto().totalQtdItemProperty().getValue().toString(),
                getTmodelSaidaProduto().totalQtdItemProperty()
        ));

        getLblTotalQtdProduto().textProperty().bind(Bindings.createStringBinding(() ->
                        getTmodelSaidaProduto().totalQtdProdutoProperty().getValue().toString(),
                getTmodelSaidaProduto().totalQtdProdutoProperty()
        ));

        getLblTotalQtdVolume().textProperty().bind(Bindings.createStringBinding(() ->
                        getTmodelSaidaProduto().totalQtdVolumeProperty().getValue().toString(),
                getTmodelSaidaProduto().totalQtdVolumeProperty()
        ));

        getLblTotalBruto().textProperty().bind(Bindings.createStringBinding(() ->
                        ServiceMascara.getMoeda(getTmodelSaidaProduto().totalBrutoProperty().getValue(), 2),
                getTmodelSaidaProduto().totalBrutoProperty()
        ));

        getLblTotalDesconto().textProperty().bind(Bindings.createStringBinding(() ->
                        ServiceMascara.getMoeda(getTmodelSaidaProduto().totalDescontoProperty().getValue(), 2),
                getTmodelSaidaProduto().totalDescontoProperty()
        ));

        getLblTotalLiquido().textProperty().bind(Bindings.createStringBinding(() ->
                        ServiceMascara.getMoeda(getTmodelSaidaProduto().totalLiquidoProperty().get().setScale(2), 2),
                getTmodelSaidaProduto().totalLiquidoProperty()));

        getSaidaProdutoProdutoObservableList().addListener((ListChangeListener<? super SaidaProdutoProduto>) change -> {
            showStatusBar();
        });

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
                            setTmodelProduto(new TmodelProduto(TModelTipo.PROD_VENDA));
                            getTmodelProduto().criaTabela();

                            setTmodelSaidaProduto(new TmodelSaidaProduto());
                            getTmodelSaidaProduto().criaTabela();
                            break;

                        case TABELA_VINCULAR:
                            getTmodelProduto().setLblRegistrosLocalizados(getLblRegistrosLocalizados());
                            getTmodelProduto().setTtvProduto(getTtvProdutos());
                            getTmodelProduto().setTxtPesquisa(getTxtPesquisa());
                            setProdutoObservableList(getTmodelProduto().getProdutoObservableList());
                            setProdutoFilteredList(getTmodelProduto().getProdutoFilteredList());
                            getTmodelProduto().escutaLista();

                            getTmodelSaidaProduto().setTvSaidaProdutoProduto(getTvItensPedido());
                            getTmodelSaidaProduto().setTxtPesquisa(getTxtPesquisa());
                            getTmodelSaidaProduto().setDtpDtSaida(getDtpDtSaida());
                            getTmodelSaidaProduto().setDtpDtVencimento(getDtpDtVencimento());
                            setSaidaProdutoProdutoObservableList(getTmodelSaidaProduto().getSaidaProdutoProdutoObservableList());
                            getTmodelSaidaProduto().escutaLista();

                            break;

                        case COMBOS_PREENCHER:
                            getCboEmpresa().setItems(
                                    new EmpresaDAO().getAll(Empresa.class, null, null, null, "razao")
                                            .stream().filter(Empresa::isCliente)
                                            .collect(Collectors.toCollection(FXCollections::observableArrayList))
                            );

                            getCboEmpresa().getItems().add(0, new Empresa());

                            getCboNfeDadosNaturezaOperacao().setItems(
                                    NfeDadosNaturezaOperacao.getList()
                                            .stream()
                                            .collect(Collectors.toCollection(FXCollections::observableArrayList))
                            );
                            break;

                        case TABELA_PREENCHER:
                            getTmodelProduto().preencheTabela();

                            getTmodelSaidaProduto().preencheTabela();
                            break;

                        case SALVAR_SAIDA:
                            if (getTmodelSaidaProduto().guardarSaidaProduto()) {
                                if (getTmodelSaidaProduto().salvarSaidaProduto()) {
                                    getProdutoObservableList().setAll(new ProdutoDAO().getAll(Produto.class, null, null, null, "descricao"));
                                    getTtvProdutos().refresh();
                                } else {
                                    Thread.currentThread().interrupt();
                                }
                            } else {
                                Thread.currentThread().interrupt();
                            }
                            break;
                    }
                }
                updateMessage("tarefa concluída!!!");
                updateProgress(qtdTasks, qtdTasks);
                return null;
            }
        };
    }

//    private Task newTaskF2() {
//        int qtdTasks = getEnumsTasksList().size();
//        final int[] cont = {1};
//        return new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                updateMessage("Finalizando...");
//                for (EnumsTasks tasks : getEnumsTasksList()) {
//                    updateProgress(cont[0]++, qtdTasks);
//                    Thread.sleep(200);
//                    updateMessage(String.format("%s%s", tasks.getDescricao(),
//                            tasks.getDescricao().endsWith(" de ") ? getNomeController() : ""));
//                    switch (tasks) {
//                        case SALVAR_SAIDA:
//                            if (guardarSaidaProduto()) {
//                                if (!salvarSaidaProduto())
//                                    Thread.currentThread().interrupt();
//                            } else {
//                                Thread.currentThread().interrupt();
//                            }
//                            break;
//                    }
//                }
//                updateMessage("saida finalizada com sucesso!!!");
//                updateProgress(qtdTasks, qtdTasks);
//                return null;
//            }
//        };
//    }

    /**
     * END Tasks
     */

    /**
     * Begin voids
     */

    private void limpaCampos(AnchorPane anchorPane) {
        ServiceCampoPersonalizado.fieldClear(anchorPane);
        if (anchorPane == getPainelViewSaidaProduto()) {
            getCboEmpresa().getEditor().clear();
            getCboEmpresa().requestFocus();
            getTmodelSaidaProduto().limpaCampos();
        }
    }

    private void limpaEndereco() {
        getLblLogradoruro().setText("");
        getLblNumero().setText("");
        getLblBairro().setText("");
        getLblComplemento().setText("");
    }

    private boolean teclaDisponivel(KeyCode keyCode) {
        return ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().getStbTeclas().getText().contains(String.format("%s-", keyCode.toString()));
    }

    private void organizaPosicaoCampos(Integer diff) {
        getTpnNfe().setPrefHeight(getTpnNfe().getPrefHeight() + diff);
        getTpnProdutos().setLayoutY(getTpnProdutos().getLayoutY() + diff);
        getTpnItensPedido().setPrefHeight(getTpnItensPedido().getPrefHeight() + (diff * -1));
        getTpnItensPedido().setLayoutY(getTpnItensPedido().getLayoutY() + diff);
        getTvItensPedido().setPrefHeight(getTvItensPedido().getPrefHeight() + (diff * -1));
        getvBoxTotalQtdItem().setLayoutY(getvBoxTotalQtdItem().getLayoutY() + (diff * -1));
        getvBoxTotalQtdProduto().setLayoutY(getvBoxTotalQtdProduto().getLayoutY() + (diff * -1));
        getvBoxTotalQtdVolume().setLayoutY(getvBoxTotalQtdVolume().getLayoutY() + (diff * -1));
        getvBoxTotalBruto().setLayoutY(getvBoxTotalBruto().getLayoutY() + (diff * -1));
        getvBoxTotalDesconto().setLayoutY(getvBoxTotalDesconto().getLayoutY() + (diff * -1));
        getvBoxTotalLiquido().setLayoutY(getvBoxTotalLiquido().getLayoutY() + (diff * -1));
    }

    private void showStatusBar() {
        try {
            if (getSaidaProdutoProdutoObservableList().size() <= 0 || getCboEmpresa().getValue().idProperty().getValue() == 0)
                ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().atualizaStatusBar(statusBarProperty().getValue().getDescricao().replace("  [F2-Finalizar venda]", ""));
            else
                ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().atualizaStatusBar(statusBarProperty().getValue().getDescricao());
        } catch (Exception ex) {
            ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().atualizaStatusBar(statusBarProperty().getValue().getDescricao());
        }
    }

    /**
     * END voids
     */


    public AnchorPane getPainelViewSaidaProduto() {
        return painelViewSaidaProduto;
    }

    public void setPainelViewSaidaProduto(AnchorPane painelViewSaidaProduto) {
        this.painelViewSaidaProduto = painelViewSaidaProduto;
    }

    public TitledPane getTpnCliente() {
        return tpnCliente;
    }

    public void setTpnCliente(TitledPane tpnCliente) {
        this.tpnCliente = tpnCliente;
    }

    public DatePicker getDtpDtSaida() {
        return dtpDtSaida;
    }

    public void setDtpDtSaida(DatePicker dtpDtSaida) {
        this.dtpDtSaida = dtpDtSaida;
    }

    public DatePicker getDtpDtVencimento() {
        return dtpDtVencimento;
    }

    public void setDtpDtVencimento(DatePicker dtpDtVencimento) {
        this.dtpDtVencimento = dtpDtVencimento;
    }

    public ComboBox<Empresa> getCboEmpresa() {
        return cboEmpresa;
    }

    public void setCboEmpresa(ComboBox<Empresa> cboEmpresa) {
        this.cboEmpresa = cboEmpresa;
    }

    public Label getLblLimite() {
        return lblLimite;
    }

    public void setLblLimite(Label lblLimite) {
        this.lblLimite = lblLimite;
    }

    public Label getLblLimiteUtilizado() {
        return lblLimiteUtilizado;
    }

    public void setLblLimiteUtilizado(Label lblLimiteUtilizado) {
        this.lblLimiteUtilizado = lblLimiteUtilizado;
    }

    public Label getLblLimiteDisponivel() {
        return lblLimiteDisponivel;
    }

    public void setLblLimiteDisponivel(Label lblLimiteDisponivel) {
        this.lblLimiteDisponivel = lblLimiteDisponivel;
    }

    public Label getLblPrazo() {
        return lblPrazo;
    }

    public void setLblPrazo(Label lblPrazo) {
        this.lblPrazo = lblPrazo;
    }

    public Label getLblUltimoPedidoDt() {
        return lblUltimoPedidoDt;
    }

    public void setLblUltimoPedidoDt(Label lblUltimoPedidoDt) {
        this.lblUltimoPedidoDt = lblUltimoPedidoDt;
    }

    public Label getLblUltimoPedidoDias() {
        return lblUltimoPedidoDias;
    }

    public void setLblUltimoPedidoDias(Label lblUltimoPedidoDias) {
        this.lblUltimoPedidoDias = lblUltimoPedidoDias;
    }

    public Label getLblUltimoPedidoVlr() {
        return lblUltimoPedidoVlr;
    }

    public void setLblUltimoPedidoVlr(Label lblUltimoPedidoVlr) {
        this.lblUltimoPedidoVlr = lblUltimoPedidoVlr;
    }

    public Label getLblQtdPedidos() {
        return lblQtdPedidos;
    }

    public void setLblQtdPedidos(Label lblQtdPedidos) {
        this.lblQtdPedidos = lblQtdPedidos;
    }

    public Label getLblTicketMedioVlr() {
        return lblTicketMedioVlr;
    }

    public void setLblTicketMedioVlr(Label lblTicketMedioVlr) {
        this.lblTicketMedioVlr = lblTicketMedioVlr;
    }

    public ComboBox<Endereco> getCboEndereco() {
        return cboEndereco;
    }

    public void setCboEndereco(ComboBox<Endereco> cboEndereco) {
        this.cboEndereco = cboEndereco;
    }

    public Label getLblLogradoruro() {
        return lblLogradoruro;
    }

    public void setLblLogradoruro(Label lblLogradoruro) {
        this.lblLogradoruro = lblLogradoruro;
    }

    public Label getLblNumero() {
        return lblNumero;
    }

    public void setLblNumero(Label lblNumero) {
        this.lblNumero = lblNumero;
    }

    public Label getLblBairro() {
        return lblBairro;
    }

    public void setLblBairro(Label lblBairro) {
        this.lblBairro = lblBairro;
    }

    public Label getLblComplemento() {
        return lblComplemento;
    }

    public void setLblComplemento(Label lblComplemento) {
        this.lblComplemento = lblComplemento;
    }

    public ComboBox<Telefone> getCboTelefone() {
        return cboTelefone;
    }

    public void setCboTelefone(ComboBox<Telefone> cboTelefone) {
        this.cboTelefone = cboTelefone;
    }

    public TitledPane getTpnNfe() {
        return tpnNfe;
    }

    public void setTpnNfe(TitledPane tpnNfe) {
        this.tpnNfe = tpnNfe;
    }

    public Tab getTabNfeDados() {
        return tabNfeDados;
    }

    public void setTabNfeDados(Tab tabNfeDados) {
        this.tabNfeDados = tabNfeDados;
    }

    public ComboBox<NfeDadosNaturezaOperacao> getCboNfeDadosNaturezaOperacao() {
        return cboNfeDadosNaturezaOperacao;
    }

    public void setCboNfeDadosNaturezaOperacao(ComboBox<NfeDadosNaturezaOperacao> cboNfeDadosNaturezaOperacao) {
        this.cboNfeDadosNaturezaOperacao = cboNfeDadosNaturezaOperacao;
    }

    public TextField getTxtNfeDadosNumero() {
        return txtNfeDadosNumero;
    }

    public void setTxtNfeDadosNumero(TextField txtNfeDadosNumero) {
        this.txtNfeDadosNumero = txtNfeDadosNumero;
    }

    public TextField getTxtNfeDadosSerie() {
        return txtNfeDadosSerie;
    }

    public void setTxtNfeDadosSerie(TextField txtNfeDadosSerie) {
        this.txtNfeDadosSerie = txtNfeDadosSerie;
    }

    public ComboBox<NfeDadosModelo> getCboNfeDadosModelo() {
        return cboNfeDadosModelo;
    }

    public void setCboNfeDadosModelo(ComboBox<NfeDadosModelo> cboNfeDadosModelo) {
        this.cboNfeDadosModelo = cboNfeDadosModelo;
    }

    public DatePicker getDtpNfeDadosDtEmissao() {
        return dtpNfeDadosDtEmissao;
    }

    public void setDtpNfeDadosDtEmissao(DatePicker dtpNfeDadosDtEmissao) {
        this.dtpNfeDadosDtEmissao = dtpNfeDadosDtEmissao;
    }

    public TextField getTxtNfeDadosHoraEmissao() {
        return txtNfeDadosHoraEmissao;
    }

    public void setTxtNfeDadosHoraEmissao(TextField txtNfeDadosHoraEmissao) {
        this.txtNfeDadosHoraEmissao = txtNfeDadosHoraEmissao;
    }

    public DatePicker getDtpNfeDadosDtSaida() {
        return dtpNfeDadosDtSaida;
    }

    public void setDtpNfeDadosDtSaida(DatePicker dtpNfeDadosDtSaida) {
        this.dtpNfeDadosDtSaida = dtpNfeDadosDtSaida;
    }

    public TextField getTxtNfeDadosHoraSaida() {
        return txtNfeDadosHoraSaida;
    }

    public void setTxtNfeDadosHoraSaida(TextField txtNfeDadosHoraSaida) {
        this.txtNfeDadosHoraSaida = txtNfeDadosHoraSaida;
    }

    public ComboBox<NfeDadosDestinoOperacao> getCboNfeDadosDestinoOperacao() {
        return cboNfeDadosDestinoOperacao;
    }

    public void setCboNfeDadosDestinoOperacao(ComboBox<NfeDadosDestinoOperacao> cboNfeDadosDestinoOperacao) {
        this.cboNfeDadosDestinoOperacao = cboNfeDadosDestinoOperacao;
    }

    public ComboBox<NfeDadosIndicadorConsumidorFinal> getCboNfeDadosIndicadorConsumidorFinal() {
        return cboNfeDadosIndicadorConsumidorFinal;
    }

    public void setCboNfeDadosIndicadorConsumidorFinal(ComboBox<NfeDadosIndicadorConsumidorFinal> cboNfeDadosIndicadorConsumidorFinal) {
        this.cboNfeDadosIndicadorConsumidorFinal = cboNfeDadosIndicadorConsumidorFinal;
    }

    public ComboBox<NfeDadosIndicadorPresenca> getCboNfeDadosIndicadorPresenca() {
        return cboNfeDadosIndicadorPresenca;
    }

    public void setCboNfeDadosIndicadorPresenca(ComboBox<NfeDadosIndicadorPresenca> cboNfeDadosIndicadorPresenca) {
        this.cboNfeDadosIndicadorPresenca = cboNfeDadosIndicadorPresenca;
    }

    public Tab getTabNfeImpressao() {
        return tabNfeImpressao;
    }

    public void setTabNfeImpressao(Tab tabNfeImpressao) {
        this.tabNfeImpressao = tabNfeImpressao;
    }

    public ComboBox<NfeImpressaoTpImp> getCboNfeImpressaoTpImp() {
        return cboNfeImpressaoTpImp;
    }

    public void setCboNfeImpressaoTpImp(ComboBox<NfeImpressaoTpImp> cboNfeImpressaoTpImp) {
        this.cboNfeImpressaoTpImp = cboNfeImpressaoTpImp;
    }

    public ComboBox<NfeImpressaoTpEmis> getCboNfeImpressaoTpEmis() {
        return cboNfeImpressaoTpEmis;
    }

    public void setCboNfeImpressaoTpEmis(ComboBox<NfeImpressaoTpEmis> cboNfeImpressaoTpEmis) {
        this.cboNfeImpressaoTpEmis = cboNfeImpressaoTpEmis;
    }

    public ComboBox<NfeImpressaoFinNFe> getCboNfeImpressaoFinNFe() {
        return cboNfeImpressaoFinNFe;
    }

    public void setCboNfeImpressaoFinNFe(ComboBox<NfeImpressaoFinNFe> cboNfeImpressaoFinNFe) {
        this.cboNfeImpressaoFinNFe = cboNfeImpressaoFinNFe;
    }

    public Tab getTabNfeTransporta() {
        return tabNfeTransporta;
    }

    public void setTabNfeTransporta(Tab tabNfeTransporta) {
        this.tabNfeTransporta = tabNfeTransporta;
    }

    public ComboBox<NfeTransporteModFrete> getCboNfeTransporteModFrete() {
        return cboNfeTransporteModFrete;
    }

    public void setCboNfeTransporteModFrete(ComboBox<NfeTransporteModFrete> cboNfeTransporteModFrete) {
        this.cboNfeTransporteModFrete = cboNfeTransporteModFrete;
    }

    public ComboBox<Empresa> getCboNfeTransporteTransportadora() {
        return cboNfeTransporteTransportadora;
    }

    public void setCboNfeTransporteTransportadora(ComboBox<Empresa> cboNfeTransporteTransportadora) {
        this.cboNfeTransporteTransportadora = cboNfeTransporteTransportadora;
    }

    public Tab getTabNfeCobranca() {
        return tabNfeCobranca;
    }

    public void setTabNfeCobranca(Tab tabNfeCobranca) {
        this.tabNfeCobranca = tabNfeCobranca;
    }

    public ComboBox<NfeCobrancaDuplicataNumero> getCboNfeCobrancaDuplicataNumeros() {
        return cboNfeCobrancaDuplicataNumeros;
    }

    public void setCboNfeCobrancaDuplicataNumeros(ComboBox<NfeCobrancaDuplicataNumero> cboNfeCobrancaDuplicataNumeros) {
        this.cboNfeCobrancaDuplicataNumeros = cboNfeCobrancaDuplicataNumeros;
    }

    public TextField getTxtNfeCobrancaDuplicataValor() {
        return txtNfeCobrancaDuplicataValor;
    }

    public void setTxtNfeCobrancaDuplicataValor(TextField txtNfeCobrancaDuplicataValor) {
        this.txtNfeCobrancaDuplicataValor = txtNfeCobrancaDuplicataValor;
    }

    public ComboBox<NfeCobrancaDuplicataPagamentoIndicador> getCboNfeCobrancaPagamentoIndicador() {
        return cboNfeCobrancaPagamentoIndicador;
    }

    public void setCboNfeCobrancaPagamentoIndicador(ComboBox<NfeCobrancaDuplicataPagamentoIndicador> cboNfeCobrancaPagamentoIndicador) {
        this.cboNfeCobrancaPagamentoIndicador = cboNfeCobrancaPagamentoIndicador;
    }

    public ComboBox<NfeCobrancaDuplicataPagamentoMeio> getCboNfeCobrancaPagamentoMeio() {
        return cboNfeCobrancaPagamentoMeio;
    }

    public void setCboNfeCobrancaPagamentoMeio(ComboBox<NfeCobrancaDuplicataPagamentoMeio> cboNfeCobrancaPagamentoMeio) {
        this.cboNfeCobrancaPagamentoMeio = cboNfeCobrancaPagamentoMeio;
    }

    public TextField getTxtNfeCobrancaPagamentoValor() {
        return txtNfeCobrancaPagamentoValor;
    }

    public void setTxtNfeCobrancaPagamentoValor(TextField txtNfeCobrancaPagamentoValor) {
        this.txtNfeCobrancaPagamentoValor = txtNfeCobrancaPagamentoValor;
    }

    public Tab getTabNfeInformacoes() {
        return tabNfeInformacoes;
    }

    public void setTabNfeInformacoes(Tab tabNfeInformacoes) {
        this.tabNfeInformacoes = tabNfeInformacoes;
    }

    public TextArea getTxaNfeInformacoesAdicionais() {
        return txaNfeInformacoesAdicionais;
    }

    public void setTxaNfeInformacoesAdicionais(TextArea txaNfeInformacoesAdicionais) {
        this.txaNfeInformacoesAdicionais = txaNfeInformacoesAdicionais;
    }

    public TitledPane getTpnProdutos() {
        return tpnProdutos;
    }

    public void setTpnProdutos(TitledPane tpnProdutos) {
        this.tpnProdutos = tpnProdutos;
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

    public TreeTableView<Produto> getTtvProdutos() {
        return ttvProdutos;
    }

    public void setTtvProdutos(TreeTableView<Produto> ttvProdutos) {
        this.ttvProdutos = ttvProdutos;
    }

    public TitledPane getTpnItensPedido() {
        return tpnItensPedido;
    }

    public void setTpnItensPedido(TitledPane tpnItensPedido) {
        this.tpnItensPedido = tpnItensPedido;
    }

    public TableView<SaidaProdutoProduto> getTvItensPedido() {
        return tvItensPedido;
    }

    public void setTvItensPedido(TableView<SaidaProdutoProduto> tvItensPedido) {
        this.tvItensPedido = tvItensPedido;
    }

    public VBox getvBoxTotalQtdItem() {
        return vBoxTotalQtdItem;
    }

    public void setvBoxTotalQtdItem(VBox vBoxTotalQtdItem) {
        this.vBoxTotalQtdItem = vBoxTotalQtdItem;
    }

    public Label getLblTotalQtdItem() {
        return lblTotalQtdItem;
    }

    public void setLblTotalQtdItem(Label lblTotalQtdItem) {
        this.lblTotalQtdItem = lblTotalQtdItem;
    }

    public VBox getvBoxTotalQtdProduto() {
        return vBoxTotalQtdProduto;
    }

    public void setvBoxTotalQtdProduto(VBox vBoxTotalQtdProduto) {
        this.vBoxTotalQtdProduto = vBoxTotalQtdProduto;
    }

    public Label getLblTotalQtdProduto() {
        return lblTotalQtdProduto;
    }

    public void setLblTotalQtdProduto(Label lblTotalQtdProduto) {
        this.lblTotalQtdProduto = lblTotalQtdProduto;
    }

    public VBox getvBoxTotalQtdVolume() {
        return vBoxTotalQtdVolume;
    }

    public void setvBoxTotalQtdVolume(VBox vBoxTotalQtdVolume) {
        this.vBoxTotalQtdVolume = vBoxTotalQtdVolume;
    }

    public Label getLblTotalQtdVolume() {
        return lblTotalQtdVolume;
    }

    public void setLblTotalQtdVolume(Label lblTotalQtdVolume) {
        this.lblTotalQtdVolume = lblTotalQtdVolume;
    }

    public VBox getvBoxTotalBruto() {
        return vBoxTotalBruto;
    }

    public void setvBoxTotalBruto(VBox vBoxTotalBruto) {
        this.vBoxTotalBruto = vBoxTotalBruto;
    }

    public Label getLblTotalBruto() {
        return lblTotalBruto;
    }

    public void setLblTotalBruto(Label lblTotalBruto) {
        this.lblTotalBruto = lblTotalBruto;
    }

    public VBox getvBoxTotalDesconto() {
        return vBoxTotalDesconto;
    }

    public void setvBoxTotalDesconto(VBox vBoxTotalDesconto) {
        this.vBoxTotalDesconto = vBoxTotalDesconto;
    }

    public Label getLblTotalDesconto() {
        return lblTotalDesconto;
    }

    public void setLblTotalDesconto(Label lblTotalDesconto) {
        this.lblTotalDesconto = lblTotalDesconto;
    }

    public Label getLblTotalLiquido() {
        return lblTotalLiquido;
    }

    public void setLblTotalLiquido(Label lblTotalLiquido) {
        this.lblTotalLiquido = lblTotalLiquido;
    }

    public VBox getvBoxTotalLiquido() {
        return vBoxTotalLiquido;
    }

    public void setvBoxTotalLiquido(VBox vBoxTotalLiquido) {
        this.vBoxTotalLiquido = vBoxTotalLiquido;
    }

    /**
     *
     */
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

    public Task<Void> getTaskSaidaProduto() {
        return taskSaidaProduto;
    }

    public void setTaskSaidaProduto(Task<Void> taskSaidaProduto) {
        this.taskSaidaProduto = taskSaidaProduto;
    }

    public EventHandler getEventHandlerSaidaProduto() {
        return eventHandlerSaidaProduto;
    }

    public void setEventHandlerSaidaProduto(EventHandler eventHandlerSaidaProduto) {
        this.eventHandlerSaidaProduto = eventHandlerSaidaProduto;
    }

    public ServiceAlertMensagem getAlertMensagem() {
        return alertMensagem;
    }

    public void setAlertMensagem(ServiceAlertMensagem alertMensagem) {
        this.alertMensagem = alertMensagem;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public TmodelProduto getTmodelProduto() {
        return tmodelProduto;
    }

    public void setTmodelProduto(TmodelProduto tmodelProduto) {
        this.tmodelProduto = tmodelProduto;
    }

    public ObservableList<Produto> getProdutoObservableList() {
        return produtoObservableList;
    }

    public void setProdutoObservableList(ObservableList<Produto> produtoObservableList) {
        this.produtoObservableList = produtoObservableList;
    }

    public FilteredList<Produto> getProdutoFilteredList() {
        return produtoFilteredList;
    }

    public void setProdutoFilteredList(FilteredList<Produto> produtoFilteredList) {
        this.produtoFilteredList = produtoFilteredList;
    }

    public StatusBarSaidaProduto getStatusBar() {
        return statusBar.get();
    }

    public ObjectProperty<StatusBarSaidaProduto> statusBarProperty() {
        return statusBar;
    }

    public void setStatusBar(StatusBarSaidaProduto statusBar) {
        this.statusBar.set(statusBar);
    }

    public TmodelSaidaProduto getTmodelSaidaProduto() {
        return tmodelSaidaProduto;
    }

    public void setTmodelSaidaProduto(TmodelSaidaProduto tmodelSaidaProduto) {
        this.tmodelSaidaProduto = tmodelSaidaProduto;
    }

    public ObservableList<SaidaProdutoProduto> getSaidaProdutoProdutoObservableList() {
        return saidaProdutoProdutoObservableList;
    }

    public void setSaidaProdutoProdutoObservableList(ObservableList<SaidaProdutoProduto> saidaProdutoProdutoObservableList) {
        this.saidaProdutoProdutoObservableList = saidaProdutoProdutoObservableList;
    }

}
