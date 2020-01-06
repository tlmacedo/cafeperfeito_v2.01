package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.FiscalFreteSituacaoTributariaDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.FiscalTributosSefazAmDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.*;
import br.com.tlmacedo.cafeperfeito.model.tm.TmodelEntradaProduto;
import br.com.tlmacedo.cafeperfeito.model.tm.TmodelProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.*;
import br.com.tlmacedo.cafeperfeito.service.*;
import br.com.tlmacedo.cafeperfeito.service.autoComplete.ServiceAutoCompleteComboBox;
import br.com.tlmacedo.cafeperfeito.view.ViewEntradaProduto;
import br.inf.portalfiscal.xsd.cte.procCTe.CteProc;
import br.inf.portalfiscal.xsd.cte.procCTe.TCTe;
import br.inf.portalfiscal.xsd.nfe.procNFe.TNfeProc;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_NFE_TO_LOCAL_DATE;

/**
 * Created by IntelliJ IDEA.
 * User: thiagomacedo
 * Date: 2019-02-22
 * Time: 12:33
 */

public class ControllerEntradaProduto implements Initializable, ModeloCafePerfeito {


    public AnchorPane painelViewEntradaProduto;
    public TitledPane tpnEntradaNfe;
    public TitledPane tpnNfeDetalhe;
    public ComboBox<Empresa> cboNfeLojaDestino;
    public TextField txtNfeChave;
    public TextField txtNfeNumero;
    public TextField txtNfeSerie;
    public ComboBox<NfeCteModelo> cboNfeModelo;
    public ComboBox<Empresa> cboNfeFornecedor;
    public DatePicker dtpNfeEmissao;
    public DatePicker dtpNfeEntrada;

    public TitledPane tpnNfeDetalheFiscal;
    public TextField txtNfeFiscalControle;
    public TextField txtNfeFiscalOrigem;
    public ComboBox<FiscalTributosSefazAm> cboNfeFiscalTributo;
    public TextField txtNfeFiscalVlrNFe;
    public TextField txtNfeFiscalVlrTributo;
    public TextField txtNfeFiscalVlrMulta;
    public TextField txtNfeFiscalVlrJuros;
    public TextField txtNfeFiscalVlrTaxa;
    public Label lblNfeFiscalVlrTotal;
    public Label lblNfeFiscalVlrPercentual;

    public TitledPane tpnCteDetalhe;
    public TextField txtCteChave;
    public ComboBox<CteTomadorServico> cboCteTomadorServico;
    public TextField txtCteNumero;
    public TextField txtCteSerie;
    public ComboBox<NfeCteModelo> cboCteModelo;
    public DatePicker dtpCteEmissao;
    public ComboBox<Empresa> cboCteTransportadora;
    public ComboBox<FiscalFreteSituacaoTributaria> cboCteSistuacaoTributaria;
    public TextField txtCteVlrCte;
    public TextField txtCteQtdVolume;
    public TextField txtCtePesoBruto;
    public TextField txtCteVlrBruto;
    public TextField txtCteVlrTaxa;
    public TextField txtCteVlrColeta;
    public TextField txtCteVlrImposto;
    public Label lblCteVlrLiquido;

    public TitledPane tpnCteDetalheFiscal;
    public TextField txtCteFiscalControle;
    public TextField txtCteFiscalOrigem;
    public ComboBox<FiscalTributosSefazAm> cboCteFiscalTributo;
    public Label lblCteFiscalVlrCte;
    public TextField txtCteFiscalVlrTributo;
    public TextField txtCteFiscalVlrMulta;
    public TextField txtCteFiscalVlrJuros;
    public TextField txtCteFiscalVlrTaxa;
    public Label lblCteFiscalVlrTotal;
    public Label lblCteFiscalVlrPercentual;

    public TitledPane tpnItensTotaisDetalhe;
    //    public TitledPane tpnCadastroProduto;
    public TextField txtPesquisaProduto;
    public Label lblStatus;
    public Label lblRegistrosLocalizados;
    public TreeTableView<Produto> ttvProduto;
    public VBox vBoxItensNfeDetalhe;
    public TableView<EntradaProdutoProduto> tvItensNfeDetalhe;
    public VBox vBoxTotalQtdItem;
    public Label lblQtdItem;
    public VBox vBoxTotalQtdTotal;
    public Label lblQtdTotal;
    public VBox vBoxTotalQtdVolume;
    public Label lblQtdVolume;
    public VBox vBoxTotalBruto;
    public Label lblTotalBruto;
    public VBox vBoxTotalImposto;
    public Label lblTotalImposto;
    public VBox vBoxTotalFrete;
    public Label lblTotalFrete;
    public VBox vBoxTotalDesconto;
    public Label lblTotalDesconto;
    public VBox vBoxTotalLiquido;
    public Label lblTotalLiquido;

    private boolean tabCarregada = false;
    private List<EnumsTasks> enumsTasksList = new ArrayList<>();

    private String nomeTab = ViewEntradaProduto.getTitulo();
    private String nomeController = "entradaProduto";
    private ObjectProperty<StatusBarEntradaProduto> statusBar = new SimpleObjectProperty<>(StatusBarEntradaProduto.DIGITACAO);
    private EventHandler eventHandlerEntradaProduto;
    private ServiceAlertMensagem alertMensagem;

    private TmodelProduto tmodelProduto;
    private FilteredList<Produto> produtoFilteredList;

    private TmodelEntradaProduto tmodelEntradaProduto;
    //    private SaidaProduto saidaProduto = new SaidaProduto();
//    private SaidaProdutoDAO saidaProdutoDAO = new SaidaProdutoDAO();
    private ObservableList<EntradaProdutoProduto> entradaProdutoProdutoObservableList = FXCollections.observableArrayList();

//    private ObservableList<Empresa> empresaObservableList = FXCollections.observableArrayList(
//            new EmpresaDAO().getAll(Empresa.class, null, "razao, fantasia"));
//    private ObjectProperty<Empresa> empresa = new SimpleObjectProperty<>();
//    private ObjectProperty<List<Endereco>> enderecoList = new SimpleObjectProperty<>();
//    private ObjectProperty<Endereco> endereco = new SimpleObjectProperty<>();
//    private ObjectProperty<List<Telefone>> telefoneList = new SimpleObjectProperty<>();

    private StringProperty nfeFiscalVlrTotal = new SimpleStringProperty();

    ServiceCalculaTempo tempo = new ServiceCalculaTempo();

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
            Platform.runLater(() -> limpaCampos(getPainelViewEntradaProduto()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void fieldsFormat() throws Exception {
        ServiceCampoPersonalizado.fieldTextFormat(getPainelViewEntradaProduto());
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
                    .removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerEntradaProduto());
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

        setTabCarregada(new ServiceSegundoPlano().executaListaTarefas(newTaskEntradaProduto(), String.format("Abrindo %s!", getNomeTab())));
    }

    @Override
    public void fatorarObjetos() {
        getTpnNfeDetalheFiscal().setExpanded(false);
        getTpnCteDetalheFiscal().setExpanded(false);
        getTpnCteDetalhe().setExpanded(false);
    }

    @Override
    public void escutarTecla() {
        escutaTitledTab();
        statusBarProperty().addListener((ov, o, n) -> {
            if (n == null)
                statusBarProperty().setValue(StatusBarEntradaProduto.DIGITACAO);
            showStatusBar();
        });

//        getTtvProduto().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() != KeyCode.ENTER
//                    || getTtvProduto().getSelectionModel().getSelectedItem() == null
//                    || getTtvProduto().getSelectionModel().getSelectedItem().getValue().tblEstoqueProperty().getValue() <= 0)
//                return;
//            Produto produtoSelecionado = getProdutoSelecionado();
//            if (getSaidaProdutoProdutoObservableList().stream()
//                    .filter(saidaProdutoProduto -> saidaProdutoProduto.loteProperty().getValue().equals(produtoSelecionado.tblLoteProperty().getValue())
//                            && saidaProdutoProduto.getProduto().idProperty().getValue().intValue() == produtoSelecionado.idProperty().getValue().intValue())
//                    .findFirst().orElse(null) == null) {
//                getSaidaProdutoProdutoObservableList().add(new SaidaProdutoProduto(produtoSelecionado, TipoCodigoCFOP.VENDA, 1));
//                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F8));
//                if (empresaProperty().getValue() != null)
//                    getTmodelSaidaProduto().calculaDescontoCliente();
//            } else {
//                for (int i = 0; i < getSaidaProdutoProdutoObservableList().size(); i++) {
//                    SaidaProdutoProduto saida = getSaidaProdutoProdutoObservableList().get(i);
//                    if (saida.loteProperty().getValue().equals(produtoSelecionado.tblLoteProperty().getValue())
//                            && saida.produtoProperty().getValue().idProperty().getValue().intValue()
//                            == produtoSelecionado.idProperty().getValue().intValue()) {
//                        getTvItensPedido().requestFocus();
//                        getTvItensPedido().getSelectionModel().select(i, getTmodelSaidaProduto().getColQtd());
//                    }
//                }
//            }
//        });
//
//        getTvItensPedido().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() != KeyCode.HELP)
//                return;
//            Produto produtoAdicional = new Produto(getTvItensPedido().getSelectionModel().getSelectedItem().produtoProperty().getValue());
//            getSaidaProdutoProdutoObservableList().add(new SaidaProdutoProduto(produtoAdicional, TipoCodigoCFOP.AMOSTRA, 1));
//        });
//
        setEventHandlerEntradaProduto(new EventHandler<KeyEvent>() {
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
                            limpaCampos(getPainelViewEntradaProduto());
                            break;
                        case F2:
//                            getEnumsTasksList().clear();
//                            getEnumsTasksList().add(EnumsTasks.SALVAR_SAIDA);
//
//                            if (ServiceMascara.getBigDecimalFromTextField(getLblLimiteDisponivel().getText(), 2)
//                                    .compareTo(ServiceMascara.getBigDecimalFromTextField(getLblTotalLiquido().getText(), 2)) >= 0) {
//                                boolean usarCredDeb = false;
//                                BigDecimal credDeb = BigDecimal.ZERO;
//                                if ((credDeb = ServiceMascara.getBigDecimalFromTextField(getLblLimiteUtilizado().getText(), 2)).compareTo(BigDecimal.ZERO) < 0) {
//                                    setAlertMensagem(new ServiceAlertMensagem());
//                                    getAlertMensagem().setCabecalho("Crédito disponível");
//                                    getAlertMensagem().setContentText(String.format("o cliente tem um crédito de R$ %s\ndeseja utilizar esse valor para abater no pedido?",
//                                            ServiceMascara.getMoeda((credDeb.multiply(new BigDecimal("-1."))), 2)));
//                                    getAlertMensagem().setStrIco("");
//                                    ButtonType btnResult;
//                                    if ((btnResult = getAlertMensagem().alertYesNoCancel().get()) == ButtonType.CANCEL)
//                                        return;
//                                    usarCredDeb = (btnResult == ButtonType.YES);
//                                } else if (credDeb.compareTo(BigDecimal.ZERO) > 0) {
//                                    setAlertMensagem(new ServiceAlertMensagem());
//                                    getAlertMensagem().setCabecalho("Débito detectado");
//                                    getAlertMensagem().setContentText(String.format("o cliente tem um dédito de R$ %s\ndeseja acrescentar esse valor no pedido atual?",
//                                            ServiceMascara.getMoeda((credDeb.multiply(new BigDecimal("-1."))), 2)));
//                                    getAlertMensagem().setStrIco("");
//                                    ButtonType btnResult;
//                                    if ((btnResult = getAlertMensagem().alertYesNoCancel().get()) == ButtonType.CANCEL)
//                                        return;
//                                    usarCredDeb = (btnResult == ButtonType.YES);
//                                } else {
//                                    credDeb = BigDecimal.ZERO;
//                                }
//
//                                if (new ServiceSegundoPlano().executaListaTarefas(newTaskSaidaProduto(), String.format("Salvando %s!", getNomeTab()))) {
//                                    if (usarCredDeb) {
//                                        try {
//                                            getContasAReceberDAO().transactionBegin();
//                                            baixaCredito(credDeb);
//                                            PagamentoModalidade tipBaixa = null;
//                                            if (credDeb.compareTo(BigDecimal.ZERO) < 0)
//                                                tipBaixa = PagamentoModalidade.CREDITO;
//                                            else if (credDeb.compareTo(BigDecimal.ZERO) > 0)
//                                                tipBaixa = PagamentoModalidade.DEBITO;
//                                            getContasAReceber().getRecebimentoList().add(addRecebimento(getContasAReceber(), tipBaixa, credDeb));
//                                            getContasAReceberDAO().transactionCommit();
//                                        } catch (Exception ex) {
//                                            getContasAReceberDAO().transactionRollback();
//                                            ex.printStackTrace();
//                                        }
//                                    }
//
//                                    new ViewRecebimento().openViewRecebimento(getContasAReceber());
//
//                                    if (getSaidaProdutoNfe() != null) {
//                                        nfeAddCobranca();
//                                        gerarDanfe();
//                                    }
//
//                                    atualizaTotaisCliente(getContasAReceber());
//                                    System.out.printf("001OiOiOiOiOiOiOiOi\n");
//
//                                    limpaCampos(getPainelViewSaidaProduto());
//                                    System.out.printf("002OiOiOiOiOiOiOiOi\n");
//                                }
//                            } else {
//                                setAlertMensagem(new ServiceAlertMensagem());
//                                getAlertMensagem().setCabecalho("Limite excedido");
//                                getAlertMensagem().setContentText("Cliente não possui limite para finalizar o pedido!");
//                                getAlertMensagem().setStrIco("");
//                                getAlertMensagem().alertOk();
//                            }
//                            System.out.printf("003OiOiOiOiOiOiOiOi\n");
                            break;
                        case F5:
                            getTxtNfeChave().requestFocus();
                            break;
                        case F6:
                            if (!getTpnCteDetalhe().isExpanded()) return;
                            getTxtCteChave().requestFocus();
                            break;
                        case F7:
                            getTxtPesquisaProduto().requestFocus();
                            break;
                        case F8:
                            getTvItensNfeDetalhe().requestFocus();
                            getTvItensNfeDetalhe().getSelectionModel().select(getEntradaProdutoProdutoObservableList().size() - 1,
                                    getTmodelEntradaProduto().getColQtd());
                            break;
                        case F9:
                            getTpnNfeDetalheFiscal().setExpanded(!getTpnNfeDetalheFiscal().isExpanded());
                            break;
                        case F10:
                            getTpnCteDetalhe().setExpanded(!getTpnCteDetalhe().isExpanded());
                            break;
                        case F11:
                            getTpnCteDetalheFiscal().setExpanded(!getTpnCteDetalheFiscal().isExpanded());
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
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().addEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerEntradaProduto());
                showStatusBar();
            } else {
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerEntradaProduto());
            }
        });

        new ServiceAutoCompleteComboBox(Empresa.class, getCboNfeFornecedor());

        new ServiceAutoCompleteComboBox(FiscalTributosSefazAm.class, getCboNfeFiscalTributo());

        new ServiceAutoCompleteComboBox(Empresa.class, getCboCteTransportadora());

        new ServiceAutoCompleteComboBox(FiscalFreteSituacaoTributaria.class, getCboCteSistuacaoTributaria());

        new ServiceAutoCompleteComboBox(FiscalTributosSefazAm.class, getCboCteFiscalTributo());

        getTxtNfeChave().setOnDragOver(event -> {
            if (getTxtNfeChave().isDisable()) return;
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles())
                if (Pattern.compile(".xml").matcher(dragboard.getFiles().get(0).toPath().toString()).find())
                    event.acceptTransferModes(TransferMode.ANY);
        });

        getTxtNfeChave().setOnDragDropped(event -> {
            if (getTxtNfeChave().isDisable()) return;
            Dragboard dragboard = event.getDragboard();
            addXmlNfe(dragboard.getFiles().get(0));
        });

        getTxtCteChave().setOnDragOver(event -> {
            if (getTxtCteChave().isDisable()) return;
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles())
                if (Pattern.compile(".xml").matcher(dragboard.getFiles().get(0).toPath().toString()).find())
                    event.acceptTransferModes(TransferMode.ANY);
        });

        getTxtCteChave().setOnDragDropped(event -> {
            if (getTxtCteChave().isDisable()) return;
            Dragboard dragboard = event.getDragboard();
            addXmlCte(dragboard.getFiles().get(0));
        });

        getTpnNfeDetalhe().textProperty().bind(Bindings.createStringBinding(() -> {
                    return String.format("Detalhe da nf-e%s",
                            getTxtNfeNumero().getText().length() == 0
                                    ? ""
                                    : String.format(": [%s]", getTxtNfeNumero().getText().trim()));
                }, getTxtNfeNumero().textProperty()
        ));

        getTpnCteDetalhe().textProperty().bind(Bindings.createStringBinding(() -> {
                    if (getTpnCteDetalhe().isExpanded()) {
                        return String.format("Detalhe frete do ct-e%s",
                                getTxtCteNumero().getText().length() == 0
                                        ? ""
                                        : String.format(": [%s]", getTxtCteNumero().getText().trim()));
                    } else {
                        return "Nf-e sem frete";
                    }
                }, getTxtCteNumero().textProperty()
        ));

        getLblNfeFiscalVlrTotal().textProperty().bind(Bindings.createStringBinding(() -> {
                    BigDecimal vlrTributo = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrTributo().getText(), 2);
                    BigDecimal vlrMulta = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrMulta().getText(), 2);
                    BigDecimal vlrJuros = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrJuros().getText(), 2);
                    BigDecimal vlrTaxa = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrTaxa().getText(), 2);

                    return ServiceMascara.getMoeda(vlrTributo.add(vlrMulta).add(vlrJuros).add(vlrTaxa), 2);
                }, getTxtNfeFiscalVlrTributo().textProperty(), getTxtNfeFiscalVlrMulta().textProperty(),
                getTxtNfeFiscalVlrJuros().textProperty(), getTxtNfeFiscalVlrTaxa().textProperty()
        ));
    }

    /**
     * Begin Tasks
     */

    private Task newTaskEntradaProduto() {
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
                                tempo.fim("inicio");

                                tempo.start("setTmodelProduto");
                                setTmodelProduto(new TmodelProduto(TModelTipo.PROD_COMPRA));
                                tempo.fim("setTmodelProduto");
                                tempo.start("getTmodelProduto().criaTabela()");
                                getTmodelProduto().criaTabela();
                                tempo.fim("getTmodelProduto().criaTabela()");

//                                setTmodelEntradaProduto(new TmodelEntradaProduto());
//                                getTmodelEntradaProduto().criaTabela();
                                break;

                            case TABELA_VINCULAR:
                                tempo.start("TABELA_VINCULAR");
                                getTmodelProduto().setLblRegistrosLocalizados(getLblRegistrosLocalizados());
                                getTmodelProduto().setTtvProduto(getTtvProduto());
                                getTmodelProduto().setTxtPesquisa(getTxtPesquisaProduto());
                                setProdutoFilteredList(getTmodelProduto().getProdutoFilteredList());
                                tempo.fim("TABELA_VINCULAR");
                                tempo.start("getTmodelProduto().escutaLista()");
                                getTmodelProduto().escutaLista();
                                tempo.fim("getTmodelProduto().escutaLista()");

//                                getTmodelEntradaProduto().setTvItensNfe(getTtvItensNfe());
//                                getTmodelEntradaProduto().setTxtPesquisaProduto(getTxtPesquisa());
//                                getTmodelEntradaProduto().setDtpDtSaida(getDtpDtSaida());
//                                getTmodelEntradaProduto().setDtpDtVencimento(getDtpDtVencimento());
////                                empresaProperty().setValue(getTmodelSaidaProduto().empresaProperty().getValue());
//                                getTmodelEntradaProduto().setFornecedor(empresaProperty().getValue());
//                                getTmodelEntradaProduto().setEntradaProduto(getSaidaProduto());
//                                getTmodelEntradaProduto().setEntradaProdutoProdutoObservableList(getSaidaProdutoProdutoObservableList());
////                                setSaidaProduto(getTmodelSaidaProduto().getSaidaProduto());
////                                setSaidaProdutoProdutoObservableList(getTmodelSaidaProduto().getSaidaProdutoProdutoObservableList());
//                                getTmodelEntradaProduto().escutaLista();
                                break;
                            case COMBOS_PREENCHER:
//                                ServiceCalculaTempo tempo = new ServiceCalculaTempo();
                                tempo.start("loadListaEmpresa");
                                loadListaEmpresas();
                                tempo.fim("loadListaEmpresa");

                                tempo.start("setCboNfeModelo");
                                getCboNfeModelo().setItems(
                                        Arrays.stream(NfeCteModelo.values()).collect(Collectors.toCollection(FXCollections::observableArrayList))
                                );
                                tempo.fim("setCboNfeModelo");
                                tempo.start("getCboCteModelo");
                                getCboCteModelo().setItems(
                                        Arrays.stream(NfeCteModelo.values()).collect(Collectors.toCollection(FXCollections::observableArrayList))
                                );
                                tempo.fim("getCboCteModelo");
                                tempo.start("getCboCteSistuacaoTributaria");
                                getCboCteSistuacaoTributaria().setItems(new FiscalFreteSituacaoTributariaDAO()
                                        .getAll(FiscalFreteSituacaoTributaria.class, null, "id").stream()
                                        .collect(Collectors.toCollection(FXCollections::observableArrayList))
                                );
                                tempo.fim("getCboCteSistuacaoTributaria");
                                tempo.start("FiscalTributosSefazAm");
                                ObservableList<FiscalTributosSefazAm> tributosSefazAmObservableList = new FiscalTributosSefazAmDAO()
                                        .getAll(FiscalTributosSefazAm.class, null, "id").stream()
                                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                                getCboNfeFiscalTributo().setItems(tributosSefazAmObservableList);
                                getCboCteFiscalTributo().setItems(tributosSefazAmObservableList);
                                tempo.fim("FiscalTributosSefazAm");
                                tempo.start("getCboCteTomadorServico");
                                getCboCteTomadorServico().setItems(
                                        Arrays.stream(CteTomadorServico.values()).collect(Collectors.toCollection(FXCollections::observableArrayList))
                                );
                                tempo.fim("getCboCteTomadorServico");
                                break;

                            case TABELA_PREENCHER:
                                tempo.start("getTmodelProduto().preencheTabela()");
                                getTmodelProduto().preencheTabela();
                                tempo.fim("getTmodelProduto().preencheTabela()");

//                                getTmodelEntradaProduto().preencheTabela();
                                break;

                            case SALVAR_SAIDA:
//                                if (guardarSaidaProduto()) {
//                                    if (salvarSaidaProduto()) {
//                                        getTmodelProduto().atualizarProdutos();
//                                    } else {
//                                        Thread.currentThread().interrupt();
//                                    }
//                                } else {
//                                    Thread.currentThread().interrupt();
//                                }
                                break;
                            case NFE_GERAR:
//                                gerarXmlNFe();
                                break;
                            case NFE_ASSINAR:
//                                if (xmlNFeProperty().getValue() == null)
//                                    Thread.currentThread().interrupt();
//                                assinarXmlNFe();
                                break;
                            case NFE_TRANSMITIR:
//                                if (xmlNFeAssinadoProperty().getValue() == null)
//                                    Thread.currentThread().interrupt();
//                                transmitirXmlNFe();
                                break;
                            case NFE_RETORNO:
//                                if (xmlNFeAutorizacaoProperty().getValue() == null)
//                                    Thread.currentThread().interrupt();
//                                retornoXmlNFe();
//                                if (xmlNFeRetAutorizacaoProperty().getValue() == null)
//                                    Thread.currentThread().interrupt();
//                                retornoProcNFe();
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
    }

    private void escutaTitledTab() {
        getTpnNfeDetalheFiscal().expandedProperty().addListener((ov, o, n) -> {
            int diff = (getTpnNfeDetalheFiscal().getHeight() == 0) ? 44 : (int) getTpnNfeDetalheFiscal().getHeight() - 23;
            if (!n) diff = (diff * -1);
            setHeightTpnNfeDetalhe(diff);
            getTpnNfeDetalheFiscal().setText(n ? "Informações de imposto" : "Nf-e sem imposto");
        });

        getTpnCteDetalheFiscal().expandedProperty().addListener((ov, o, n) -> {
            int diff = (getTpnCteDetalheFiscal().getHeight() == 0) ? 44 : (int) getTpnCteDetalheFiscal().getHeight() - 23;
            if (!n) diff = (diff * -1);
            setHeightTpnCteDetalhe(diff);
            getTpnCteDetalheFiscal().setText(n ? "Informações de imposto no frete" : "Frete sem imposto");
        });

        getTpnCteDetalhe().expandedProperty().addListener((ov, o, n) -> {
            int diff = (getTpnCteDetalhe().getHeight() == 0) ? 111 : (int) getTpnCteDetalhe().getHeight() - 23;
            if (!n) diff = (diff * -1);
            setHeightTpnEntradaNfe(diff);
        });
    }

    private void setHeightTpnNfeDetalhe(int diff) {
        getTpnNfeDetalhe().setPrefHeight(getTpnNfeDetalhe().getPrefHeight() + diff);
        setLayoutTpnCteDetalhe(diff);
    }

    private void setHeightTpnCteDetalhe(int diff) {
        getTpnCteDetalhe().setPrefHeight(getTpnCteDetalhe().getPrefHeight() + diff);
        setHeightTpnEntradaNfe(diff);
    }

    private void setLayoutTpnCteDetalhe(int diff) {
        getTpnCteDetalhe().setLayoutY(getTpnCteDetalhe().getLayoutY() + diff);
        setHeightTpnEntradaNfe(diff);
    }

    private void setHeightTpnEntradaNfe(int diff) {
        getTpnEntradaNfe().setPrefHeight(getTpnEntradaNfe().getPrefHeight() + diff);
        ajustaTpnItensNota(diff);
    }

    private void ajustaTpnItensNota(int diff) {
        getTpnItensTotaisDetalhe().setLayoutY(getTpnItensTotaisDetalhe().getLayoutY() + diff);
        getTpnItensTotaisDetalhe().setPrefHeight(getTpnItensTotaisDetalhe().getPrefHeight() + (diff * -1));
        getvBoxItensNfeDetalhe().setPrefHeight(getvBoxItensNfeDetalhe().getPrefHeight() + (diff * -1));
    }

    private void showStatusBar() {
        try {
            if (getEntradaProdutoProdutoObservableList().size() <= 0)
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

        getCboNfeLojaDestino().setItems(
                empresaObservableList.stream()
                        .filter(Empresa::isLoja)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

        getCboNfeFornecedor().setItems(
                empresaObservableList.stream()
                        .filter(Empresa::isFornecedor)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );

        getCboCteTransportadora().setItems(
                empresaObservableList.stream()
                        .filter(Empresa::isTransportadora)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
    }

    private void addXmlNfe(File file) {
        if (!file.getName().toLowerCase().contains("nfe")) return;
        TNfeProc nfeProc = null;
        try {
            nfeProc = ServiceUtilXml.xmlToObject(ServiceUtilXml.FileXml4String(new FileReader(file)), TNfeProc.class);
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
        if (nfeProc == null) return;
        getTxtNfeChave().setText(nfeProc.getNFe().getInfNFe().getId().replaceAll("\\D", ""));
        getTxtNfeNumero().setText(nfeProc.getNFe().getInfNFe().getIde().getNNF());
        getTxtNfeSerie().setText(nfeProc.getNFe().getInfNFe().getIde().getSerie());

        getTxtNfeFiscalVlrNFe().setText(nfeProc.getNFe().getInfNFe().getTotal().getICMSTot().getVNF());

        TNfeProc finalNfeProc = nfeProc;
        getCboNfeModelo().getSelectionModel().select(
                getCboNfeModelo().getItems().stream()
                        .filter(modeloNfeCte -> modeloNfeCte.getDescricao().equals(finalNfeProc.getNFe().getInfNFe().getIde().getMod()))
                        .findFirst().orElse(null)
        );
        getCboNfeLojaDestino().getSelectionModel().select(
                getCboNfeLojaDestino().getItems().stream()
                        .filter(loja -> loja.getCnpj().equals(finalNfeProc.getNFe().getInfNFe().getDest().getCNPJ()))
                        .findFirst().orElse(null)
        );
        getCboNfeFornecedor().getSelectionModel().select(
                getCboNfeFornecedor().getItems().stream()
                        .filter(fornecedor -> fornecedor.getCnpj().equals(finalNfeProc.getNFe().getInfNFe().getEmit().getCNPJ()))
                        .findFirst().orElse(null)
        );
        getDtpNfeEmissao().setValue(LocalDate.parse(nfeProc.getNFe().getInfNFe().getIde().getDhEmi(), DTF_NFE_TO_LOCAL_DATE));
        getDtpNfeEntrada().setValue(LocalDate.now());
    }

    private void addXmlCte(File file) {
        if (!file.getName().toLowerCase().contains("cte")) return;
        CteProc cteProc = null;
        try {
            cteProc = ServiceUtilXml.xmlToObject(ServiceUtilXml.FileXml4String(new FileReader(file)), CteProc.class);
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
        if (cteProc == null) return;

        getTxtCteChave().setText(cteProc.getCTe().getInfCte().getId().replaceAll("\\D", ""));
        getTxtCteNumero().setText(cteProc.getCTe().getInfCte().getIde().getNCT());
        getTxtCteSerie().setText(cteProc.getCTe().getInfCte().getIde().getSerie());

        getTxtCteVlrCte().setText(cteProc.getCTe().getInfCte().getVPrest().getVTPrest());
        getTxtCteVlrImposto().setText(cteProc.getCTe().getInfCte().getImp().getICMS().getICMS00().getVICMS());

        for (br.inf.portalfiscal.xsd.cte.procCTe.TCTe.InfCte.InfCTeNorm.InfCarga.InfQ infQ : cteProc.getCTe().getInfCte().getInfCTeNorm().getInfCarga().getInfQ())
            switch (infQ.getTpMed().toLowerCase()) {
                case "volume":
                case "volumes":
                    getTxtCteQtdVolume().setText(infQ.getQCarga());
                    break;
                case "peso bruto":
                    getTxtCtePesoBruto().setText(BigDecimal.valueOf(Double.parseDouble(infQ.getQCarga())).setScale(2).toString());
            }

        double tmpTaxas = 0.;
        for (br.inf.portalfiscal.xsd.cte.procCTe.TCTe.InfCte.VPrest.Comp comp : cteProc.getCTe().getInfCte().getVPrest().getComp())
            if (comp.getXNome().toLowerCase().contains("peso"))
                getTxtCteVlrBruto().setText(comp.getVComp());
            else if (comp.getXNome().toLowerCase().contains("coleta"))
                getTxtCteVlrColeta().setText(comp.getVComp());
            else
                tmpTaxas += Double.parseDouble(comp.getVComp());
        getTxtCteVlrTaxa().setText(BigDecimal.valueOf(tmpTaxas).setScale(2).toString());

        getLblCteVlrLiquido().setText(cteProc.getCTe().getInfCte().getVPrest().getVTPrest());

        getLblCteFiscalVlrCte().setText(cteProc.getCTe().getInfCte().getVPrest().getVTPrest());

        CteProc finalCteProc = cteProc;
        getCboCteTomadorServico().getSelectionModel().select(
                getCboCteTomadorServico().getItems().stream()
                        .filter(tomadorServico -> tomadorServico.getCod() == Integer.valueOf(finalCteProc.getCTe().getInfCte().getIde().getToma3().getToma()))
                        .findFirst().orElse(null)
        );
        getCboCteModelo().getSelectionModel().select(
                getCboCteModelo().getItems().stream()
                        .filter(modeloNfeCte -> modeloNfeCte.getDescricao().equals(finalCteProc.getCTe().getInfCte().getIde().getMod()))
                        .findFirst().orElse(null)
        );
        getCboCteTransportadora().getSelectionModel().select(
                getCboCteTransportadora().getItems().stream()
                        .filter(transportadora -> transportadora.getCnpj().equals(finalCteProc.getCTe().getInfCte().getEmit().getCNPJ()))
                        .findFirst().orElse(null)
        );
        getCboCteSistuacaoTributaria().getSelectionModel().select(
                getCboCteSistuacaoTributaria().getItems().stream()
                        .filter(situacaoTributaria -> situacaoTributaria.getId() == Integer.valueOf(finalCteProc.getCTe().getInfCte().getImp().getICMS().getICMS00().getCST()))
                        .findFirst().orElse(null)
        );
        getDtpCteEmissao().setValue(LocalDate.parse(cteProc.getCTe().getInfCte().getIde().getDhEmi(), DTF_NFE_TO_LOCAL_DATE));

        if (getTxtNfeChave().getText().equals("")) {
            File filetmp = null;
            for (TCTe.InfCte.InfCTeNorm.InfDoc.InfNFe infNFe : cteProc.getCTe().getInfCte().getInfCTeNorm().getInfDoc().getInfNFe())
                if ((filetmp = new File(file.getParent() + "/" + infNFe.getChave() + "-nfe.xml")).exists())
                    addXmlNfe(filetmp);
        }
    }

    /**
     * END voids
     */


    /**
     * Begin returns
     */

    /**
     * END returns
     */

    /**
     * Begin Getters e Setters
     */

    public AnchorPane getPainelViewEntradaProduto() {
        return painelViewEntradaProduto;
    }

    public void setPainelViewEntradaProduto(AnchorPane painelViewEntradaProduto) {
        this.painelViewEntradaProduto = painelViewEntradaProduto;
    }

    public TitledPane getTpnEntradaNfe() {
        return tpnEntradaNfe;
    }

    public void setTpnEntradaNfe(TitledPane tpnEntradaNfe) {
        this.tpnEntradaNfe = tpnEntradaNfe;
    }

    public TitledPane getTpnNfeDetalhe() {
        return tpnNfeDetalhe;
    }

    public void setTpnNfeDetalhe(TitledPane tpnNfeDetalhe) {
        this.tpnNfeDetalhe = tpnNfeDetalhe;
    }

    public ComboBox<Empresa> getCboNfeLojaDestino() {
        return cboNfeLojaDestino;
    }

    public void setCboNfeLojaDestino(ComboBox<Empresa> cboNfeLojaDestino) {
        this.cboNfeLojaDestino = cboNfeLojaDestino;
    }

    public TextField getTxtNfeChave() {
        return txtNfeChave;
    }

    public void setTxtNfeChave(TextField txtNfeChave) {
        this.txtNfeChave = txtNfeChave;
    }

    public TextField getTxtNfeNumero() {
        return txtNfeNumero;
    }

    public void setTxtNfeNumero(TextField txtNfeNumero) {
        this.txtNfeNumero = txtNfeNumero;
    }

    public TextField getTxtNfeSerie() {
        return txtNfeSerie;
    }

    public void setTxtNfeSerie(TextField txtNfeSerie) {
        this.txtNfeSerie = txtNfeSerie;
    }

    public ComboBox<NfeCteModelo> getCboNfeModelo() {
        return cboNfeModelo;
    }

    public void setCboNfeModelo(ComboBox<NfeCteModelo> cboNfeModelo) {
        this.cboNfeModelo = cboNfeModelo;
    }

    public ComboBox<Empresa> getCboNfeFornecedor() {
        return cboNfeFornecedor;
    }

    public void setCboNfeFornecedor(ComboBox<Empresa> cboNfeFornecedor) {
        this.cboNfeFornecedor = cboNfeFornecedor;
    }

    public DatePicker getDtpNfeEmissao() {
        return dtpNfeEmissao;
    }

    public void setDtpNfeEmissao(DatePicker dtpNfeEmissao) {
        this.dtpNfeEmissao = dtpNfeEmissao;
    }

    public DatePicker getDtpNfeEntrada() {
        return dtpNfeEntrada;
    }

    public void setDtpNfeEntrada(DatePicker dtpNfeEntrada) {
        this.dtpNfeEntrada = dtpNfeEntrada;
    }

    public TitledPane getTpnNfeDetalheFiscal() {
        return tpnNfeDetalheFiscal;
    }

    public void setTpnNfeDetalheFiscal(TitledPane tpnNfeDetalheFiscal) {
        this.tpnNfeDetalheFiscal = tpnNfeDetalheFiscal;
    }

    public TextField getTxtNfeFiscalControle() {
        return txtNfeFiscalControle;
    }

    public void setTxtNfeFiscalControle(TextField txtNfeFiscalControle) {
        this.txtNfeFiscalControle = txtNfeFiscalControle;
    }

    public TextField getTxtNfeFiscalOrigem() {
        return txtNfeFiscalOrigem;
    }

    public void setTxtNfeFiscalOrigem(TextField txtNfeFiscalOrigem) {
        this.txtNfeFiscalOrigem = txtNfeFiscalOrigem;
    }

    public ComboBox<FiscalTributosSefazAm> getCboNfeFiscalTributo() {
        return cboNfeFiscalTributo;
    }

    public void setCboNfeFiscalTributo(ComboBox<FiscalTributosSefazAm> cboNfeFiscalTributo) {
        this.cboNfeFiscalTributo = cboNfeFiscalTributo;
    }

    public TextField getTxtNfeFiscalVlrNFe() {
        return txtNfeFiscalVlrNFe;
    }

    public void setTxtNfeFiscalVlrNFe(TextField txtNfeFiscalVlrNFe) {
        this.txtNfeFiscalVlrNFe = txtNfeFiscalVlrNFe;
    }

    public TextField getTxtNfeFiscalVlrTributo() {
        return txtNfeFiscalVlrTributo;
    }

    public void setTxtNfeFiscalVlrTributo(TextField txtNfeFiscalVlrTributo) {
        this.txtNfeFiscalVlrTributo = txtNfeFiscalVlrTributo;
    }

    public TextField getTxtNfeFiscalVlrMulta() {
        return txtNfeFiscalVlrMulta;
    }

    public void setTxtNfeFiscalVlrMulta(TextField txtNfeFiscalVlrMulta) {
        this.txtNfeFiscalVlrMulta = txtNfeFiscalVlrMulta;
    }

    public TextField getTxtNfeFiscalVlrJuros() {
        return txtNfeFiscalVlrJuros;
    }

    public void setTxtNfeFiscalVlrJuros(TextField txtNfeFiscalVlrJuros) {
        this.txtNfeFiscalVlrJuros = txtNfeFiscalVlrJuros;
    }

    public TextField getTxtNfeFiscalVlrTaxa() {
        return txtNfeFiscalVlrTaxa;
    }

    public void setTxtNfeFiscalVlrTaxa(TextField txtNfeFiscalVlrTaxa) {
        this.txtNfeFiscalVlrTaxa = txtNfeFiscalVlrTaxa;
    }

    public Label getLblNfeFiscalVlrTotal() {
        return lblNfeFiscalVlrTotal;
    }

    public void setLblNfeFiscalVlrTotal(Label lblNfeFiscalVlrTotal) {
        this.lblNfeFiscalVlrTotal = lblNfeFiscalVlrTotal;
    }

    public Label getLblNfeFiscalVlrPercentual() {
        return lblNfeFiscalVlrPercentual;
    }

    public void setLblNfeFiscalVlrPercentual(Label lblNfeFiscalVlrPercentual) {
        this.lblNfeFiscalVlrPercentual = lblNfeFiscalVlrPercentual;
    }

    public TitledPane getTpnCteDetalhe() {
        return tpnCteDetalhe;
    }

    public void setTpnCteDetalhe(TitledPane tpnCteDetalhe) {
        this.tpnCteDetalhe = tpnCteDetalhe;
    }

    public TextField getTxtCteChave() {
        return txtCteChave;
    }

    public void setTxtCteChave(TextField txtCteChave) {
        this.txtCteChave = txtCteChave;
    }

    public ComboBox<CteTomadorServico> getCboCteTomadorServico() {
        return cboCteTomadorServico;
    }

    public void setCboCteTomadorServico(ComboBox<CteTomadorServico> cboCteTomadorServico) {
        this.cboCteTomadorServico = cboCteTomadorServico;
    }

    public TextField getTxtCteNumero() {
        return txtCteNumero;
    }

    public void setTxtCteNumero(TextField txtCteNumero) {
        this.txtCteNumero = txtCteNumero;
    }

    public TextField getTxtCteSerie() {
        return txtCteSerie;
    }

    public void setTxtCteSerie(TextField txtCteSerie) {
        this.txtCteSerie = txtCteSerie;
    }

    public ComboBox<NfeCteModelo> getCboCteModelo() {
        return cboCteModelo;
    }

    public void setCboCteModelo(ComboBox<NfeCteModelo> cboCteModelo) {
        this.cboCteModelo = cboCteModelo;
    }

    public DatePicker getDtpCteEmissao() {
        return dtpCteEmissao;
    }

    public void setDtpCteEmissao(DatePicker dtpCteEmissao) {
        this.dtpCteEmissao = dtpCteEmissao;
    }

    public ComboBox<Empresa> getCboCteTransportadora() {
        return cboCteTransportadora;
    }

    public void setCboCteTransportadora(ComboBox<Empresa> cboCteTransportadora) {
        this.cboCteTransportadora = cboCteTransportadora;
    }

    public ComboBox<FiscalFreteSituacaoTributaria> getCboCteSistuacaoTributaria() {
        return cboCteSistuacaoTributaria;
    }

    public void setCboCteSistuacaoTributaria(ComboBox<FiscalFreteSituacaoTributaria> cboCteSistuacaoTributaria) {
        this.cboCteSistuacaoTributaria = cboCteSistuacaoTributaria;
    }

    public TextField getTxtCteVlrCte() {
        return txtCteVlrCte;
    }

    public void setTxtCteVlrCte(TextField txtCteVlrCte) {
        this.txtCteVlrCte = txtCteVlrCte;
    }

    public TextField getTxtCteQtdVolume() {
        return txtCteQtdVolume;
    }

    public void setTxtCteQtdVolume(TextField txtCteQtdVolume) {
        this.txtCteQtdVolume = txtCteQtdVolume;
    }

    public TextField getTxtCtePesoBruto() {
        return txtCtePesoBruto;
    }

    public void setTxtCtePesoBruto(TextField txtCtePesoBruto) {
        this.txtCtePesoBruto = txtCtePesoBruto;
    }

    public TextField getTxtCteVlrBruto() {
        return txtCteVlrBruto;
    }

    public void setTxtCteVlrBruto(TextField txtCteVlrBruto) {
        this.txtCteVlrBruto = txtCteVlrBruto;
    }

    public TextField getTxtCteVlrTaxa() {
        return txtCteVlrTaxa;
    }

    public void setTxtCteVlrTaxa(TextField txtCteVlrTaxa) {
        this.txtCteVlrTaxa = txtCteVlrTaxa;
    }

    public TextField getTxtCteVlrColeta() {
        return txtCteVlrColeta;
    }

    public void setTxtCteVlrColeta(TextField txtCteVlrColeta) {
        this.txtCteVlrColeta = txtCteVlrColeta;
    }

    public TextField getTxtCteVlrImposto() {
        return txtCteVlrImposto;
    }

    public void setTxtCteVlrImposto(TextField txtCteVlrImposto) {
        this.txtCteVlrImposto = txtCteVlrImposto;
    }

    public Label getLblCteVlrLiquido() {
        return lblCteVlrLiquido;
    }

    public void setLblCteVlrLiquido(Label lblCteVlrLiquido) {
        this.lblCteVlrLiquido = lblCteVlrLiquido;
    }

    public TitledPane getTpnCteDetalheFiscal() {
        return tpnCteDetalheFiscal;
    }

    public void setTpnCteDetalheFiscal(TitledPane tpnCteDetalheFiscal) {
        this.tpnCteDetalheFiscal = tpnCteDetalheFiscal;
    }

    public TextField getTxtCteFiscalControle() {
        return txtCteFiscalControle;
    }

    public void setTxtCteFiscalControle(TextField txtCteFiscalControle) {
        this.txtCteFiscalControle = txtCteFiscalControle;
    }

    public TextField getTxtCteFiscalOrigem() {
        return txtCteFiscalOrigem;
    }

    public void setTxtCteFiscalOrigem(TextField txtCteFiscalOrigem) {
        this.txtCteFiscalOrigem = txtCteFiscalOrigem;
    }

    public ComboBox<FiscalTributosSefazAm> getCboCteFiscalTributo() {
        return cboCteFiscalTributo;
    }

    public void setCboCteFiscalTributo(ComboBox<FiscalTributosSefazAm> cboCteFiscalTributo) {
        this.cboCteFiscalTributo = cboCteFiscalTributo;
    }

    public Label getLblCteFiscalVlrCte() {
        return lblCteFiscalVlrCte;
    }

    public void setLblCteFiscalVlrCte(Label lblCteFiscalVlrCte) {
        this.lblCteFiscalVlrCte = lblCteFiscalVlrCte;
    }

    public TextField getTxtCteFiscalVlrTributo() {
        return txtCteFiscalVlrTributo;
    }

    public void setTxtCteFiscalVlrTributo(TextField txtCteFiscalVlrTributo) {
        this.txtCteFiscalVlrTributo = txtCteFiscalVlrTributo;
    }

    public TextField getTxtCteFiscalVlrMulta() {
        return txtCteFiscalVlrMulta;
    }

    public void setTxtCteFiscalVlrMulta(TextField txtCteFiscalVlrMulta) {
        this.txtCteFiscalVlrMulta = txtCteFiscalVlrMulta;
    }

    public TextField getTxtCteFiscalVlrJuros() {
        return txtCteFiscalVlrJuros;
    }

    public void setTxtCteFiscalVlrJuros(TextField txtCteFiscalVlrJuros) {
        this.txtCteFiscalVlrJuros = txtCteFiscalVlrJuros;
    }

    public TextField getTxtCteFiscalVlrTaxa() {
        return txtCteFiscalVlrTaxa;
    }

    public void setTxtCteFiscalVlrTaxa(TextField txtCteFiscalVlrTaxa) {
        this.txtCteFiscalVlrTaxa = txtCteFiscalVlrTaxa;
    }

    public Label getLblCteFiscalVlrTotal() {
        return lblCteFiscalVlrTotal;
    }

    public void setLblCteFiscalVlrTotal(Label lblCteFiscalVlrTotal) {
        this.lblCteFiscalVlrTotal = lblCteFiscalVlrTotal;
    }

    public Label getLblCteFiscalVlrPercentual() {
        return lblCteFiscalVlrPercentual;
    }

    public void setLblCteFiscalVlrPercentual(Label lblCteFiscalVlrPercentual) {
        this.lblCteFiscalVlrPercentual = lblCteFiscalVlrPercentual;
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

    public TreeTableView<Produto> getTtvProduto() {
        return ttvProduto;
    }

    public void setTtvProduto(TreeTableView<Produto> ttvProduto) {
        this.ttvProduto = ttvProduto;
    }

    public VBox getvBoxItensNfeDetalhe() {
        return vBoxItensNfeDetalhe;
    }

    public void setvBoxItensNfeDetalhe(VBox vBoxItensNfeDetalhe) {
        this.vBoxItensNfeDetalhe = vBoxItensNfeDetalhe;
    }

    public TableView<EntradaProdutoProduto> getTvItensNfeDetalhe() {
        return tvItensNfeDetalhe;
    }

    public void setTvItensNfeDetalhe(TableView<EntradaProdutoProduto> tvItensNfeDetalhe) {
        this.tvItensNfeDetalhe = tvItensNfeDetalhe;
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

    public VBox getvBoxTotalImposto() {
        return vBoxTotalImposto;
    }

    public void setvBoxTotalImposto(VBox vBoxTotalImposto) {
        this.vBoxTotalImposto = vBoxTotalImposto;
    }

    public Label getLblTotalImposto() {
        return lblTotalImposto;
    }

    public void setLblTotalImposto(Label lblTotalImposto) {
        this.lblTotalImposto = lblTotalImposto;
    }

    public VBox getvBoxTotalFrete() {
        return vBoxTotalFrete;
    }

    public void setvBoxTotalFrete(VBox vBoxTotalFrete) {
        this.vBoxTotalFrete = vBoxTotalFrete;
    }

    public Label getLblTotalFrete() {
        return lblTotalFrete;
    }

    public void setLblTotalFrete(Label lblTotalFrete) {
        this.lblTotalFrete = lblTotalFrete;
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

    public StatusBarEntradaProduto getStatusBar() {
        return statusBar.get();
    }

    public ObjectProperty<StatusBarEntradaProduto> statusBarProperty() {
        return statusBar;
    }

    public void setStatusBar(StatusBarEntradaProduto statusBar) {
        this.statusBar.set(statusBar);
    }

    public EventHandler getEventHandlerEntradaProduto() {
        return eventHandlerEntradaProduto;
    }

    public void setEventHandlerEntradaProduto(EventHandler eventHandlerEntradaProduto) {
        this.eventHandlerEntradaProduto = eventHandlerEntradaProduto;
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

    public TmodelEntradaProduto getTmodelEntradaProduto() {
        return tmodelEntradaProduto;
    }

    public void setTmodelEntradaProduto(TmodelEntradaProduto tmodelEntradaProduto) {
        this.tmodelEntradaProduto = tmodelEntradaProduto;
    }

    public ObservableList<EntradaProdutoProduto> getEntradaProdutoProdutoObservableList() {
        return entradaProdutoProdutoObservableList;
    }

    public void setEntradaProdutoProdutoObservableList(ObservableList<EntradaProdutoProduto> entradaProdutoProdutoObservableList) {
        this.entradaProdutoProdutoObservableList = entradaProdutoProdutoObservableList;
    }

    public String getNfeFiscalVlrTotal() {
        return nfeFiscalVlrTotal.get();
    }

    public StringProperty nfeFiscalVlrTotalProperty() {
        return nfeFiscalVlrTotal;
    }

    public void setNfeFiscalVlrTotal(String nfeFiscalVlrTotal) {
        this.nfeFiscalVlrTotal.set(nfeFiscalVlrTotal);
    }

    /**
     * END Getters e Setters
     */


}