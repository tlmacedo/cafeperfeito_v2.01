package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: thiagomacedo
 * Date: 2019-02-22
 * Time: 12:33
 */

public class ControllerEntradaProduto implements Initializable, ModeloCafePerfeito {


    @Override
    public void fieldsFormat() throws Exception {

    }

    @Override
    public void fechar() {

    }

    @Override
    public void criarObjetos() throws Exception {

    }

    @Override
    public void preencherObjetos() throws Exception {

    }

    @Override
    public void fatorarObjetos() throws Exception {

    }

    @Override
    public void escutarTecla() throws Exception {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
//
//    public AnchorPane painelViewEntradaProduto;
//    public TitledPane tpnEntradaNfe;
//
//    public TitledPane tpnNfeDetalhe;
//    public ComboBox cboNfeLojaDestino;
//    public TextField txtNfeChave;
//    public TextField txtNfeNumero;
//    public TextField txtNfeSerie;
//    public ComboBox cboNfeModelo;
//    public ComboBox cboNfeFornecedor;
//    public DatePicker dtpNfeEmissao;
//    public DatePicker dtpNfeEntrada;
//    public TitledPane tpnNfeFiscal;
//    public TextField txtNfeFiscalControle;
//    public TextField txtNfeFiscalOrigem;
//    public ComboBox cboNfeFiscalTributo;
//    public TextField txtNfeFiscalVlrNFe;
//    public TextField txtNfeFiscalVlrTributo;
//    public TextField txtNfeFiscalVlrMulta;
//    public TextField txtNfeFiscalVlrJuros;
//    public TextField txtNfeFiscalVlrTaxa;
//    public TextField txtNfeFiscalVlrTotal;
//    public TextField txtNfeFiscalVlrPercentual;
//
//    public TitledPane tpnCteDetalhe;
//    public TextField txtCteChave;
//    public ComboBox cboCteTomadorServico;
//    public TextField txtCteNumero;
//    public TextField txtCteSerie;
//    public ComboBox cboCteModelo;
//    public DatePicker dtpCteEmissao;
//    public ComboBox cboCteTransportadora;
//    public ComboBox cboCteSistuacaoTributaria;
//    public TextField txtCteVlrCte;
//    public TextField txtCteQtdVolume;
//    public TextField txtCtePesoBruto;
//    public TextField txtCteVlrBruto;
//    public TextField txtCteVlrTaxa;
//    public TextField txtCteVlrColeta;
//    public TextField txtCteVlrImposto;
//    public TextField txtCteVlrLiquido;
//
//    public TitledPane tpnCteFiscal;
//    public TextField txtCteFiscalControle;
//    public TextField txtCteFiscalOrigem;
//    public ComboBox cboCteFiscalTributo;
//    public TextField txtCteFiscalVlrCte;
//    public TextField txtCteFiscalVlrTributo;
//    public TextField txtCteFiscalVlrMulta;
//    public TextField txtCteFiscalVlrJuros;
//    public TextField txtCteFiscalVlrTaxa;
//    public TextField txtCteFiscalVlrTotal;
//    public TextField txtCteFiscalVlrPercentual;
//
//    public TitledPane tpnItensTotaisNfe;
//    public TitledPane tpnCadastroProduto;
//    public TextField txtPesquisaProduto;
//    public Label lblStatus;
//    public Label lblRegistrosLocalizados;
//    public TreeTableView ttvProduto;
//
//    public TableView ttvItensNfe;
//    public Label lblQtdItem;
//    public Label lblQtdTotal;
//    public Label lblQtdVolume;
//    public Label lblTotalBruto;
//    public Label lblTotalImposto;
//    public Label lblTotalFrete;
//    public Label lblTotalDesconto;
//    public Label lblTotalLiquido;
//
//
//    private boolean tabCarregada = false;
//    private List<EnumsTasks> enumsTasksList = new ArrayList<>();
//
//    private String nomeTab = ViewEntradaProduto.getTitulo();
//    private String nomeController = "entradaProduto";
//    private ObjectProperty<StatusBarEntradaProduto> statusBar = new SimpleObjectProperty<>();
//    private EventHandler eventHandlerEntradaProduto;
//    private ServiceAlertMensagem alertMensagem;
//
//    private TmodelProduto tmodelProduto;
//    private FilteredList<Produto> produtoFilteredList;
//
//    private TmodelSaidaProduto tmodelSaidaProduto;
//    private SaidaProduto saidaProduto = new SaidaProduto();
//    private SaidaProdutoDAO saidaProdutoDAO = new SaidaProdutoDAO();
//    private ContasAReceber contasAReceber;
//    private ContasAReceberDAO contasAReceberDAO = new ContasAReceberDAO();
//    private Recebimento recebimento;
//    private RecebimentoDAO recebimentoDAO = new RecebimentoDAO();
//    private SaidaProdutoNfe saidaProdutoNfe;
//    private SaidaProdutoNfeDAO saidaProdutoNfeDAO = new SaidaProdutoNfeDAO();
//
//
//    private ObservableList<ContasAReceber> contasAReceberObservableList =
//            FXCollections.observableArrayList(getContasAReceberDAO().getAll(ContasAReceber.class, null, "dtCadastro DESC"));
//
//    private ObservableList<SaidaProdutoProduto> saidaProdutoProdutoObservableList = FXCollections.observableArrayList();
//
//    private NotaFiscal notaFiscal;
//    private ObjectProperty<LoadCertificadoA3> loadCertificadoA3 = new SimpleObjectProperty<>();
//    private IntegerProperty nfeLastNumber = new SimpleIntegerProperty(0);
//    private StringProperty informacaoNFE = new SimpleStringProperty();
//    private ObjectProperty<BigDecimal> valorParcela = new SimpleObjectProperty<>(BigDecimal.ZERO);
//    private ObjectProperty<NFev400> nFev400 = new SimpleObjectProperty<>();
//    private ObjectProperty<TEnviNFe> tEnviNFe = new SimpleObjectProperty<>();
//    private StringProperty xmlNFe = new SimpleStringProperty();
//    private StringProperty xmlNFeAssinado = new SimpleStringProperty();
//    private StringProperty xmlNFeAutorizacao = new SimpleStringProperty();
//    private StringProperty xmlNFeRetAutorizacao = new SimpleStringProperty();
//    private StringProperty xmlNFeProc = new SimpleStringProperty();
//
//    private ObservableList<Empresa> empresaObservableList = FXCollections.observableArrayList(
//            new EmpresaDAO().getAll(Empresa.class, null, "razao, fantasia"));
//    private ObjectProperty<Empresa> empresa = new SimpleObjectProperty<>();
//    private ObjectProperty<List<Endereco>> enderecoList = new SimpleObjectProperty<>();
//    private ObjectProperty<Endereco> endereco = new SimpleObjectProperty<>();
//    private ObjectProperty<List<Telefone>> telefoneList = new SimpleObjectProperty<>();
//
//
////
////    boolean tabCarregada = false;
////
////
////    private EventHandler eventHandlerEntradaProduto;
////    private ObjectProperty<StatusBarEntradaProduto> statusBar = new SimpleObjectProperty<>();
////
////    private String nomeController = "entradaProduto";
////    private String nomeTab = "";
////    private ServiceAlertMensagem alertMensagem;
////    private List<Pair> listaTarefa = new ArrayList<>();
////
////    private TmodelProduto tmodelProduto;
////    private TablePosition tp;
////    private TabModelEntradaProdutoProduto modelEntradaProdutoProduto;
////    private EntradaProduto entradaProduto = new EntradaProduto();
////    private ObservableList<EntradaProduto> entradaProdutoObservableList =
////            FXCollections.observableArrayList(new EntradaProdutoDAO().getAll(EntradaProduto.class, null, null, null, null));
////    private ObservableList<Produto> produtoObservableList = FXCollections.observableArrayList(new ProdutoDAO().getAll(Produto.class, "descricao", null, null, null));
////    private FilteredList<Produto> produtoFilteredList = new FilteredList<>(produtoObservableList);
////    private ObservableList<EntradaProdutoProduto> entradaProdutoProdutoObservableList = FXCollections.observableArrayList();
////    private EntradaProdutoDAO entradaProdutoDAO = new EntradaProdutoDAO();
////
////    private EntradaProdutoProduto entradaProdutoProduto = new EntradaProdutoProduto();
////    private IntegerProperty numItens = new SimpleIntegerProperty();
////    private IntegerProperty qtdItens = new SimpleIntegerProperty();
////    private IntegerProperty qtdVolume = new SimpleIntegerProperty();
////    private ObjectProperty<BigDecimal> totalBruto = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO.setScale(2));
////    private ObjectProperty<BigDecimal> totalFrete = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO.setScale(2));
////    private ObjectProperty<BigDecimal> totalImpostoEntrada = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO.setScale(2));
////    private ObjectProperty<BigDecimal> totalImpostoItens = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO.setScale(2));
////    private ObjectProperty<BigDecimal> totalImposto = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO.setScale(2));
////    private ObjectProperty<BigDecimal> totalDesconto = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO.setScale(2));
////    private ObjectProperty<BigDecimal> totalLiquido = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO.setScale(2));
////
////    private ObjectProperty<BigDecimal> nfeVlr = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO.setScale(2));
////    private ObjectProperty<BigDecimal> nfeVlrTributo = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO.setScale(2));
////    private ObjectProperty<BigDecimal> nfeVlrMulta = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO.setScale(2));
////    private ObjectProperty<BigDecimal> nfeVlrJuros = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO.setScale(2));
////    private ObjectProperty<BigDecimal> nfeVlrTaxa = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO.setScale(2));
////    private ObjectProperty<BigDecimal> nfeVlrTotal = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO.setScale(2));
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        try {
//            criarObjetos();
//            preencherObjetos();
//            fatorarObjetos();
//            if (!isTabCarregada()) {
//                Platform.runLater(() -> fechar());
//                return;
//            }
//            escutarTecla();
//            fieldsFormat();
//            Platform.runLater(() -> limparCampos());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    @Override
//    public void fieldsFormat() throws Exception {
//        ServiceCampoPersonalizado.fieldTextFormat(getPainelViewEntradaProduto());
//    }
//
//    @Override
//    public void fechar() {
//        ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getTabs().remove(
//                ControllerPrincipal.getCtrlPrincipal().getTabPaneViewPrincipal().getTabs().stream()
//                        .filter(tab -> tab.textProperty().get().equals(getNomeTab()))
//                        .findFirst().orElse(null)
//        );
//        if (isTabCarregada())
//            ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal()
//                    .removeEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerEntradaProduto());
//    }
//
//    @Override
//    public void criarObjetos() {
//
//        setNomeTab(ViewEntradaProduto.getTitulo());
//        getListaTarefa().add(new Pair("criarTabela", "criando tabela de " + getNomeController()));
//    }
//
//    @Override
//    public void preencherObjetos() {
//        getListaTarefa().add(new Pair("vinculandoObjetosTabela", "vinculando objetos a tableModel"));
//        getListaTarefa().add(new Pair("preencherTabela", "preenchendo tabela " + getNomeController()));
//
//        getListaTarefa().add(new Pair("preencherCombos", "carregando informações do formulario"));
//
//        setTabCarregada(new ServiceSegundoPlano().executaListaTarefas(taskEntradaProduto(), getListaTarefa().size(),
//                String.format("Abrindo %s", getNomeTab())));
//    }
//
//    @Override
//    public void fatorarObjetos() {
//        getTpnNfeFiscal().setExpanded(false);
//        getTpnCteFiscal().setExpanded(false);
//        getTpnCteDetalhe().setExpanded(false);
//    }
//
//    @Override
//    @SuppressWarnings("Duplicates")
//    public void escutarTecla() {
//        escutaTitledTab();
//
//        if (statusBarProperty().get() == null)
//            setStatusBar(StatusBarEntradaProduto.DIGITACAO);
//        ControllerPrincipal.ctrlPrincipal.getServiceStatusBar().atualizaStatusBar(statusBarProperty().get().getDescricao());
//
//        getLblStatus().textProperty().bind(Bindings.createStringBinding(() -> {
//            switch (statusBarProperty().get()) {
//                case DIGITACAO:
//                    ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F1));
//                    break;
//                case LANCADO:
//                case INCLUIDO:
//                case FATURADO:
//                    ServiceCampoPersonalizado.fieldDisable(getPainelViewEntradaProduto(), true);
//                    break;
//            }
//            ControllerPrincipal.ctrlPrincipal.getServiceStatusBar().atualizaStatusBar(statusBarProperty().get().getDescricao());
//            return String.format("[%s]", statusBarProperty().get());
//        }, statusBarProperty()));
//
//        ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
//            if (ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getTabs().size() == 0) return;
//            if (ControllerPrincipal.ctrlPrincipal.getTabSelecionada().equals(getNomeTab()))
//                ControllerPrincipal.ctrlPrincipal.getServiceStatusBar().atualizaStatusBar(getStatusBar().getDescricao());
//        });
//
//        getLblStatus().widthProperty().addListener((ov, o, n) -> {
//            getLblRegistrosLocalizados().setLayoutX(getLblStatus().getLayoutX() + n.doubleValue() + 20.);
//        });
//
//        getTtvProduto().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() != KeyCode.ENTER || getTtvProduto().getSelectionModel().getSelectedItem() == null)
//                return;
//            Produto produtoSelecionado;
//            if ((produtoSelecionado = getTtvProduto().getSelectionModel().getSelectedItem().getValue()).getCodigo().equals(""))
//                produtoSelecionado = getTtvProduto().getSelectionModel().getSelectedItem().getParent().getValue();
//            getEntradaProdutoProdutoObservableList().add(new EntradaProdutoProduto(produtoSelecionado));
//            ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F8));
//        });
//
//        setEventHandlerEntradaProduto(new EventHandler<KeyEvent>() {
//            /**
//             * Invoked when a specific event of the type for which this handler is
//             * registered happens.
//             *
//             * @param event the event which occurred
//             */
//            @Override
//            public void handle(KeyEvent event) {
//                if (ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedIndex() < 0)
//                    return;
//                if (!ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedItem().getText().equals(ViewEntradaProduto.getTituloJanela()))
//                    return;
//                switch (event.getCode()) {
//                    case F1:
//                        if (!teclaFuncaoDisponivel(event.getCode())) return;
//                        limparCampos();
//                        getTxtNfeChave().requestFocus();
//                        break;
//                    case F2:
//                        if (!teclaFuncaoDisponivel(event.getCode())) return;
//                        if (!guardarEntradaProduto()) break;
//                        if (!salvarEntradaProduto()) break;
//                        getModelProduto().atualizaProdutos();
//                        break;
//                    case F3:
//                        if (!teclaFuncaoDisponivel(event.getCode())) return;
//                        setAlertMensagem(new ServiceAlertMensagem());
//                        getAlertMensagem().setCabecalho(String.format("Excluir entrada"));
//                        getAlertMensagem().setPromptText(String.format("%s, deseja excluir entrada",
//                                LogadoInf.getUserLog().getApelido()));
//                        if (getAlertMensagem().getRetornoAlert_Yes_No().get() == ButtonType.NO) return;
////                        if (orcamento != null)
////                            entradaTempDAO.remove(orcamento);
//                        setStatusBar(StatusBarEntradaProduto.DIGITACAO);
//                        break;
//                    case F4:
//                        if (!teclaFuncaoDisponivel(event.getCode())) return;
////                        if (!validarEntradaProduto()) break;
////                        salvarEntradaTemp();
//                        setStatusBar(StatusBarEntradaProduto.DIGITACAO);
//                        break;
//                    case F5:
//                        if (!teclaFuncaoDisponivel(event.getCode())) return;
//                        getTxtNfeChave().requestFocus();
//                        break;
//                    case F6:
//                        if (!teclaFuncaoDisponivel(event.getCode())) return;
//                        getTxtCteChave().requestFocus();
//                        break;
//                    case F7:
//                        if (!teclaFuncaoDisponivel(event.getCode())) return;
//                        getTxtPesquisaProduto().requestFocus();
//                        break;
//                    case F8:
//                        if (!teclaFuncaoDisponivel(event.getCode())) return;
//                        getTtvItensNfe().requestFocus();
//                        getTtvItensNfe().getSelectionModel().select(getEntradaProdutoProdutoObservableList().size() - 1,
//                                getModelEntradaProdutoProduto().getColunaLote());
//                        getTtvItensNfe().getFocusModel().focus(getEntradaProdutoProdutoObservableList().size() - 1,
//                                getModelEntradaProdutoProduto().getColunaLote());
//                        break;
//                    case F9:
//                        if (!teclaFuncaoDisponivel(event.getCode())) return;
//                        getTpnNfeFiscal().setExpanded(!getTpnNfeFiscal().isExpanded());
//                        break;
//                    case F10:
//                        if (!teclaFuncaoDisponivel(event.getCode())) return;
//                        getTpnCteDetalhe().setExpanded(!getTpnCteDetalhe().isExpanded());
//                        break;
//                    case F11:
//                        if (!teclaFuncaoDisponivel(event.getCode())) return;
//                        getTpnCteDetalhe().setExpanded(true);
//                        getTpnCteFiscal().setExpanded(!getTpnCteFiscal().isExpanded());
//                        break;
//                    case F12:
//                        if (!teclaFuncaoDisponivel(event.getCode())) return;
//                        fechar();
//                        break;
//                    case HELP:
////                        if (getStatusFormulario().equals(StatusFormulario.PESQUISA)) return;
////                        keyInsert();
//                        break;
//                    case DELETE:
////                        if (getStatusFormulario().equals(StatusFormulario.PESQUISA)) return;
////                        keyDelete();
//                        break;
//                    case B:
////                        if (getStatusFormulario().equals(StatusFormulario.PESQUISA)) return;
////                        if (CODE_KEY_CTRL_ALT_B.match(event) || CHAR_KEY_CTRL_ALT_B.match(event))
////                            addCodeBar("");
//                        break;
//                    case Z:
////                        if (getStatusFormulario().equals(StatusFormulario.PESQUISA)) return;
////                        if (CODE_KEY_CTRL_Z.match(event) || CHAR_KEY_CTRL_Z.match(event))
////                            reverseImageProduto();
//                        break;
//                }
//                if (ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedIndex() > 0)
//                    ControllerPrincipal.ctrlPrincipal.tabPaneViewPrincipal.getSelectionModel().getSelectedItem().setOnCloseRequest(event1 -> {
//                        if (!getStatusBar().equals(StatusBarEntradaProduto.DIGITACAO)) {
//                            event1.consume();
//                        }
//                    });
//            }
//        });
//
//        ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.addEventHandler(KeyEvent.KEY_PRESSED, getEventHandlerEntradaProduto());
//
//        getTxtPesquisaProduto().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() != KeyCode.ENTER) return;
//            getTtvProduto().requestFocus();
//            getTtvProduto().getSelectionModel().selectFirst();
//        });
//
//        getTxtNfeChave().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (!getStatusBar().equals(StatusBarEntradaProduto.DIGITACAO)) return;
//            if (event.getCode() != KeyCode.ENTER || getTxtNfeChave().getText().replaceAll("\\D", "").length() != 44)
//                return;
//            buscaEntrada(getTxtNfeChave().getText());
//        });
//
//        getTxtCteChave().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (!getStatusBar().equals(StatusBarEntradaProduto.DIGITACAO)) return;
//            if (event.getCode() != KeyCode.ENTER || getTxtCteChave().getText().replaceAll("\\D", "").length() != 44)
//                return;
//            buscaEntrada(getTxtCteChave().getText());
//        });
//
//        getTxtNfeChave().setOnDragOver(event -> {
//            if (getTxtNfeChave().isDisabled()) return;
//            Dragboard board = event.getDragboard();
//            if (board.hasFiles())
//                if (Pattern.compile(".xml").matcher(board.getFiles().get(0).toPath().toString()).find())
//                    event.acceptTransferModes(TransferMode.ANY);
//        });
//
//        getTxtNfeChave().setOnDragDropped(event -> {
//            if (getTxtNfeChave().isDisabled()) return;
//            Dragboard board = event.getDragboard();
//            addXmlNfe(board.getFiles().get(0));
//        });
//
//        getTxtCteChave().setOnDragOver(event -> {
//            if (getTxtCteChave().isDisabled()) return;
//            Dragboard board = event.getDragboard();
//            if (board.hasFiles())
//                if (Pattern.compile(".xml").matcher(board.getFiles().get(0).toPath().toString()).find())
//                    event.acceptTransferModes(TransferMode.ANY);
//        });
//
//        getTxtCteChave().setOnDragDropped(event -> {
//            if (getTxtCteChave().isDisabled()) return;
//            Dragboard board = event.getDragboard();
//            addXmlCte(board.getFiles().get(0));
//        });
//
//        getTpnNfeDetalhe().textProperty().bind(Bindings.createStringBinding(() ->
//                String.format("Detalhe da nf-e %s",
//                        getTxtNfeNumero().getText().length() == 0
//                                ? ""
//                                : "[" + getTxtNfeNumero().getText().trim() + "]"
//                ), getTxtNfeNumero().textProperty()
//        ));
//
//        getTpnNfeFiscal().textProperty().bind(Bindings.createStringBinding(() ->
//                String.format("%s",
//                        getTpnNfeFiscal().isExpanded()
//                                ? "Informações de imposto"
//                                : "Nf-e sem imposto"
//                ), getTpnNfeFiscal().expandedProperty()
//        ));
//
//        getTpnCteDetalhe().textProperty().bind(Bindings.createStringBinding(() -> {
//                    if (getTpnCteDetalhe().isExpanded()) {
//                        return String.format("Detalhe frete do ct-e %s",
//                                getTxtCteNumero().getText().length() == 0
//                                        ? ""
//                                        : "[" + getTxtCteNumero().getText().trim() + "]"
//                        );
//                    } else {
//                        return "Nf-e sem frete";
//                    }
//                }, getTxtCteNumero().textProperty()
//        ));
//
//        getTpnCteFiscal().textProperty().bind(Bindings.createStringBinding(() ->
//                String.format("%s",
//                        getTpnCteFiscal().isExpanded()
//                                ? "Informações de imposto no frete"
//                                : "Frete sem imposto"
//                ), getTpnCteFiscal().expandedProperty()
//        ));
//
//        getLblQtdItem().textProperty().bind(numItensProperty().asString());
//        getLblQtdTotal().textProperty().bind(qtdItensProperty().asString());
//        getLblQtdVolume().textProperty().bind(qtdVolumeProperty().asString());
//        getLblTotalBruto().textProperty().bind(Bindings.createStringBinding(() ->
//                ServiceMascara.getMoeda2(totalBrutoProperty().get(), 2), totalBrutoProperty()
//        ));
//        getLblTotalImposto().textProperty().bind(Bindings.createStringBinding(() ->
//                ServiceMascara.getMoeda2(totalImpostoProperty().get(), 2), totalImpostoProperty()
//        ));
//        getLblTotalFrete().textProperty().bind(Bindings.createStringBinding(() ->
//                ServiceMascara.getMoeda2(totalFreteProperty().get(), 2), totalFreteProperty()
//        ));
//        getLblTotalDesconto().textProperty().bind(Bindings.createStringBinding(() ->
//                ServiceMascara.getMoeda2(totalDescontoProperty().get(), 2), totalDescontoProperty()
//        ));
//        getLblTotalLiquido().textProperty().bind(Bindings.createStringBinding(() ->
//                ServiceMascara.getMoeda2(totalLiquidoProperty().get(), 2), totalLiquidoProperty()
//        ));
//
//        getEntradaProdutoProdutoObservableList().addListener((ListChangeListener<? super EntradaProdutoProduto>) c -> {
//            totalizaTabela();
//        });
//
//        getTxtNfeFiscalVlrTotal().textProperty().bind(Bindings.createStringBinding(() -> {
//                    BigDecimal vlrTributo = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrTributo().getText(), 2);
//                    BigDecimal vlrMulta = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrMulta().getText(), 2);
//                    BigDecimal vlrJuros = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrJuros().getText(), 2);
//                    BigDecimal vlrTaxa = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrTaxa().getText(), 2);
//
//                    return ServiceMascara.getMoeda2(vlrTributo.add(vlrMulta).add(vlrJuros).add(vlrTaxa), 2);
//                }, getTxtNfeFiscalVlrTributo().textProperty(), getTxtNfeFiscalVlrMulta().textProperty(),
//                getTxtNfeFiscalVlrJuros().textProperty(), getTxtNfeFiscalVlrTaxa().textProperty()
//        ));
//
//        getTxtNfeFiscalVlrPercentual().textProperty().bind(Bindings.createStringBinding(() -> {
//                    BigDecimal vlrNfe = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrNFe().getText(), 2);
//                    BigDecimal vlrTotal = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrTotal().getText(), 2);
//                    try {
//                        return ServiceMascara.getMoeda2(vlrTotal
//                                .divide(vlrNfe, 5, RoundingMode.HALF_UP)
//                                .multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP), 2);
//                    } catch (ArithmeticException ae) {
//                        return "0,00";
//                    }
//                }, getTxtNfeFiscalVlrNFe().textProperty(), getTxtNfeFiscalVlrTotal().textProperty()
//        ));
//
//        getTxtCteFiscalVlrTotal().textProperty().bind(Bindings.createStringBinding(() -> {
//                    BigDecimal vlrTributo = ServiceMascara.getBigDecimalFromTextField(getTxtCteFiscalVlrTributo().getText(), 2);
//                    BigDecimal vlrMulta = ServiceMascara.getBigDecimalFromTextField(getTxtCteFiscalVlrMulta().getText(), 2);
//                    BigDecimal vlrJuros = ServiceMascara.getBigDecimalFromTextField(getTxtCteFiscalVlrJuros().getText(), 2);
//                    BigDecimal vlrTaxa = ServiceMascara.getBigDecimalFromTextField(getTxtCteFiscalVlrTaxa().getText(), 2);
//
//                    return ServiceMascara.getMoeda2(vlrTributo.add(vlrMulta).add(vlrJuros).add(vlrTaxa), 2);
//                }, getTxtCteFiscalVlrTributo().textProperty(), getTxtCteFiscalVlrMulta().textProperty(),
//                getTxtCteFiscalVlrJuros().textProperty(), getTxtCteFiscalVlrTaxa().textProperty()
//        ));
//
//        getTxtCteFiscalVlrPercentual().textProperty().bind(Bindings.createStringBinding(() -> {
//                    BigDecimal vlrCte = ServiceMascara.getBigDecimalFromTextField(getTxtCteFiscalVlrCte().getText(), 2);
//                    BigDecimal vlrTotal = ServiceMascara.getBigDecimalFromTextField(getTxtCteFiscalVlrTotal().getText(), 2);
//                    try {
//                        return ServiceMascara.getMoeda2(vlrTotal
//                                .divide(vlrCte, 5, RoundingMode.HALF_UP)
//                                .multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP), 2);
//                    } catch (ArithmeticException ae) {
//                        return "0,00";
//                    }
//                }, getTxtCteFiscalVlrCte().textProperty(), getTxtCteFiscalVlrTotal().textProperty()
//        ));
//
//        totalFreteProperty().bind(Bindings.createObjectBinding(() -> {
//                    BigDecimal vlrBruto = ServiceMascara.getBigDecimalFromTextField(getTxtCteVlrBruto().getText(), 2);
//                    BigDecimal vlrTaxa = ServiceMascara.getBigDecimalFromTextField(getTxtCteVlrTaxa().getText(), 2);
//                    BigDecimal vlrColeta = ServiceMascara.getBigDecimalFromTextField(getTxtCteVlrColeta().getText(), 2);
//                    BigDecimal vlrImposto = ServiceMascara.getBigDecimalFromTextField(getTxtCteVlrImposto().getText(), 2);
//                    BigDecimal vlrTotal = vlrBruto.add(vlrTaxa).add(vlrColeta).add(vlrImposto);
//                    getTxtCteVlrLiquido().setText(ServiceMascara.getMoeda2(vlrTotal, 2));
//                    getTxtCteVlrCte().setText(ServiceMascara.getMoeda2(vlrTotal, 2));
//                    if (getTpnCteFiscal().isExpanded())
//                        getTxtCteFiscalVlrCte().setText(ServiceMascara.getMoeda2(vlrTotal, 2));
//
//                    return vlrTotal;
//                }, getTxtCteVlrBruto().textProperty(), getTxtCteVlrTaxa().textProperty(),
//                getTxtCteVlrColeta().textProperty(), getTxtCteVlrImposto().textProperty()
//        ));
//
//        totalImpostoEntradaProperty().bind(Bindings.createObjectBinding(() -> {
//                    BigDecimal vlrImpostoNfe = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrTotal().getText(), 2);
//                    BigDecimal vlrImpostoCte = ServiceMascara.getBigDecimalFromTextField(getTxtCteFiscalVlrTotal().getText(), 2);
//
//                    return vlrImpostoNfe.add(vlrImpostoCte);
//
//                }, getTxtNfeFiscalVlrTotal().textProperty(), getTxtCteFiscalVlrTotal().textProperty()
//        ));
//
//        totalImpostoProperty().bind(Bindings.createObjectBinding(() ->
//                        totalImpostoEntradaProperty().get().add(totalImpostoItensProperty().get())
//                , totalImpostoEntradaProperty(), totalImpostoItensProperty()
//        ));
//
//        totalLiquidoProperty().bind(Bindings.createObjectBinding(() ->
//                        (totalBrutoProperty().get().add(totalImpostoProperty().get()).add(totalFreteProperty().get()))
//                                .subtract(totalDescontoProperty().get())
//                , totalBrutoProperty(), totalImpostoProperty(), totalFreteProperty(), totalDescontoProperty()
//        ));
//
//    }
//
//
//    @SuppressWarnings("Duplicates")
//    Task taskEntradaProduto() {
//        ControllerEntradaProduto tmpEntrada = this;
//        Task<Void> voidTask = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                updateMessage("carregando");
//                for (Pair tarefaAtual : getListaTarefa()) {
//                    updateProgress(getListaTarefa().indexOf(tarefaAtual), getListaTarefa().size());
//                    Thread.sleep(200);
//                    updateMessage(tarefaAtual.getValue().toString());
//                    switch (tarefaAtual.getKey().toString()) {
//                        case "criarTabela":
//                            setModelProduto(new TabModelProduto());
//                            getModelProduto().tabela();
//                            getModelProduto().setTipoForm(null);
//
//                            setModelEntradaProdutoProduto(new TabModelEntradaProdutoProduto(tmpEntrada));
//                            getModelEntradaProdutoProduto().tabela();
//                            break;
//
//                        case "vinculandoObjetosTabela":
//                            getModelProduto().setLblRegistrosLocalizados(getLblRegistrosLocalizados());
//                            getModelProduto().setTtvProduto(getTtvProduto());
//                            getModelProduto().setTxtPesquisaProduto(getTxtPesquisaProduto());
//                            getModelProduto().setProdutoObservableList(getProdutoObservableList());
//                            getModelProduto().setProdutoFilteredList(getProdutoFilteredList());
//                            getModelProduto().escutaLista();
//
//                            getModelEntradaProdutoProduto().setTp(getTp());
//                            getModelEntradaProdutoProduto().setTxtPesquisaProduto(getTxtPesquisaProduto());
//                            getModelEntradaProdutoProduto().setTtvItensNfe(getTtvItensNfe());
//                            getModelEntradaProdutoProduto().setEntradaProdutoProduto(getEntradaProdutoProduto());
//                            getModelEntradaProdutoProduto().setEntradaProdutoProdutoObservableList(getEntradaProdutoProdutoObservableList());
//                            getModelEntradaProdutoProduto().escutaLista();
//                            break;
//
//                        case "preencherTabela":
//                            getModelProduto().preencherTabela();
//
//                            getModelEntradaProdutoProduto().preencherTabela();
//                            break;
//
//                        case "preencherCombos":
//                            ObservableList<Empresa> tmpEmpresas = FXCollections.observableArrayList(new EmpresaDAO().getAll(Empresa.class, null, null, null, null))
//                                    .sorted(Comparator.comparing(Empresa::getRazao));
//                            getCboNfeLojaDestino().setItems(
//                                    tmpEmpresas.stream()
//                                            .filter(loja -> (loja.isLojaSistema()))
//                                            .collect(Collectors.toCollection(FXCollections::observableArrayList))
//                            );
//                            getCboNfeFornecedor().setItems(
//                                    tmpEmpresas.stream()
//                                            .filter(fornecedor -> (fornecedor.isFornecedor()))
//                                            .collect(Collectors.toCollection(FXCollections::observableArrayList))
//                            );
//                            getCboCteTransportadora().setItems(
//                                    tmpEmpresas.stream()
//                                            .filter(transportadora -> (transportadora.isTransportadora()))
//                                            .collect(Collectors.toCollection(FXCollections::observableArrayList))
//                            );
//                            getCboNfeModelo().setItems(NfeCteModelo.getList().stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
//                            getCboCteModelo().setItems(NfeCteModelo.getList().stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
//                            getCboCteSistuacaoTributaria().setItems(
//                                    new FiscalFreteSituacaoTributariaDAO().getAll(FiscalFreteSituacaoTributaria.class, null, null, null, null)
//                                            .stream().collect(Collectors.toCollection(FXCollections::observableArrayList))
//                            );
//                            ObservableList<FiscalTributosSefazAm> tmpImposto = new FiscalTributosSefazAmDAO().getAll(FiscalTributosSefazAm.class, null, null, null, null)
//                                    .stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
//                            getCboNfeFiscalTributo().setItems(tmpImposto);
//                            getCboCteFiscalTributo().setItems(tmpImposto);
//                            getCboCteTomadorServico().setItems(CteTomadorServico.getList().stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
//                            break;
//                    }
//                }
//                updateProgress(getListaTarefa().size(), getListaTarefa().size());
//                return null;
//            }
//        };
//        return voidTask;
//    }
//
//
//    /**
//     * Start
//     * Return values
//     */
//
//
//    /**
//     * END
//     * Return Values
//     */
//
//    /**
//     * start
//     * Booleans
//     */
//
//    private boolean teclaFuncaoDisponivel(KeyCode keyCode) {
//        return getStatusBar().getDescricao().contains(keyCode.toString());
//    }
//
////    private boolean validarEntradaProduto() {
////        if (validarEntradaNfe() && validarEntradaFiscalNfe() && validarEntradaCte() && validarEntradaFiscalCte()) {
////            guardarEntradaProduto();
////            return true;
////        }
////        return false;
////    }
//
//    private boolean salvarEntradaProduto() {
//        if (getEntradaProdutoProdutoObservableList().size() == 0) {
//            ControllerPrincipal.ctrlPrincipal.painelViewPrincipal.fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F7));
//            return false;
//        }
//        try {
//            getEntradaProduto().setUsuarioCadastro(LogadoInf.getUserLog());
//            getEntradaProduto().setSituacao(SituacaoEntrada.LANCADO);
//            setEntradaProduto(getEntradaProdutoDAO().persiste(getEntradaProduto()));
//            salvarEntradaEstoque();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
////    @SuppressWarnings("Duplicates")
////    private boolean validarEntradaNfe() {
////        boolean result = true;
////        String dado = "";
////        if (result)
////            if (!(result = (cboNfeLojaDestino.getSelectionModel().getSelectedItem() != null))) {
////                dado += "Loja de destino";
////                cboNfeLojaDestino.requestFocus();
////            }
////        if (result)
////            if (result = (txtNfeChave.getText().replaceAll("\\D", "").length() == 44)) {
////                EntradaProduto ent;
////                if ((ent = verificaExistente(txtNfeChave.getText(), getEntradaProduto().getId())) != null) {
////                    carregarEntradaExistente(ent, txtNfeChave.getText());
////                    txtNfeChave.requestFocus();
////                    return false;
////                }
////            }
////        if (result)
////            if (!(result = txtNfeNumero.getText().length() >= 1)) {
////                dado = "nfe número";
////                txtNfeNumero.requestFocus();
////            }
////        if (result)
////            if (!(result = txtNfeSerie.getText().length() >= 1)) {
////                dado = "nfe série";
////                txtNfeSerie.requestFocus();
////            }
////        if (result)
////            if (!(result = (cboNfeModelo.getSelectionModel().getSelectedItem() != null))) {
////                dado += "nfe modelo nfe";
////                cboNfeModelo.requestFocus();
////            }
////        if (result)
////            if (!(result = (cboNfeFornecedor.getSelectionModel().getSelectedItem() != null))) {
////                dado += "nfe fornecedor";
////                cboNfeFornecedor.requestFocus();
////            }
////        if (result)
////            if (Period.between(dtpNfeEmissao.getValue(), dtpNfeEntrada.getValue()).isNegative()
////                    || Period.between(dtpNfeEmissao.getValue(), LocalDate.now()).isNegative()) {
////                dado += "nfe data emissão";
////                dtpNfeEmissao.requestFocus();
////            }
////        if (result)
////            if (Period.between(dtpNfeEntrada.getValue(), LocalDate.now()).isNegative()) {
////                dado += "nfe data entrada";
////                dtpNfeEntrada.requestFocus();
////            }
////        if (!result) {
////            alertMensagem = new ServiceAlertMensagem();
////            alertMensagem.setCabecalho(String.format("Dados inválido"));
////            alertMensagem.setPromptText(String.format("%s, '%s' incompleto(a) ou invalido(a)",
////                    LogadoInf.getUserLog().getApelido(), dado));
////            alertMensagem.setStrIco("ic_atencao_triangulo_24dp.png");
////            alertMensagem.getRetornoAlert_OK();
////        } // else result = guardarEntradaProduto();
////        return result;
////    }
////
////    @SuppressWarnings("Duplicates")
////    private boolean validarEntradaFiscalNfe() {
////        boolean result = true;
////        String dado = "";
////        if (!tpnNfeFiscal.isExpanded()) {
////            ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnNfeFiscal.getContent());
////            return true;
////        }
////        if (result)
////            if (!(result = txtNfeFiscalControle.getText().length() >= 1)) {
////                dado = "fiscal nfe número controle";
////                txtNfeFiscalControle.requestFocus();
////            }
////        if (result)
////            if (!(result = txtNfeFiscalOrigem.getText().length() >= 1)) {
////                dado = "fiscal nfe documento origem";
////                txtNfeFiscalOrigem.requestFocus();
////            }
////        if (result)
////            if (!(result = (cboNfeFiscalTributo.getSelectionModel().getSelectedItem() != null))) {
////                dado += "fiscal nfe tributo";
////                cboNfeFiscalTributo.requestFocus();
////            }
////        if (result)
////            if (!(result = Double.parseDouble(txtNfeFiscalVlrNFe.getText().replace(".", "").replace(",", ".")) > 0)) {
////                dado += "fiscal nfe vlr nfe";
////                txtNfeFiscalVlrNFe.requestFocus();
////            }
////        if (!result) {
////            alertMensagem = new ServiceAlertMensagem();
////            alertMensagem.setCabecalho(String.format("Dados inválido"));
////            alertMensagem.setPromptText(String.format("%s, '%s' incompleto(a) ou invalido(a)",
////                    LogadoInf.getUserLog().getApelido(), dado));
////            alertMensagem.setStrIco("ic_atencao_triangulo_24dp.png");
////            alertMensagem.getRetornoAlert_OK();
////        }// else result = guardarEntradaProduto();
////        return result;
////    }
////
////    @SuppressWarnings("Duplicates")
////    private boolean validarEntradaCte() {
////        boolean result = true;
////        String dado = "";
////        if (!tpnCteDetalhe.isExpanded()) {
////            ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnCteDetalhe.getContent());
////            return true;
////        }
////        if (result)
////            if (result = (txtCteChave.getText().replaceAll("\\D", "").length() == 44)) {
////                EntradaProduto ent;
////                if ((ent = verificaExistente(txtCteChave.getText(), getEntradaProduto().getId())) != null) {
////                    carregarEntradaExistente(ent, txtCteChave.getText());
////                    txtCteChave.requestFocus();
////                    return false;
////                }
////            }
////        if (result)
////            if (!(result = (cboCteTomadorServico.getSelectionModel().getSelectedItem() != null))) {
////                dado += "Cte tomador serviço";
////                cboCteTomadorServico.requestFocus();
////            }
////        if (result)
////            if (!(result = txtCteNumero.getText().length() >= 1)) {
////                dado = "Cte número";
////                txtCteNumero.requestFocus();
////            }
////        if (result)
////            if (!(result = txtCteSerie.getText().length() >= 1)) {
////                dado = "Cte série";
////                txtCteSerie.requestFocus();
////            }
////        if (result)
////            if (!(result = (cboCteModelo.getSelectionModel().getSelectedItem() != null))) {
////                dado += "Cte modelo cte";
////                cboCteModelo.requestFocus();
////            }
////        if (result)
////            if (!(result = (cboCteSistuacaoTributaria.getSelectionModel().getSelectedItem() != null))) {
////                dado += "Cte situação tributária";
////                cboCteSistuacaoTributaria.requestFocus();
////            }
////        if (result)
////            if (!(result = (cboCteTransportadora.getSelectionModel().getSelectedItem() != null))) {
////                dado += "Cte transportadora";
////                cboCteTransportadora.requestFocus();
////            }
////        if (result)
////            if (Period.between(dtpCteEmissao.getValue(), LocalDate.now()).isNegative()) {
////                dado += "Cte data emissão";
////                dtpCteEmissao.requestFocus();
////            }
////        if (result)
////            if (!(result = Double.parseDouble(txtCteVlrCte.getText().replace(".", "").replace(",", ".")) > 0)) {
////                dado += "Cte vlr cte";
////                txtCteVlrCte.requestFocus();
////            }
////        if (result)
////            if (!(result = Integer.parseInt(txtCteQtdVolume.getText()) > 0)) {
////                dado += "Cte qtd volume";
////                txtCteQtdVolume.requestFocus();
////            }
////        if (result)
////            if (!(result = Double.parseDouble(txtCtePesoBruto.getText().replace(".", "").replace(",", ".")) > 0)) {
////                dado += "Cte peso bruto";
////                txtCtePesoBruto.requestFocus();
////            }
////        if (result)
////            if (!(result = Double.parseDouble(txtCteVlrBruto.getText().replace(".", "").replace(",", ".")) > 0)) {
////                dado += "Cte vlr bruto";
////                txtCteVlrBruto.requestFocus();
////            }
////        if (result)
////            if (!(result = Double.parseDouble(txtCteVlrImposto.getText().replace(".", "").replace(",", ".")) > 0)) {
////                dado += "Cte vlr imposto";
////                txtCteVlrImposto.requestFocus();
////            }
////        if (result)
////            if (!(result = Double.parseDouble(txtCteVlrLiquido.getText().replace(".", "").replace(",", ".")) > 0)) {
////                dado += "Cte vlr frete liquído";
////                txtCteVlrLiquido.requestFocus();
////            }
////
////        if (!result) {
////            alertMensagem = new ServiceAlertMensagem();
////            alertMensagem.setCabecalho(String.format("Dados inválido"));
////            alertMensagem.setPromptText(String.format("%s, '%s' incompleto(a) ou invalido(a)",
////                    LogadoInf.getUserLog().getApelido(), dado));
////            alertMensagem.setStrIco("ic_atencao_triangulo_24dp.png");
////            alertMensagem.getRetornoAlert_OK();
////        } // else result = guardarEntradaProduto();
////        return result;
////    }
////
////    @SuppressWarnings("Duplicates")
////    private boolean validarEntradaFiscalCte() {
////        boolean result = true;
////        String dado = "";
////        if (!tpnCteFiscal.isExpanded()) {
////            ServiceCampoPersonalizado.fieldClear((AnchorPane) tpnCteFiscal.getContent());
////            return true;
////        }
////        if (result)
////            if (!(result = txtCteFiscalControle.getText().length() >= 1)) {
////                dado = "fiscal cte número controle";
////                txtCteFiscalControle.requestFocus();
////            }
////        if (result)
////            if (!(result = txtCteFiscalOrigem.getText().length() >= 1)) {
////                dado = "fiscal cte documento origem";
////                txtCteFiscalOrigem.requestFocus();
////            }
////        if (result)
////            if (!(result = (cboCteFiscalTributo.getSelectionModel().getSelectedItem() != null))) {
////                dado += "fiscal cte tributo";
////                cboCteFiscalTributo.requestFocus();
////            }
////        if (result)
////            if (!(result = Double.parseDouble(txtCteFiscalVlrCte.getText().replace(".", "").replace(",", ".")) > 0)) {
////                dado += "fiscal cte vlr cte";
////                txtCteFiscalVlrCte.requestFocus();
////            }
////        if (!result) {
////            alertMensagem = new ServiceAlertMensagem();
////            alertMensagem.setCabecalho(String.format("Dados inválido"));
////            alertMensagem.setPromptText(String.format("%s, '%s' incompleto(a) ou invalido(a)",
////                    LogadoInf.getUserLog().getApelido(), dado));
////            alertMensagem.setStrIco("ic_atencao_triangulo_24dp.png");
////            alertMensagem.getRetornoAlert_OK();
////        }// else result = guardarEntradaProduto();
////        return result;
////    }
//
//    private boolean jaExiste(String busca) {
//        String strBusca = busca.toLowerCase().trim().replaceAll("\\D", "");
//        EntradaProduto entradaBusca;
//        if ((entradaBusca = getEntradaProdutoObservableList().stream()
//                .filter(entrada ->
//                        (entrada.getEntradaNfe() != null && entrada.getEntradaNfe().getChave().equals(strBusca)) ||
//                                (entrada.getEntradaCte() != null && entrada.getEntradaCte().getChave().equals(strBusca))
//                ).findFirst().orElse(null)) == null)
//            return false;
//        return carregarEntradaExistente(entradaBusca, busca);
//    }
//
//    private boolean carregarEntradaExistente(EntradaProduto entradaProduto, String chave) {
//        setAlertMensagem(new ServiceAlertMensagem());
//        getAlertMensagem().setCabecalho("Informação duplicada");
//        getAlertMensagem().setPromptText(String.format("%s, a chave: [%s]\njá está cadastrado no sistema!\nDeseja carregar ela?",
//                LogadoInf.getUserLog().getApelido(),
//                chave));
//        getAlertMensagem().setStrIco("ic_atencao_triangulo_24dp");
//        if (getAlertMensagem().getRetornoAlert_Yes_No().get() == ButtonType.NO) return false;
//        setEntradaProduto(entradaProduto);
//        return true;
//    }
//
//    private boolean guardarEntradaProduto() {
//        try {
//            getEntradaProduto().setLoja(getCboNfeLojaDestino().getSelectionModel().getSelectedItem());
//
//            getEntradaProduto().getEntradaNfe().setChave(getTxtNfeChave().getText());
//            getEntradaProduto().getEntradaNfe().setNumero(getTxtNfeNumero().getText());
//            getEntradaProduto().getEntradaNfe().setSerie(getTxtNfeSerie().getText());
//            getEntradaProduto().getEntradaNfe().setModelo(getCboNfeModelo().getSelectionModel().getSelectedItem());
//            getEntradaProduto().getEntradaNfe().setEmissor(getCboNfeFornecedor().getSelectionModel().getSelectedItem());
//            getEntradaProduto().getEntradaNfe().setDataEmissao(getDtpNfeEmissao().getValue());
//            getEntradaProduto().getEntradaNfe().setDataEntrada(getDtpNfeEntrada().getValue());
//
//            if (getTpnNfeFiscal().isExpanded()) {
//                if (getEntradaProduto().getEntradaNfe().getEntradaFiscal() == null)
//                    getEntradaProduto().getEntradaNfe().setEntradaFiscal(new EntradaFiscal());
//                getEntradaProduto().getEntradaNfe().getEntradaFiscal().setControle(getTxtNfeFiscalControle().getText());
//                getEntradaProduto().getEntradaNfe().getEntradaFiscal().setDocOrigem(getTxtNfeFiscalOrigem().getText());
//                getEntradaProduto().getEntradaNfe().getEntradaFiscal().setTributosSefazAm(getCboNfeFiscalTributo().getSelectionModel().getSelectedItem());
//                getEntradaProduto().getEntradaNfe().getEntradaFiscal().setVlrDocumento(ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrNFe().getText(), 2));
//                getEntradaProduto().getEntradaNfe().getEntradaFiscal().setVlrTributo(ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrTributo().getText(), 2));
//                getEntradaProduto().getEntradaNfe().getEntradaFiscal().setVlrMulta(ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrMulta().getText(), 2));
//                getEntradaProduto().getEntradaNfe().getEntradaFiscal().setVlrJuros(ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrJuros().getText(), 2));
//                getEntradaProduto().getEntradaNfe().getEntradaFiscal().setVlrTaxa(ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrTaxa().getText(), 2));
//            } else {
//                getEntradaProduto().getEntradaNfe().setEntradaFiscal(null);
//            }
//
//            if (getTpnCteDetalhe().isExpanded()) {
//                if (getEntradaProduto().getEntradaCte() == null)
//                    getEntradaProduto().setEntradaCte(new EntradaCte());
//                getEntradaProduto().getEntradaCte().setChave(getTxtCteChave().getText());
//                getEntradaProduto().getEntradaCte().setTomadorServico(getCboCteTomadorServico().getSelectionModel().getSelectedItem());
//                getEntradaProduto().getEntradaCte().setNumero(getTxtCteNumero().getText());
//                getEntradaProduto().getEntradaCte().setSerie(getTxtCteSerie().getText());
//                getEntradaProduto().getEntradaCte().setModelo(getCboCteModelo().getSelectionModel().getSelectedItem());
//                getEntradaProduto().getEntradaCte().setSituacaoTributaria(getCboCteSistuacaoTributaria().getSelectionModel().getSelectedItem());
//                getEntradaProduto().getEntradaCte().setEmissor(getCboCteTransportadora().getSelectionModel().getSelectedItem());
//                getEntradaProduto().getEntradaCte().setDataEmissao(getDtpCteEmissao().getValue());
//                getEntradaProduto().getEntradaCte().setVlrCte(ServiceMascara.getBigDecimalFromTextField(getTxtCteVlrCte().getText(), 2));
//                getEntradaProduto().getEntradaCte().setQtdVolume(Integer.parseInt(getTxtCteQtdVolume().getText()));
//                getEntradaProduto().getEntradaCte().setPesoBruto(ServiceMascara.getBigDecimalFromTextField(getTxtCtePesoBruto().getText(), 2));
//                getEntradaProduto().getEntradaCte().setVlrFreteBruto(ServiceMascara.getBigDecimalFromTextField(getTxtCteVlrBruto().getText(), 2));
//                getEntradaProduto().getEntradaCte().setVlrTaxas(ServiceMascara.getBigDecimalFromTextField(getTxtCteVlrTaxa().getText(), 2));
//                getEntradaProduto().getEntradaCte().setVlrColeta(ServiceMascara.getBigDecimalFromTextField(getTxtCteVlrColeta().getText(), 2));
//                getEntradaProduto().getEntradaCte().setVlrImpostoFrete(ServiceMascara.getBigDecimalFromTextField(getTxtCteVlrImposto().getText(), 2));
//
//                if (getTpnCteFiscal().isExpanded()) {
//                    if (getEntradaProduto().getEntradaCte().getEntradaFiscal() == null)
//                        getEntradaProduto().getEntradaCte().setEntradaFiscal(new EntradaFiscal());
//                    getEntradaProduto().getEntradaCte().getEntradaFiscal().setControle(getTxtCteFiscalControle().getText());
//                    getEntradaProduto().getEntradaCte().getEntradaFiscal().setDocOrigem(getTxtCteFiscalOrigem().getText());
//                    getEntradaProduto().getEntradaCte().getEntradaFiscal().setTributosSefazAm(getCboCteFiscalTributo().getSelectionModel().getSelectedItem());
//                    getEntradaProduto().getEntradaCte().getEntradaFiscal().setVlrDocumento(ServiceMascara.getBigDecimalFromTextField(getTxtCteFiscalVlrCte().getText(), 2));
//                    getEntradaProduto().getEntradaCte().getEntradaFiscal().setVlrTributo(ServiceMascara.getBigDecimalFromTextField(getTxtCteFiscalVlrTributo().getText(), 2));
//                    getEntradaProduto().getEntradaCte().getEntradaFiscal().setVlrMulta(ServiceMascara.getBigDecimalFromTextField(getTxtCteFiscalVlrMulta().getText(), 2));
//                    getEntradaProduto().getEntradaCte().getEntradaFiscal().setVlrJuros(ServiceMascara.getBigDecimalFromTextField(getTxtCteFiscalVlrJuros().getText(), 2));
//                    getEntradaProduto().getEntradaCte().getEntradaFiscal().setVlrTaxa(ServiceMascara.getBigDecimalFromTextField(getTxtCteFiscalVlrTaxa().getText(), 2));
//                } else {
//                    getEntradaProduto().getEntradaCte().setEntradaFiscal(null);
//                }
//            } else {
//                getEntradaProduto().setEntradaCte(null);
//            }
//            getEntradaProduto().setEntradaProdutoProdutoList(getEntradaProdutoProdutoObservableList());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * END
//     * Booleans
//     */
//
//    /**
//     * start
//     * voids
//     */
//    @SuppressWarnings("Duplicates")
//
//    private void closeTabColapsed() {
//        getTpnNfeFiscal().setExpanded(false);
//        getTpnCteFiscal().setExpanded(false);
//        getTpnCteDetalhe().setExpanded(false);
//    }
//
//    private void limparCampos() {
//        getTxtPesquisaProduto().setText("");
//        ServiceCampoPersonalizado.fieldClear(getPainelViewEntradaProduto());
//        closeTabColapsed();
//    }
//
////    private void atualizaTotaisFreteImposto() {
////        BigDecimal impostoNfe = ServiceMascara.getBigDecimalFromTextField(txtNfeFiscalVlrTotal.getText(), 2);
////        BigDecimal impostoCte = ServiceMascara.getBigDecimalFromTextField(txtCteFiscalVlrTotal.getText(), 2);
////        modelEntradaProdutoProduto.setTotalImpostoEntrada(impostoNfe.add(impostoCte));
////        BigDecimal frete = ServiceMascara.getBigDecimalFromTextField(txtCteVlrLiquido.getText(), 2);
////        modelEntradaProdutoProduto.setTotalFrete(frete);
////    }
//
////    private void salvarEntradaTemp() {
////        try {
////            if (orcamento == null)
////                orcamento = new OrcamentoEntrada();
////            orcamento.setOrcamento(new ObjectMapper().writeValueAsString(getEntradaProduto()));
////            orcamento = entradaTempDAO.persiste(orcamento);
////            setSituacaoEntrada(SituacaoEntrada.DIGITACAO);
////        } catch (JsonProcessingException e) {
////            e.printStackTrace();
////            entradaTempDAO.getEntityManager().getTransaction().rollback();
////        }
////    }
//
//    private void salvarEntradaEstoque() {
//        BigDecimal qtdTotal = ServiceMascara.getBigDecimalFromTextField(getLblQtdTotal().getText(), 0);
//
//        BigDecimal freteBruto = ServiceMascara.getBigDecimalFromTextField(getTxtCteVlrBruto().getText(), 2);
//        BigDecimal freteColeta = ServiceMascara.getBigDecimalFromTextField(getTxtCteVlrColeta().getText(), 2);
//        BigDecimal freteLiquido = ServiceMascara.getBigDecimalFromTextField(getTxtCteVlrLiquido().getText(), 2);
//        BigDecimal freteLiquidoUnd = freteLiquido.divide(qtdTotal, 2, RoundingMode.HALF_UP);
//
//        BigDecimal impostoFrete = freteLiquido.subtract(freteBruto.add(freteColeta));
//        BigDecimal impostoFreteUnd = impostoFrete.divide(qtdTotal, 2, RoundingMode.HALF_UP);
//
//        BigDecimal impostoFreteEntrada = ServiceMascara.getBigDecimalFromTextField(getTxtCteFiscalVlrTotal().getText(), 2);
//        BigDecimal impostoFreteEntradaUnd = impostoFreteEntrada.divide(qtdTotal, 2, RoundingMode.HALF_UP);
//
//        BigDecimal impostoEntrada = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrTotal().getText(), 2);
//        BigDecimal impostoEntradaUnd = impostoEntrada.divide(qtdTotal, 2, RoundingMode.HALF_UP);
//
//        BigDecimal totImposto = ServiceMascara.getBigDecimalFromTextField(getLblTotalImposto().getText(), 2);
//        BigDecimal imposto = totImposto.subtract(impostoEntrada.add(impostoFreteEntrada));
//        BigDecimal impostoUnd = imposto.divide(qtdTotal, 2, RoundingMode.HALF_UP);
//
//
//        try {
//            getEntradaProduto().getEntradaProdutoProdutoList().stream()
//                    .forEach(entradaProdutoProduto -> {
//                        BigDecimal vlrDesc = entradaProdutoProduto.getVlrDesconto().divide(BigDecimal.valueOf(entradaProdutoProduto.getQtd()), 2, RoundingMode.HALF_UP);
//                        BigDecimal vlrLiquido = entradaProdutoProduto.getVlrFabrica().add(freteLiquidoUnd)
//                                .add(impostoFreteEntradaUnd).add(impostoEntradaUnd).add(impostoUnd)
//                                .subtract(vlrDesc);
//                        ProdutoEstoque estoque = new ProdutoEstoque();
//                        estoque.setQtd(entradaProdutoProduto.getQtd());
//                        estoque.setLote(entradaProdutoProduto.getLote());
//                        estoque.setValidade(entradaProdutoProduto.getValidade());
//                        estoque.setVlrBruto(entradaProdutoProduto.getVlrFabrica());
//                        estoque.setVlrFreteImposto(impostoFreteUnd.setScale(2, RoundingMode.HALF_UP));
//                        estoque.setVlrFreteImpostoEntrada(impostoFreteEntradaUnd.setScale(2, RoundingMode.HALF_UP));
//                        estoque.setVlrFreteLiquido(freteLiquidoUnd.setScale(2, RoundingMode.HALF_UP));
//                        estoque.setVlrImpostoEntrada(impostoEntradaUnd.setScale(2, RoundingMode.HALF_UP));
//                        estoque.setVlrImpostoProduto(impostoUnd.setScale(2, RoundingMode.HALF_UP));
//                        estoque.setVlrLiquido(vlrLiquido.setScale(2, RoundingMode.HALF_UP));
//                        estoque.setDocumentoEntrada(txtNfeNumero.getText().replaceAll("\\D", ""));
//                        estoque.setDocumentoEntradaChaveNfe(txtNfeChave.getText().replaceAll("\\D", ""));
//                        estoque.setUsuarioCadastro(LogadoInf.getUserLog());
//
//                        Produto produto = entradaProdutoProduto.getProduto();
//
//                        if (estoque.vlrBrutoProperty().get().setScale(2).compareTo(produto.precoFabricaProperty().get().setScale(2)) != 0) {
//                            BigDecimal margem = produto.getPrecoConsumidor().divide(produto.getPrecoFabrica(), 2, RoundingMode.HALF_UP).subtract(BigDecimal.ONE).multiply(new BigDecimal("100"));
//                            produto.setPrecoFabrica(estoque.vlrBrutoProperty().get().setScale(2, RoundingMode.HALF_UP));
//                            BigDecimal newConsumidor = margem.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP).add(BigDecimal.ONE).multiply(produto.getPrecoFabrica());
//                            produto.setPrecoConsumidor(newConsumidor.setScale(2, RoundingMode.HALF_UP));
//                        }
//                        produto.setUltFrete(estoque.vlrFreteLiquidoProperty().get().setScale(2, RoundingMode.HALF_UP));
//                        BigDecimal ultImposto = estoque.vlrFreteImpostoEntradaProperty().get()
//                                .add(estoque.vlrImpostoEntradaProperty().get())
//                                .add(estoque.vlrImpostoProdutoProperty().get());
//                        produto.setUltImpostoSefaz(ultImposto.setScale(2, RoundingMode.HALF_UP));
//
//                        produto.getProdutoEstoqueList().add(estoque);
//
//                        //produtoDAO.persiste(produto);
//                    });
//            getEntradaProduto().setSituacao(SituacaoEntrada.INCLUIDO);
//            setEntradaProduto(getEntradaProdutoDAO().persiste(getEntradaProduto()));
//            setStatusBar(StatusBarEntradaProduto.DIGITACAO);
//        } catch (RuntimeException ex) {
//            ex.printStackTrace();
//            getEntradaProdutoDAO().getEntityManager().getTransaction().rollback();
//        }
//
//    }
//
//    private void buscaEntrada(String chave) {
//        if (!jaExiste(chave)) return;
//        setStatusBar(StatusBarEntradaProduto.toEnum(getEntradaProduto().getSituacao().getCod()));
//        exibirEntrada();
//    }
//
//    @SuppressWarnings("Duplicates")
//    private void exibirEntrada() {
//        cboNfeLojaDestino.getSelectionModel().select(
//                cboNfeLojaDestino.getItems().stream()
//                        .filter(loja -> loja.getId() == getEntradaProduto().getLoja().getId())
//                        .findFirst().orElse(null)
//        );
//
//        if (getEntradaProduto().getEntradaNfe() != null) {
//            txtNfeChave.setText(getEntradaProduto().getEntradaNfe().getChave());
//            cboNfeFornecedor.getSelectionModel().select(
//                    cboNfeFornecedor.getItems().stream()
//                            .filter(loja -> loja.getId() == entradaProduto.getLoja().getId())
//                            .findFirst().orElse(null)
//            );
//            txtNfeNumero.setText(getEntradaProduto().getEntradaNfe().getNumero());
//            txtNfeSerie.setText(getEntradaProduto().getEntradaNfe().getSerie());
//            cboNfeModelo.getSelectionModel().select(
//                    cboNfeModelo.getItems().stream()
//                            .filter(modelNfe -> modelNfe.getCod() == getEntradaProduto().getEntradaNfe().getModelo().getCod())
//                            .findFirst().orElse(null)
//            );
//            cboNfeFornecedor.getSelectionModel().select(
//                    cboNfeFornecedor.getItems().stream()
//                            .filter(fornecedor -> fornecedor.getId() == getEntradaProduto().getEntradaNfe().getEmissor().getId())
//                            .findFirst().orElse(null)
//            );
//            dtpNfeEmissao.setValue(getEntradaProduto().getEntradaNfe().getDataEmissao());
//            dtpNfeEntrada.setValue(getEntradaProduto().getEntradaNfe().getDataEntrada());
//
//            if (getEntradaProduto().getEntradaNfe().getEntradaFiscal() != null) {
//                tpnNfeFiscal.setExpanded(true);
//                txtNfeFiscalControle.setText(getEntradaProduto().getEntradaNfe().getEntradaFiscal().getControle());
//                txtNfeFiscalOrigem.setText(getEntradaProduto().getEntradaNfe().getEntradaFiscal().getDocOrigem());
//                cboNfeFiscalTributo.getSelectionModel().select(
//                        cboNfeFiscalTributo.getItems().stream()
//                                .filter(fiscalNfe -> fiscalNfe.getId() == getEntradaProduto().getEntradaNfe().getEntradaFiscal().getTributosSefazAm().getId())
//                                .findFirst().orElse(null)
//                );
//                txtNfeFiscalVlrNFe.setText(getEntradaProduto().getEntradaNfe().getEntradaFiscal().getVlrDocumento().toString());
//                txtNfeFiscalVlrTributo.setText(getEntradaProduto().getEntradaNfe().getEntradaFiscal().getVlrTributo().toString());
//                txtNfeFiscalVlrMulta.setText(getEntradaProduto().getEntradaNfe().getEntradaFiscal().getVlrMulta().toString());
//                txtNfeFiscalVlrJuros.setText(getEntradaProduto().getEntradaNfe().getEntradaFiscal().getVlrJuros().toString());
//                txtNfeFiscalVlrTaxa.setText(getEntradaProduto().getEntradaNfe().getEntradaFiscal().getVlrTaxa().toString());
//            }
//        }
//        if (getEntradaProduto().getEntradaCte() != null) {
//            tpnCteDetalhe.setExpanded(true);
//
//            txtCteChave.setText(getEntradaProduto().getEntradaCte().getChave());
//            cboCteTomadorServico.getSelectionModel().select(
//                    cboCteTomadorServico.getItems().stream()
//                            .filter(tomadorServico -> tomadorServico.getCod() == getEntradaProduto().getEntradaCte().getTomadorServico().getCod())
//                            .findFirst().orElse(null)
//            );
//            txtCteNumero.setText(getEntradaProduto().getEntradaCte().getNumero());
//            txtCteSerie.setText(getEntradaProduto().getEntradaCte().getSerie());
//            cboCteModelo.getSelectionModel().select(
//                    cboCteModelo.getItems().stream()
//                            .filter(modelCte -> modelCte.getCod() == getEntradaProduto().getEntradaCte().getModelo().getCod())
//                            .findFirst().orElse(null)
//            );
//            dtpCteEmissao.setValue(getEntradaProduto().getEntradaCte().getDataEmissao());
//            cboCteTransportadora.getSelectionModel().select(
//                    cboCteTransportadora.getItems().stream()
//                            .filter(transportadora -> transportadora.getId() == getEntradaProduto().getEntradaCte().getEmissor().getId())
//                            .findFirst().orElse(null)
//            );
//            if (getEntradaProduto().getEntradaCte().getSituacaoTributaria() == null)
//                cboCteSistuacaoTributaria.getSelectionModel().select(-1);
//            else
//                cboCteSistuacaoTributaria.getSelectionModel().select(
//                        cboCteSistuacaoTributaria.getItems().stream()
//                                .filter(situacaoTributaria -> situacaoTributaria.getId() == getEntradaProduto().getEntradaCte().getSituacaoTributaria().getId())
//                                .findFirst().orElse(null)
//                );
//
//            txtCteVlrCte.setText(getEntradaProduto().getEntradaCte().getVlrCte().toString());
//            txtCteQtdVolume.setText(String.valueOf(getEntradaProduto().getEntradaCte().getQtdVolume()));
//            txtCtePesoBruto.setText(getEntradaProduto().getEntradaCte().getPesoBruto().toString());
//            txtCteVlrBruto.setText(getEntradaProduto().getEntradaCte().getVlrFreteBruto().toString());
//            txtCteVlrTaxa.setText(getEntradaProduto().getEntradaCte().getVlrTaxas().toString());
//            txtCteVlrColeta.setText(getEntradaProduto().getEntradaCte().getVlrColeta().toString());
//            txtCteVlrImposto.setText(getEntradaProduto().getEntradaCte().getVlrImpostoFrete().toString());
//
//            if (getEntradaProduto().getEntradaCte().getEntradaFiscal() != null) {
//                tpnCteFiscal.setExpanded(true);
//
//                txtCteFiscalControle.setText(getEntradaProduto().getEntradaCte().getEntradaFiscal().getControle());
//                txtCteFiscalOrigem.setText(getEntradaProduto().getEntradaCte().getEntradaFiscal().getDocOrigem());
//                cboCteFiscalTributo.getSelectionModel().select(
//                        cboCteFiscalTributo.getItems().stream()
//                                .filter(fiscalCte -> fiscalCte.getId() == getEntradaProduto().getEntradaCte().getEntradaFiscal().getTributosSefazAm().getId())
//                                .findFirst().orElse(null)
//                );
//                txtCteFiscalVlrCte.setText(getEntradaProduto().getEntradaCte().getEntradaFiscal().getVlrDocumento().toString());
//                txtCteFiscalVlrTributo.setText(getEntradaProduto().getEntradaCte().getEntradaFiscal().getVlrTributo().toString());
//                txtCteFiscalVlrMulta.setText(getEntradaProduto().getEntradaCte().getEntradaFiscal().getVlrMulta().toString());
//                txtCteFiscalVlrJuros.setText(getEntradaProduto().getEntradaCte().getEntradaFiscal().getVlrJuros().toString());
//                txtCteFiscalVlrTaxa.setText(getEntradaProduto().getEntradaCte().getEntradaFiscal().getVlrTaxa().toString());
//            }
//        }
//
//        modelEntradaProdutoProduto.getEntradaProdutoProdutoObservableList().clear();
//        entradaProduto.getEntradaProdutoProdutoList().stream()
//                .forEach(entradaProdutoProduto ->
//                        modelEntradaProdutoProduto.getEntradaProdutoProdutoObservableList().add(entradaProdutoProduto)
//                );
//    }
//
//
//    private void addXmlNfe(File file) {
//        if (!file.getName().toLowerCase().contains("nfe")) return;
//        TNfeProc nfeProc = null;
//        try {
//            //nfeProc = ServiceXmlUtil.xmlToObject(ServiceXmlUtil.leXml(new FileInputStream(file)), TNfeProc.class);
//            nfeProc = ServiceUtilXml.xmlToObject(ServiceUtilXml.FileXml4String(new FileReader(file)), TNfeProc.class);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (nfeProc == null) return;
//        txtNfeChave.setText(nfeProc.getNFe().getInfNFe().getId().replaceAll("\\D", ""));
//        txtNfeNumero.setText(nfeProc.getNFe().getInfNFe().getIde().getNNF());
//        txtNfeSerie.setText(nfeProc.getNFe().getInfNFe().getIde().getSerie());
//
//        txtNfeFiscalVlrNFe.setText(nfeProc.getNFe().getInfNFe().getTotal().getICMSTot().getVNF());
//
//        TNfeProc finalNfeProc = nfeProc;
//        cboNfeModelo.getSelectionModel().select(
//                cboNfeModelo.getItems().stream()
//                        .filter(modeloNfeCte -> modeloNfeCte.getDescricao().equals(finalNfeProc.getNFe().getInfNFe().getIde().getMod()))
//                        .findFirst().orElse(null)
//        );
//        cboNfeLojaDestino.getSelectionModel().select(
//                cboNfeLojaDestino.getItems().stream()
//                        .filter(loja -> loja.getCnpj().equals(finalNfeProc.getNFe().getInfNFe().getDest().getCNPJ()))
//                        .findFirst().orElse(null)
//        );
//        cboNfeFornecedor.getSelectionModel().select(
//                cboNfeFornecedor.getItems().stream()
//                        .filter(fornecedor -> fornecedor.getCnpj().equals(finalNfeProc.getNFe().getInfNFe().getEmit().getCNPJ()))
//                        .findFirst().orElse(null)
//        );
//        dtpNfeEmissao.setValue(LocalDate.parse(nfeProc.getNFe().getInfNFe().getIde().getDhEmi(), DTF_NFE_TO_LOCAL_DATE));
//        dtpNfeEntrada.setValue(LocalDate.now());
//    }
//
//    private void addXmlCte(File file) {
//        if (!file.getName().toLowerCase().contains("cte")) return;
//        CteProc cteProc = null;
//        try {
//            //cteProc = ServiceXmlUtil.xmlToObject(ServiceXmlUtil.leXml(new FileInputStream(file)), CteProc.class);
//            cteProc = ServiceUtilXml.xmlToObject(ServiceUtilXml.FileXml4String(new FileReader(file)), CteProc.class);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (cteProc == null) return;
//
//        txtCteChave.setText(cteProc.getCTe().getInfCte().getId().replaceAll("\\D", ""));
//        txtCteNumero.setText(cteProc.getCTe().getInfCte().getIde().getNCT());
//        txtCteSerie.setText(cteProc.getCTe().getInfCte().getIde().getSerie());
//
//        txtCteVlrCte.setText(cteProc.getCTe().getInfCte().getVPrest().getVTPrest());
//        txtCteVlrImposto.setText(cteProc.getCTe().getInfCte().getImp().getICMS().getICMS00().getVICMS());
//
//        for (br.inf.portalfiscal.xsd.cte.procCTe.TCTe.InfCte.InfCTeNorm.InfCarga.InfQ infQ : cteProc.getCTe().getInfCte().getInfCTeNorm().getInfCarga().getInfQ())
//            switch (infQ.getTpMed().toLowerCase()) {
//                case "volume":
//                case "volumes":
//                    txtCteQtdVolume.setText(infQ.getQCarga());
//                    break;
//                case "peso bruto":
//                    txtCtePesoBruto.setText(BigDecimal.valueOf(Double.parseDouble(infQ.getQCarga())).setScale(2).toString());
//            }
//
//        double tmpTaxas = 0.;
//        for (br.inf.portalfiscal.xsd.cte.procCTe.TCTe.InfCte.VPrest.Comp comp : cteProc.getCTe().getInfCte().getVPrest().getComp())
//            if (comp.getXNome().toLowerCase().contains("peso"))
//                txtCteVlrBruto.setText(comp.getVComp());
//            else if (comp.getXNome().toLowerCase().contains("coleta"))
//                txtCteVlrColeta.setText(comp.getVComp());
//            else
//                tmpTaxas += Double.parseDouble(comp.getVComp());
//        txtCteVlrTaxa.setText(BigDecimal.valueOf(tmpTaxas).setScale(2).toString());
//
//        txtCteVlrLiquido.setText(cteProc.getCTe().getInfCte().getVPrest().getVTPrest());
//
//        txtCteFiscalVlrCte.setText(cteProc.getCTe().getInfCte().getVPrest().getVTPrest());
//
//        CteProc finalCteProc = cteProc;
//        cboCteTomadorServico.getSelectionModel().select(
//                cboCteTomadorServico.getItems().stream()
//                        .filter(tomadorServico -> tomadorServico.getCod() == Integer.valueOf(finalCteProc.getCTe().getInfCte().getIde().getToma3().getToma()))
//                        .findFirst().orElse(null)
//        );
//        cboCteModelo.getSelectionModel().select(
//                cboCteModelo.getItems().stream()
//                        .filter(modeloNfeCte -> modeloNfeCte.getDescricao().equals(finalCteProc.getCTe().getInfCte().getIde().getMod()))
//                        .findFirst().orElse(null)
//        );
//        cboCteTransportadora.getSelectionModel().select(
//                cboCteTransportadora.getItems().stream()
//                        .filter(transportadora -> transportadora.getCnpj().equals(finalCteProc.getCTe().getInfCte().getEmit().getCNPJ()))
//                        .findFirst().orElse(null)
//        );
//        cboCteSistuacaoTributaria.getSelectionModel().select(
//                cboCteSistuacaoTributaria.getItems().stream()
//                        .filter(situacaoTributaria -> situacaoTributaria.getId() == Integer.valueOf(finalCteProc.getCTe().getInfCte().getImp().getICMS().getICMS00().getCST()))
//                        .findFirst().orElse(null)
//        );
//        dtpCteEmissao.setValue(LocalDate.parse(cteProc.getCTe().getInfCte().getIde().getDhEmi(), DTF_NFE_TO_LOCAL_DATE));
//
//        if (txtNfeChave.getText().equals("")) {
//            File filetmp = null;
//            for (TCTe.InfCte.InfCTeNorm.InfDoc.InfNFe infNFe : cteProc.getCTe().getInfCte().getInfCTeNorm().getInfDoc().getInfNFe())
//                if ((filetmp = new File(file.getParent() + "/" + infNFe.getChave() + "-nfe.xml")).exists())
//                    addXmlNfe(filetmp);
//        }
//    }
//
//    @SuppressWarnings("Duplicates")
//
//    private void escutaTitledTab() {
//        getTpnNfeFiscal().expandedProperty().addListener((ov, o, n) -> {
//            int diff = 40;
//            if (!n) diff = (diff * (-1));
//            getTpnEntradaNfe().setPrefHeight(getTpnEntradaNfe().getPrefHeight() + (diff * 1));
//            getTpnNfeDetalhe().setPrefHeight(getTpnNfeDetalhe().getPrefHeight() + (diff * 1));
//            getTpnCteDetalhe().setLayoutY(getTpnCteDetalhe().getLayoutY() + (diff * 1));
//            getTpnItensTotaisNfe().setLayoutY(getTpnItensTotaisNfe().getLayoutY() + (diff * 1));
//            getTpnItensTotaisNfe().setPrefHeight(getTpnItensTotaisNfe().getPrefHeight() + (diff * -1));
//            getTpnItensNfe().setPrefHeight(getTpnItensNfe().getPrefHeight() + (diff * -1));
//            getTtvItensNfe().setPrefHeight(getTtvItensNfe().getPrefHeight() + (diff * -1));
//            if (n) {
//                getTxtNfeFiscalControle().requestFocus();
//            } else {
//                getTxtNfeChave().requestFocus();
//                ServiceCampoPersonalizado.fieldClear((AnchorPane) getTpnNfeFiscal().getContent());
//            }
//        });
//
//        getTpnCteDetalhe().expandedProperty().addListener((ov, o, n) -> {
//            int diff = 100;
//            if (!n) {
//                getTpnCteFiscal().setExpanded(false);
//                diff = (diff * (-1));
//            }
//            getTpnEntradaNfe().setPrefHeight(getTpnEntradaNfe().getPrefHeight() + (diff * 1));
//            getTpnItensTotaisNfe().setLayoutY(getTpnItensTotaisNfe().getLayoutY() + (diff * 1));
//            getTpnItensTotaisNfe().setPrefHeight(getTpnItensTotaisNfe().getPrefHeight() + (diff * -1));
//            getTpnItensNfe().setPrefHeight(getTpnItensNfe().getPrefHeight() + (diff * -1));
//            getTtvItensNfe().setPrefHeight(getTtvItensNfe().getPrefHeight() + (diff * -1));
//            if (n) {
//                getTxtCteChave().requestFocus();
//            } else {
//                getTxtNfeChave().requestFocus();
//                ServiceCampoPersonalizado.fieldClear((AnchorPane) getTpnCteDetalhe().getContent());
//            }
//        });
//
//        getTpnCteFiscal().expandedProperty().addListener((ov, o, n) -> {
//            int diff = 40;
//            if (!n) diff = (diff * (-1));
//            getTpnEntradaNfe().setPrefHeight(getTpnEntradaNfe().getPrefHeight() + (diff * 1));
//            getTpnCteDetalhe().setPrefHeight(getTpnCteDetalhe().getPrefHeight() + (diff * 1));
//            getTpnItensTotaisNfe().setLayoutY(getTpnItensTotaisNfe().getLayoutY() + (diff * 1));
//            getTpnItensTotaisNfe().setPrefHeight(getTpnItensTotaisNfe().getPrefHeight() + (diff * -1));
//            getTpnItensNfe().setPrefHeight(getTpnItensNfe().getPrefHeight() + (diff * -1));
//            getTtvItensNfe().setPrefHeight(getTtvItensNfe().getPrefHeight() + (diff * -1));
//            if (n) {
//                getTxtCteFiscalVlrCte().setText(getTxtCteVlrLiquido().getText());
//                getTxtCteFiscalControle().requestFocus();
//            } else {
//                getTxtCteChave().requestFocus();
//                ServiceCampoPersonalizado.fieldClear((AnchorPane) getTpnCteFiscal().getContent());
//            }
//        });
//    }
//
//    @SuppressWarnings("Duplicates")
//    private void resultVlrTributoNfe() {
//        BigDecimal vlrNfe = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrNFe().getText(), 2);
//        BigDecimal vlrTributo = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrTributo().getText(), 2);
//        BigDecimal vlrMulta = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrMulta().getText(), 2);
//        BigDecimal vlrJuros = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrJuros().getText(), 2);
//        BigDecimal vlrTaxa = ServiceMascara.getBigDecimalFromTextField(getTxtNfeFiscalVlrTaxa().getText(), 2);
//
//        BigDecimal vlrTotal = vlrTributo.add(vlrMulta).add(vlrJuros).add(vlrTaxa);
//        BigDecimal vlrPercentual = BigDecimal.valueOf((vlrTotal.doubleValue() / (vlrNfe.doubleValue() == 0. ? 0.00000000001 : vlrNfe.doubleValue())) * 100);
//
//        getTxtNfeFiscalVlrTotal().setText(vlrTotal.setScale(2).toString());
//        getTxtNfeFiscalVlrPercentual().setText(vlrPercentual.setScale(2, RoundingMode.HALF_UP).toString());
//    }
//
//    @SuppressWarnings("Duplicates")
//    private void resultVlrTributoCte() {
//        BigDecimal vlrCte = ServiceMascara.getBigDecimalFromTextField(txtCteFiscalVlrCte.getText(), 2);
//        BigDecimal vlrTributo = ServiceMascara.getBigDecimalFromTextField(txtCteFiscalVlrTributo.getText(), 2);
//        BigDecimal vlrMulta = ServiceMascara.getBigDecimalFromTextField(txtCteFiscalVlrMulta.getText(), 2);
//        BigDecimal vlrJuros = ServiceMascara.getBigDecimalFromTextField(txtCteFiscalVlrJuros.getText(), 2);
//        BigDecimal vlrTaxa = ServiceMascara.getBigDecimalFromTextField(txtCteFiscalVlrTaxa.getText(), 2);
//
//        BigDecimal vlrTotal = vlrTributo.add(vlrMulta).add(vlrJuros).add(vlrTaxa);
//        BigDecimal vlrPercentual = BigDecimal.valueOf((vlrTotal.doubleValue() / (vlrCte.doubleValue() == 0. ? 0.00000000001 : vlrCte.doubleValue())) * 100);
//
//        txtCteFiscalVlrTotal.setText(vlrTotal.setScale(2).toString());
//        txtCteFiscalVlrPercentual.setText(vlrPercentual.setScale(2, RoundingMode.HALF_UP).toString());
////        modelEntradaProdutoProduto.setTotalImpostoEntrada(
////                vlrTotal.add(ServiceMascara.getBigDecimalFromTextField(txtNfeFiscalVlrTotal.getText(), 2))
////        );
//    }
//
//    public void totalizaTabela() {
//        setNumItens(getEntradaProdutoProdutoObservableList().stream()
//                .collect(Collectors.groupingBy(EntradaProdutoProduto::getDescricao, Collectors.counting()))
//                .size());
//        setQtdItens(getEntradaProdutoProdutoObservableList().stream().collect(Collectors.summingInt(EntradaProdutoProduto::getQtd)));
//        setQtdVolume(getEntradaProdutoProdutoObservableList().stream().collect(Collectors.summingInt(EntradaProdutoProduto::getVolume)));
//
//        BigDecimal totBruto = getEntradaProdutoProdutoObservableList().stream().map(EntradaProdutoProduto::getVlrBruto)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        setTotalBruto(totBruto.setScale(2));
//
//        BigDecimal totImpostoItens = getEntradaProdutoProdutoObservableList().stream().map(EntradaProdutoProduto::getVlrImposto)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        setTotalImpostoItens(totImpostoItens);
//
//        BigDecimal totFrete = getTotalFrete();
//
//        BigDecimal totDesconto = getEntradaProdutoProdutoObservableList().stream().map(EntradaProdutoProduto::getVlrDesconto)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        setTotalDesconto(totDesconto.setScale(2));
//    }
//
//    /**
//     * END
//     * voids
//     */
//
//    /**
//     * start
//     * Getters e setters
//     */
//
//    public AnchorPane getPainelViewEntradaProduto() {
//        return painelViewEntradaProduto;
//    }
//
//    public void setPainelViewEntradaProduto(AnchorPane painelViewEntradaProduto) {
//        this.painelViewEntradaProduto = painelViewEntradaProduto;
//    }
//
//    public TitledPane getTpnEntradaNfe() {
//        return tpnEntradaNfe;
//    }
//
//    public void setTpnEntradaNfe(TitledPane tpnEntradaNfe) {
//        this.tpnEntradaNfe = tpnEntradaNfe;
//    }
//
//    public TitledPane getTpnNfeDetalhe() {
//        return tpnNfeDetalhe;
//    }
//
//    public void setTpnNfeDetalhe(TitledPane tpnNfeDetalhe) {
//        this.tpnNfeDetalhe = tpnNfeDetalhe;
//    }
//
//    public ComboBox<Empresa> getCboNfeLojaDestino() {
//        return cboNfeLojaDestino;
//    }
//
//    public void setCboNfeLojaDestino(ComboBox<Empresa> cboNfeLojaDestino) {
//        this.cboNfeLojaDestino = cboNfeLojaDestino;
//    }
//
//    public TextField getTxtNfeChave() {
//        return txtNfeChave;
//    }
//
//    public void setTxtNfeChave(TextField txtNfeChave) {
//        this.txtNfeChave = txtNfeChave;
//    }
//
//    public TextField getTxtNfeNumero() {
//        return txtNfeNumero;
//    }
//
//    public void setTxtNfeNumero(TextField txtNfeNumero) {
//        this.txtNfeNumero = txtNfeNumero;
//    }
//
//    public TextField getTxtNfeSerie() {
//        return txtNfeSerie;
//    }
//
//    public void setTxtNfeSerie(TextField txtNfeSerie) {
//        this.txtNfeSerie = txtNfeSerie;
//    }
//
//    public ComboBox<NfeCteModelo> getCboNfeModelo() {
//        return cboNfeModelo;
//    }
//
//    public void setCboNfeModelo(ComboBox<NfeCteModelo> cboNfeModelo) {
//        this.cboNfeModelo = cboNfeModelo;
//    }
//
//    public ComboBox<Empresa> getCboNfeFornecedor() {
//        return cboNfeFornecedor;
//    }
//
//    public void setCboNfeFornecedor(ComboBox<Empresa> cboNfeFornecedor) {
//        this.cboNfeFornecedor = cboNfeFornecedor;
//    }
//
//    public DatePicker getDtpNfeEmissao() {
//        return dtpNfeEmissao;
//    }
//
//    public void setDtpNfeEmissao(DatePicker dtpNfeEmissao) {
//        this.dtpNfeEmissao = dtpNfeEmissao;
//    }
//
//    public DatePicker getDtpNfeEntrada() {
//        return dtpNfeEntrada;
//    }
//
//    public void setDtpNfeEntrada(DatePicker dtpNfeEntrada) {
//        this.dtpNfeEntrada = dtpNfeEntrada;
//    }
//
//    public TitledPane getTpnNfeFiscal() {
//        return tpnNfeFiscal;
//    }
//
//    public void setTpnNfeFiscal(TitledPane tpnNfeFiscal) {
//        this.tpnNfeFiscal = tpnNfeFiscal;
//    }
//
//    public TextField getTxtNfeFiscalControle() {
//        return txtNfeFiscalControle;
//    }
//
//    public void setTxtNfeFiscalControle(TextField txtNfeFiscalControle) {
//        this.txtNfeFiscalControle = txtNfeFiscalControle;
//    }
//
//    public TextField getTxtNfeFiscalOrigem() {
//        return txtNfeFiscalOrigem;
//    }
//
//    public void setTxtNfeFiscalOrigem(TextField txtNfeFiscalOrigem) {
//        this.txtNfeFiscalOrigem = txtNfeFiscalOrigem;
//    }
//
//    public ComboBox<FiscalTributosSefazAm> getCboNfeFiscalTributo() {
//        return cboNfeFiscalTributo;
//    }
//
//    public void setCboNfeFiscalTributo(ComboBox<FiscalTributosSefazAm> cboNfeFiscalTributo) {
//        this.cboNfeFiscalTributo = cboNfeFiscalTributo;
//    }
//
//    public TextField getTxtNfeFiscalVlrNFe() {
//        return txtNfeFiscalVlrNFe;
//    }
//
//    public void setTxtNfeFiscalVlrNFe(TextField txtNfeFiscalVlrNFe) {
//        this.txtNfeFiscalVlrNFe = txtNfeFiscalVlrNFe;
//    }
//
//    public TextField getTxtNfeFiscalVlrTributo() {
//        return txtNfeFiscalVlrTributo;
//    }
//
//    public void setTxtNfeFiscalVlrTributo(TextField txtNfeFiscalVlrTributo) {
//        this.txtNfeFiscalVlrTributo = txtNfeFiscalVlrTributo;
//    }
//
//    public TextField getTxtNfeFiscalVlrMulta() {
//        return txtNfeFiscalVlrMulta;
//    }
//
//    public void setTxtNfeFiscalVlrMulta(TextField txtNfeFiscalVlrMulta) {
//        this.txtNfeFiscalVlrMulta = txtNfeFiscalVlrMulta;
//    }
//
//    public TextField getTxtNfeFiscalVlrJuros() {
//        return txtNfeFiscalVlrJuros;
//    }
//
//    public void setTxtNfeFiscalVlrJuros(TextField txtNfeFiscalVlrJuros) {
//        this.txtNfeFiscalVlrJuros = txtNfeFiscalVlrJuros;
//    }
//
//    public TextField getTxtNfeFiscalVlrTaxa() {
//        return txtNfeFiscalVlrTaxa;
//    }
//
//    public void setTxtNfeFiscalVlrTaxa(TextField txtNfeFiscalVlrTaxa) {
//        this.txtNfeFiscalVlrTaxa = txtNfeFiscalVlrTaxa;
//    }
//
//    public TextField getTxtNfeFiscalVlrTotal() {
//        return txtNfeFiscalVlrTotal;
//    }
//
//    public void setTxtNfeFiscalVlrTotal(TextField txtNfeFiscalVlrTotal) {
//        this.txtNfeFiscalVlrTotal = txtNfeFiscalVlrTotal;
//    }
//
//    public TextField getTxtNfeFiscalVlrPercentual() {
//        return txtNfeFiscalVlrPercentual;
//    }
//
//    public void setTxtNfeFiscalVlrPercentual(TextField txtNfeFiscalVlrPercentual) {
//        this.txtNfeFiscalVlrPercentual = txtNfeFiscalVlrPercentual;
//    }
//
//    public TitledPane getTpnCteDetalhe() {
//        return tpnCteDetalhe;
//    }
//
//    public void setTpnCteDetalhe(TitledPane tpnCteDetalhe) {
//        this.tpnCteDetalhe = tpnCteDetalhe;
//    }
//
//    public TextField getTxtCteChave() {
//        return txtCteChave;
//    }
//
//    public void setTxtCteChave(TextField txtCteChave) {
//        this.txtCteChave = txtCteChave;
//    }
//
//    public ComboBox<CteTomadorServico> getCboCteTomadorServico() {
//        return cboCteTomadorServico;
//    }
//
//    public void setCboCteTomadorServico(ComboBox<CteTomadorServico> cboCteTomadorServico) {
//        this.cboCteTomadorServico = cboCteTomadorServico;
//    }
//
//    public TextField getTxtCteNumero() {
//        return txtCteNumero;
//    }
//
//    public void setTxtCteNumero(TextField txtCteNumero) {
//        this.txtCteNumero = txtCteNumero;
//    }
//
//    public TextField getTxtCteSerie() {
//        return txtCteSerie;
//    }
//
//    public void setTxtCteSerie(TextField txtCteSerie) {
//        this.txtCteSerie = txtCteSerie;
//    }
//
//    public ComboBox<NfeCteModelo> getCboCteModelo() {
//        return cboCteModelo;
//    }
//
//    public void setCboCteModelo(ComboBox<NfeCteModelo> cboCteModelo) {
//        this.cboCteModelo = cboCteModelo;
//    }
//
//    public ComboBox<FiscalFreteSituacaoTributaria> getCboCteSistuacaoTributaria() {
//        return cboCteSistuacaoTributaria;
//    }
//
//    public void setCboCteSistuacaoTributaria(ComboBox<FiscalFreteSituacaoTributaria> cboCteSistuacaoTributaria) {
//        this.cboCteSistuacaoTributaria = cboCteSistuacaoTributaria;
//    }
//
//    public ComboBox<Empresa> getCboCteTransportadora() {
//        return cboCteTransportadora;
//    }
//
//    public void setCboCteTransportadora(ComboBox<Empresa> cboCteTransportadora) {
//        this.cboCteTransportadora = cboCteTransportadora;
//    }
//
//    public DatePicker getDtpCteEmissao() {
//        return dtpCteEmissao;
//    }
//
//    public void setDtpCteEmissao(DatePicker dtpCteEmissao) {
//        this.dtpCteEmissao = dtpCteEmissao;
//    }
//
//    public TextField getTxtCteVlrCte() {
//        return txtCteVlrCte;
//    }
//
//    public void setTxtCteVlrCte(TextField txtCteVlrCte) {
//        this.txtCteVlrCte = txtCteVlrCte;
//    }
//
//    public TextField getTxtCteQtdVolume() {
//        return txtCteQtdVolume;
//    }
//
//    public void setTxtCteQtdVolume(TextField txtCteQtdVolume) {
//        this.txtCteQtdVolume = txtCteQtdVolume;
//    }
//
//    public TextField getTxtCtePesoBruto() {
//        return txtCtePesoBruto;
//    }
//
//    public void setTxtCtePesoBruto(TextField txtCtePesoBruto) {
//        this.txtCtePesoBruto = txtCtePesoBruto;
//    }
//
//    public TextField getTxtCteVlrBruto() {
//        return txtCteVlrBruto;
//    }
//
//    public void setTxtCteVlrBruto(TextField txtCteVlrBruto) {
//        this.txtCteVlrBruto = txtCteVlrBruto;
//    }
//
//    public TextField getTxtCteVlrTaxa() {
//        return txtCteVlrTaxa;
//    }
//
//    public void setTxtCteVlrTaxa(TextField txtCteVlrTaxa) {
//        this.txtCteVlrTaxa = txtCteVlrTaxa;
//    }
//
//    public TextField getTxtCteVlrColeta() {
//        return txtCteVlrColeta;
//    }
//
//    public void setTxtCteVlrColeta(TextField txtCteVlrColeta) {
//        this.txtCteVlrColeta = txtCteVlrColeta;
//    }
//
//    public TextField getTxtCteVlrImposto() {
//        return txtCteVlrImposto;
//    }
//
//    public void setTxtCteVlrImposto(TextField txtCteVlrImposto) {
//        this.txtCteVlrImposto = txtCteVlrImposto;
//    }
//
//    public TextField getTxtCteVlrLiquido() {
//        return txtCteVlrLiquido;
//    }
//
//    public void setTxtCteVlrLiquido(TextField txtCteVlrLiquido) {
//        this.txtCteVlrLiquido = txtCteVlrLiquido;
//    }
//
//    public TitledPane getTpnCteFiscal() {
//        return tpnCteFiscal;
//    }
//
//    public void setTpnCteFiscal(TitledPane tpnCteFiscal) {
//        this.tpnCteFiscal = tpnCteFiscal;
//    }
//
//    public TextField getTxtCteFiscalControle() {
//        return txtCteFiscalControle;
//    }
//
//    public void setTxtCteFiscalControle(TextField txtCteFiscalControle) {
//        this.txtCteFiscalControle = txtCteFiscalControle;
//    }
//
//    public TextField getTxtCteFiscalOrigem() {
//        return txtCteFiscalOrigem;
//    }
//
//    public void setTxtCteFiscalOrigem(TextField txtCteFiscalOrigem) {
//        this.txtCteFiscalOrigem = txtCteFiscalOrigem;
//    }
//
//    public ComboBox<FiscalTributosSefazAm> getCboCteFiscalTributo() {
//        return cboCteFiscalTributo;
//    }
//
//    public void setCboCteFiscalTributo(ComboBox<FiscalTributosSefazAm> cboCteFiscalTributo) {
//        this.cboCteFiscalTributo = cboCteFiscalTributo;
//    }
//
//    public TextField getTxtCteFiscalVlrCte() {
//        return txtCteFiscalVlrCte;
//    }
//
//    public void setTxtCteFiscalVlrCte(TextField txtCteFiscalVlrCte) {
//        this.txtCteFiscalVlrCte = txtCteFiscalVlrCte;
//    }
//
//    public TextField getTxtCteFiscalVlrTributo() {
//        return txtCteFiscalVlrTributo;
//    }
//
//    public void setTxtCteFiscalVlrTributo(TextField txtCteFiscalVlrTributo) {
//        this.txtCteFiscalVlrTributo = txtCteFiscalVlrTributo;
//    }
//
//    public TextField getTxtCteFiscalVlrMulta() {
//        return txtCteFiscalVlrMulta;
//    }
//
//    public void setTxtCteFiscalVlrMulta(TextField txtCteFiscalVlrMulta) {
//        this.txtCteFiscalVlrMulta = txtCteFiscalVlrMulta;
//    }
//
//    public TextField getTxtCteFiscalVlrJuros() {
//        return txtCteFiscalVlrJuros;
//    }
//
//    public void setTxtCteFiscalVlrJuros(TextField txtCteFiscalVlrJuros) {
//        this.txtCteFiscalVlrJuros = txtCteFiscalVlrJuros;
//    }
//
//    public TextField getTxtCteFiscalVlrTaxa() {
//        return txtCteFiscalVlrTaxa;
//    }
//
//    public void setTxtCteFiscalVlrTaxa(TextField txtCteFiscalVlrTaxa) {
//        this.txtCteFiscalVlrTaxa = txtCteFiscalVlrTaxa;
//    }
//
//    public TextField getTxtCteFiscalVlrTotal() {
//        return txtCteFiscalVlrTotal;
//    }
//
//    public void setTxtCteFiscalVlrTotal(TextField txtCteFiscalVlrTotal) {
//        this.txtCteFiscalVlrTotal = txtCteFiscalVlrTotal;
//    }
//
//    public TextField getTxtCteFiscalVlrPercentual() {
//        return txtCteFiscalVlrPercentual;
//    }
//
//    public void setTxtCteFiscalVlrPercentual(TextField txtCteFiscalVlrPercentual) {
//        this.txtCteFiscalVlrPercentual = txtCteFiscalVlrPercentual;
//    }
//
//    public TitledPane getTpnItensTotaisNfe() {
//        return tpnItensTotaisNfe;
//    }
//
//    public void setTpnItensTotaisNfe(TitledPane tpnItensTotaisNfe) {
//        this.tpnItensTotaisNfe = tpnItensTotaisNfe;
//    }
//
//    public TitledPane getTpnCadastroProduto() {
//        return tpnCadastroProduto;
//    }
//
//    public void setTpnCadastroProduto(TitledPane tpnCadastroProduto) {
//        this.tpnCadastroProduto = tpnCadastroProduto;
//    }
//
//    public TextField getTxtPesquisaProduto() {
//        return txtPesquisaProduto;
//    }
//
//    public void setTxtPesquisaProduto(TextField txtPesquisaProduto) {
//        this.txtPesquisaProduto = txtPesquisaProduto;
//    }
//
//    public TreeTableView<Produto> getTtvProduto() {
//        return ttvProduto;
//    }
//
//    public void setTtvProduto(TreeTableView<Produto> ttvProduto) {
//        this.ttvProduto = ttvProduto;
//    }
//
//    public Label getLblStatus() {
//        return lblStatus;
//    }
//
//    public void setLblStatus(Label lblStatus) {
//        this.lblStatus = lblStatus;
//    }
//
//    public Label getLblRegistrosLocalizados() {
//        return lblRegistrosLocalizados;
//    }
//
//    public void setLblRegistrosLocalizados(Label lblRegistrosLocalizados) {
//        this.lblRegistrosLocalizados = lblRegistrosLocalizados;
//    }
//
//    public TableView<EntradaProdutoProduto> getTtvItensNfe() {
//        return ttvItensNfe;
//    }
//
//    public void setTtvItensNfe(TableView<EntradaProdutoProduto> ttvItensNfe) {
//        this.ttvItensNfe = ttvItensNfe;
//    }
//
//    public Label getLblQtdItem() {
//        return lblQtdItem;
//    }
//
//    public void setLblQtdItem(Label lblQtdItem) {
//        this.lblQtdItem = lblQtdItem;
//    }
//
//    public Label getLblQtdTotal() {
//        return lblQtdTotal;
//    }
//
//    public void setLblQtdTotal(Label lblQtdTotal) {
//        this.lblQtdTotal = lblQtdTotal;
//    }
//
//    public Label getLblQtdVolume() {
//        return lblQtdVolume;
//    }
//
//    public void setLblQtdVolume(Label lblQtdVolume) {
//        this.lblQtdVolume = lblQtdVolume;
//    }
//
//    public Label getLblTotalBruto() {
//        return lblTotalBruto;
//    }
//
//    public void setLblTotalBruto(Label lblTotalBruto) {
//        this.lblTotalBruto = lblTotalBruto;
//    }
//
//    public Label getLblTotalImposto() {
//        return lblTotalImposto;
//    }
//
//    public void setLblTotalImposto(Label lblTotalImposto) {
//        this.lblTotalImposto = lblTotalImposto;
//    }
//
//    public Label getLblTotalFrete() {
//        return lblTotalFrete;
//    }
//
//    public void setLblTotalFrete(Label lblTotalFrete) {
//        this.lblTotalFrete = lblTotalFrete;
//    }
//
//    public Label getLblTotalDesconto() {
//        return lblTotalDesconto;
//    }
//
//    public void setLblTotalDesconto(Label lblTotalDesconto) {
//        this.lblTotalDesconto = lblTotalDesconto;
//    }
//
//    public Label getLblTotalLiquido() {
//        return lblTotalLiquido;
//    }
//
//    public void setLblTotalLiquido(Label lblTotalLiquido) {
//        this.lblTotalLiquido = lblTotalLiquido;
//    }
//
//    public boolean isTabCarregada() {
//        return tabCarregada;
//    }
//
//    public void setTabCarregada(boolean tabCarregada) {
//        this.tabCarregada = tabCarregada;
//    }
//
//    public EventHandler getEventHandlerEntradaProduto() {
//        return eventHandlerEntradaProduto;
//    }
//
//    public void setEventHandlerEntradaProduto(EventHandler eventHandlerEntradaProduto) {
//        this.eventHandlerEntradaProduto = eventHandlerEntradaProduto;
//    }
//
//    public StatusBarEntradaProduto getStatusBar() {
//        return statusBar.get();
//    }
//
//    public ObjectProperty<StatusBarEntradaProduto> statusBarProperty() {
//        return statusBar;
//    }
//
//    public void setStatusBar(StatusBarEntradaProduto statusBar) {
//        this.statusBar.set(statusBar);
//    }
//
//    public String getNomeController() {
//        return nomeController;
//    }
//
//    public void setNomeController(String nomeController) {
//        this.nomeController = nomeController;
//    }
//
//    public String getNomeTab() {
//        return nomeTab;
//    }
//
//    public void setNomeTab(String nomeTab) {
//        this.nomeTab = nomeTab;
//    }
//
//    public ServiceAlertMensagem getAlertMensagem() {
//        return alertMensagem;
//    }
//
//    public void setAlertMensagem(ServiceAlertMensagem alertMensagem) {
//        this.alertMensagem = alertMensagem;
//    }
//
//    public List<Pair> getListaTarefa() {
//        return listaTarefa;
//    }
//
//    public void setListaTarefa(List<Pair> listaTarefa) {
//        this.listaTarefa = listaTarefa;
//    }
//
//    public TmodelProduto getTmodelProduto() {
//        return tmodelProduto;
//    }
//
//    public void setTmodelProduto(TmodelProduto tmodelProduto) {
//        this.tmodelProduto = tmodelProduto;
//    }
//
//    public TablePosition getTp() {
//        return tp;
//    }
//
//    public void setTp(TablePosition tp) {
//        this.tp = tp;
//    }
//
//    /**
//     * END
//     * Getters e setters
//     */

}
