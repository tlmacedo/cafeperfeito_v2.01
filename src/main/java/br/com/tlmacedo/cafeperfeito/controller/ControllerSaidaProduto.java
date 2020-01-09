package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.model.dao.ContasAReceberDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.EmpresaDAO;
import br.com.tlmacedo.cafeperfeito.model.dao.SaidaProdutoDAO;
import br.com.tlmacedo.cafeperfeito.model.enums.*;
import br.com.tlmacedo.cafeperfeito.model.tm.TmodelProduto;
import br.com.tlmacedo.cafeperfeito.model.tm.TmodelSaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.*;
import br.com.tlmacedo.cafeperfeito.service.*;
import br.com.tlmacedo.cafeperfeito.service.autoComplete.ServiceAutoCompleteComboBox;
import br.com.tlmacedo.cafeperfeito.view.ViewSaidaProduto;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_DATA;
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

    public TitledPane tpnProdutos;
    public TextField txtPesquisaProduto;
    public Label lblRegistrosLocalizados;
    public TreeTableView<Produto> ttvProduto;

    public TitledPane tpnItensPedido;
    public TableView<SaidaProdutoProduto> tvItensNfe;
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
    private ObjectProperty<StatusBarSaidaProduto> statusBar = new SimpleObjectProperty<>(StatusBarSaidaProduto.DIGITACAO);
    private EventHandler eventHandlerSaidaProduto;
    private ServiceAlertMensagem alertMensagem;

    private TmodelProduto tmodelProduto;
    private FilteredList<Produto> produtoFilteredList;

    private TmodelSaidaProduto tmodelSaidaProduto;
    private SaidaProduto saidaProduto = new SaidaProduto();
    private SaidaProdutoDAO saidaProdutoDAO = new SaidaProdutoDAO();
    private ObservableList<SaidaProdutoProduto> saidaProdutoProdutoObservableList = FXCollections.observableArrayList();

    private ObservableList<Empresa> empresaObservableList = FXCollections.observableArrayList(
            new EmpresaDAO().getAll(Empresa.class, null, "razao, fantasia"));
    private ObjectProperty<Empresa> empresa = new SimpleObjectProperty<>();
    private ObjectProperty<List<Endereco>> enderecoList = new SimpleObjectProperty<>();
    private ObjectProperty<Endereco> endereco = new SimpleObjectProperty<>();
    private ObjectProperty<List<Telefone>> telefoneList = new SimpleObjectProperty<>();

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
    public void fatorarObjetos() throws Exception {

    }

    @Override
    public void escutarTecla() throws Exception {
        statusBarProperty().addListener((ov, o, n) -> {
            if (n == null)
                statusBarProperty().setValue(StatusBarSaidaProduto.DIGITACAO);
            showStatusBar();
        });

        getTtvProduto().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER
                    || getTtvProduto().getSelectionModel().getSelectedItem() == null
                    || getTtvProduto().getSelectionModel().getSelectedItem().getValue().tblEstoqueProperty().getValue() <= 0)
                return;
            Produto produtoSelecionado = getProdutoSelecionado();
            if (getSaidaProdutoProdutoObservableList().stream()
                    .filter(saidaProdutoProduto -> saidaProdutoProduto.loteProperty().getValue().equals(produtoSelecionado.tblLoteProperty().getValue())
                            && saidaProdutoProduto.getProduto().idProperty().getValue().intValue() == produtoSelecionado.idProperty().getValue().intValue())
                    .findFirst().orElse(null) == null) {
                getSaidaProdutoProdutoObservableList().add(new SaidaProdutoProduto(produtoSelecionado, TipoCodigoCFOP.COMERCIALIZACAO, 1));
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F8));
                if (empresaProperty().getValue() != null)
                    getTmodelSaidaProduto().calculaDescontoCliente();
            } else {
                for (int i = 0; i < getSaidaProdutoProdutoObservableList().size(); i++) {
                    SaidaProdutoProduto saida = getSaidaProdutoProdutoObservableList().get(i);
                    if (saida.loteProperty().getValue().equals(produtoSelecionado.tblLoteProperty().getValue())
                            && saida.produtoProperty().getValue().idProperty().getValue().intValue()
                            == produtoSelecionado.idProperty().getValue().intValue()) {
                        getTvItensNfe().requestFocus();
                        getTvItensNfe().getSelectionModel().select(i, getTmodelSaidaProduto().getColQtd());
                    }
                }
            }
        });

        getTvItensNfe().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.HELP)
                return;
            Produto produtoAdicional = new Produto(getTvItensNfe().getSelectionModel().getSelectedItem().produtoProperty().getValue());
            getSaidaProdutoProdutoObservableList().add(new SaidaProdutoProduto(produtoAdicional, TipoCodigoCFOP.AMOSTRA, 1));
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
                        case F6:
                            getCboEmpresa().getEditor().setEditable(true);
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

        new ServiceAutoCompleteComboBox(Empresa.class, getCboEmpresa());

        getCboEmpresa().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER)
                return;
            if (getCboEmpresa().getSelectionModel().getSelectedItem() != null)
                getTxtPesquisaProduto().requestFocus();
        });

        getCboEmpresa().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            if (n == null) {
                if (getCboEmpresa().getItems().size() > 0)
                    n = getCboEmpresa().getItems().get(0);
                limpaCampos(getTpnCliente());
            }
            getDtpDtSaida().setValue(LocalDate.now());
            getLblLimite().setText(ServiceMascara.getMoeda(n.limiteProperty().getValue(), 2));
            getLblLimiteUtilizado().setText(ServiceMascara.getMoeda(n.limiteUtilizadoProperty().getValue(), 2));
            getLblLimiteDisponivel().setText(ServiceMascara.getMoeda(
                    n.limiteProperty().getValue().subtract(n.limiteUtilizadoProperty().getValue()), 2));
            getLblPrazo().setText(n.prazoProperty().getValue().toString());
            getLabelUltimoPedidoDt().setText("Últ. pedido");
            if (n.dtUltimoPedidoProperty().getValue() == null) {
                if (n.dtCadastroProperty().getValue() == null) {
                    getLblUltimoPedidoDt().setText("");
                    getLblUltimoPedidoDias().setText("");
                } else {
                    getLabelUltimoPedidoDt().setText("Dt. cadastro");
                    getLblUltimoPedidoDt().setText(n.dtCadastroProperty().getValue().format(DTF_DATA));
                    getLblUltimoPedidoDias().setText(String.valueOf(DAYS.between(n.dtCadastroProperty().getValue().toLocalDate(), LocalDate.now())));
                }
            } else {
                getLblUltimoPedidoDt().setText(n.dtUltimoPedidoProperty().getValue().format(DTF_DATA));
                getLblUltimoPedidoDias().setText(String.valueOf(DAYS.between(n.dtUltimoPedidoProperty().getValue(), LocalDate.now())));
            }
            getLblUltimoPedidoVlr().setText(ServiceMascara.getMoeda(n.vlrUltimoPedidoProperty().getValue(), 2));
            getLblQtdPedidos().setText(n.qtdPedidosProperty().getValue().toString());
            getLblTicketMedioVlr().setText(ServiceMascara.getMoeda(n.vlrTickeMedioProperty().getValue(), 2));

            getCboEndereco().getItems().clear();
            getCboEndereco().getItems().setAll(n.getEnderecoList());
            getCboEndereco().getSelectionModel().select(getCboEndereco().getItems().stream()
                    .filter(endereco1 -> endereco1.getTipo().equals(TipoEndereco.ENTREGA))
                    .findFirst().orElse(getCboEndereco().getItems().stream().findFirst().orElse(null)));

            getCboTelefone().getItems().clear();
            getCboTelefone().getItems().setAll(n.getTelefoneList());
            getCboTelefone().getSelectionModel().select(getCboTelefone().getItems().stream()
                    .filter(telefone -> telefone.isPrincipal())
                    .findFirst().orElse(getCboTelefone().getItems().stream().findFirst().orElse(null)));
        });

        getCboEndereco().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            limpaEndereco();
            if (n == null)
                return;
            getLblLogradoruro().setText(n.logradouroProperty().getValue());
            getLblNumero().setText(n.numeroProperty().getValue());
            getLblBairro().setText(n.bairroProperty().getValue());
            getLblComplemento().setText(n.complementoProperty().getValue());
        });

        getCboEndereco().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() != KeyCode.ENTER)
                return;
            getTxtPesquisaProduto().requestFocus();
        });

    }

    /**
     * Begin Tasks
     */

    private Task newTaskSaidaProduto() {
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
                                setTmodelProduto(new TmodelProduto(TModelTipo.PROD_VENDA));
                                getTmodelProduto().criaTabela();

                                setTmodelSaidaProduto(new TmodelSaidaProduto());
                                getTmodelSaidaProduto().criaTabela();
                                break;

                            case TABELA_VINCULAR:
                                getTmodelProduto().setLblRegistrosLocalizados(getLblRegistrosLocalizados());
                                getTmodelProduto().setTtvProduto(getTtvProduto());
                                getTmodelProduto().setTxtPesquisa(getTxtPesquisaProduto());
                                setProdutoFilteredList(getTmodelProduto().getProdutoFilteredList());
                                getTmodelProduto().escutaLista();

                                getTmodelSaidaProduto().setTvSaidaProdutoProduto(getTvItensNfe());
                                getTmodelSaidaProduto().setTxtPesquisa(getTxtPesquisaProduto());
                                getTmodelSaidaProduto().setDtpDtSaida(getDtpDtSaida());
                                getTmodelSaidaProduto().setDtpDtVencimento(getDtpDtVencimento());
//                                empresaProperty().setValue(getTmodelSaidaProduto().empresaProperty().getValue());
                                getTmodelSaidaProduto().setEmpresa(empresaProperty().getValue());
                                getTmodelSaidaProduto().setSaidaProduto(getSaidaProduto());
                                getTmodelSaidaProduto().setSaidaProdutoProdutoObservableList(getSaidaProdutoProdutoObservableList());
//                                setSaidaProduto(getTmodelSaidaProduto().getSaidaProduto());
//                                setSaidaProdutoProdutoObservableList(getTmodelSaidaProduto().getSaidaProdutoProdutoObservableList());
                                getTmodelSaidaProduto().escutaLista();
                                break;
                            case COMBOS_PREENCHER:
                                ServiceCalculaTempo tempo = new ServiceCalculaTempo();
                                loadListaEmpresas();
//                                informacoesAdicionais();
                                //tempo.fim();

                                //tempo.start();
//                                getCboEmpresa().setItems(getEmpresaObservableList().stream()
//                                        .filter(clientes -> clientes.isCliente())
//                                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                //tempo.start();
                                getCboEmpresa().getItems().add(0, new Empresa());
                                //tempo.fim();

                                //tempo.start();
                                getCboNfeDadosNaturezaOperacao().setItems(
                                        Arrays.stream(NfeDadosNaturezaOperacao.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                //tempo.start();
                                getCboNfeDadosModelo().setItems(
                                        Arrays.stream(NfeDadosModelo.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                //tempo.start();
                                getCboNfeDadosDestinoOperacao().setItems(
                                        Arrays.stream(NfeDadosDestinoOperacao.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                //tempo.start();
                                getCboNfeDadosIndicadorConsumidorFinal().setItems(
                                        Arrays.stream(NfeDadosIndicadorConsumidorFinal.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                //tempo.start();
                                getCboNfeDadosIndicadorPresenca().setItems(
                                        Arrays.stream(NfeDadosIndicadorPresenca.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                //tempo.start();
                                getCboNfeImpressaoTpImp().setItems(
                                        Arrays.stream(NfeImpressaoTpImp.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                //tempo.start();
                                getCboNfeImpressaoTpEmis().setItems(
                                        Arrays.stream(NfeImpressaoTpEmis.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                //tempo.start();
                                getCboNfeImpressaoFinNFe().setItems(
                                        Arrays.stream(NfeImpressaoFinNFe.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                //tempo.start();
                                getCboNfeTransporteModFrete().setItems(
                                        Arrays.stream(NfeTransporteModFrete.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                //tempo.start();
                                getCboNfeTransporteTransportadora().setItems(
                                        getEmpresaObservableList().stream()
                                                .filter(tranportadoras -> tranportadoras.isTransportadora())
                                                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                //tempo.start();
                                getCboNfeCobrancaDuplicataNumeros().setItems(
                                        Arrays.stream(NfeCobrancaDuplicataNumero.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                //tempo.start();
                                getCboNfeCobrancaPagamentoIndicador().setItems(
                                        Arrays.stream(NfeCobrancaDuplicataPagamentoIndicador.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                //tempo.start();
                                getCboNfeCobrancaPagamentoMeio().setItems(
                                        Arrays.stream(NfeCobrancaDuplicataPagamentoMeio.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));
                                //tempo.fim();

                                break;

                            case TABELA_PREENCHER:
                                getTmodelProduto().preencheTabela();

                                getTmodelSaidaProduto().preencheTabela();
                                break;

                            case SALVAR_ENT_SAIDA:
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
        if (anchorPane == getPainelViewSaidaProduto()) {
            getCboEmpresa().getSelectionModel().select(0);
            getCboEmpresa().requestFocus();
        }
        ServiceCampoPersonalizado.fieldClear(anchorPane);
    }

    private void limpaEndereco() {
        getLblLogradoruro().setText("");
        getLblNumero().setText("");
        getLblBairro().setText("");
        getLblComplemento().setText("");
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

    private void loadListaEmpresas() {
        try {
            ObservableList<Empresa> empresaObservableList = FXCollections.observableArrayList(
                    new EmpresaDAO().getAll(Empresa.class, "cliente = '1'", "razao, fantasia"));
            ObservableList<ContasAReceber> contasAReceberObservableList = FXCollections.observableArrayList(
                    new ContasAReceberDAO().getAll(ContasAReceber.class, null, "dtCadastro DESC"));
            ObservableList<ContasAReceber> aReceberClienteObservableList = FXCollections.observableArrayList();
            empresaObservableList.stream()
                    .filter(empresa1 -> contasAReceberObservableList.stream()
                            .filter(contasAReceber -> contasAReceber.saidaProdutoProperty().getValue()
                                    .clienteProperty().getValue().idProperty().getValue().equals(empresa1.idProperty().getValue())).count() > 0)
                    .forEach(empresa1 -> {
                        aReceberClienteObservableList.setAll(contasAReceberObservableList.stream()
                                .filter(contasAReceber -> contasAReceber.saidaProdutoProperty().getValue()
                                        .clienteProperty().getValue().idProperty().getValue().equals(empresa1.idProperty().getValue()))
                                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
                        ContasAReceber ultConta = aReceberClienteObservableList.stream()
                                .findFirst().get();
                        empresa1.limiteUtilizadoProperty().setValue(
                                aReceberClienteObservableList.stream()
                                        .map(ContasAReceber::getValor)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                                        .subtract(aReceberClienteObservableList.stream()
                                                .map(ContasAReceber::getRecebimentoList)
                                                .map(recebimentos -> recebimentos.stream()
                                                        .filter(recebimento -> recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
                                                        .map(Recebimento::getValor)
                                                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                                                .reduce(BigDecimal.ZERO, BigDecimal::add))
                        );
                        empresa1.dtUltimoPedidoProperty().setValue(ultConta.saidaProdutoProperty().getValue().dtSaidaProperty().getValue());
                        empresa1.vlrUltimoPedidoProperty().setValue(ultConta.valorProperty().getValue());
                        empresa1.qtdPedidosProperty().setValue(aReceberClienteObservableList.size());
                        empresa1.vlrTickeMedioProperty().setValue(
                                aReceberClienteObservableList.stream()
                                        .map(ContasAReceber::getValor)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                                        .divide(new BigDecimal(empresa1.qtdPedidosProperty().getValue()), 2, RoundingMode.HALF_UP)
                        );
                    });
            getCboEmpresa().setItems(empresaObservableList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * END voids
     */


    /**
     * Begin returns
     */

    private Produto getProdutoSelecionado() {
        Produto produtoSelecionado;
        if (getTtvProduto().getSelectionModel().getSelectedItem().getValue().idProperty().getValue() != 0) {
            produtoSelecionado = new Produto(getTtvProduto().getSelectionModel().getSelectedItem().getValue());
            produtoSelecionado.setTblEstoque(getTtvProduto().getSelectionModel().getSelectedItem().getChildren().get(0).getValue().getTblEstoque());
            produtoSelecionado.setTblLote(getTtvProduto().getSelectionModel().getSelectedItem().getChildren().get(0).getValue().getTblLote());
            produtoSelecionado.setTblValidade(getTtvProduto().getSelectionModel().getSelectedItem().getChildren().get(0).getValue().getTblValidade());
        } else {
            produtoSelecionado = new Produto((getTtvProduto().getSelectionModel().getSelectedItem().getParent().getValue()));
            produtoSelecionado.setTblEstoque(getTtvProduto().getSelectionModel().getSelectedItem().getValue().getTblEstoque());
            produtoSelecionado.setTblLote(getTtvProduto().getSelectionModel().getSelectedItem().getValue().getTblLote());
            produtoSelecionado.setTblValidade(getTtvProduto().getSelectionModel().getSelectedItem().getValue().getTblValidade());
        }
        return produtoSelecionado;
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

    public TitledPane getTpnProdutos() {
        return tpnProdutos;
    }

    public void setTpnProdutos(TitledPane tpnProdutos) {
        this.tpnProdutos = tpnProdutos;
    }

    public TextField getTxtPesquisaProduto() {
        return txtPesquisaProduto;
    }

    public void setTxtPesquisaProduto(TextField txtPesquisaProduto) {
        this.txtPesquisaProduto = txtPesquisaProduto;
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

    public TitledPane getTpnItensPedido() {
        return tpnItensPedido;
    }

    public void setTpnItensPedido(TitledPane tpnItensPedido) {
        this.tpnItensPedido = tpnItensPedido;
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
        return saidaProduto;
    }

    public void setSaidaProduto(SaidaProduto saidaProduto) {
        this.saidaProduto = saidaProduto;
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

    public ObservableList<Empresa> getEmpresaObservableList() {
        return empresaObservableList;
    }

    public void setEmpresaObservableList(ObservableList<Empresa> empresaObservableList) {
        this.empresaObservableList = empresaObservableList;
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

    public List<Endereco> getEnderecoList() {
        return enderecoList.get();
    }

    public ObjectProperty<List<Endereco>> enderecoListProperty() {
        return enderecoList;
    }

    public void setEnderecoList(List<Endereco> enderecoList) {
        this.enderecoList.set(enderecoList);
    }

    public Endereco getEndereco() {
        return endereco.get();
    }

    public ObjectProperty<Endereco> enderecoProperty() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco.set(endereco);
    }

    public List<Telefone> getTelefoneList() {
        return telefoneList.get();
    }

    public ObjectProperty<List<Telefone>> telefoneListProperty() {
        return telefoneList;
    }

    public void setTelefoneList(List<Telefone> telefoneList) {
        this.telefoneList.set(telefoneList);
    }

    /**
     * END Getters e Setters
     */

}
