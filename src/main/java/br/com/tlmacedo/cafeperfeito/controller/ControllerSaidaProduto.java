package br.com.tlmacedo.cafeperfeito.controller;

import br.com.tlmacedo.cafeperfeito.interfaces.ModeloCafePerfeito;
import br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert;
import br.com.tlmacedo.cafeperfeito.model.dao.*;
import br.com.tlmacedo.cafeperfeito.model.enums.*;
import br.com.tlmacedo.cafeperfeito.model.tm.TmodelProduto;
import br.com.tlmacedo.cafeperfeito.model.tm.TmodelSaidaProduto;
import br.com.tlmacedo.cafeperfeito.model.vo.*;
import br.com.tlmacedo.cafeperfeito.nfe.LoadCertificadoA3;
import br.com.tlmacedo.cafeperfeito.nfe.NewNotaFiscal;
import br.com.tlmacedo.cafeperfeito.service.*;
import br.com.tlmacedo.cafeperfeito.service.autoComplete.ServiceAutoCompleteComboBox;
import br.com.tlmacedo.cafeperfeito.service.format.FormatDataPicker;
import br.com.tlmacedo.cafeperfeito.view.ViewRecebimento;
import br.com.tlmacedo.cafeperfeito.view.ViewSaidaProduto;
import br.com.tlmacedo.nfe.service.*;
import br.com.tlmacedo.nfe.v400.EnviNfe_v400;
import br.inf.portalfiscal.xsd.nfe.consReciNFe.TConsReciNFe;
import br.inf.portalfiscal.xsd.nfe.enviNFe.TEnviNFe;
import br.inf.portalfiscal.xsd.nfe.procNFe.TNFe;
import br.inf.portalfiscal.xsd.nfe.procNFe.TProtNFe;
import br.inf.portalfiscal.xsd.nfe.retConsReciNFe.TRetConsReciNFe;
import br.inf.portalfiscal.xsd.nfe.retEnviNFe.TRetEnviNFe;
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
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.sql.rowset.serial.SerialBlob;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.DTF_DATA;
import static br.com.tlmacedo.cafeperfeito.interfaces.Regex_Convert.MY_ZONE_TIME;
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
    private EventHandler eventHandlerSaidaProduto;
    private ServiceAlertMensagem alertMensagem;

    private TmodelProduto tmodelProduto;
    private ObservableList<Produto> produtoObservableList;
    private FilteredList<Produto> produtoFilteredList;

    private TmodelSaidaProduto tmodelSaidaProduto;
    private SaidaProduto saidaProduto = new SaidaProduto();
    private SaidaProdutoDAO saidaProdutoDAO = new SaidaProdutoDAO();
    private ContasAReceber contasAReceber;
    private ContasAReceberDAO contasAReceberDAO = new ContasAReceberDAO();
    private Recebimento recebimento;
    private RecebimentoDAO recebimentoDAO = new RecebimentoDAO();
    private SaidaProdutoNfe saidaProdutoNfe;
    private SaidaProdutoNfeDAO saidaProdutoNfeDAO = new SaidaProdutoNfeDAO();


    private ObservableList<ContasAReceber> contasAReceberObservableList =
            FXCollections.observableArrayList(getContasAReceberDAO().getAll(ContasAReceber.class, null, "dtCadastro DESC"));

    private ObservableList<SaidaProdutoProduto> saidaProdutoProdutoObservableList = FXCollections.observableArrayList();

    private NewNotaFiscal newNotaFiscal;
    private ObjectProperty<LoadCertificadoA3> loadCertificadoA3 = new SimpleObjectProperty<>();
    private IntegerProperty nfeLastNumber = new SimpleIntegerProperty(0);
    private StringProperty informacaoNFE = new SimpleStringProperty();
    private ObjectProperty<BigDecimal> valorParcela = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private ObjectProperty<NFev400> nFev400 = new SimpleObjectProperty<>();
    private ObjectProperty<TEnviNFe> tEnviNFe = new SimpleObjectProperty<>();
    private StringProperty xmlNFe = new SimpleStringProperty();
    private StringProperty xmlNFeAssinado = new SimpleStringProperty();
    private StringProperty xmlNFeAutorizacao = new SimpleStringProperty();
    private StringProperty xmlNFeRetAutorizacao = new SimpleStringProperty();
    private StringProperty xmlNFeProc = new SimpleStringProperty();

    private ObservableList<Empresa> empresaObservableList = FXCollections.observableArrayList(
            new EmpresaDAO().getAll(Empresa.class, null, "razao, fantasia"));
    private ObjectProperty<Empresa> empresa = new SimpleObjectProperty<>();
    private ObjectProperty<List<Endereco>> enderecoList = new SimpleObjectProperty<>();
    private ObjectProperty<Endereco> endereco = new SimpleObjectProperty<>();
    private ObjectProperty<List<Telefone>> telefoneList = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    public void escutarTecla() throws Exception {

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
            Produto produtoEscolhido = getProdutoSelecionado();
            if (getSaidaProdutoProdutoObservableList().stream()
                    .filter(saidaProdutoProduto -> saidaProdutoProduto.loteProperty().getValue().equals(produtoEscolhido.tblLoteProperty().getValue())
                            && saidaProdutoProduto.getProduto().idProperty().getValue().intValue() == produtoEscolhido.idProperty().getValue().intValue())
                    .findFirst().orElse(null) == null) {
                getSaidaProdutoProdutoObservableList().add(new SaidaProdutoProduto(produtoEscolhido, TipoSaidaProduto.VENDA, 1));
                ControllerPrincipal.getCtrlPrincipal().getPainelViewPrincipal().fireEvent(ServiceComandoTecladoMouse.pressTecla(KeyCode.F8));
                if (getTmodelSaidaProduto().empresaProperty().getValue() != null)
                    getTmodelSaidaProduto().calculaDescontoCliente();
            } else {
                for (int i = 0; i < getSaidaProdutoProdutoObservableList().size(); i++) {
                    SaidaProdutoProduto saida = getSaidaProdutoProdutoObservableList().get(i);
                    if (saida.loteProperty().getValue().equals(produtoEscolhido.tblLoteProperty().getValue())
                            && saida.produtoProperty().getValue().idProperty().getValue().intValue()
                            == produtoEscolhido.idProperty().getValue().intValue()) {
                        getTvItensPedido().requestFocus();
                        getTvItensPedido().getSelectionModel().select(i, getTmodelSaidaProduto().getColQtd());
                    }
                }
//                final int[] row = {0};
//                for (SaidaProdutoProduto saida : getSaidaProdutoProdutoObservableList()) {
//                    if (saida.loteProperty().getValue().equals(produtoEscolhido.tblLoteProperty().getValue()))
//                        break;
//                    row[0]++;
//                }
//                getTvItensPedido().requestFocus();
//                getTvItensPedido().getSelectionModel().select(row[0], getTmodelSaidaProduto().getColQtd());
            }
        });

        getTvItensPedido().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() != KeyCode.HELP) return;
            Produto produtoAdicional = new Produto(getTvItensPedido().getSelectionModel().getSelectedItem().produtoProperty().getValue());
            getSaidaProdutoProdutoObservableList().add(new SaidaProdutoProduto(produtoAdicional, TipoSaidaProduto.AMOSTRA, 1));
//            Produto produtoEscolhido = new Produto(getTvItensPedido().getSelectionModel().getSelectedItem().produtoProperty().getValue());
//            produtoEscolhido.tblEstoqueProperty().setValue(getTvItensPedido().getSelectionModel().getSelectedItem().estoqueProperty().getValue());
//            produtoEscolhido.tblLoteProperty().setValue(getTvItensPedido().getSelectionModel().getSelectedItem().loteProperty().getValue());
//            produtoEscolhido.tblValidadeProperty().setValue(getTvItensPedido().getSelectionModel().getSelectedItem().dtValidadeProperty().getValue());
//            getSaidaProdutoProdutoObservableList().add(new SaidaProdutoProduto(produtoEscolhido, TipoSaidaProduto.AMOSTRA, 1));
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
                            getEnumsTasksList().clear();
                            getEnumsTasksList().add(EnumsTasks.SALVAR_SAIDA);

                            if (ServiceMascara.getBigDecimalFromTextField(getLblLimiteDisponivel().getText(), 2)
                                    .compareTo(ServiceMascara.getBigDecimalFromTextField(getLblTotalLiquido().getText(), 2)) >= 0) {
                                boolean utilizaCredito = false;
                                BigDecimal credito = BigDecimal.ZERO;
                                if ((credito = ServiceMascara.getBigDecimalFromTextField(getLblLimiteUtilizado().getText(), 2)).compareTo(BigDecimal.ZERO) < 0) {
                                    setAlertMensagem(new ServiceAlertMensagem());
                                    getAlertMensagem().setCabecalho("Crédito disponível");
                                    getAlertMensagem().setContentText(String.format("o Cliente tem um crédito de R$ %s\ndeseja utilizar esse valor para abater no pedido?",
                                            ServiceMascara.getMoeda(credito, 2)));
                                    getAlertMensagem().setStrIco("");
                                    ButtonType btnResult;
                                    if ((btnResult = getAlertMensagem().alertYesNoCancel().get()) == ButtonType.CANCEL)
                                        return;
                                    utilizaCredito = (btnResult == ButtonType.YES);
                                } else {
                                    credito = BigDecimal.ZERO;
                                }
                                if (new ServiceSegundoPlano().executaListaTarefas(newTaskSaidaProduto(), String.format("Salvando %s!", getNomeTab()))) {
                                    if (utilizaCredito) {
                                        baixaCredito(credito);
                                        getContasAReceber().getRecebimentoList().add(addRecebimento(getContasAReceber(), PagamentoModalidade.CREDITO, credito));
                                    }
//                                    try {
//                                        getContasAReceberDAO().transactionBegin();
//                                        setContasAReceber(getContasAReceberDAO().setTransactionPersist(getContasAReceber()));
//                                        getContasAReceberDAO().transactionCommit();
//                                    } catch (Exception ex) {
//                                        getContasAReceberDAO().transactionRollback();
//                                    }


                                    ServiceUtilJSon.printJsonFromObject(getSaidaProduto().getContasAReceber(), "SaidaProduto001:");
                                    new ViewRecebimento().openViewRecebimento(getContasAReceber());
                                    try {
                                        getContasAReceberDAO().transactionBegin();
                                        setContasAReceber(getContasAReceberDAO().setTransactionPersist(getContasAReceber()));
                                        getContasAReceberDAO().transactionCommit();
                                    } catch (Exception ex) {
                                        getContasAReceberDAO().transactionRollback();
                                    }
                                    atualizaTotaisCliente(getContasAReceber());

//                                    try {
//                                        getRecebimentoDAO().transactionBegin();
//                                        setContasAReceber(getContasAReceberDAO().setTransactionPersist(getContasAReceber()));
//                                        getContasAReceberDAO().transactionCommit();
//                                    } catch (Exception ex) {
//                                        getContasAReceberDAO().transactionRollback();
//                                    }

                                    ServiceUtilJSon.printJsonFromObject(getSaidaProduto().getContasAReceber(), "SaidaProduto002:");
                                    //System.out.printf("saidaProduto002:\n***********------\n%s\n------***********", getTmodelSaidaProduto().toString());
                                    //System.out.printf("saidaProduto003:\n***********------\n%s\n------***********", getTmodelSaidaProduto().toString());

                                    if (getSaidaProdutoNfe() != null)
                                        gerarDanfe();
                                    ServiceUtilJSon.printJsonFromObject(getSaidaProduto().getContasAReceber(), "SaidaProduto004:");
                                    //System.out.printf("saidaProduto004:\n***********------\n%s\n------***********", getTmodelSaidaProduto().toString());

                                    limpaCampos(getPainelViewSaidaProduto());
                                }
                            } else {
                                setAlertMensagem(new ServiceAlertMensagem());
                                getAlertMensagem().setCabecalho("Limite excedido");
                                getAlertMensagem().setContentText("Cliente não possui limite para finalizar o pedido!");
                                getAlertMensagem().setStrIco("");
                                getAlertMensagem().alertOk();
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

//        getCboEmpresa().focusedProperty().addListener((ov, o, n) -> {
//            if (!n) {
//                if (getCboEmpresa().getValue() == null || getCboEmpresa().getValue().idProperty().getValue() == 0) {
//                    getCboEndereco().getItems().clear();
//                    getCboTelefone().getItems().clear();
//                } else {
//                    getCboEndereco().setItems(getCboEmpresa().getValue().getEnderecoList().stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
//                    getCboTelefone().setItems(getCboEmpresa().getValue().getTelefoneList().stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
//                }
//                getTmodelSaidaProduto().setEmpresa(getCboEmpresa().getValue());
//                getCboEndereco().getSelectionModel().select(0);
//                getCboTelefone().getSelectionModel().select(0);
//
//                getLblLimite().setText(getCboEmpresa().getValue() == null || getCboEmpresa().getValue().idProperty().getValue() == 0
//                        ? "0,00"
//                        : ServiceMascara.getMoeda(getCboEmpresa().getValue().limiteProperty().getValue(), 2));
//                getTmodelSaidaProduto().prazoProperty().setValue(getCboEmpresa().getValue() == null ? 0 : getCboEmpresa().getValue().getPrazo());
//            }
//            showStatusBar();
//        });

        empresaProperty().bind(Bindings.createObjectBinding(() -> {
                    if (getCboEmpresa().getValue() == null)
                        return new Empresa();
                    return getCboEmpresa().getValue();
                }, getCboEmpresa().valueProperty())
        );

        empresaProperty().addListener((ov, o, n) -> {
            getDtpDtSaida().setValue(LocalDate.now());
            getTmodelSaidaProduto().empresaProperty().setValue(n);
//            if (n == null) {
//                empresaProperty().setValue(getEmpresaObservableList().get(0));
//                return;
//            }

            getLblLimite().setText(ServiceMascara.getMoeda(n.limiteProperty().getValue(), 2));
            getLblLimiteUtilizado().setText(ServiceMascara.getMoeda(n.limiteUtilizadoProperty().getValue(), 2));

            getTmodelSaidaProduto().prazoProperty().setValue(n.prazoProperty().getValue());

            getLblUltimoPedidoDt().setText(n.dtUltimoPedidoProperty().getValue() != null ? n.dtUltimoPedidoProperty().getValue().format(DTF_DATA) : "");

            getLblUltimoPedidoDias().setText(n == null
                    ? ""
                    : (n.dtUltimoPedidoProperty().getValue() != null
                    ? String.valueOf(DAYS.between(n.dtUltimoPedidoProperty().getValue(), LocalDate.now()))
                    : (n.dtCadastroProperty().getValue() == null
                    ? ""
                    : String.valueOf(DAYS.between(n.dtCadastroProperty().getValue().toLocalDate(), LocalDate.now())))));
            getLblUltimoPedidoVlr().setText(ServiceMascara.getMoeda(n.vlrUltimoPedidoProperty().getValue(), 2));
            getLblQtdPedidos().setText(n.qtdPedidosProperty().getValue().toString());
            getLblTicketMedioVlr().setText(ServiceMascara.getMoeda(n.vlrTickeMedioProperty().getValue(), 2));

            enderecoListProperty().setValue(n.getEnderecoList());
            telefoneListProperty().setValue(n.getTelefoneList());

            getCboEndereco().getSelectionModel().select(0);
            getCboTelefone().getSelectionModel().select(0);
            n.getEnderecoList().stream()
                    .filter(endereco1 -> endereco1.getTipo().equals(TipoEndereco.ENTREGA))
                    .findFirst().ifPresent(endereco1 -> getCboEndereco().getSelectionModel().select(endereco1));
            showStatusBar();
        });

        getLblLimiteDisponivel().textProperty().bind(Bindings.createStringBinding(() -> {
                    try {
                        return ServiceMascara.getMoeda(
                                empresaProperty().getValue().limiteProperty().getValue()
                                        .subtract(empresaProperty().getValue().limiteUtilizadoProperty().getValue()), 2);
                    } catch (Exception ex) {
                        return "0,00";
                    }
                }, getLblLimite().textProperty(), getLblLimiteUtilizado().textProperty())
        );

        enderecoListProperty().addListener((ov, o, n) -> {
            if (n != null)
                getCboEndereco().setItems(FXCollections.observableArrayList(n));
            else
                getCboEndereco().getItems().clear();
        });

        enderecoProperty().bind(Bindings.createObjectBinding(() ->
                getCboEndereco().getValue(), getCboEndereco().valueProperty()
        ));

        enderecoProperty().addListener((ov, o, n) -> {
            if (n == null) {
                limpaEndereco();
                return;
            }
            getLblLogradoruro().setText(n.getLogradouro());
            getLblNumero().setText(n.getNumero());
            getLblBairro().setText(n.getBairro());
            getLblComplemento().setText(n.getComplemento());
        });

//        getTmodelSaidaProduto().prazoProperty().bind(Bindings.createIntegerBinding(() -> {
//                    if (empresaProperty().getValue() == null)
//                        return 0;
//                    return empresaProperty().getValue().prazoProperty().getValue();
//                }, empresaProperty()
//        ));

        getCboEmpresa().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (getCboEmpresa().getSelectionModel().getSelectedItem() != null)
                getTxtPesquisa().requestFocus();
        });

        getCboEndereco().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (getCboEndereco().getSelectionModel().getSelectedItem() != null)
                getTxtPesquisa().requestFocus();
        });

//        getLblLimiteDisponivel().textProperty().bind(Bindings.createStringBinding(() -> {
//            BigDecimal vLimite = ServiceMascara.getBigDecimalFromTextField(getLblLimite().getText(), 2);
//            BigDecimal vUtilizado = ServiceMascara.getBigDecimalFromTextField(getLblLimiteUtilizado().getText(), 2);
//            return ServiceMascara.getMoeda(vLimite.subtract(vUtilizado).setScale(2, RoundingMode.HALF_UP), 2);
//        }, getLblLimite().textProperty(), getLblLimiteUtilizado().textProperty()));

        getTmodelSaidaProduto().prazoProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            getLblPrazo().setText(n.toString());
            getDtpDtVencimento().setValue(getDtpDtSaida().getValue().plusDays(n.longValue()));
        });

        getDtpDtSaida().valueProperty().addListener((ov, o, n) -> {
            if (n == null) return;
            getDtpDtVencimento().setValue(getDtpDtSaida().getValue().plusDays(Integer.parseInt(getLblPrazo().getText())));
        });

//        getCboEndereco().getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
//            if (n == null) {
//                limpaEndereco();
//                return;
//            }
//            getLblLogradoruro().setText(n.getLogradouro());
//            getLblNumero().setText(n.getNumero());
//            getLblBairro().setText(n.getBairro());
//            getLblComplemento().setText(n.getComplemento());
//        });

        getTabNfeInformacoes().selectedProperty().addListener((ov, o, n) -> {
            int diff = 21;
            if (!n) diff = (diff * (-1));
            organizaPosicaoCampos(diff);
        });

        getTpnNfe().expandedProperty().addListener((ov, o, n) -> {
            int diff = 85;
            if (!n) diff = (diff * (-1));
            organizaPosicaoCampos(diff);
            if (n) {
                getCboNfeDadosNaturezaOperacao().requestFocus();
                getTxtNfeDadosNumero().setText(String.valueOf(nfeLastNumberProperty().getValue() + 1));
                getTxtNfeDadosSerie().setText(String.valueOf(TCONFIG.getNfe().getNFeSerie()));
            } else {
                limpaCampos(getTpnNfe());
                getTxtPesquisa().requestFocus();
            }

        });

        getTpnNfe().setExpanded(false);

        getCboNfeTransporteTransportadora().disableProperty().bind(Bindings.createBooleanBinding(() ->
                        getCboNfeTransporteModFrete().getSelectionModel().getSelectedItem().equals(NfeTransporteModFrete.REMETENTE),
                getCboNfeTransporteModFrete().getSelectionModel().selectedItemProperty()));


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

        informacaoNFEProperty().bind(Bindings.createStringBinding(() ->
                getNFeInfAdicionas(), getLblTotalLiquido().textProperty(), getDtpDtVencimento().valueProperty())
        );

        informacaoNFEProperty().addListener((ov, o, n) -> {
            if (n != null)
                getTxaNfeInformacoesAdicionais().setText(n);
        });

        valorParcelaProperty().bind(Bindings.createObjectBinding(() ->
                        ServiceMascara.getBigDecimalFromTextField(getLblTotalLiquido().getText(), 2)
                                .divide(new BigDecimal(getCboNfeCobrancaDuplicataNumeros().getValue().getCod()), 2, RoundingMode.HALF_UP),
                getLblTotalLiquido().textProperty(), getCboNfeCobrancaDuplicataNumeros().valueProperty()));

        valorParcelaProperty().addListener((ov, o, n) -> {
            if (n != null)
                getTxtNfeCobrancaDuplicataValor().setText(ServiceMascara.getMoeda(valorParcelaProperty().getValue(), 2));
            else
                getTxtNfeCobrancaDuplicataValor().setText(getLblTotalLiquido().getText());
        });

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
                                getTmodelProduto().setTtvProduto(getTtvProdutos());
                                getTmodelProduto().setTxtPesquisa(getTxtPesquisa());
                                setProdutoObservableList(getTmodelProduto().getProdutoObservableList());
                                setProdutoFilteredList(getTmodelProduto().getProdutoFilteredList());
                                getTmodelProduto().escutaLista();

                                getTmodelSaidaProduto().setTvSaidaProdutoProduto(getTvItensPedido());
                                getTmodelSaidaProduto().setTxtPesquisa(getTxtPesquisa());
                                getTmodelSaidaProduto().setDtpDtSaida(getDtpDtSaida());
                                getTmodelSaidaProduto().setDtpDtVencimento(getDtpDtVencimento());
                                getTmodelSaidaProduto().empresaProperty().setValue(empresaProperty().getValue());
                                getTmodelSaidaProduto().setSaidaProduto(getSaidaProduto());
                                getTmodelSaidaProduto().setSaidaProdutoProdutoObservableList(getSaidaProdutoProdutoObservableList());
//                                setSaidaProduto(getTmodelSaidaProduto().getSaidaProduto());
//                                setSaidaProdutoProdutoObservableList(getTmodelSaidaProduto().getSaidaProdutoProdutoObservableList());
                                getTmodelSaidaProduto().escutaLista();
                                break;
                            case COMBOS_PREENCHER:

                                informacoesAdicionais();

                                getCboEmpresa().setItems(getEmpresaObservableList().stream()
                                        .filter(clientes -> clientes.isCliente())
                                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));


                                getCboEmpresa().getItems().add(0, new Empresa());

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

                                getCboNfeTransporteTransportadora().setItems(
                                        getEmpresaObservableList().stream()
                                                .filter(tranportadoras -> tranportadoras.isTransportadora())
                                                .collect(Collectors.toCollection(FXCollections::observableArrayList)));

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

                            case SALVAR_SAIDA:
                                if (guardarSaidaProduto()) {
                                    if (salvarSaidaProduto()) {
                                        getProdutoObservableList().setAll(new ProdutoDAO().getAll(Produto.class, null, "descricao"));
                                        getTtvProdutos().refresh();
                                    } else {
                                        Thread.currentThread().interrupt();
                                    }
                                } else {
                                    Thread.currentThread().interrupt();
                                }
                                break;
                            case NFE_GERAR:
                                gerarXmlNFe();
                                break;
                            case NFE_ASSINAR:
                                if (xmlNFeProperty().getValue() == null)
                                    Thread.currentThread().interrupt();
                                assinarXmlNFe();
                                break;
                            case NFE_TRANSMITIR:
                                if (xmlNFeAssinadoProperty().getValue() == null)
                                    Thread.currentThread().interrupt();
                                transmitirXmlNFe();
                                break;
                            case NFE_RETORNO:
                                if (xmlNFeAutorizacaoProperty().getValue() == null)
                                    Thread.currentThread().interrupt();
                                retornoXmlNFe();
                                if (xmlNFeRetAutorizacaoProperty().getValue() == null)
                                    Thread.currentThread().interrupt();
                                retornoProcNFe();
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
        if (titledPane.equals("tpnNfe"))
            getTxaNfeInformacoesAdicionais().setText(getNFeInfAdicionas());
    }

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

    private void limpaInfoCliente() {
        getLblLimite().setText("0,00");
        getLblLimiteUtilizado().setText("0,00");
        getLblLimiteDisponivel().setText("0,00");
        getLblPrazo().setText("0");
        getLblUltimoPedidoDt().setText("");
        getLblUltimoPedidoDias().setText("");
        getLblUltimoPedidoVlr().setText("0,00");
        getLblQtdPedidos().setText("0");
        getLblTicketMedioVlr().setText("0,00");
    }

//    private boolean teclaDisponivel(KeyCode keyCode) {
//        return ControllerPrincipal.getCtrlPrincipal().getServiceStatusBar().getStbTeclas().getText().contains(String.format("%s-", keyCode.toString()));
//    }

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

    private void informacoesAdicionais() {
        try {
            getContasAReceberObservableList().stream()
                    .map(ContasAReceber::getSaidaProduto)
                    .collect(Collectors.groupingBy(SaidaProduto::getCliente, LinkedHashMap::new, Collectors.toList()))
                    .forEach((empresa, saidaProdutos) -> {
                        saidaProdutos.stream().map(SaidaProduto::getSaidaProdutoNfeList)
                                .forEach(saidaProdutoNfes -> {
                                    for (SaidaProdutoNfe nfe : saidaProdutoNfes)
                                        if (nfe.getNumero() > getNfeLastNumber())
                                            setNfeLastNumber(nfe.getNumero());
                                });
                        BigDecimal vlrLimiteUtilizado =
                                getContasAReceberObservableList().stream()
                                        .filter(aReceber -> aReceber.getSaidaProduto().getCliente().idProperty().getValue() == empresa.idProperty().getValue())
                                        .map(ContasAReceber::getValor)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                                        .subtract(
                                                getContasAReceberObservableList().stream()
                                                        .filter(aReceber -> aReceber.getSaidaProduto().getCliente().idProperty().getValue() ==
                                                                empresa.idProperty().getValue())
                                                        .map(ContasAReceber::getRecebimentoList)
                                                        .map(recebimentos -> recebimentos.stream()
                                                                .filter(recebimento -> recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
                                                                .map(Recebimento::getValor)
                                                                .reduce(BigDecimal.ZERO, BigDecimal::add))
                                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                                        );
                        LocalDate dtUltimoPedido = saidaProdutos.get(0).dtSaidaProperty().getValue();
                        BigDecimal vlrUltimoPedido = saidaProdutos.get(0).getSaidaProdutoProdutoList().stream()
                                .map(saidaProdutoProduto -> saidaProdutoProduto.vlrBrutoProperty().getValue()
                                        .subtract(saidaProdutoProduto.vlrDescontoProperty().getValue()))
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        BigDecimal vlrTicketMedio =
                                saidaProdutos.stream()
                                        .map(SaidaProduto::getSaidaProdutoProdutoList)
                                        .map(saidaProdutoProdutos -> saidaProdutoProdutos.stream()
                                                .map(saidaProdutoProduto -> saidaProdutoProduto.vlrBrutoProperty().getValue()
                                                        .subtract(saidaProdutoProduto.vlrDescontoProperty().getValue()))
                                                .reduce(BigDecimal.ZERO, BigDecimal::add))
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                                        .divide(BigDecimal.valueOf(saidaProdutos.size()), 4, RoundingMode.HALF_UP);
                        getEmpresaObservableList().stream()
                                .filter(empresa1 -> empresa1.idProperty().getValue() == empresa.idProperty().getValue())
                                .findFirst().ifPresent(empresa1 -> {
                            empresa1.limiteUtilizadoProperty().setValue(vlrLimiteUtilizado);
                            empresa1.dtUltimoPedidoProperty().setValue(dtUltimoPedido);
                            empresa1.vlrUltimoPedidoProperty().setValue(vlrUltimoPedido);
                            empresa1.qtdPedidosProperty().setValue(saidaProdutos.size());
                            empresa1.vlrTickeMedioProperty().setValue(vlrTicketMedio);
                        });
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void atualizaTotaisCliente(ContasAReceber contasAReceber) {

        empresaProperty().getValue().limiteUtilizadoProperty().setValue(
                empresaProperty().getValue().limiteUtilizadoProperty().getValue().add(contasAReceber.valorProperty().getValue())
        );
        empresaProperty().getValue().dtUltimoPedidoProperty().setValue(
                contasAReceber.dtCadastroProperty().getValue().toLocalDate()
        );
        empresaProperty().getValue().vlrUltimoPedidoProperty().setValue(
                empresaProperty().getValue().vlrUltimoPedidoProperty().getValue()
        );
        BigDecimal totalPedidos = empresaProperty().getValue().vlrTickeMedioProperty().getValue()
                .multiply(BigDecimal.valueOf(empresaProperty().getValue().qtdPedidosProperty().getValue()));
        empresaProperty().getValue().qtdPedidosProperty().setValue(empresaProperty().getValue().qtdPedidosProperty().getValue() + 1);
        empresaProperty().getValue().vlrTickeMedioProperty().setValue(
                totalPedidos.add(contasAReceber.valorProperty().getValue())
                        .divide(BigDecimal.valueOf(empresaProperty().getValue().qtdPedidosProperty().getValue()), 4, RoundingMode.HALF_UP)
        );

        if (getSaidaProdutoNfe() != null)
            nfeLastNumberProperty().setValue(getSaidaProdutoNfe().numeroProperty().getValue());

    }

    private void baixaCredito(BigDecimal vlrCredito) throws Exception {
        final BigDecimal[] vlrCreditoABaixar = {vlrCredito};
        getContasAReceberObservableList().stream()
                .sorted(Comparator.comparing(ContasAReceber::getDtCadastro))
                .filter(aReceber -> aReceber.getSaidaProduto().getCliente().idProperty().getValue() == empresaProperty().getValue().idProperty().getValue()
                        && aReceber.getRecebimentoList().stream()
                        .filter(recebimento -> recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
                        .map(Recebimento::getValor)
                        .reduce(BigDecimal.ZERO, BigDecimal::add).compareTo(aReceber.valorProperty().getValue()) > 0)
                .forEach(aReceber -> {
                    if (vlrCreditoABaixar[0].compareTo(BigDecimal.ZERO) < 0) {
                        BigDecimal saldoConta = aReceber.valorProperty().getValue().subtract(aReceber.getRecebimentoList().stream()
                                .filter(recebimento -> recebimento.getPagamentoSituacao().equals(PagamentoSituacao.QUITADO))
                                .map(Recebimento::getValor)
                                .reduce(BigDecimal.ZERO, BigDecimal::add));
                        aReceber.getRecebimentoList().add(addRecebimento(aReceber, PagamentoModalidade.CREDITO_BAIXA, saldoConta));
                        try {
                            aReceber = null;//getTmodelSaidaProduto().getSaidaProdutoDAO().setTransactionPersist(aReceber);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        vlrCreditoABaixar[0] = vlrCreditoABaixar[0].add(saldoConta);
                    }
                });
    }

    public boolean guardarSaidaProduto() {
        try {
            getSaidaProduto().setCliente(empresaProperty().getValue());
            getSaidaProduto().setVendedor(UsuarioLogado.getUsuario());
            getSaidaProduto().setDtSaida(getDtpDtSaida().getValue());

            getSaidaProdutoProdutoObservableList().stream().forEach(saidaProdutoProduto -> {
                saidaProdutoProduto.setSaidaProduto(getSaidaProduto());
                saidaProdutoProduto.setVlrEntrada(BigDecimal.ZERO);
                saidaProdutoProduto.setVlrEntradaBruto(BigDecimal.ZERO);
            });

            getSaidaProduto().setSaidaProdutoProdutoList(getSaidaProdutoProdutoObservableList());

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean salvarSaidaProduto() {
        try {
            getSaidaProdutoDAO().transactionBegin();
            if (getContasAReceber() == null)
                setContasAReceber(new ContasAReceber());
            setSaidaProduto(getSaidaProdutoDAO().setTransactionPersist(getSaidaProduto()));
            getSaidaProdutoDAO().transactionCommit();
            getContasAReceber().setSaidaProduto(getSaidaProduto());

            if (getTmodelSaidaProduto().baixarEstoque()) {
                getContasAReceberDAO().transactionBegin();
//                if (getContasAReceber() == null)
//                    setContasAReceber(new ContasAReceber());

                getContasAReceber().dtVencimentoProperty().setValue(getDtpDtVencimento().getValue());
                getContasAReceber().valorProperty().setValue(getSaidaProdutoProdutoObservableList().stream()
                        .map(SaidaProdutoProduto::getVlrLiquido)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
                getContasAReceber().setUsuarioCadastro(UsuarioLogado.getUsuario());
                getContasAReceber().setSaidaProduto(getSaidaProduto());

                setContasAReceber(getContasAReceberDAO().setTransactionPersist(getContasAReceber()));

                getSaidaProduto().setContasAReceber(getContasAReceber());

                getContasAReceberDAO().transactionCommit();

                if (getTpnNfe().isExpanded())
                    if (!guardarNfe())
                        throw new RuntimeException();
            }
        } catch (Exception ex) {
            getContasAReceberDAO().transactionRollback();
            getSaidaProdutoDAO().transactionRollback();
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean guardarNfe() {
        try {
            if (getSaidaProdutoNfe() == null) {
                setSaidaProdutoNfe(new SaidaProdutoNfe());
                getSaidaProdutoNfe().setSaidaProduto(getSaidaProduto());
                getSaidaProduto().getSaidaProdutoNfeList().add(getSaidaProdutoNfe());
            }
            setnFev400(new NFev400(TCONFIG.getNfe().getTpAmb()));

            getSaidaProdutoNfe().saidaProdutoProperty().setValue(getSaidaProduto());
            getSaidaProdutoNfe().setImpressaoFinNFe(getCboNfeImpressaoFinNFe().getValue());
            getSaidaProdutoNfe().setPagamentoIndicador(getCboNfeCobrancaPagamentoIndicador().getValue());
            getSaidaProdutoNfe().setPagamentoMeio(getCboNfeCobrancaPagamentoMeio().getValue());
            getSaidaProdutoNfe().setNaturezaOperacao(getCboNfeDadosNaturezaOperacao().getValue());
            getSaidaProdutoNfe().numeroProperty().setValue(Integer.parseInt(getTxtNfeDadosNumero().getText().replaceAll("\\D", "")));
            getSaidaProdutoNfe().serieProperty().setValue(Integer.parseInt(getTxtNfeDadosSerie().getText().replaceAll("\\D", "")));
            getSaidaProdutoNfe().setModelo(getCboNfeDadosModelo().getValue());
            LocalDateTime dtHoraEmissao, dtHoraSaida;
            LocalTime horaEmissao, horaSaida;
            horaEmissao = LocalTime.of(Integer.parseInt(getTxtNfeDadosHoraEmissao().getText().substring(0, 2)), Integer.parseInt(getTxtNfeDadosHoraEmissao().getText().substring(3)));
            horaSaida = LocalTime.of(Integer.parseInt(getTxtNfeDadosHoraSaida().getText().substring(0, 2)), Integer.parseInt(getTxtNfeDadosHoraSaida().getText().substring(3)));
            dtHoraEmissao = getDtpNfeDadosDtEmissao().getValue().atTime(horaEmissao);
            dtHoraSaida = getDtpNfeDadosDtSaida().getValue().atTime(horaSaida);
            getSaidaProdutoNfe().dtHoraEmissaoProperty().setValue(dtHoraEmissao);
            getSaidaProdutoNfe().dtHoraSaidaProperty().setValue(dtHoraSaida);
            getSaidaProdutoNfe().setDestinoOperacao(getCboNfeDadosDestinoOperacao().getValue());
            getSaidaProdutoNfe().setConsumidorFinal(getCboNfeDadosIndicadorConsumidorFinal().getValue());
            getSaidaProdutoNfe().setIndicadorPresenca(getCboNfeDadosIndicadorPresenca().getValue());
            getSaidaProdutoNfe().setModFrete(getCboNfeTransporteModFrete().getValue());
            getSaidaProdutoNfe().setImpressaoTpEmis(getCboNfeImpressaoTpEmis().getValue());
            getSaidaProdutoNfe().setImpressaoTpImp(getCboNfeImpressaoTpImp().getValue());
            if (getCboNfeTransporteTransportadora().getSelectionModel().getSelectedIndex() >= 0)
                getSaidaProdutoNfe().setTransportador(getCboNfeTransporteTransportadora().getValue());
            else
                getSaidaProdutoNfe().setTransportador(null);

            getSaidaProdutoNfe().informacaoAdicionalProperty().setValue(getTxaNfeInformacoesAdicionais().getText());

            getSaidaProdutoNfe().setCobrancaNumero("");

            getSaidaProdutoNfe().setChave(ServiceValidarDado.gerarChaveNfe(getSaidaProdutoNfe()));
            getSaidaProdutoNfe().setStatusSefaz(NfeStatusSefaz.DIGITACAO);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private void nfeAddCobranca() {
        Recebimento rec;
        if ((rec = getContasAReceber().getRecebimentoList().stream().sorted(Comparator.comparing(Recebimento::getId).reversed())
                .findFirst().orElse(null)) != null) {
            if (rec.documentoProperty().getValue().equals("")) {
                getContasAReceberDAO().transactionBegin();
                rec.documentoProperty().setValue(ServiceValidarDado.gerarCodigoCafePerfeito(
                        Recebimento.class,
                        getContasAReceber().dtCadastroProperty().getValue().toLocalDate()));
                try {
                    setContasAReceber(getContasAReceberDAO().setTransactionPersist(getContasAReceber()));
                    getContasAReceberDAO().transactionCommit();
                } catch (Exception ex) {
                    getContasAReceberDAO().transactionRollback();
                    ex.printStackTrace();
                }
            }
        } else {
            rec = new Recebimento();
            rec.documentoProperty().setValue(getSaidaProdutoNfe().numeroProperty().getValue().toString());
        }

        getSaidaProdutoNfe().cobrancaNumeroProperty().setValue(rec.documentoProperty().getValue());

    }

    private boolean gerarDanfe() {
        try {
            ServiceUtilJSon.printJsonFromObject(getSaidaProdutoNfe(), "saidaNFe001:");
            getSaidaProdutoNfeDAO().transactionBegin();
            nfeAddCobranca();
            try {
                setSaidaProdutoNfe(getSaidaProdutoNfeDAO().setTransactionPersist(getSaidaProdutoNfe()));
                ServiceUtilJSon.printJsonFromObject(getSaidaProdutoNfe(), "saidaNFe002:");
                getSaidaProdutoNfeDAO().transactionCommit();
            } catch (Exception ex) {
                getSaidaProdutoNfeDAO().transactionRollback();
                ex.printStackTrace();
                return false;
            }

            setAlertMensagem(null);
            getEnumsTasksList().clear();
            getEnumsTasksList().add(EnumsTasks.NFE_GERAR);
            getEnumsTasksList().add(EnumsTasks.NFE_ASSINAR);
            getEnumsTasksList().add(EnumsTasks.NFE_TRANSMITIR);
            getEnumsTasksList().add(EnumsTasks.NFE_RETORNO);
            if (new ServiceSegundoPlano().executaListaTarefas(newTaskSaidaProduto(), String.format("Gerando NFe [%d]!", (nfeLastNumberProperty().getValue() + 1)))) {
                setAlertMensagem(new ServiceAlertMensagem());
                getAlertMensagem().setCabecalho("NFe");
                getAlertMensagem().setContentText("Nota fiscal eletronica gerada com sucesso!!!");
                getAlertMensagem().alertOk();
                return true;
            } else {
                if (getAlertMensagem() != null)
                    getAlertMensagem().alertOk();
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void gerarXmlNFe() throws Exception {
        ServiceUtilJSon.printJsonFromObject(getSaidaProduto().getContasAReceber(), "NewNotaFiscal001:");

        setNewNotaFiscal(new NewNotaFiscal());
        getNewNotaFiscal().setSaidaProduto(getSaidaProduto());
        ServiceUtilJSon.printJsonFromObject(getNewNotaFiscal().getSaidaProduto().getContasAReceber(), "NewNotaFiscal003:");
        getNewNotaFiscal().gerarNovaNotaFiscal();
        nFev400Property().getValue().setEnviNfe_v400(new EnviNfe_v400(getNewNotaFiscal().getEnviNfeVO(), MY_ZONE_TIME));
        xmlNFeProperty().setValue(ServiceUtilXml.objectToXml(nFev400Property().getValue().getEnviNfe_v400().gettEnviNFe()));
        System.out.printf("xmlNFe:\n%s\n---------------------**********************\n\n", xmlNFeProperty().getValue());
    }

    private void assinarXmlNFe() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException, JAXBException, SQLException {
        if (loadCertificadoA3Property().getValue() == null)
            loadCertificadoA3Property().setValue(new LoadCertificadoA3());

        while (loadCertificadoA3Property().getValue().load())
            loadCertificadoA3Property().getValue().load();

        nFev400Property().getValue().setAssinarXml(new NFeAssinarXml(xmlNFeProperty().getValue(), loadCertificadoA3Property().getValue().getCertificates()));
        xmlNFeAssinadoProperty().setValue(nFev400Property().getValue().getAssinarXml().getXmlAssinadoNFe());
        getSaidaProdutoNfe().setXmlAssinatura(new SerialBlob(xmlNFeAssinadoProperty().getValue().getBytes()));
        try {
            getSaidaProdutoNfeDAO().transactionBegin();
            setSaidaProdutoNfe(getSaidaProdutoNfeDAO().setTransactionPersist(getSaidaProdutoNfe()));
            getSaidaProdutoNfeDAO().transactionCommit();
        } catch (Exception e) {
            getSaidaProdutoNfeDAO().transactionRollback();
            e.printStackTrace();
        }
        System.out.printf("xmlNFeAssinado:\n%s\n---------------------**********************\n\n", xmlNFeAssinadoProperty().getValue());
    }

    private boolean transmitirXmlNFe() throws XMLStreamException, RemoteException, JAXBException {
        nFev400Property().getValue().setAutorizacaoNFe(new NFeAutorizacao(xmlNFeAssinadoProperty().getValue()));
        xmlNFeAutorizacaoProperty().setValue(nFev400Property().getValue().getAutorizacaoNFe().getXmlAutorizacaoNFe());
        System.out.printf("xmlNFeAutorizacao:\n%s\n---------------------**********************\n\n", xmlNFeAutorizacaoProperty().getValue());
        return true;
    }

    private void retornoXmlNFe() throws JAXBException, InterruptedException, XMLStreamException, RemoteException {
        nFev400Property().getValue().setConsReciNFe(new NFeRetConsReciNfe(ServiceUtilXml.xmlToObject(xmlNFeAutorizacaoProperty().getValue(), TRetEnviNFe.class)));
        TConsReciNFe tConsReciNFe = nFev400Property().getValue().getConsReciNFe().gettConsReciNFe();

        nFev400Property().getValue().setRetAutorizacaoNFe(new NFeRetAutorizacao(ServiceUtilXml.objectToXml(tConsReciNFe)));
        xmlNFeRetAutorizacaoProperty().setValue(nFev400Property().getValue().getRetAutorizacaoNFe().getXmlRetAutorizacaoNFe());
        System.out.printf("xmlRetConsReciNfe:\n%s\n---------------------**********************\n\n", nFev400Property().getValue().getRetAutorizacaoNFe().getXmlRetAutorizacaoNFe());
        TRetConsReciNFe tRetConsReciNFe = ServiceUtilXml.xmlToObject(xmlNFeRetAutorizacaoProperty().getValue(), TRetConsReciNFe.class);
        br.inf.portalfiscal.xsd.nfe.retConsReciNFe.TProtNFe tProtNFe = tRetConsReciNFe.getProtNFe().get(0);

        if (tProtNFe.getInfProt().getCStat().equals("100")) {
            xmlNFeRetAutorizacaoProperty().setValue(nFev400Property().getValue().getRetAutorizacaoNFe().getXmlRetAutorizacaoNFe());
            System.out.printf("xmlNFeRetAutorizacao:\n%s\n---------------------**********************\n\n", xmlNFeRetAutorizacaoProperty().getValue());
        } else {
            setAlertMensagem(new ServiceAlertMensagem());
            getAlertMensagem().setCabecalho("Retorno inválido!!!");
            getAlertMensagem().setContentText(String.format("tpAmb: %s\ncStat: %s\nxMotivo: %s",
                    tProtNFe.getInfProt().getTpAmb(),
                    tProtNFe.getInfProt().getCStat(),
                    tProtNFe.getInfProt().getXMotivo()));
            Thread.sleep(200);
            throw new RuntimeException();
        }
    }

    private void retornoProcNFe() throws JAXBException, SQLException {
        nFev400Property().getValue().setProcNFe(new NFeProc(xmlNFeAssinadoProperty().getValue(), xmlNFeRetAutorizacaoProperty().getValue()));
        nFev400Property().getValue().getProcNFe().setStrVersao(nFev400Property().getValue().getConsReciNFe().gettConsReciNFe().getVersao());
        nFev400Property().getValue().getProcNFe().setTnFe(ServiceUtilXml.xmlToObject(nFev400Property().getValue().getProcNFe().getStringTNFe(), TNFe.class));
        nFev400Property().getValue().getProcNFe().settProtNFe(ServiceUtilXml.xmlToObject(nFev400Property().getValue().getProcNFe().getStringTProtNFe(), TProtNFe.class));

        xmlNFeProcProperty().setValue(ServiceUtilXml.objectToXml(nFev400Property().getValue().getProcNFe().getResultNFeProc()));
        getSaidaProdutoNfe().setXmlProtNfe(new SerialBlob(xmlNFeProcProperty().getValue().getBytes()));
        try {
            getSaidaProdutoNfeDAO().transactionBegin();
            setSaidaProdutoNfe(getSaidaProdutoNfeDAO().setTransactionPersist(getSaidaProdutoNfe()));
            getSaidaProdutoNfeDAO().transactionCommit();
        } catch (Exception e) {
            getSaidaProdutoNfeDAO().transactionRollback();
            e.printStackTrace();
        }
        System.out.printf("xmlNFeProc:\n%s\n---------------------**********************\n\n", xmlNFeProcProperty().getValue());
    }

    private String getNFeInfAdicionas() {
        String strInf = String.format(TCONFIG.getNfe().getInfAdic(),
                getLblTotalLiquido().getText(),
                (getDtpDtVencimento().getValue() != null)
                        ? String.format(" dt. Venc.: %s",
                        getDtpDtVencimento().getValue().format(Regex_Convert.DTF_DATA))
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
     * END voids
     */


    /**
     * Begin returns
     */

    private Recebimento addRecebimento(ContasAReceber aReceber, PagamentoModalidade modalidade, BigDecimal vlr) {
        Recebimento recebimento = new Recebimento();
        recebimento.setaReceber(aReceber);
        recebimento.setPagamentoSituacao(PagamentoSituacao.QUITADO);
        String codDocRecebimento =
                ServiceValidarDado.gerarCodigoCafePerfeito(Recebimento.class, getDtpDtSaida().getValue());
        recebimento.documentoProperty().setValue(String.format("UC%s", codDocRecebimento));
        recebimento.setPagamentoModalidade(modalidade);
        recebimento.valorProperty().setValue(vlr);
        if (modalidade.equals(PagamentoModalidade.CREDITO))
            recebimento.valorProperty().setValue(vlr.multiply(new BigDecimal("-1.0")));
        recebimento.setUsuarioPagamento(UsuarioLogado.getUsuario());
        recebimento.dtPagamentoProperty().setValue(LocalDate.now());
        recebimento.setUsuarioCadastro(UsuarioLogado.getUsuario());
        return recebimento;
    }

    private Produto getProdutoSelecionado() {
        Produto produtoEscolhido;
        if (getTtvProdutos().getSelectionModel().getSelectedItem().getValue().idProperty().getValue() != 0) {
            produtoEscolhido = new Produto(getTtvProdutos().getSelectionModel().getSelectedItem().getValue());
            produtoEscolhido.setTblEstoque(getTtvProdutos().getSelectionModel().getSelectedItem().getChildren().get(0).getValue().getTblEstoque());
            produtoEscolhido.setTblLote(getTtvProdutos().getSelectionModel().getSelectedItem().getChildren().get(0).getValue().getTblLote());
            produtoEscolhido.setTblValidade(getTtvProdutos().getSelectionModel().getSelectedItem().getChildren().get(0).getValue().getTblValidade());
        } else {
            produtoEscolhido = new Produto(getTtvProdutos().getSelectionModel().getSelectedItem().getParent().getValue());
            produtoEscolhido.setTblEstoque(getTtvProdutos().getSelectionModel().getSelectedItem().getValue().getTblEstoque());
            produtoEscolhido.setTblLote(getTtvProdutos().getSelectionModel().getSelectedItem().getValue().getTblLote());
            produtoEscolhido.setTblValidade(getTtvProdutos().getSelectionModel().getSelectedItem().getValue().getTblValidade());
        }
        return produtoEscolhido;
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

    public TmodelSaidaProduto getTmodelSaidaProduto() {
        return tmodelSaidaProduto;
    }

    public void setTmodelSaidaProduto(TmodelSaidaProduto tmodelSaidaProduto) {
        this.tmodelSaidaProduto = tmodelSaidaProduto;
    }

    public SaidaProdutoDAO getSaidaProdutoDAO() {
        return saidaProdutoDAO;
    }

    public void setSaidaProdutoDAO(SaidaProdutoDAO saidaProdutoDAO) {
        this.saidaProdutoDAO = saidaProdutoDAO;
    }

    public ContasAReceber getContasAReceber() {
        return contasAReceber;
    }

    public void setContasAReceber(ContasAReceber contasAReceber) {
        this.contasAReceber = contasAReceber;
    }

    public ContasAReceberDAO getContasAReceberDAO() {
        return contasAReceberDAO;
    }

    public void setContasAReceberDAO(ContasAReceberDAO contasAReceberDAO) {
        this.contasAReceberDAO = contasAReceberDAO;
    }

    public Recebimento getRecebimento() {
        return recebimento;
    }

    public void setRecebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
    }

    public RecebimentoDAO getRecebimentoDAO() {
        return recebimentoDAO;
    }

    public void setRecebimentoDAO(RecebimentoDAO recebimentoDAO) {
        this.recebimentoDAO = recebimentoDAO;
    }

    public ObservableList<ContasAReceber> getContasAReceberObservableList() {
        return contasAReceberObservableList;
    }

    public void setContasAReceberObservableList(ObservableList<ContasAReceber> contasAReceberObservableList) {
        this.contasAReceberObservableList = contasAReceberObservableList;
    }

    public ObservableList<SaidaProdutoProduto> getSaidaProdutoProdutoObservableList() {
        return saidaProdutoProdutoObservableList;
    }

    public void setSaidaProdutoProdutoObservableList(ObservableList<SaidaProdutoProduto> saidaProdutoProdutoObservableList) {
        this.saidaProdutoProdutoObservableList = saidaProdutoProdutoObservableList;
    }

    public NewNotaFiscal getNewNotaFiscal() {
        return newNotaFiscal;
    }

    public void setNewNotaFiscal(NewNotaFiscal newNotaFiscal) {
        this.newNotaFiscal = newNotaFiscal;
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

    public SaidaProdutoNfe getSaidaProdutoNfe() {
        return saidaProdutoNfe;
    }

    public void setSaidaProdutoNfe(SaidaProdutoNfe saidaProdutoNfe) {
        this.saidaProdutoNfe = saidaProdutoNfe;
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

    public String getInformacaoNFE() {
        return informacaoNFE.get();
    }

    public StringProperty informacaoNFEProperty() {
        return informacaoNFE;
    }

    public void setInformacaoNFE(String informacaoNFE) {
        this.informacaoNFE.set(informacaoNFE);
    }

    public BigDecimal getValorParcela() {
        return valorParcela.get();
    }

    public ObjectProperty<BigDecimal> valorParcelaProperty() {
        return valorParcela;
    }

    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela.set(valorParcela);
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

    public SaidaProduto getSaidaProduto() {
        return saidaProduto;
    }

    public void setSaidaProduto(SaidaProduto saidaProduto) {
        this.saidaProduto = saidaProduto;
    }

    public SaidaProdutoNfeDAO getSaidaProdutoNfeDAO() {
        return saidaProdutoNfeDAO;
    }

    public void setSaidaProdutoNfeDAO(SaidaProdutoNfeDAO saidaProdutoNfeDAO) {
        this.saidaProdutoNfeDAO = saidaProdutoNfeDAO;
    }

    /**
     * END Getters e Setters
     */

}
