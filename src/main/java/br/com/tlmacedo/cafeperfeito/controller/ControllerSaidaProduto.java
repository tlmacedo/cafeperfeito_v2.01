package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.*;
import br.com.tlmacedo.cafeperfeito.model.enums.*;
import br.com.tlmacedo.cafeperfeito.model.tm.TmodelProduto;
import br.com.tlmacedo.cafeperfeito.model.tm.TmodelSaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.*;
import br.com.tlmacedo.cafeperfeito.nfe.LoadCertificadoA3;
import br.com.tlmacedo.cafeperfeito.nfe.NFeXml;
import br.com.tlmacedo.cafeperfeito.nfe.NFeXmlAssinar;
import br.com.tlmacedo.cafeperfeito.service.*;
import br.com.tlmacedo.cafeperfeito.service.autoComplete.ServiceAutoCompleteComboBox;
import br.com.tlmacedo.cafeperfeito.service.format.FormatDataPicker;
import br.com.tlmacedo.cafeperfeito.service.format.ServiceFormatDataPicker;
import br.com.tlmacedo.cafeperfeito.view.ViewRecebimento;
import br.com.tlmacedo.cafeperfeito.view.ViewSaidaProduto;
import br.com.tlmacedo.nfe.service.*;
import br.inf.portalfiscal.xsd.nfe.consReciNFe.TConsReciNFe;
import br.inf.portalfiscal.xsd.nfe.enviNFe.TEnviNFe;
import br.inf.portalfiscal.xsd.nfe.procNFe.TNFe;
import br.inf.portalfiscal.xsd.nfe.procNFe.TProtNFe;
import br.inf.portalfiscal.xsd.nfe.retConsReciNFe.TRetConsReciNFe;
import br.inf.portalfiscal.xsd.nfe.retEnviNFe.TRetEnviNFe;
import com.google.gson.internal.Pair;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.sql.rowset.serial.SerialBlob;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.math.BigDecimal;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_DATA;
import static br.com.tlmacedo.cafeperfeito.service.ServiceVariaveisSistema.TCONFIG;
import static java.time.temporal.ChronoUnit.DAYS;

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
    public Label labelUltimoPedidoDt;
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

    public TitledPane tpnItensTotaisDetalhe;
    public TextField txtPesquisaProduto;
    public Label lblStatus;
    public Label lblRegistrosLocalizados;
    public TreeTableView<Object> ttvProdutoEstoque;
    public VBox vBoxItensNfeDetalhe;
    public TableView<SaidaProdutoProduto> tvItensNfe;
    public VBox vBoxTotalQtdItem;
    public Label lblQtdItem;
    public VBox vBoxTotalQtdTotal;
    public Label lblQtdTotal;
    public VBox vBoxTotalQtdVolume;
    public Label lblQtdVolume;
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
    private ObjectProperty<StatusBarSaidaProduto> statusBar = new SimpleObjectProperty<>(StatusBarSaidaProduto.DIGITACAO);
    private EventHandler eventHandlerSaidaProduto;
    private ServiceAlertMensagem alertMensagem;

    private TmodelProduto tmodelProduto;
    private FilteredList<Produto> produtoFilteredList;

    private TmodelSaidaProduto tmodelSaidaProduto;
    private ObjectProperty<SaidaProduto> saidaProduto = new SimpleObjectProperty<>();
    private SaidaProdutoDAO saidaProdutoDAO;
    private ObservableList<SaidaProdutoProduto> saidaProdutoProdutoObservableList = FXCollections.observableArrayList();

    private ObjectProperty<Empresa> empresa = new SimpleObjectProperty<>();
    private IntegerProperty prazo = new SimpleIntegerProperty(0);
    private List<FichaKardex> fichaKardexList;

    private ObjectProperty<LoadCertificadoA3> loadCertificadoA3 = new SimpleObjectProperty<>();
    private IntegerProperty nfeLastNumber = new SimpleIntegerProperty(0);
    private ObjectProperty<SaidaProdutoNfe> saidaProdutoNfe = new SimpleObjectProperty<>();
    private StringProperty informacoesAdicionasNFe = new SimpleStringProperty();
    private ObjectProperty<NFev400> nFev400 = new SimpleObjectProperty<>();
    private ObjectProperty<TEnviNFe> tEnviNFe = new SimpleObjectProperty<>();
    private StringProperty xmlNFe = new SimpleStringProperty();
    private StringProperty xmlNFeAssinado = new SimpleStringProperty();
    private StringProperty xmlNFeAutorizacao = new SimpleStringProperty();
    private StringProperty xmlNFeRetAutorizacao = new SimpleStringProperty();
    private StringProperty xmlNFeProc = new SimpleStringProperty();

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
            Platform.runLater(() -> limpaCampos(getPainelViewSaidaProduto()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void fieldsFormat() throws Exception {
        ServiceCampoPersonalizado.fieldTextFormat(getPainelViewSaidaProduto());
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
                    .removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerSaidaProduto());
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

        setTabCarregada(new ServiceSegundoPlano().executaListaTarefas(newTaskSaidaProduto(), String.format("Abrindo %s!", getNomeTab())));
    }

    @Override
    public void fatorarObjetos() {
        getTpnNfe().setExpanded(false);

        getDtpDtSaida().setDayCellFactory(param -> new FormatDataPicker(null));
        getDtpDtVencimento().setDayCellFactory(param -> new FormatDataPicker(null));
        getDtpNfeDadosDtEmissao().setDayCellFactory(param -> new FormatDataPicker(null));
        getDtpNfeDadosDtSaida().setDayCellFactory(param -> new FormatDataPicker(null));

        getDtpDtSaida().valueProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            int prazo = (empresaProperty().getValue() == null
                    ? 0 : empresaProperty().getValue().prazoProperty().getValue());
            getDtpDtVencimento().setValue(n.plusDays(prazo));
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
        escutaTitledTab();
        statusBarProperty().addListener((ov, o, n) -> {
            if (n == null)
                statusBarProperty().setValue(StatusBarSaidaProduto.DIGITACAO);
            showStatusBar();
        });

        getTtvProdutoEstoque().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() != KeyCode.ENTER
                    || getTtvProdutoEstoque().getSelectionModel().getSelectedItem() == null
                    || (getTtvProdutoEstoque().getSelectionModel().getSelectedItem().getValue() instanceof Produto
                    && ((Produto) getTtvProdutoEstoque().getSelectionModel().getSelectedItem().getValue()).tblEstoqueProperty().getValue() <= 0)
                    || (getTtvProdutoEstoque().getSelectionModel().getSelectedItem().getValue() instanceof ProdutoEstoque
                    && ((ProdutoEstoque) getTtvProdutoEstoque().getSelectionModel().getSelectedItem().getValue()).qtdProperty().getValue() <= 0))
                return;
            ProdutoEstoque estoqueEscolhido = getEstoqueSelecionado();
            SaidaProdutoProduto saida;
            if ((saida = getSaidaProdutoProdutoObservableList().stream()
                    .filter(saidaProdutoProduto -> saidaProdutoProduto.loteProperty().getValue().equals(estoqueEscolhido.loteProperty().getValue())
                            && saidaProdutoProduto.produtoProperty().getValue().idProperty().getValue().intValue()
                            == estoqueEscolhido.produtoProperty().getValue().idProperty().getValue().intValue())
                    .findFirst().orElse(null)) == null) {
                getSaidaProdutoProdutoObservableList().add(new SaidaProdutoProduto(estoqueEscolhido, TipoCodigoCFOP.COMERCIALIZACAO, 1));
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F8));
            } else {
                getTvItensNfe().requestFocus();
                getTvItensNfe().getSelectionModel().select(getSaidaProdutoProdutoObservableList().indexOf(saida),
                        getTmodelSaidaProduto().getColQtd());
            }
        });

        setEventHandlerSaidaProduto(new EventHandler<KeyEvent>() {
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
                            limpaCampos(getPainelViewSaidaProduto());
                            break;
                        case F2:
                            if (validarSaida()) {
                                getEnumsTasksList().clear();
                                getEnumsTasksList().add(EnumsTasks.SALVAR_ENT_SAIDA);
                                if (new ServiceSegundoPlano().executaListaTarefas(newTaskSaidaProduto(), String.format("Salvando %s!", getNomeTab()))) {

                                    ServiceUtilJSon.printJsonFromObject(getSaidaProduto(), "saidaProduto");
                                    new ViewRecebimento().openViewRecebimento(getSaidaProduto().contasAReceberProperty().getValue());

                                    if (getSaidaProduto().getSaidaProdutoNfeList().size() > 0)
                                        gerarDanfe();

                                    atualizaTotaisCliente();

                                    limpaCampos(getPainelViewSaidaProduto());
                                }
                            } else {
                                setAlertMensagem(new ServiceAlertMensagem());
                                getAlertMensagem().setCabecalho("Entrada invalida");
                                getAlertMensagem().setContentText("Verifique a saida de produtos pois está invalida");
                                getAlertMensagem().setStrIco("");
                                getAlertMensagem().alertOk();
                            }
                            break;
                        case F6:
                            getCboEmpresa().requestFocus();
                            break;
                        case F7:
                            getTxtPesquisaProduto().requestFocus();
                            break;
                        case F8:
                            getTvItensNfe().requestFocus();
                            getTvItensNfe().getSelectionModel().select(getSaidaProdutoProdutoObservableList().size() - 1,
                                    getTmodelSaidaProduto().getColQtd());
                            break;
                        case F9:
                            getTpnNfe().setExpanded(!getTpnNfe().isExpanded());
                            if (getTpnNfe().isExpanded())
                                getTxtNfeDadosNumero().requestFocus();
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

        ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            if (n.getText().equals(getNomeTab())) {
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().addEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerSaidaProduto());
                showStatusBar();
            } else {
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerSaidaProduto());
            }
        });

        getSaidaProdutoProdutoObservableList().addListener((ListChangeListener<? super SaidaProdutoProduto>) c -> {
            showStatusBar();
        });

        new ServiceAutoCompleteComboBox(Empresa.class, getCboEmpresa());

        new ServiceAutoCompleteComboBox(Empresa.class, getCboNfeTransporteTransportadora());

        empresaProperty().bind(getCboEmpresa().valueProperty());

        empresaProperty().addListener((ov, o, n) -> {
            if (n == null)
                n = new Empresa();
            getTmodelSaidaProduto().empresaProperty().setValue(n);
            getTmodelSaidaProduto().prazoProperty().setValue(n.prazoProperty().getValue() == null ? 0 : n.prazoProperty().getValue());
            exibirEmpresaDetalhe(n);
            showStatusBar();
        });

        getCboEmpresa().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER && getCboEmpresa().getValue() != null)
                getTxtPesquisaProduto().requestFocus();
        });

        getCboEndereco().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            if (n == null) {
                limpaEndereco();
                return;
            }
            getLblLogradoruro().setText(n.logradouroProperty().getValue());
            getLblNumero().setText(n.numeroProperty().getValue());
            getLblBairro().setText(n.bairroProperty().getValue());
            getLblComplemento().setText(n.complementoProperty().getValue());
        });

        prazoProperty().addListener((ov, o, n) -> {
            if (n == null)
                getDtpDtVencimento().setValue(getDtpDtSaida().getValue());
            else
                getDtpDtVencimento().setValue(getDtpDtSaida().getValue().plusDays(n.intValue()));
        });

        getTxtPesquisaProduto().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER) return;
            getTtvProdutoEstoque().requestFocus();
            getTtvProdutoEstoque().getSelectionModel().selectFirst();
        });

        getLblQtdItem().textProperty().bind(getTmodelSaidaProduto().totalQtdItemProperty().asString());
        getLblQtdTotal().textProperty().bind(getTmodelSaidaProduto().totalQtdProdutoProperty().asString());
        getLblQtdVolume().textProperty().bind(getTmodelSaidaProduto().totalQtdVolumeProperty().asString());
        getLblTotalBruto().textProperty().bind(Bindings.createStringBinding(() ->
                        ServiceMascara.getMoeda(getTmodelSaidaProduto().totalBrutoProperty().getValue(), 2),
                getTmodelSaidaProduto().totalBrutoProperty()
        ));
        getLblTotalDesconto().textProperty().bind(Bindings.createStringBinding(() ->
                        ServiceMascara.getMoeda(getTmodelSaidaProduto().totalDescontoProperty().getValue(), 2),
                getTmodelSaidaProduto().totalDescontoProperty()
        ));
        getLblTotalLiquido().textProperty().bind(Bindings.createStringBinding(() ->
                        ServiceMascara.getMoeda(getTmodelSaidaProduto().totalLiquidoProperty().getValue(), 2),
                getTmodelSaidaProduto().totalLiquidoProperty()
        ));

        getTpnNfe().expandedProperty().addListener((ov, o, n) -> {
            if (n) {
                getTxtNfeDadosNumero().setText(String.valueOf(nfeLastNumberProperty().getValue() + 1));
                getTxtNfeDadosSerie().setText(String.valueOf(TCONFIG.getNfe().getNFeSerie()));
                getCboNfeDadosNaturezaOperacao().requestFocus();
            } else {
                limpaCampos(getTpnNfe());
                getTxtPesquisaProduto().requestFocus();
            }
        });

        getCboNfeTransporteTransportadora().disableProperty().bind(getCboNfeTransporteModFrete().valueProperty().isEqualTo(NfeTransporteModFrete.REMETENTE));

        getCboNfeTransporteTransportadora().disableProperty().addListener((ov, o, n) -> {
            if (n)
                getCboNfeTransporteTransportadora().getSelectionModel().select(-1);
        });

        informacoesAdicionasNFeProperty().bind(
                Bindings.createStringBinding(() -> refreshNFeInfAdicionas(),
                        getLblTotalLiquido().textProperty(), getDtpDtVencimento().valueProperty()));

        informacoesAdicionasNFeProperty().addListener((ov, o, n) -> {
            if (n == null)
                n = "";
            getTxaNfeInformacoesAdicionais().setText(n);
        });

        getDtpDtSaida().focusedProperty().addListener((ov, o, n) -> {
            ServiceFormatDataPicker.formatDataPicker(getDtpDtSaida(), n);
        });

        getDtpDtVencimento().focusedProperty().addListener((ov, o, n) -> {
            ServiceFormatDataPicker.formatDataPicker(getDtpDtVencimento(), n);
        });

        getDtpNfeDadosDtSaida().focusedProperty().addListener((ov, o, n) -> {
            ServiceFormatDataPicker.formatDataPicker(getDtpNfeDadosDtSaida(), n);
        });

        getDtpNfeDadosDtEmissao().focusedProperty().addListener((ov, o, n) -> {
            ServiceFormatDataPicker.formatDataPicker(getDtpNfeDadosDtEmissao(), n);
        });

        //getCboNfeTransporteModFrete().disableProperty().bind(getCboNfeTransporteModFrete().selectionModelProperty().isEqualTo(NfeTransporteModFrete.REMETENTE));
    }

    /**
     * Begin Tasks
     */

    private Task newTaskSaidaProduto() {
        try {
            int qtdTasks = getEnumsTasksList().size();
            final int[] cont = {0};
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
                                getTmodelProduto().setTtvProdutoEstoque(getTtvProdutoEstoque());
                                getTmodelProduto().setTxtPesquisaProduto(getTxtPesquisaProduto());
                                setProdutoFilteredList(getTmodelProduto().getProdutoFilteredList());
                                getTmodelProduto().escutaLista();

                                setFichaKardexList(new ArrayList<>());
                                getTmodelSaidaProduto().setFichaKardexList(getFichaKardexList());
                                getTmodelSaidaProduto().setTvItensNfe(getTvItensNfe());
                                getTmodelSaidaProduto().setTxtPesquisaProduto(getTxtPesquisaProduto());
                                getTmodelSaidaProduto().setSaidaProdutoProdutoObservableList(getSaidaProdutoProdutoObservableList());
                                prazoProperty().bind(getTmodelSaidaProduto().prazoProperty());
                                getTmodelSaidaProduto().escutaLista();
                                break;

                            case COMBOS_PREENCHER:
                                loadListaEmpresas();

                                getCboNfeDadosNaturezaOperacao().setItems(
                                        Arrays.stream(NfeDadosNaturezaOperacao.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

                                getCboNfeDadosModelo().setItems(
                                        Arrays.stream(NfeDadosModelo.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

                                getCboNfeDadosDestinoOperacao().setItems(
                                        Arrays.stream(NfeDadosDestinoOperacao.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

                                getCboNfeDadosIndicadorConsumidorFinal().setItems(
                                        Arrays.stream(NfeDadosIndicadorConsumidorFinal.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

                                getCboNfeDadosIndicadorPresenca().setItems(
                                        Arrays.stream(NfeDadosIndicadorPresenca.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

                                getCboNfeImpressaoTpImp().setItems(
                                        Arrays.stream(NfeImpressaoTpImp.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

                                getCboNfeImpressaoTpEmis().setItems(
                                        Arrays.stream(NfeImpressaoTpEmis.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

                                getCboNfeImpressaoFinNFe().setItems(
                                        Arrays.stream(NfeImpressaoFinNFe.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

                                getCboNfeTransporteModFrete().setItems(
                                        Arrays.stream(NfeTransporteModFrete.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

                                getCboNfeCobrancaDuplicataNumeros().setItems(
                                        Arrays.stream(NfeCobrancaDuplicataNumero.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

                                getCboNfeCobrancaPagamentoIndicador().setItems(
                                        Arrays.stream(NfeCobrancaDuplicataPagamentoIndicador.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

                                getCboNfeCobrancaPagamentoMeio().setItems(
                                        Arrays.stream(NfeCobrancaDuplicataPagamentoMeio.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                break;

                            case TABELA_PREENCHER:
                                getTmodelProduto().preencheTabela();

                                getTmodelSaidaProduto().preencheTabela();
                                break;

                            case SALVAR_ENT_SAIDA:
                                if (guardarSaidaProduto()) {
                                    if (salvarSaidaProduto()) {
                                        getTmodelProduto().atualizarProdutos();
                                    } else {
                                        Thread.currentThread().interrupt();
                                    }
                                } else {
                                    Thread.currentThread().interrupt();
                                }
                                break;
                            case NFE_GERAR:
                                xmlNFe_gerar();
                                break;
                            case NFE_ASSINAR:
                                if (xmlNFeProperty().getValue() == null)
                                    Thread.currentThread().interrupt();
                                xmlNFe_assinar();
                                break;
                            case NFE_TRANSMITIR:
                                if (xmlNFeAssinadoProperty().getValue() == null)
                                    Thread.currentThread().interrupt();
                                xmlNFe_transmitir();
                                break;
                            case NFE_RETORNO:
                                if (xmlNFeAutorizacaoProperty().getValue() == null)
                                    Thread.currentThread().interrupt();
                                xmlNFe_retorno();
                                if (xmlNFeRetAutorizacaoProperty().getValue() == null)
                                    Thread.currentThread().interrupt();
                                procNFe_retorno();
                                break;
                            case RELATORIO_IMPRIME_NFE:
                                if (xmlNFeProcProperty().getValue() == null)
                                    Thread.currentThread().interrupt();
                                ControllerPrincipal.getCtrlPrincipal().getPrincipalStage().getScene().setCursor(Cursor.CROSSHAIR);
                                ServiceFileXmlSave.saveTNfeProcToFile(nFev400Property().getValue().getProcNFe().gettNfeProc());
                                ControllerPrincipal.getCtrlPrincipal().getPrincipalStage().getScene().setCursor(Cursor.DEFAULT);
                                break;
                        }
                    }
                    updateMessage("tarefa concluída!!!");
                    updateProgress(qtdTasks, qtdTasks);
                    return null;
                }
            };
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
//        return null;
    }


    /**
     * END Tasks
     */

    /**
     * Begin voids
     */

    private void limpaCampos(TitledPane titledPane) {
        limpaCampos((AnchorPane) titledPane.getContent());
    }

    private void limpaCampos(AnchorPane anchorPane) {
        ServiceCampoPersonalizado.fieldClear(anchorPane);
        if (anchorPane.equals(getPainelViewSaidaProduto())) {
            getCboEmpresa().getSelectionModel().select(-1);
            getCboEmpresa().requestFocus();
            getTpnNfe().setExpanded(false);
        }
    }

    private void limpaEndereco() {
        getLblLogradoruro().setText("");
        getLblNumero().setText("");
        getLblBairro().setText("");
        getLblComplemento().setText("");
    }

    private void escutaTitledTab() {
        getTpnNfe().expandedProperty().addListener((ov, o, n) -> {
            int diff = (getTpnNfe().getHeight() == 0) ? 85 : (int) getTpnNfe().getHeight() - 23;
            if (!n) diff = (diff * -1);
            ajustaTpnItensNota(diff);
//            getTpnNfe().setText(n ? "Informações de imposto" : "Nf-e sem imposto");
        });
    }

    private void ajustaTpnItensNota(int diff) {
        getTpnItensTotaisDetalhe().setLayoutY(getTpnItensTotaisDetalhe().getLayoutY() + diff);
        getTpnItensTotaisDetalhe().setPrefHeight(getTpnItensTotaisDetalhe().getPrefHeight() + (diff * -1));
        getvBoxItensNfeDetalhe().setPrefHeight(getvBoxItensNfeDetalhe().getPrefHeight() + (diff * -1));
    }

    private void showStatusBar() {
        try {
            if (getSaidaProdutoProdutoObservableList().size() <= 0)
                ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().atualizaStatusBar(statusBarProperty().getValue().getDescricao().replace("  [F2-Finalizar venda]", ""));
            else
                ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().atualizaStatusBar(statusBarProperty().getValue().getDescricao());
        } catch (Exception ex) {
            ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().atualizaStatusBar(statusBarProperty().getValue().getDescricao());
        }
    }

    private void loadListaEmpresas() {
        ObservableList<Empresa> empresaObservableList = FXCollections.observableArrayList(
                new EmpresaDAO().getAll(Empresa.class, null, "razao, fantasia"));
        empresaObservableList.add(0, new Empresa());
        ObservableList<ContasAReceber> contasAReceberObservableList =
                FXCollections.observableArrayList(new ContasAReceberDAO().getAll(ContasAReceber.class, null, "dtCadastro DESC"));

        getCboEmpresa().setItems(empresaObservableList);

        getCboNfeTransporteTransportadora().setItems(
                empresaObservableList.stream()
                        .filter(tranportadoras -> tranportadoras.isTransportadora())
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));

        empresaObservableList.stream()
                .filter(empresa -> contasAReceberObservableList.stream()
                        .filter(contasAReceber -> contasAReceber.saidaProdutoProperty().getValue()
                                .clienteProperty().getValue().idProperty().getValue().equals(empresa.idProperty().getValue())).count() > 0)
                .forEach(empresa -> {
                    empresa.limiteUtilizadoProperty().setValue(
                            contasAReceberObservableList.stream()
                                    .filter(aReceber -> aReceber.getSaidaProduto().getCliente().idProperty().getValue() == empresa.idProperty().getValue())
                                    .map(ContasAReceber::getValor)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                                    .subtract(
                                            contasAReceberObservableList.stream()
                                                    .filter(aReceber -> aReceber.getSaidaProduto().getCliente().idProperty().getValue() ==
                                                            empresa.idProperty().getValue())
                                                    .map(ContasAReceber::getRecebimentoList)
                                                    .map(recebimentos -> recebimentos.stream()
                                                            .filter(recebimento -> recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
                                                            .map(Recebimento::getValor)
                                                            .reduce(BigDecimal.ZERO, BigDecimal::add))
                                                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                                    )
                    );
                });

        Integer nLast = 0;
        try {
            nLast = new SaidaProdutoNfeDAO().getLast(SaidaProdutoNfe.class, "numero").numeroProperty().getValue();
        } catch (Exception ex) {
            if (!(ex instanceof NullPointerException))
                ex.printStackTrace();
            nLast = 0;
        }
        nfeLastNumberProperty().setValue(nLast);
    }

    private void exibirEmpresaDetalhe(Empresa empresa) {
        getTmodelSaidaProduto().empresaProperty().setValue(empresa);
        //getTmodelSaidaProduto().prazoProperty().setValue(empresa.prazoProperty().getValue() == null ? 0 : empresa.prazoProperty().getValue());

        getDtpDtSaida().setValue(LocalDate.now());
        getDtpDtVencimento().setValue(getDtpDtSaida().getValue().plusDays(prazoProperty().getValue()));
        getLblLimite().setText(ServiceMascara.getMoeda(empresa.limiteProperty().getValue(), 2));
        getLblLimiteUtilizado().setText(ServiceMascara.getMoeda(empresa.limiteUtilizadoProperty().getValue(), 2));

        getLblUltimoPedidoDt().setText(empresa.dtUltimoPedidoProperty().getValue() != null
                ? empresa.dtUltimoPedidoProperty().getValue().format(DTF_DATA)
                : "");

        getLblUltimoPedidoDias().setText(empresa == null
                ? ""
                : (empresa.dtUltimoPedidoProperty().getValue() != null
                ? String.valueOf(DAYS.between(empresa.dtUltimoPedidoProperty().getValue(), LocalDate.now()))
                : (empresa.dtCadastroProperty().getValue() == null
                ? ""
                : String.valueOf(DAYS.between(empresa.dtCadastroProperty().getValue().toLocalDate(), LocalDate.now())))));

        getLblUltimoPedidoVlr().setText(ServiceMascara.getMoeda(empresa.vlrUltimoPedidoProperty().getValue(), 2));
        getLblQtdPedidos().setText(empresa.qtdPedidosProperty().getValue().toString());
        getLblTicketMedioVlr().setText(ServiceMascara.getMoeda(empresa.vlrTickeMedioProperty().getValue(), 2));

        if (empresa.getEnderecoList() != null)
            getCboEndereco().setItems(empresa.getEnderecoList().stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
        else
            getCboEndereco().getItems().clear();

        if (empresa.getTelefoneList() != null)
            getCboTelefone().setItems(empresa.getTelefoneList().stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
        else
            getCboTelefone().getItems().clear();

        getCboEndereco().getSelectionModel().select(0);
        getCboTelefone().getSelectionModel().select(0);

        getCboEndereco().getItems().stream()
                .filter(endereco -> endereco.getTipo().equals(TipoEndereco.ENTREGA))
                .findFirst().ifPresent(endereco -> getCboEndereco().getSelectionModel().select(endereco));

        showStatusBar();
    }

    private void guardarSaidaProdutoProduto() {
        getSaidaProdutoProdutoObservableList().stream()
                .forEach(saidaProdutoProduto -> {
                    saidaProdutoProduto.saidaProdutoProperty().setValue(getSaidaProduto());
                });
    }

    private void xmlNFe_gerar() throws Exception {
        xmlNFeProperty().setValue(NFeXml.getXml(getSaidaProduto()));
        SaidaProdutoNfe nfeTemp = getSaidaProduto().getSaidaProdutoNfeList().stream().filter(saidaProdutoNfe -> !saidaProdutoNfe.isCancelada()).findFirst().orElse(null);
        if (!saidaProdutoNfeProperty().getValue().equals(nfeTemp) && nfeTemp != null)
            saidaProdutoNfeProperty().setValue(nfeTemp);
        System.out.printf("xmlNFe:\n%s\n---------------------**********************\n\n", xmlNFeProperty().getValue());
    }

    private void xmlNFe_assinar() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException, SQLException {
        SaidaProdutoNfeDAO saidaProdutoNfeDAO = new SaidaProdutoNfeDAO();
        try {
            if (!loadCertificadoA3Property().getValue().equals(null)) {
                xmlNFeAssinadoProperty().setValue(NFeXmlAssinar.getXmlAssinado(xmlNFeProperty().getValue(), loadCertificadoA3Property().getValue()));
            } else {
                Pair<String, LoadCertificadoA3> meuPair = NFeXmlAssinar.getXmlAssinado(xmlNFeProperty().getValue());
                loadCertificadoA3Property().setValue(meuPair.second);
                xmlNFeAssinadoProperty().setValue(meuPair.first);
            }
            saidaProdutoNfeProperty().getValue().xmlAssinaturaProperty().setValue(new SerialBlob(xmlNFeAssinadoProperty().getValue().getBytes()));
            saidaProdutoNfeDAO.transactionBegin();
            saidaProdutoNfeProperty().setValue(saidaProdutoNfeDAO.setTransactionPersist(saidaProdutoNfeProperty().getValue()));
            saidaProdutoNfeDAO.transactionCommit();
        } catch (Exception ex) {
            if (ex instanceof NullPointerException) {
                loadCertificadoA3Property().setValue(new LoadCertificadoA3());
                loadCertificadoA3Property().getValue().load();
                xmlNFe_assinar();
                return;
            }
            ex.printStackTrace();
            saidaProdutoNfeDAO.transactionRollback();
        }
        System.out.printf("xmlNFeAssinado:\n%s\n---------------------**********************\n\n", xmlNFeAssinadoProperty().getValue());
    }

    private void xmlNFe_transmitir() throws XMLStreamException, RemoteException { //throws XMLStreamException, RemoteException {
        nFev400Property().setValue(new NFev400(TCONFIG.getNfe().getTpAmb()));
        nFev400Property().getValue().setAutorizacaoNFe(new NFeAutorizacao(xmlNFeAssinadoProperty().getValue()));
        xmlNFeAutorizacaoProperty().setValue(nFev400Property().getValue().getAutorizacaoNFe().getXmlAutorizacaoNFe());
        System.out.printf("xmlNFeAutorizacao:\n%s\n---------------------**********************\n\n", xmlNFeAutorizacaoProperty().getValue());
        //return (xmlNFeAutorizacaoProperty().getValue() != null);
    }

    private void xmlNFe_retorno() throws JAXBException, RemoteException, InterruptedException, XMLStreamException {
        nFev400Property().getValue().setConsReciNFe(new NFeRetConsReciNfe(ServiceUtilXml.xmlToObject(xmlNFeAutorizacaoProperty().getValue(), TRetEnviNFe.class)));
        TConsReciNFe tConsReciNFe = nFev400Property().getValue().getConsReciNFe().gettConsReciNFe();

        nFev400Property().getValue().setRetAutorizacaoNFe(new NFeRetAutorizacao(ServiceUtilXml.objectToXml(tConsReciNFe)));
        xmlNFeRetAutorizacaoProperty().setValue(nFev400Property().getValue().getRetAutorizacaoNFe().getXmlRetAutorizacaoNFe());
        System.out.printf("xmlRetConsReciNfe:\n%s\n---------------------**********************\n\n", nFev400Property().getValue().getRetAutorizacaoNFe().getXmlRetAutorizacaoNFe());
        TRetConsReciNFe tRetConsReciNFe = ServiceUtilXml.xmlToObject(xmlNFeRetAutorizacaoProperty().getValue(), TRetConsReciNFe.class);
        br.inf.portalfiscal.xsd.nfe.retConsReciNFe.TProtNFe tProtNFe = tRetConsReciNFe.getProtNFe().get(0);

        if (tProtNFe.getInfProt().getCStat().equals("100")) {
            xmlNFeRetAutorizacaoProperty().setValue(nFev400Property().getValue().getRetAutorizacaoNFe().getXmlRetAutorizacaoNFe());
            getAlertMensagem().setCabecalho("NFe");
            getAlertMensagem().setContentText("Nota fiscal eletronica gerada com sucesso!!!");
            System.out.printf("xmlNFeRetAutorizacao:\n%s\n---------------------**********************\n\n", xmlNFeRetAutorizacaoProperty().getValue());
        } else {
            getAlertMensagem().setCabecalho("Retorno inválido!!!");
            getAlertMensagem().setContentText(String.format("tpAmb: %s\ncStat: %s\nxMotivo: %s",
                    tProtNFe.getInfProt().getTpAmb(),
                    tProtNFe.getInfProt().getCStat(),
                    tProtNFe.getInfProt().getXMotivo()));
            Thread.sleep(200);
            throw new RuntimeException();
        }
    }

    private void procNFe_retorno() throws JAXBException, SQLException {
        nFev400Property().getValue().setProcNFe(new NFeProc(xmlNFeAssinadoProperty().getValue(), xmlNFeRetAutorizacaoProperty().getValue()));
        nFev400Property().getValue().getProcNFe().setStrVersao(nFev400Property().getValue().getConsReciNFe().gettConsReciNFe().getVersao());
        nFev400Property().getValue().getProcNFe().setTnFe(ServiceUtilXml.xmlToObject(nFev400Property().getValue().getProcNFe().getStringTNFe(), TNFe.class));
        nFev400Property().getValue().getProcNFe().settProtNFe(ServiceUtilXml.xmlToObject(nFev400Property().getValue().getProcNFe().getStringTProtNFe(), TProtNFe.class));

        xmlNFeProcProperty().setValue(ServiceUtilXml.objectToXml(nFev400Property().getValue().getProcNFe().getResultNFeProc()));
        saidaProdutoNfeProperty().getValue().digValProperty().setValue(Base64.getEncoder().encodeToString(nFev400Property().getValue().getProcNFe().gettNfeProc().getProtNFe().getInfProt().getDigVal()));
        saidaProdutoNfeProperty().getValue().xmlProtNfeProperty().setValue(new SerialBlob(xmlNFeProcProperty().getValue().getBytes()));
        SaidaProdutoNfeDAO saidaProdutoNfeDAO = new SaidaProdutoNfeDAO();
        try {
            saidaProdutoNfeDAO.transactionBegin();
            saidaProdutoNfeProperty().setValue(saidaProdutoNfeDAO.setTransactionPersist(saidaProdutoNfeProperty().getValue()));
            saidaProdutoNfeDAO.transactionCommit();
        } catch (Exception ex) {
            ex.printStackTrace();
            saidaProdutoNfeDAO.transactionRollback();
        }
        System.out.printf("xmlNFeProc:\n%s\n---------------------**********************\n\n", xmlNFeProcProperty().getValue());
    }

    private void atualizaTotaisCliente() {
        getCboEmpresa().getSelectionModel().getSelectedItem()
                .vlrUltimoPedidoProperty().setValue(ServiceMascara.getBigDecimalFromTextField(getLblTotalLiquido().getText(), 2));

        getCboEmpresa().getSelectionModel().getSelectedItem()
                .limiteUtilizadoProperty().getValue()
                .add(getCboEmpresa().getSelectionModel().getSelectedItem().vlrUltimoPedidoProperty().getValue());

        getCboEmpresa().getSelectionModel().getSelectedItem()
                .dtUltimoPedidoProperty().setValue(LocalDate.now());

        getCboEmpresa().getSelectionModel().getSelectedItem()
                .vlrTickeMedioProperty().setValue(
                (getCboEmpresa().getSelectionModel().getSelectedItem().vlrTickeMedioProperty().getValue()
                        .multiply(new BigDecimal(getCboEmpresa().getSelectionModel().getSelectedItem().qtdPedidosProperty().getValue()))
                        .add(getCboEmpresa().getSelectionModel().getSelectedItem().vlrUltimoPedidoProperty().getValue()))
                        .divide(new BigDecimal(getCboEmpresa().getSelectionModel().getSelectedItem()
                                .qtdPedidosProperty().getValue() + 1))
        );

        getCboEmpresa().getSelectionModel().getSelectedItem()
                .qtdPedidosProperty().setValue(getCboEmpresa().getSelectionModel().getSelectedItem().qtdPedidosProperty().getValue() + 1);

        if (saidaProdutoNfeProperty().getValue() != null)
            nfeLastNumberProperty().setValue(saidaProdutoNfeProperty().getValue().numeroProperty().getValue());

    }

    private void salvarContasAReceber() {
        ContasAReceber contasAReceber = new ContasAReceber();
        contasAReceber.saidaProdutoProperty().setValue(getSaidaProduto());
        getSaidaProduto().contasAReceberProperty().setValue(contasAReceber);

        contasAReceber.dtVencimentoProperty().setValue(getDtpDtVencimento().getValue());
        contasAReceber.valorProperty().setValue(ServiceMascara.getBigDecimalFromTextField(getLblTotalLiquido().getText(), 2));
        contasAReceber.usuarioCadastroProperty().setValue(UsuarioLogado.getUsuario());
    }

    /**
     * END voids
     */


    /**
     * Begin returns
     */

    private ProdutoEstoque getEstoqueSelecionado() {
        ProdutoEstoque estoqueSelecionado;
        if (getTtvProdutoEstoque().getSelectionModel().getSelectedItem().getValue() instanceof ProdutoEstoque)
            estoqueSelecionado = ((ProdutoEstoque) getTtvProdutoEstoque().getSelectionModel().getSelectedItem().getValue());
        else
            estoqueSelecionado = ((ProdutoEstoque) getTtvProdutoEstoque().getSelectionModel().getSelectedItem().getChildren().get(0).getValue());
        return estoqueSelecionado;
    }

    private boolean guardarSaidaProduto() {
        try {
            setSaidaProduto(new SaidaProduto());

            getSaidaProduto().setSaidaProdutoProdutoList(getSaidaProdutoProdutoObservableList());
            getSaidaProduto().setCliente(getCboEmpresa().getSelectionModel().getSelectedItem());
            getSaidaProduto().setVendedor(UsuarioLogado.usuarioProperty().getValue());
            getSaidaProduto().setDtSaida(getDtpDtSaida().getValue());

            guardarSaidaProdutoProduto();

            if (getTpnNfe().isExpanded()) {
                saidaProdutoNfeProperty().setValue(new SaidaProdutoNfe());
                saidaProdutoNfeProperty().getValue().saidaProdutoProperty().setValue(getSaidaProduto());
                getSaidaProduto().getSaidaProdutoNfeList().add(saidaProdutoNfeProperty().getValue());

                saidaProdutoNfeProperty().getValue().canceladaProperty().setValue(false);
                saidaProdutoNfeProperty().getValue().statusSefazProperty().setValue(NfeStatusSefaz.DIGITACAO);
                saidaProdutoNfeProperty().getValue().naturezaOperacaoProperty().setValue(getCboNfeDadosNaturezaOperacao().getSelectionModel().getSelectedItem());
                saidaProdutoNfeProperty().getValue().modeloProperty().setValue(getCboNfeDadosModelo().getSelectionModel().getSelectedItem());
                saidaProdutoNfeProperty().getValue().serieProperty().setValue(Integer.valueOf(getTxtNfeDadosSerie().getText()));
                saidaProdutoNfeProperty().getValue().numeroProperty().setValue(Integer.valueOf(getTxtNfeDadosNumero().getText()));
                saidaProdutoNfeProperty().getValue().dtHoraEmissaoProperty().setValue(getDtpNfeDadosDtEmissao().getValue()
                        .atTime(LocalTime.parse(getTxtNfeDadosHoraEmissao().getText())));
                saidaProdutoNfeProperty().getValue().dtHoraSaidaProperty().setValue(getDtpNfeDadosDtSaida().getValue()
                        .atTime(LocalTime.parse(getTxtNfeDadosHoraSaida().getText())));
                saidaProdutoNfeProperty().getValue().destinoOperacaoProperty().setValue(getCboNfeDadosDestinoOperacao().getSelectionModel().getSelectedItem());
                saidaProdutoNfeProperty().getValue().consumidorFinalProperty().setValue(getCboNfeDadosIndicadorConsumidorFinal().getSelectionModel().getSelectedItem());
                saidaProdutoNfeProperty().getValue().indicadorPresencaProperty().setValue(getCboNfeDadosIndicadorPresenca().getSelectionModel().getSelectedItem());
                saidaProdutoNfeProperty().getValue().modFreteProperty().setValue(getCboNfeTransporteModFrete().getSelectionModel().getSelectedItem());
                saidaProdutoNfeProperty().getValue().transportadorProperty().setValue(getCboNfeTransporteTransportadora().getSelectionModel().getSelectedItem());
                saidaProdutoNfeProperty().getValue().cobrancaNumeroProperty().setValue(getCboNfeCobrancaDuplicataNumeros().getSelectionModel().getSelectedItem().getDescricao());
                saidaProdutoNfeProperty().getValue().pagamentoIndicadorProperty().setValue(getCboNfeCobrancaPagamentoIndicador().getSelectionModel().getSelectedItem());
                saidaProdutoNfeProperty().getValue().pagamentoMeioProperty().setValue(getCboNfeCobrancaPagamentoMeio().getSelectionModel().getSelectedItem());
                saidaProdutoNfeProperty().getValue().informacaoAdicionalProperty().setValue(getTxaNfeInformacoesAdicionais().getText().trim());

                saidaProdutoNfeProperty().getValue().chaveProperty().setValue(ServiceValidarDado.gerarChaveNfe(saidaProdutoNfeProperty().getValue()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean validarSaida() {
        return (validarCliente() && validarNFe()
                && getSaidaProdutoProdutoObservableList().size() > 0);
    }

    private boolean validarCliente() {
        if (getDtpDtSaida().getValue() == null) {
            getDtpDtSaida().requestFocus();
            return false;
        }
        if (getDtpDtVencimento().getValue() == null) {
            getDtpDtVencimento().requestFocus();
            return false;
        }
        if (getCboEmpresa().getSelectionModel().getSelectedItem() == null) {
            getCboEmpresa().requestFocus();
            return false;
        }
        if (getCboEndereco().getSelectionModel().getSelectedItem() == null) {
            getCboEndereco().getSelectionModel().select(0);
        }
        return true;
    }

    private boolean validarNFe() {
        if (!getTpnNfe().isExpanded())
            return true;
        if (getCboNfeDadosNaturezaOperacao().getSelectionModel().getSelectedItem() == null) {
            getCboNfeDadosNaturezaOperacao().requestFocus();
            return false;
        }
        if (getTxtNfeDadosNumero().getText().trim().replaceAll("\\D", "").length() == 0) {
            getTxtNfeDadosNumero().requestFocus();
            return false;
        }
        if (getTxtNfeDadosSerie().getText().trim().replaceAll("\\D", "").length() == 0) {
            getTxtNfeDadosSerie().requestFocus();
            return false;
        }
        if (getCboNfeDadosModelo().getSelectionModel().getSelectedItem() == null) {
            getCboNfeDadosModelo().requestFocus();
            return false;
        }
        if (getDtpNfeDadosDtEmissao().getValue() == null || getDtpNfeDadosDtEmissao().getValue().compareTo(LocalDate.now()) > 0) {
            getDtpNfeDadosDtEmissao().requestFocus();
            return false;
        }
        if (getTxtNfeDadosHoraEmissao().getText().trim().replaceAll("\\D", "").length() == 0) {
            getTxtNfeDadosHoraEmissao().requestFocus();
            return false;
        }
        if (getDtpNfeDadosDtSaida().getValue() == null || getDtpNfeDadosDtSaida().getValue().compareTo(LocalDate.now()) > 0) {
            getDtpNfeDadosDtSaida().requestFocus();
            return false;
        }
        if (getTxtNfeDadosHoraSaida().getText().trim().replaceAll("\\D", "").length() == 0) {
            getTxtNfeDadosHoraSaida().requestFocus();
            return false;
        }
        if (getCboNfeDadosDestinoOperacao().getSelectionModel().getSelectedItem() == null) {
            getCboNfeDadosDestinoOperacao().requestFocus();
            return false;
        }
        return true;
    }

    private boolean salvarSaidaProduto() {
        try {
            setSaidaProdutoDAO(new SaidaProdutoDAO());
            getSaidaProdutoDAO().transactionBegin();
            if (!getTmodelSaidaProduto().baixarEstoque()) return false;
            salvarContasAReceber();
            saidaProdutoProperty().setValue(getSaidaProdutoDAO().setTransactionPersist(saidaProdutoProperty().getValue()));
            getSaidaProdutoDAO().transactionCommit();
            salvarFichaKardexList();

        } catch (Exception ex) {
            ex.printStackTrace();
            getSaidaProdutoDAO().transactionRollback();
            return false;
        }
        return true;
    }

    private boolean salvarFichaKardexList() {
        FichaKardexDAO fichaKardexDAO = new FichaKardexDAO();
        try {
            fichaKardexDAO.transactionBegin();
            for (FichaKardex ficha : getFichaKardexList()) {
                ficha.setDocumento(getSaidaProduto().idProperty().getValue().toString());
                fichaKardexDAO.setTransactionPersist(ficha);
            }
            fichaKardexDAO.transactionCommit();
        } catch (Exception ex) {
            fichaKardexDAO.transactionRollback();
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean gerarDanfe() {
        boolean retorno = false;
        try {
            getEnumsTasksList().clear();
            getEnumsTasksList().add(EnumsTasks.NFE_GERAR);
            getEnumsTasksList().add(EnumsTasks.NFE_ASSINAR);
            getEnumsTasksList().add(EnumsTasks.NFE_TRANSMITIR);
            getEnumsTasksList().add(EnumsTasks.NFE_RETORNO);
            getEnumsTasksList().add(EnumsTasks.RELATORIO_IMPRIME_NFE);
            setAlertMensagem(new ServiceAlertMensagem());
            getAlertMensagem().setCabecalho("Retorno inválido");
            getAlertMensagem().setContentText("alguma coisa de errado aconteceu com a nota!!!");
            retorno = new ServiceSegundoPlano().executaListaTarefas(newTaskSaidaProduto(), String.format("Gerando NFe [%d]!", Integer.valueOf(getTxtNfeDadosNumero().getText())));
            getAlertMensagem().alertOk();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retorno;
    }

    private String refreshNFeInfAdicionas() {
        String strInf = String.format(TCONFIG.getNfe().getInfAdic(),
                getLblTotalLiquido().getText(),
                (getDtpDtVencimento().getValue() != null)
                        ? String.format("dt. Venc.: %s",
                        getDtpDtVencimento().getValue().format(DTF_DATA))
                        : "",
                TCONFIG.getInfLoja().getBanco(),
                TCONFIG.getInfLoja().getAgencia(), TCONFIG.getInfLoja().getContaCorrente())
                .toUpperCase();

        int start = 0, end = 0;
        String retorno = strInf;
        if (getTxaNfeInformacoesAdicionais().getText().length() > 0) {
            start = getTxaNfeInformacoesAdicionais().getText().indexOf("-** ");
            end = getTxaNfeInformacoesAdicionais().getText().indexOf(" **-");
            if (end > 0)
                retorno = getTxaNfeInformacoesAdicionais().getText().replace(
                        getTxaNfeInformacoesAdicionais().getText().substring(start, end + 4),
                        strInf
                );
        }
        return retorno;
    }


    /**
     * END returns
     */

    /**
     * Begin Getters e Setters
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

    public Label getLabelUltimoPedidoDt() {
        return labelUltimoPedidoDt;
    }

    public void setLabelUltimoPedidoDt(Label labelUltimoPedidoDt) {
        this.labelUltimoPedidoDt = labelUltimoPedidoDt;
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

    public TitledPane getTpnItensTotaisDetalhe() {
        return tpnItensTotaisDetalhe;
    }

    public void setTpnItensTotaisDetalhe(TitledPane tpnItensTotaisDetalhe) {
        this.tpnItensTotaisDetalhe = tpnItensTotaisDetalhe;
    }

    public TextField getTxtPesquisaProduto() {
        return txtPesquisaProduto;
    }

    public void setTxtPesquisaProduto(TextField txtPesquisaProduto) {
        this.txtPesquisaProduto = txtPesquisaProduto;
    }

    public Label getLblStatus() {
        return lblStatus;
    }

    public void setLblStatus(Label lblStatus) {
        this.lblStatus = lblStatus;
    }

    public Label getLblRegistrosLocalizados() {
        return lblRegistrosLocalizados;
    }

    public void setLblRegistrosLocalizados(Label lblRegistrosLocalizados) {
        this.lblRegistrosLocalizados = lblRegistrosLocalizados;
    }

    public TreeTableView<Object> getTtvProdutoEstoque() {
        return ttvProdutoEstoque;
    }

    public void setTtvProdutoEstoque(TreeTableView<Object> ttvProdutoEstoque) {
        this.ttvProdutoEstoque = ttvProdutoEstoque;
    }

    public VBox getvBoxItensNfeDetalhe() {
        return vBoxItensNfeDetalhe;
    }

    public void setvBoxItensNfeDetalhe(VBox vBoxItensNfeDetalhe) {
        this.vBoxItensNfeDetalhe = vBoxItensNfeDetalhe;
    }

    public TableView<SaidaProdutoProduto> getTvItensNfe() {
        return tvItensNfe;
    }

    public void setTvItensNfe(TableView<SaidaProdutoProduto> tvItensNfe) {
        this.tvItensNfe = tvItensNfe;
    }

    public VBox getvBoxTotalQtdItem() {
        return vBoxTotalQtdItem;
    }

    public void setvBoxTotalQtdItem(VBox vBoxTotalQtdItem) {
        this.vBoxTotalQtdItem = vBoxTotalQtdItem;
    }

    public Label getLblQtdItem() {
        return lblQtdItem;
    }

    public void setLblQtdItem(Label lblQtdItem) {
        this.lblQtdItem = lblQtdItem;
    }

    public VBox getvBoxTotalQtdTotal() {
        return vBoxTotalQtdTotal;
    }

    public void setvBoxTotalQtdTotal(VBox vBoxTotalQtdTotal) {
        this.vBoxTotalQtdTotal = vBoxTotalQtdTotal;
    }

    public Label getLblQtdTotal() {
        return lblQtdTotal;
    }

    public void setLblQtdTotal(Label lblQtdTotal) {
        this.lblQtdTotal = lblQtdTotal;
    }

    public VBox getvBoxTotalQtdVolume() {
        return vBoxTotalQtdVolume;
    }

    public void setvBoxTotalQtdVolume(VBox vBoxTotalQtdVolume) {
        this.vBoxTotalQtdVolume = vBoxTotalQtdVolume;
    }

    public Label getLblQtdVolume() {
        return lblQtdVolume;
    }

    public void setLblQtdVolume(Label lblQtdVolume) {
        this.lblQtdVolume = lblQtdVolume;
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

    public VBox getvBoxTotalLiquido() {
        return vBoxTotalLiquido;
    }

    public void setvBoxTotalLiquido(VBox vBoxTotalLiquido) {
        this.vBoxTotalLiquido = vBoxTotalLiquido;
    }

    public Label getLblTotalLiquido() {
        return lblTotalLiquido;
    }

    public void setLblTotalLiquido(Label lblTotalLiquido) {
        this.lblTotalLiquido = lblTotalLiquido;
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

    public StatusBarSaidaProduto getStatusBar() {
        return statusBar.get();
    }

    public ObjectProperty<StatusBarSaidaProduto> statusBarProperty() {
        return statusBar;
    }

    public void setStatusBar(StatusBarSaidaProduto statusBar) {
        this.statusBar.set(statusBar);
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

    public TmodelProduto getTmodelProduto() {
        return tmodelProduto;
    }

    public void setTmodelProduto(TmodelProduto tmodelProduto) {
        this.tmodelProduto = tmodelProduto;
    }

    public FilteredList<Produto> getProdutoFilteredList() {
        return produtoFilteredList;
    }

    public void setProdutoFilteredList(FilteredList<Produto> produtoFilteredList) {
        this.produtoFilteredList = produtoFilteredList;
    }

    public TmodelSaidaProduto getTmodelSaidaProduto() {
        return tmodelSaidaProduto;
    }

    public void setTmodelSaidaProduto(TmodelSaidaProduto tmodelSaidaProduto) {
        this.tmodelSaidaProduto = tmodelSaidaProduto;
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

    public SaidaProdutoDAO getSaidaProdutoDAO() {
        return saidaProdutoDAO;
    }

    public void setSaidaProdutoDAO(SaidaProdutoDAO saidaProdutoDAO) {
        this.saidaProdutoDAO = saidaProdutoDAO;
    }

    public ObservableList<SaidaProdutoProduto> getSaidaProdutoProdutoObservableList() {
        return saidaProdutoProdutoObservableList;
    }

    public void setSaidaProdutoProdutoObservableList(ObservableList<SaidaProdutoProduto> saidaProdutoProdutoObservableList) {
        this.saidaProdutoProdutoObservableList = saidaProdutoProdutoObservableList;
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

    public List<FichaKardex> getFichaKardexList() {
        return fichaKardexList;
    }

    public void setFichaKardexList(List<FichaKardex> fichaKardexList) {
        this.fichaKardexList = fichaKardexList;
    }

    public int getPrazo() {
        return prazo.get();
    }

    public IntegerProperty prazoProperty() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo.set(prazo);
    }

    public String getXmlNFe() {
        return xmlNFe.get();
    }

    public StringProperty xmlNFeProperty() {
        return xmlNFe;
    }

    public void setXmlNFe(String xmlNFe) {
        this.xmlNFe.set(xmlNFe);
    }

    public String getXmlNFeAssinado() {
        return xmlNFeAssinado.get();
    }

    public StringProperty xmlNFeAssinadoProperty() {
        return xmlNFeAssinado;
    }

    public void setXmlNFeAssinado(String xmlNFeAssinado) {
        this.xmlNFeAssinado.set(xmlNFeAssinado);
    }

    public String getXmlNFeAutorizacao() {
        return xmlNFeAutorizacao.get();
    }

    public StringProperty xmlNFeAutorizacaoProperty() {
        return xmlNFeAutorizacao;
    }

    public void setXmlNFeAutorizacao(String xmlNFeAutorizacao) {
        this.xmlNFeAutorizacao.set(xmlNFeAutorizacao);
    }

    public String getXmlNFeRetAutorizacao() {
        return xmlNFeRetAutorizacao.get();
    }

    public StringProperty xmlNFeRetAutorizacaoProperty() {
        return xmlNFeRetAutorizacao;
    }

    public void setXmlNFeRetAutorizacao(String xmlNFeRetAutorizacao) {
        this.xmlNFeRetAutorizacao.set(xmlNFeRetAutorizacao);
    }

    public String getXmlNFeProc() {
        return xmlNFeProc.get();
    }

    public StringProperty xmlNFeProcProperty() {
        return xmlNFeProc;
    }

    public void setXmlNFeProc(String xmlNFeProc) {
        this.xmlNFeProc.set(xmlNFeProc);
    }

    public int getNfeLastNumber() {
        return nfeLastNumber.get();
    }

    public IntegerProperty nfeLastNumberProperty() {
        return nfeLastNumber;
    }

    public void setNfeLastNumber(int nfeLastNumber) {
        this.nfeLastNumber.set(nfeLastNumber);
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

    public String getInformacoesAdicionasNFe() {
        return informacoesAdicionasNFe.get();
    }

    public StringProperty informacoesAdicionasNFeProperty() {
        return informacoesAdicionasNFe;
    }

    public void setInformacoesAdicionasNFe(String informacoesAdicionasNFe) {
        this.informacoesAdicionasNFe.set(informacoesAdicionasNFe);
    }

    public NFev400 getnFev400() {
        return nFev400.get();
    }

    public ObjectProperty<NFev400> nFev400Property() {
        return nFev400;
    }

    public void setnFev400(NFev400 nFev400) {
        this.nFev400.set(nFev400);
    }

    public TEnviNFe gettEnviNFe() {
        return tEnviNFe.get();
    }

    public ObjectProperty<TEnviNFe> tEnviNFeProperty() {
        return tEnviNFe;
    }

    public void settEnviNFe(TEnviNFe tEnviNFe) {
        this.tEnviNFe.set(tEnviNFe);
    }

    public LoadCertificadoA3 getLoadCertificadoA3() {
        return loadCertificadoA3.get();
    }

    public ObjectProperty<LoadCertificadoA3> loadCertificadoA3Property() {
        return loadCertificadoA3;
    }

    public void setLoadCertificadoA3(LoadCertificadoA3 loadCertificadoA3) {
        this.loadCertificadoA3.set(loadCertificadoA3);
    }

    /**
     * END Getters e Setters
     */

}
